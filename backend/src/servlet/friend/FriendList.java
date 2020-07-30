package servlet.friend;
import bean.User;
import dao.*;
import util.JSON;
import java.io.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "FriendList", value = "/friend-list")
public class FriendList extends HttpServlet
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
            List<Integer> list = DAOManager.getFriendsDAO().getFriendUIDsByUID(user.getUID());
            List<User> users = new ArrayList<>(list.size());
            UserDAO userDAO = DAOManager.getUserDAO();
            for (Integer e: list)
            {
                User friend = userDAO.getUser(e);
                friend.setPass(null);
                friend.setLastModified(null);
                users.add(friend);
            }
            out.print(JSON.toJSON(users));
        }
    }
}