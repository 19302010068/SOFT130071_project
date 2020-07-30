package servlet.account;
import bean.User;
import dao.DAOManager;
import util.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Profile", value = "/profile")
public class Profile extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        if (user == null)
            out.print(JSON.toJSON("unauthorized"));
        else
        {
            Map<String, Object> map = new HashMap<>();
            map.put("email", user.getEmail());
            map.put("joined", user.getJoined());
            map.put("lastModified", user.getLastModified());
            map.put("hideFavourites", DAOManager.getHideFavouritesDAO().exists(user.getUID()));
            out.print(JSON.toJSON(map));
        }
    }
}