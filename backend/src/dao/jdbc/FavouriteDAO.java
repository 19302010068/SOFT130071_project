package dao.jdbc;
import bean.Favourite;
import util.JDBC;
import java.util.*;

public class FavouriteDAO implements dao.FavouriteDAO
{
    private static FavouriteDAO favouriteDAO = new FavouriteDAO();

    private FavouriteDAO() {}

    public static FavouriteDAO getInstance()
    {
        return favouriteDAO;
    }

    @Override
    public void addFavourite(Favourite favourite)
    {
        if (JDBC.update("INSERT INTO favourites (UID, ImageID) VALUES (?, ?)", favourite.getUID(), favourite.getImageID()) != 1)
            throw new RuntimeException();
    }

    @Override
    public List<Integer> getUIDByImageID(Integer imageID)
    {
        return JDBC.queryIntegers("SELECT UID FROM favourites WHERE ImageID = ?", imageID);
    }

    @Override
    public List<Integer> getImageIDByUID(Integer uID)
    {
        return JDBC.queryIntegers("SELECT ImageID FROM favourites WHERE UID = ?", uID);
    }

    @Override
    public long count(Integer imageID)
    {
        return (long)JDBC.queryScalar("SELECT COUNT(*) FROM favourites WHERE ImageID = ?", imageID);
    }

    @Override
    public void toggle(Favourite favourite)
    {
        if (JDBC.update("DELETE FROM favourites WHERE UID = ? AND ImageID = ?", favourite.getUID(), favourite.getImageID()) == 0)
            addFavourite(favourite);
    }

    @Override
    public boolean exists(Favourite favourite)
    {
        return (long)JDBC.queryScalar("SELECT COUNT(*) FROM favourites WHERE UID = ? AND ImageID = ?", favourite.getUID(), favourite.getImageID()) != 0;
    }
}