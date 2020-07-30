package dao;
import util.Reflect;
import java.io.*;
import java.util.Properties;

public class DAOManager
{
    private static ChatDAO chatDAO;
    private static CityDAO cityDAO;
    private static ContinentDAO continentDAO;
    private static CountryDAO countryDAO;
    private static FavouriteDAO favouriteDAO;
    private static FootprintDAO footprintDAO;
    private static FriendsDAO friendsDAO;
    private static HideFavouritesDAO hideFavouritesDAO;
    private static ImageDAO imageDAO;
    private static KudosDAO kudosDAO;
    private static PostDAO postDAO;
    private static UserDAO userDAO;

    static
    {
        try
        {
            Properties properties = new Properties();
            InputStream inputStream = DAOManager.class.getClassLoader().getResourceAsStream("dao/DAOManager.properties");
            if (inputStream != null)
            {
                properties.load(inputStream);
                use(properties.getProperty("use"));
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void use(String pkg)
    {
        setChatDAO(pkg + ".ChatDAO");
        setCityDAO(pkg + ".CityDAO");
        setContinentDAO(pkg + ".ContinentDAO");
        setCountryDAO(pkg + ".CountryDAO");
        setFavouriteDAO(pkg + ".FavouriteDAO");
        setFootprintDAO(pkg + ".FootprintDAO");
        setFriendsDAO(pkg + ".FriendsDAO");
        setHideFavouritesDAO(pkg + ".HideFavouritesDAO");
        setImageDAO(pkg + ".ImageDAO");
        setKudosDAO(pkg + ".KudosDAO");
        setPostDAO(pkg + ".PostDAO");
        setUserDAO(pkg + ".UserDAO");
    }

    public static ChatDAO getChatDAO()
    {
        return chatDAO;
    }

    public static void setChatDAO(String className)
    {
        chatDAO = (ChatDAO)Reflect.getClassInstance(className);
    }

    public static CityDAO getCityDAO()
    {
        return cityDAO;
    }

    public static void setCityDAO(String className)
    {
        cityDAO = (CityDAO)Reflect.getClassInstance(className);
    }

    public static ContinentDAO getContinentDAO()
    {
        return continentDAO;
    }

    public static void setContinentDAO(String className)
    {
        continentDAO = (ContinentDAO)Reflect.getClassInstance(className);
    }

    public static CountryDAO getCountryDAO()
    {
        return countryDAO;
    }

    public static void setCountryDAO(String className)
    {
        countryDAO = (CountryDAO)Reflect.getClassInstance(className);
    }

    public static FavouriteDAO getFavouriteDAO()
    {
        return favouriteDAO;
    }

    public static void setFavouriteDAO(String className)
    {
        favouriteDAO = (FavouriteDAO)Reflect.getClassInstance(className);
    }

    public static FootprintDAO getFootprintDAO()
    {
        return footprintDAO;
    }

    public static void setFootprintDAO(String className)
    {
        footprintDAO = (FootprintDAO)Reflect.getClassInstance(className);
    }

    public static FriendsDAO getFriendsDAO()
    {
        return friendsDAO;
    }

    public static void setFriendsDAO(String className)
    {
        friendsDAO = (FriendsDAO)Reflect.getClassInstance(className);
    }

    public static HideFavouritesDAO getHideFavouritesDAO()
    {
        return hideFavouritesDAO;
    }

    public static void setHideFavouritesDAO(String className)
    {
        hideFavouritesDAO = (HideFavouritesDAO)Reflect.getClassInstance(className);
    }

    public static ImageDAO getImageDAO()
    {
        return imageDAO;
    }

    public static void setImageDAO(String className)
    {
        imageDAO = (ImageDAO)Reflect.getClassInstance(className);
    }

    public static KudosDAO getKudosDAO()
    {
        return kudosDAO;
    }

    public static void setKudosDAO(String className)
    {
        kudosDAO = (KudosDAO)Reflect.getClassInstance(className);
    }

    public static PostDAO getPostDAO()
    {
        return postDAO;
    }

    public static void setPostDAO(String className)
    {
        postDAO = (PostDAO)Reflect.getClassInstance(className);
    }

    public static UserDAO getUserDAO()
    {
        return userDAO;
    }

    public static void setUserDAO(String className)
    {
        userDAO = (UserDAO)Reflect.getClassInstance(className);
    }
}