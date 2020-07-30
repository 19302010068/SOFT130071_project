package servlet.img;
import bean.*;
import dao.DAOManager;
import util.*;
import java.io.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AddPost", value = "/add-post")
public class AddPost extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Integer imageID = Util.toInt(request.getParameter("image"));
        String message = request.getParameter("comment");
        if (user == null || message == null || message.length() == 0 || !Util.hasAccess(user, DAOManager.getImageDAO().getVisibility(imageID)))
            out.print("unauthorized");
        else
        {
            Post post = new Post();
            post.setImageID(imageID);
            post.setUID(user.getUID());
            post.setMessage(message);
            post = DAOManager.getPostDAO().addPost(post);
            Map<String, Object> map = Reflect.toMap(post);
            map.remove("imageID");
            map.put("author", DAOManager.getUserDAO().getUsername(post.getUID()).getUsername());
            map.put("stars", 0);
            map.put("starred", false);
            out.print(JSON.toJSON(map));
        }
    }
}