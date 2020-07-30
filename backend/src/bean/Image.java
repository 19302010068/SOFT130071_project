package bean;
import java.util.Date;

public class Image
{
    private Integer imageID;
    private String path;
    private Date uploaded;
    private String title;
    private String description;
    private String content1;
    private String content2;
    private String content3;
    private String content4;
    private Integer cityID;
    private Integer uID;
    private Integer visibilityID;

    public Integer getImageID()
    {
        return imageID;
    }

    public void setImageID(Integer imageID)
    {
        this.imageID = imageID;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Date getUploaded()
    {
        return uploaded;
    }

    public void setUploaded(Date uploaded)
    {
        this.uploaded = uploaded;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getContent1()
    {
        return content1;
    }

    public void setContent1(String content1)
    {
        this.content1 = content1;
    }

    public String getContent2()
    {
        return content2;
    }

    public void setContent2(String content2)
    {
        this.content2 = content2;
    }

    public String getContent3()
    {
        return content3;
    }

    public void setContent3(String content3)
    {
        this.content3 = content3;
    }

    public String getContent4()
    {
        return content4;
    }

    public void setContent4(String content4)
    {
        this.content4 = content4;
    }

    public Integer getCityID()
    {
        return cityID;
    }

    public void setCityID(Integer cityID)
    {
        this.cityID = cityID;
    }

    public Integer getUID()
    {
        return uID;
    }

    public void setUID(Integer uID)
    {
        this.uID = uID;
    }

    public Integer getVisibilityID()
    {
        return visibilityID;
    }

    public void setVisibilityID(Integer visibilityID)
    {
        this.visibilityID = visibilityID;
    }
}