package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 31/05/2017.
 */


import com.google.gson.annotations.SerializedName;

public class StepsComponents
{
    @SerializedName("id")
    private int id_;
    @SerializedName("shortDescription")
    private String shortDescription_;
    @SerializedName("description")
    private String description_;
    @SerializedName("videoURL")
    private String videoURL_;
    @SerializedName("thumbnailURL")
    private String thumbNailURL_;

    public StepsComponents(int id, String shortDescription, String description, String videoURL, String thumbNailURL)
    {
        id_ = id;
        shortDescription_   = shortDescription;
        description_        = description;
        videoURL_           = videoURL;
        thumbNailURL_       = thumbNailURL;
    }

    public int getId_()
    {
        return id_;
    }

    public String getShortDescription_()
    {
        return shortDescription_;
    }

    public String getDescription_()
    {
        return description_;
    }

    public String getVideoURL_()
    {
        return videoURL_;
    }

    public String getThumbNailURL_()
    {
        return thumbNailURL_;
    }
}
