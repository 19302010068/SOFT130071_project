package dao.jdbc;
import bean.Country;
import util.JDBC;
import java.util.*;

public class CountryDAO implements dao.CountryDAO
{
    private static CountryDAO countryDAO = new CountryDAO();

    private CountryDAO() {}

    public static CountryDAO getInstance()
    {
        return countryDAO;
    }

    @Override
    public Country getCountry(String countryCode)
    {
        return cast(JDBC.queryObjects(Country.class, "SELECT * FROM countries WHERE CountryCode = ?", countryCode));
    }

    @Override
    public List<Country> getCountriesByContinentCode(String continentCode) 
    {
        return toCountryList(JDBC.queryObjects(Country.class, "SELECT CountryCode, CountryName FROM countries WHERE ContinentCode = ?", continentCode));
    }
    
    private List<Country> toCountryList(List<Object> list)
    {
        List<Country> countryList = new ArrayList<>(list.size());
        for (Object e: list)
            countryList.add((Country)e);
        return countryList;
    }
    
    private Country cast(List<Object> list)
    {
        return (list.size() == 0) ? null : (Country)list.get(0);
    }
}