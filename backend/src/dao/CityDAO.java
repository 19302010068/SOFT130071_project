package dao;
import bean.City;
import java.util.List;

public interface CityDAO
{
    City getCity(Integer cityID);
    List<List<City>> getCitiesByCityName(String cityName, String countryCode);
}