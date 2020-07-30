package servlet.friend;
import bean.*;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "FriendPhotos", value = "/friend-photos")
public class FriendPhotos extends HttpServlet
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
                out.print(JSON.toJSON(DAOManager.getUserDAO().getUsername(friendID).getUsername(), DAOManager.getImageDAO().getImageIDByUID(friendID)));
            else
                out.print(404);
        }
    }
}