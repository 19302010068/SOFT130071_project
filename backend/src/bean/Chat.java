package bean;
import java.util.Date;

public class Chat
{
    private Integer uID1;
    private Integer uID2;
    private String message;
    private Date sent;
    private Boolean checked;

    public Integer getUID1()
    {
        return uID1;
    }

    public void setUID1(Integer uID1)
    {
        this.uID1 = uID1;
    }

    public Integer getUID2()
    {
        return uID2;
    }

    public void setUID2(Integer uID2)
    {
        this.uID2 = uID2;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Date getSent()
    {
        return sent;
    }

    public void setSent(Date sent)
    {
        this.sent = sent;
    }

    public Boolean getChecked()
    {
        return checked;
    }

    public void setChecked(Boolean checked)
    {
        this.checked = checked;
    }
}