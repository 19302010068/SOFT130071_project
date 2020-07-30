package servlet.friend;
import bean.*;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "FriendProfile", value = "/friend-profile")
public class FriendProfile extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Integer friendID = Util.toInt(request.getParameter("friend"));
        if (user == null)
            out.print(404);
        else
        {
            Friends friends = new Friends();
            friends.setUID1(user.getUID());
            friends.setUID2(friendID);
            if (DAOManager.getFriendsDAO().areFriends(friends))
            {
                User friend = DAOManager.getUserDAO().getUser(friendID);
                friend.setUID(null);
                friend.setPass(null);
                out.print(JSON.toJSON(friend));
            }
            else
                out.print(404);
        }
    }
}