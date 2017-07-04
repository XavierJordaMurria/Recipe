package xavier.jorda.cat.recipe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xj1 on 03/07/2017.
 */
public final class StepModel implements Parcelable
{
    public final String label_;
    public final String url_;

    public StepModel(String label, String url)
    {
        label_  = label;
        url_    = url;
    }

    protected StepModel(Parcel in) {
        label_ = in.readString();
        url_ = in.readString();
    }

    public static final Creator<StepModel> CREATOR = new Creator<StepModel>() {
        @Override
        public StepModel createFromParcel(Parcel in) {
            return new StepModel(in);
        }

        @Override
        public StepModel[] newArray(int size) {
            return new StepModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label_);
        dest.writeString(url_);
    }
}