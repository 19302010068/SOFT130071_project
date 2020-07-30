package dao.jdbc;
import util.JDBC;

public class HideFavouritesDAO implements dao.HideFavouritesDAO
{
    private static HideFavouritesDAO hideFavouritesDAO = new HideFavouritesDAO();

    private HideFavouritesDAO() {}

    public static HideFavouritesDAO getInstance()
    {
        return hideFavouritesDAO;
    }

    @Override
    public void add(Integer uID)
    {
        if (JDBC.update("INSERT INTO hide_favourites (UID) VALUES (?)", uID) != 1)
            throw new RuntimeException();
    }

    @Override
    public void remove(Integer uID)
    {
        JDBC.update("DELETE FROM hide_favourites WHERE UID = ?", uID);
    }

    @Override
    public boolean exists(Integer uID)
    {
        return (long)JDBC.queryScalar("SELECT COUNT(*) FROM hide_favourites WHERE UID = ?", uID) != 0;
    }
}