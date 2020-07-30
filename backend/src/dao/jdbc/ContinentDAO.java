package dao.jdbc;
import bean.Continent;
import util.JDBC;
import java.util.*;

public class ContinentDAO implements dao.ContinentDAO
{
    private static ContinentDAO continentDAO = new ContinentDAO();

    public static ContinentDAO getInstance()
    {
        return continentDAO;
    }

    @Override
    public List<Continent> dump() 
    {
        return toContinentList(JDBC.queryObjects(Continent.class, "SELECT * FROM continents"));
    }
    
    private List<Continent> toContinentList(List<Object> list)
    {
        List<Continent> continentList = new ArrayList<>(list.size());
        for (Object e: list)
            continentList.add((Continent)e);
        return continentList;
    }
}