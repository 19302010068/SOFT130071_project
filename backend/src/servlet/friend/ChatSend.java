package servlet.friend;
import bean.*;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ChatSend", value = "/chat-send")
public class ChatSend extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Integer friendID = Util.toInt(request.getParameter("friend"));
        String message = request.getParameter("message");
        if (user == null || message == null || message.length() == 0)
            out.print("unauthorized");
        else
        {
            Friends friends = new Friends();
            friends.setUID1(user.getUID());
            friends.setUID2(friendID);
            if (DAOManager.getFriendsDAO().areFriends(friends))
            {
                Chat chat = new Chat();
                chat.setUID1(user.getUID());
                chat.setUID2(friendID);
                chat.setMessage(message);
                DAOManager.getChatDAO().addChat(chat);
                out.print(JSON.toJSON(chat.getSent()));
            }
            else
                out.print("unauthorized");
        }
    }
}