package bean;

public class Country
{
    private String countryCode;
    private String countryName;
    private String continentCode;

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getContinentCode()
    {
        return continentCode;
    }

    public void setContinentCode(String continentCode)
    {
        this.continentCode = continentCode;
    }
}