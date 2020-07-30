package servlet.account;

public class Util
{
    static Boolean toBoolean(String param)
    {
        switch (param)
        {
            case "true":
                return true;
            case "false":
                return false;
            default:
                return null;
        }
    }
}