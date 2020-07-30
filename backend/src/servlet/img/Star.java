package servlet.img;
import bean.*;
import dao.DAOManager;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Star", value = "/star")
public class Star extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Integer imageID = Util.toInt(request.getParameter("image"));
        Image image = DAOManager.getImageDAO().getVisibility(imageID);

        if (user == null || !Util.hasAccess(user, image))
            out.print("unauthorized");
        else
        {
            Favourite favourite = new Favourite();
            favourite.setUID(user.getUID());
            favourite.setImageID(imageID);
            DAOManager.getFavouriteDAO().toggle(favourite);
            out.print("ok");
        }
    }
}