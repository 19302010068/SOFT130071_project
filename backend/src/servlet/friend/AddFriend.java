package servlet.friend;
import bean.*;
import dao.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AddFriend", value = "/add-friend")
public class AddFriend extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        String username = request.getParameter("username");
        String message = request.getParameter("message");
        if (user == null || username == null)
            out.print("unauthorized");
        else if (user.getUsername().equals(username))
            out.print("self");
        else
        {
            UserDAO userDAO = DAOManager.getUserDAO();
            User friend = userDAO.getUID(username);
            if (friend == null)
                out.print("unauthorized");
            else
            {
                FriendsDAO friendsDAO = DAOManager.getFriendsDAO();
                Friends friends = new Friends();
                friends.setUID1(user.getUID());
                friends.setUID2(friend.getUID());
                friends.setStatus(0);
                if (friendsDAO.areFriends(friends))
                    out.print("already");
                else
                {
                    friendsDAO.removeFriends(friends);
                    friendsDAO.addFriends(friends);
                    ChatDAO chatDAO = DAOManager.getChatDAO();
                    Chat chat = new Chat();
                    chat.setUID1(user.getUID());
                    chat.setUID2(friend.getUID());
                    chatDAO.removeChatsByUIDs(chat);
                    if (message != null)
                    {
                        chat.setMessage(message);
                        chatDAO.addChat(chat);
                    }
                    out.print("ok");
                }
            }
        }
    }
}