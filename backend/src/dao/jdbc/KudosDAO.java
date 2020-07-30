package dao.jdbc;
import bean.Kudos;
import util.JDBC;
import java.util.*;

public class KudosDAO implements dao.KudosDAO
{
    private static KudosDAO kudosDAO = new KudosDAO();

    private KudosDAO() {}

    public static KudosDAO getInstance()
    {
        return kudosDAO;
    }

    @Override
    public void addKudos(Kudos kudos)
    {
        if (JDBC.update("INSERT INTO kudos (UID, PostID) VALUES (?, ?)", kudos.getUID(), kudos.getPostID()) != 1)
            throw new RuntimeException();
    }

    @Override
    public long count(Integer postID)
    {
        return (long)JDBC.queryScalar("SELECT COUNT(*) FROM kudos WHERE PostID = ?", postID);
    }

    @Override
    public void toggle(Kudos kudos)
    {
        if (JDBC.update("DELETE FROM kudos WHERE UID = ? AND PostID = ?", kudos.getUID(), kudos.getPostID()) == 0)
            addKudos(kudos);
    }

    @Override
    public boolean exists(Kudos kudos)
    {
        return (long)JDBC.queryScalar("SELECT COUNT(*) FROM kudos WHERE UID = ? AND PostID = ?", kudos.getUID(), kudos.getPostID()) != 0;
    }
}