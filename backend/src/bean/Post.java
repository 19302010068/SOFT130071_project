package bean;
import java.util.Date;
import java.util.Objects;

public class Post
{
    private Integer postID;
    private Integer imageID;
    private Integer uID;
    private String message;
    private Date posted;

    public Integer getPostID()
    {
        return postID;
    }

    public void setPostID(Integer postID)
    {
        this.postID = postID;
    }

    public Integer getImageID()
    {
        return imageID;
    }

    public void setImageID(Integer imageID)
    {
        this.imageID = imageID;
    }

    public Integer getUID()
    {
        return uID;
    }

    public void setUID(Integer uID)
    {
        this.uID = uID;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Date getPosted()
    {
        return posted;
    }

    public void setPosted(Date posted)
    {
        this.posted = posted;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Post post = (Post)o;
        return Objects.equals(postID, post.postID) && Objects.equals(imageID, post.imageID) && Objects.equals(uID, post.uID) && Objects.equals(message, post.message) && Objects.equals(posted, post.posted);
    }
}