package dao.jdbc;
import bean.Image;
import util.JDBC;
import java.math.BigInteger;
import java.util.*;

public class ImageDAO implements dao.ImageDAO
{
    private static ImageDAO imageDAO = new ImageDAO();

    private ImageDAO() {}

    public static ImageDAO getInstance()
    {
        return imageDAO;
    }

    @Override
    public Image getImage(Integer imageID)
    {
        return cast(JDBC.queryObjects(Image.class, "SELECT * FROM images WHERE ImageID = ?", imageID));
    }

    @Override
    public void addImage(Image image)
    {
        BigInteger imageID = (BigInteger)JDBC.insert("INSERT INTO images (Path, Title, Description, Content1, Content2, Content3, Content4, CityID, UID, VisibilityID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",image.getPath(), image.getTitle(), image.getDescription(), image.getContent1(), image.getContent2(), image.getContent3(), image.getContent4(), image.getCityID(), image.getUID(), image.getVisibilityID());
        image.setImageID(imageID.intValueExact());
    }

    @Override
    public void removeImage(Integer imageID)
    {
        if (JDBC.update("DELETE FROM images WHERE ImageID = ?", imageID) != 1)
            throw new RuntimeException();
    }

    @Override
    public Image getImageOverview(Integer imageID)
    {
        return cast(JDBC.queryObjects(Image.class, "SELECT ImageID, Path, Title, Description, UID, VisibilityID FROM images WHERE ImageID = ?", imageID));
    }

    @Override
    public Image getTitle(Integer imageID)
    {
        return cast(JDBC.queryObjects(Image.class, "SELECT ImageID, Title FROM images WHERE ImageID = ?", imageID));
    }

    @Override
    public Image getVisibility(Integer imageID)
    {
        return cast(JDBC.queryObjects(Image.class, "SELECT ImageID, UID, VisibilityID FROM images WHERE ImageID = ?", imageID));
    }

    @Override
    public List<Image> getFeaturedImages()
    {
        return toImageList(JDBC.queryObjects(Image.class, "SELECT images.ImageID, Path FROM images INNER JOIN favourites ON images.ImageID = favourites.ImageID WHERE VisibilityID = 1 GROUP BY favourites.ImageID ORDER BY COUNT(*) DESC LIMIT 5"));
    }

    @Override
    public List<Image> getLatestImages()
    {
        return toImageList(JDBC.queryObjects(Image.class, "SELECT ImageID, Path, Uploaded, Content1, Content2, Content3, Content4, UID FROM images WHERE VisibilityID = 1 ORDER BY Uploaded DESC LIMIT 3"));
    }

    @Override
    public List<Integer> getImageIDByTitleStars(String title)
    {
        String like = like(title);
        List<Integer> list1 = JDBC.queryIntegers("SELECT images.ImageID FROM images INNER JOIN favourites ON images.ImageID = favourites.ImageID WHERE Title LIKE ? GROUP BY favourites.ImageID ORDER BY COUNT(*) DESC", like);
        List<Integer> list2 = JDBC.queryIntegers("SELECT ImageID FROM images WHERE Title LIKE ?", like);
        list2.removeAll(list1);
        list1.addAll(list2);
        return list1;
    }

    @Override
    public List<Integer> getImageIDByTitleTime(String title)
    {
        return JDBC.queryIntegers("SELECT ImageID FROM images WHERE Title LIKE ? ORDER BY Uploaded DESC", like(title));
    }

    @Override
    public List<Integer> getImageIDByContentStars(String content)
    {
        String like = like(content);
        List<Integer> list1 = JDBC.queryIntegers("SELECT images.ImageID FROM images INNER JOIN favourites ON images.ImageID = favourites.ImageID WHERE Content1 LIKE ? OR Content2 LIKE ? OR Content3 LIKE ? OR Content4 LIKE ? GROUP BY favourites.ImageID ORDER BY COUNT(*) DESC", like, like, like, like);
        List<Integer> list2 = JDBC.queryIntegers("SELECT ImageID FROM images WHERE Content1 LIKE ? OR Content2 LIKE ? OR Content3 LIKE ? OR Content4 LIKE ?", like, like, like, like);
        list2.removeAll(list1);
        list1.addAll(list2);
        return list1;
    }

    @Override
    public List<Integer> getImageIDByContentTime(String content)
    {
        String like = like(content);
        return JDBC.queryIntegers("SELECT ImageID FROM images WHERE Content1 LIKE ? OR Content2 LIKE ? OR Content3 LIKE ? OR Content4 LIKE ? ORDER BY Uploaded DESC", like, like, like, like);
    }

    @Override
    public List<Integer> getImageIDByUID(Integer uID)
    {
        return JDBC.queryIntegers("SELECT ImageID FROM images WHERE UID = ?", uID);
    }

    @Override
    public List<Integer> getImageIDByContent(String content)
    {
        return JDBC.queryIntegers("SELECT ImageID FROM images WHERE Content1 = ? OR Content2 = ? OR Content3 = ? OR Content4 = ?", content, content, content, content);
    }

    @Override
    public List<Integer> getImageIDByCityID(Integer cityID)
    {
        return JDBC.queryIntegers("SELECT ImageID FROM images WHERE CityID = ?", cityID);
    }

    @Override
    public List<Integer> getImageIDByCountryCode(String countryCode)
    {
        return JDBC.queryIntegers("SELECT ImageID FROM images INNER JOIN cities ON images.CityID = cities.CityID WHERE CountryCode = ?", countryCode);
    }

    private String like(String s)
    {
        return "%" + s.replace("\\", "\\\\\\").replace("%", "\\\\%") + "%";
    }

    private List<Image> toImageList(List<Object> list)
    {
        List<Image> imageList = new ArrayList<>(list.size());
        for (Object e: list)
            imageList.add((Image)e);
        return imageList;
    }

    private Image cast(List<Object> list)
    {
        return (list.size() == 0) ? null : (Image)list.get(0);
    }
}