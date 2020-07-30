package dao;
import bean.Footprint;
import java.util.List;

public interface FootprintDAO
{
    void addFootprint(Footprint footprint);
    void removeFootprint(Footprint footprint);
    List<Integer> getImageIDByUID(Integer uID);
    void removeFootprintsByUID(Integer uID);
    void removeFirstByUID(Integer uID);
    long count(Integer uID);
}