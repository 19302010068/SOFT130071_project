package dao.jdbc;
import bean.Footprint;
import util.JDBC;
import java.util.*;

public class FootprintDAO implements dao.FootprintDAO
{
    private static FootprintDAO footprintDAO = new FootprintDAO();

    private FootprintDAO() {}

    public static FootprintDAO getInstance()
    {
        return footprintDAO;
    }

    @Override
    public void addFootprint(Footprint footprint)
    {
        if (JDBC.update("INSERT INTO footprints (UID, ImageID) VALUES (?, ?)", footprint.getUID(), footprint.getImageID()) != 1)
            throw new RuntimeException();
    }

    @Override
    public void removeFootprint(Footprint footprint)
    {
        JDBC.update("DELETE FROM footprints WHERE UID = ? AND ImageID = ?", footprint.getUID(), footprint.getImageID());
    }

    @Override
    public List<Integer> getImageIDByUID(Integer uID)
    {
        return JDBC.queryIntegers("SELECT ImageID FROM footprints WHERE UID = ?", uID);
    }

    @Override
    public void removeFootprintsByUID(Integer uID)
    {
        JDBC.update("DELETE FROM footprints WHERE UID = ?", uID);
    }

    @Override
    public void removeFirstByUID(Integer uID)
    {
        if (JDBC.update("DELETE FROM footprints WHERE UID = ? LIMIT 1", uID) != 1)
            throw new RuntimeException();
    }

    @Override
    public long count(Integer uID)
    {
        return (long)JDBC.queryScalar("SELECT COUNT(*) FROM footprints WHERE UID = ?", uID);
    }
}