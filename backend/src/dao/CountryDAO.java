package dao;
import bean.Country;
import java.util.List;

public interface CountryDAO
{
    Country getCountry(String countryCode);
    List<Country> getCountriesByContinentCode(String continentCode);
}