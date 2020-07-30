package dao;
import bean.Image;
import java.util.List;

public interface ImageDAO
{
    Image getImage(Integer imageID);
    void addImage(Image image);
    void removeImage(Integer imageID);
    Image getImageOverview(Integer imageID);
    Image getTitle(Integer imageID);
    Image getVisibility(Integer imageID);
    List<Image> getFeaturedImages();
    List<Image> getLatestImages();
    List<Integer> getImageIDByTitleStars(String title);
    List<Integer> getImageIDByTitleTime(String title);
    List<Integer> getImageIDByContentStars(String content);
    List<Integer> getImageIDByContentTime(String content);
    List<Integer> getImageIDByUID(Integer uID);
    List<Integer> getImageIDByContent(String content);
    List<Integer> getImageIDByCityID(Integer cityID);
    List<Integer> getImageIDByCountryCode(String countryCode);
}