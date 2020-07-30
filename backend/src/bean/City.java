package bean;
import java.util.Objects;

public class City
{
    private Integer cityID;
    private String cityName;
    private String countryCode;

    public Integer getCityID()
    {
        return cityID;
    }

    public void setCityID(Integer cityID)
    {
        this.cityID = cityID;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        City city = (City) o;
        return Objects.equals(cityID, city.cityID) && Objects.equals(cityName, city.cityName) && Objects.equals(countryCode, city.countryCode);
    }
}