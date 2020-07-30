package bean;
import java.util.Date;

public class User
{
    private Integer uID;
    private String username;
    private String email;
    private String pass;
    private Date joined;
    private Date lastModified;

    public Integer getUID()
    {
        return uID;
    }

    public void setUID(Integer uID)
    {
        this.uID = uID;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = pass;
    }

    public Date getJoined()
    {
        return joined;
    }

    public void setJoined(Date joined)
    {
        this.joined = joined;
    }

    public Date getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
    }
}