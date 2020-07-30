package servlet.img;
import bean.*;
import dao.*;
import util.*;
import java.io.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "PhotoDetails", value = "/photo-details")
public class PhotoDetails extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Integer imageID = Util.toInt(request.getParameter("image"));
        Image image = DAOManager.getImageDAO().getImage(imageID);
        if (!Util.hasAccess(user, image))
        {
            out.print(404);
            return;
        }

        if (user != null)
        {
            Footprint footprint = new Footprint();
            footprint.setUID(user.getUID());
            footprint.setImageID(imageID);
            FootprintDAO footprintDAO = DAOManager.getFootprintDAO();
            footprintDAO.removeFootprint(footprint);
            footprintDAO.addFootprint(footprint);
            if (footprintDAO.count(user.getUID()) > 10)
                footprintDAO.removeFirstByUID(user.getUID());
        }

        Map<String, Object> map = Reflect.toMap(image);
        map.remove("visibilityID");
        City city = DAOManager.getCityDAO().getCity(image.getCityID());
        map.putAll(Reflect.toMap(city));
        Country country = DAOManager.getCountryDAO().getCountry(city.getCountryCode());
        map.put("countryName", country.getCountryName());
        User author = DAOManager.getUserDAO().getUsername(image.getUID());
        map.put("author", author.getUsername());

        FavouriteDAO favouriteDAO = DAOManager.getFavouriteDAO();
        boolean starred;
        if (user == null)
            starred = false;
        else
        {
            Favourite favourite = new Favourite();
            favourite.setUID(user.getUID());
            favourite.setImageID(imageID);
            starred = favouriteDAO.exists(favourite);
        }
        map.put("starred", starred);
        map.put("stars", favouriteDAO.count(imageID));

        out.print(JSON.toJSON(map));
    }
}