package servlet.friend;
import bean.*;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "FriendFavourites", value = "/friend-favourites")
public class FriendFavourites extends HttpServlet
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
                String friendName = DAOManager.getUserDAO().getUsername(friendID).getUsername();
                Object favourites = (DAOManager.getHideFavouritesDAO().exists(friendID)) ? "rejected" : DAOManager.getFavouriteDAO().getImageIDByUID(friendID);
                out.print(JSON.toJSON(friendName, favourites));
            }
            else
                out.print(404);
        }
    }
}