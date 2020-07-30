package servlet.img;
import bean.*;
import dao.*;
import java.security.SecureRandom;
import java.util.List;

class Util
{
    private static String imgFolder = Upload.class.getClassLoader().getResource("../../img").getPath();
    private static SecureRandom secureRandom = new SecureRandom();

    static Integer toInt(String param)
    {
        try
        {
            return Integer.parseInt(param);
        }
        catch (NumberFormatException ex)
        {
            return null;
        }
    }

    static boolean hasAccess(User user, Image image)
    {
        if (image == null)
            return false;
        else if (image.getVisibilityID() == 1)
            return true;
        else if (user == null)
            return false;
        else if (user.getUID().equals(image.getUID()))
            return true;

        if (image.getVisibilityID() == 2)
        {
            Friends friends = new Friends();
            friends.setUID1(user.getUID());
            friends.setUID2(image.getUID());
            return DAOManager.getFriendsDAO().areFriends(friends);
        }
        else
            return false;
    }

    static boolean authorOf(User user, Image image)
    {
        return user != null && image != null && user.getUID() != null && user.getUID().equals(image.getUID());
    }

    static String getImgFolder()
    {
        return imgFolder;
    }

    static String getRandom()
    {
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);

        StringBuilder stringBuilder = new StringBuilder(32);
        for (byte e: bytes)
        {
            String s = Integer.toString(Byte.toUnsignedInt(e), 16);
            if (s.length() == 1)
                stringBuilder.append('0');
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    static void moveStars(Image oldImage, Image newImage)
    {
        FavouriteDAO favouriteDAO = DAOManager.getFavouriteDAO();
        List<Integer> oldList = favouriteDAO.getUIDByImageID(oldImage.getImageID());
        Favourite favourite = new Favourite();
        favourite.setImageID(newImage.getImageID());
        if (oldImage.getVisibilityID() >= newImage.getVisibilityID())
        {
            for (Integer e: oldList)
            {
                favourite.setUID(e);
                favouriteDAO.addFavourite(favourite);
            }
        }
        else
        {
            User user = new User();
            for (Integer e: oldList)
            {
                user.setUID(e);
                if (hasAccess(user, newImage))
                {
                    favourite.setUID(e);
                    favouriteDAO.addFavourite(favourite);
                }
            }
        }
    }
}