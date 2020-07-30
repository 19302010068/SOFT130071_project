package servlet.friend;

public class Util
{
    static Integer toInt(String param)
    {
        try
        {
            return Integer.parseInt(param);
        }
        catch (NumberFormatException ex)
        {
            return null;
        }
    }
}