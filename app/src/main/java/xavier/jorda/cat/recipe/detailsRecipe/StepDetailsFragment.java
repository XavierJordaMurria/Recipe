package xavier.jorda.cat.recipe.detailsRecipe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import xavier.jorda.cat.recipe.MyApplication;
import xavier.jorda.cat.recipe.R;
import xavier.jorda.cat.recipe.model.StepsComponents;
import xavier.jorda.cat.recipe.util.Constants;

/**
 * Created by xj1 on 24/06/2017.
 */

public class StepDetailsFragment extends DetailsFragment implements ExoPlayer.EventListener
{
    private final static String TAG = StepDetailsFragment.class.getSimpleName();

    private int recipeCardPosition_;
    private int stepNumber_;
    private StepsComponents stepsComponents_;

    private static MediaSessionCompat mediaSession_;
    private PlaybackStateCompat.Builder stateBuilder_;

    private MyApplication myApp;
    private TextView stepNum_, stepInfo_, stepDescription_;

    private SimpleExoPlayer exoPlayer_;
    private SimpleExoPlayerView playerView_;

    private StepsFragment.OnItemSelectedListener listener_;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        myApp = (MyApplication)getActivity().getApplication();

        if(savedInstanceState == null)
        {
            // Get back arguments
            if(getArguments() == null)
                return;

            recipeCardPosition_ = getArguments().getInt(Constants.RECIPE_CARD_POSITION, 0);
            stepNumber_ = getArguments().getInt(Constants.STEP_NUMBER, 0);

            stepsComponents_ = myApp.recipes.get(recipeCardPosition_).getSteps_().get(stepNumber_);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.step_details, container, false);

        stepNum_ = (TextView)view.findViewById(R.id.stepNum_StepDetails);
        stepNum_.setText(getString(R.string.stepNum, stepsComponents_.getId_()));

        stepInfo_ = (TextView)view.findViewById(R.id.stepInfo_StepDetails);
        stepInfo_.setText(stepsComponents_.getShortDescription_());

        stepDescription_ = (TextView)view.findViewById(R.id.description_StepDetails);
        stepDescription_.setText(stepsComponents_.getDescription_());

        playerView_ = (SimpleExoPlayerView)view.findViewById(R.id.playerView);

        // Load the question mark as the background image until the user answers the question.
        playerView_.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));

        Button prev_,next_;
        prev_ = (Button)view.findViewById(R.id.prevBtn_StepDetails);
        prev_.setOnClickListener(v -> goPrev());

        next_ = (Button)view.findViewById(R.id.nextBtn_StepDetails);
        next_.setOnClickListener(v -> goNext());

        initializeMediaSession();

        // Initialize the player.
        initializePlayer(Uri.parse(stepsComponents_.getVideoURL_()));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    //--OnItemSelectedListener listener;
    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof StepsFragment.OnItemSelectedListener)  // context instanceof YourActivity
            this.listener_ = (StepsFragment.OnItemSelectedListener) context; // = (YourActivity) context
        else
            throw new ClassCastException(context.toString()
                    + " must implement StepsFragment.OnItemSelectedListener");
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        releasePlayer();
        mediaSession_.setActive(false);
    }


    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession()
    {

        // Create a MediaSessionCompat.
        mediaSession_ = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mediaSession_.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mediaSession_.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        stateBuilder_ = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession_.setPlaybackState(stateBuilder_.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mediaSession_.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mediaSession_.setActive(true);
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri)
    {
        if (exoPlayer_ != null)
            return;

        // Create an instance of the ExoPlayer.
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer_ = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        playerView_.setPlayer(exoPlayer_);

        // Set the ExoPlayer.EventListener to this activity.
        exoPlayer_.addListener(this);

        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");

        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        exoPlayer_.prepare(mediaSource);
        exoPlayer_.setPlayWhenReady(true);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer()
    {
        exoPlayer_.stop();
        exoPlayer_.release();
        exoPlayer_ = null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object o)
    {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray)
    {}

    @Override
    public void onLoadingChanged(boolean b)
    {}

    @Override
    public void onPlayerStateChanged(boolean b, int i)
    {}

    @Override
    public void onPlayerError(ExoPlaybackException e)
    {}

    @Override
    public void onPositionDiscontinuity()
    {}

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback
    {
        @Override
        public void onPlay()
        {
            exoPlayer_.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer_.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer_.seekTo(0);
        }
    }

    /**
     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
     */
    public static class MediaReceiver extends BroadcastReceiver
    {

        public MediaReceiver()
        {}

        @Override
        public void onReceive(Context context, Intent intent)
        {
            MediaButtonReceiver.handleIntent(mediaSession_, intent);
        }
    }

    private void goPrev()
    {
        releasePlayer();
        listener_.onStepItemSelected(--stepNumber_);
    }

    private void goNext()
    {
        releasePlayer();
        listener_.onStepItemSelected(++stepNumber_);
    }
}
