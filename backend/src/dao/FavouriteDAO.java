package dao;
import bean.Favourite;
import java.util.List;

public interface FavouriteDAO
{
    void addFavourite(Favourite favourite);
    List<Integer> getUIDByImageID(Integer imageID);
    List<Integer> getImageIDByUID(Integer uID);
    long count(Integer imageID);
    void toggle(Favourite favourite);
    boolean exists(Favourite favourite);
}