package servlet.img;
import bean.*;
import dao.*;
import util.*;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Posts", value = "/posts")
public class Posts extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Integer imageID = Util.toInt(request.getParameter("image"));
        Image image = DAOManager.getImageDAO().getVisibility(imageID);
        if (!Util.hasAccess(user, image))
        {
            out.print(404);
            return;
        }

        List<Post> posts = DAOManager.getPostDAO().getPostsByImageIDKudosTime(imageID);
        List<Map<String, Object>> list = new ArrayList<>(posts.size());
        UserDAO userDAO = DAOManager.getUserDAO();
        KudosDAO kudosDAO = DAOManager.getKudosDAO();
        for (Post e: posts)
        {
            Map<String, Object> map = Reflect.toMap(e);
            map.remove("imageID");
            map.put("author", userDAO.getUsername(e.getUID()).getUsername());
            map.put("stars", kudosDAO.count(e.getPostID()));

            boolean starred;
            if (user == null)
                starred = false;
            else
            {
                Kudos kudos = new Kudos();
                kudos.setUID(user.getUID());
                kudos.setPostID(e.getPostID());
                starred = kudosDAO.exists(kudos);
            }
            map.put("starred", starred);

            list.add(map);
        }
        out.print(JSON.toJSON(list));
    }
}