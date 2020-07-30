package dao.jdbc;
import bean.City;
import util.JDBC;
import java.util.*;

public class CityDAO implements dao.CityDAO
{
    private static CityDAO cityDAO = new CityDAO();

    private CityDAO() {}

    public static CityDAO getInstance()
    {
        return cityDAO;
    }

    @Override
    public City getCity(Integer cityID)
    {
        return cast(JDBC.queryObjects(City.class, "SELECT * FROM cities WHERE CityID = ?", cityID));
    }

    @Override
    public List<List<City>> getCitiesByCityName(String cityName, String countryCode)
    {
        final int LIMIT = 100;
        String escapedCityName = escape(cityName);
        List<List<City>> list = new ArrayList<>();
        List<City> list1 = toCityList(JDBC.queryObjects(City.class, "SELECT CityID, CityName FROM cities WHERE CityName = ? AND CountryCode = ?", cityName, countryCode));
        list.add(list1);
        List<City> list2 = toCityList(JDBC.queryObjects(City.class, "SELECT CityID, CityName FROM cities WHERE CityName LIKE ? AND CountryCode = ? LIMIT ?", escapedCityName + "%", countryCode, LIMIT + 1));
        if (list2.size() <= LIMIT)
        {
            list2.removeAll(list1);
            list.add(list2);
            List<City> list3 = toCityList(JDBC.queryObjects(City.class, "SELECT CityID, CityName FROM cities WHERE CityName LIKE ? AND CountryCode = ? LIMIT ?", "%" + escapedCityName + "%", countryCode, LIMIT + 1));
            if (list3.size() <= LIMIT)
            {
                list3.removeAll(list1);
                list3.removeAll(list2);
                list.add(list3);
            }
        }
        return list;
    }

    private List<City> toCityList(List<Object> list)
    {
        List<City> cityList = new ArrayList<>(list.size());
        for (Object e: list)
            cityList.add((City)e);
        return cityList;
    }

    private City cast(List<Object> list)
    {
        return (list.size() == 0) ? null : (City)list.get(0);
    }

    private String escape(String s)
    {
        return s.replace("\\", "\\\\\\").replace("%", "\\\\%");
    }
}