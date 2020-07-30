package servlet.img;
import bean.*;
import dao.DAOManager;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ToggleKudos", value = "/toggle-kudos")
public class ToggleKudos extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Integer postID = Util.toInt(request.getParameter("post"));
        Post post = DAOManager.getPostDAO().getImageID(postID);
        if (user == null || post == null || !Util.hasAccess(user, DAOManager.getImageDAO().getVisibility(post.getImageID())))
            out.print("unauthorized");
        else
        {
            Kudos kudos = new Kudos();
            kudos.setUID(user.getUID());
            kudos.setPostID(postID);
            DAOManager.getKudosDAO().toggle(kudos);
            out.print("ok");
        }
    }
}