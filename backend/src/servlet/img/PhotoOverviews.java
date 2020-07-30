package servlet.img;
import bean.*;
import dao.*;
import util.JSON;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "PhotoOverviews", value = "/photo-overviews")
public class PhotoOverviews extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        String[] imageIDs = request.getParameterValues("images[]");
        ImageDAO imageDAO = DAOManager.getImageDAO();

        List<Image> images = new ArrayList<>(imageIDs.length);
        for (String e: imageIDs)
        {
            Image image = imageDAO.getImageOverview(Util.toInt(e));
            if (Util.hasAccess(user, image))
            {
                image.setUID(null);
                image.setVisibilityID(null);
            }
            else
                image = new Image();
            images.add(image);
        }

        out.print(JSON.toJSON(images));
    }
}