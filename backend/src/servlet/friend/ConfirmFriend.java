package servlet.friend;
import bean.*;
import dao.*;
import util.JSON;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ConfirmFriend", value = "/confirm-friend")
public class ConfirmFriend extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Integer friendID = Util.toInt(request.getParameter("friend"));
        String action = request.getParameter("action");
        if (user != null && (action.equals("accept") || action.equals("reject")))
        {
            FriendsDAO friendsDAO = DAOManager.getFriendsDAO();
            Friends friends = new Friends();
            friends.setUID1(friendID);
            friends.setUID2(user.getUID());
            ChatDAO chatDAO = DAOManager.getChatDAO();
            Chat chat = new Chat();
            chat.setUID1(friendID);
            chat.setUID2(user.getUID());
            if (friendsDAO.arePending(friends))
            {
                friendsDAO.removeFriends(friends);
                chatDAO.removeChatsByUIDs(chat);
                if (action.equals("accept"))
                {
                    friends.setStatus(1);
                    friendsDAO.addFriends(friends);
                    friends.setUID1(user.getUID());
                    friends.setUID2(friendID);
                    friendsDAO.removeFriends(friends);
                    chat.setUID1(user.getUID());
                    chat.setUID2(friendID);
                    chatDAO.removeChatsByUIDs(chat);

                    User friend = DAOManager.getUserDAO().getUser(friendID);
                    friend.setPass(null);
                    friend.setLastModified(null);

                    out.print(JSON.toJSON("ok", friend));
                }
                else
                    out.print(JSON.toJSON("ok"));
            }
            else
                out.print(JSON.toJSON("unauthorized"));
        }
        else
            out.print(JSON.toJSON("unauthorized"));
    }
}