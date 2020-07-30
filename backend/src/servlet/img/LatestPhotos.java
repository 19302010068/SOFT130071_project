package servlet.img;
import bean.Image;
import dao.*;
import util.*;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LatestPhotos", value = "/latest-photos")
public class LatestPhotos extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

        List<Image> images = DAOManager.getImageDAO().getLatestImages();
        List<Map<String, Object>> list = new ArrayList<>(images.size());
        UserDAO userDAO = DAOManager.getUserDAO();
        for (Image e: images)
        {
            Map<String, Object> map = Reflect.toMap(e);
            map.put("author", userDAO.getUsername(e.getUID()).getUsername());
            list.add(map);
        }
        out.print(JSON.toJSON(list));
    }
}