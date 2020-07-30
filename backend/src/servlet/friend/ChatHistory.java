package servlet.friend;
import bean.*;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ChatHistory", value = "/chat-history")
public class ChatHistory extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
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
                Chat chat = new Chat();
                chat.setUID1(user.getUID());
                chat.setUID2(friendID);
                List<Chat> chats = DAOManager.getChatDAO().getChatsByUIDsUnordered(chat);
                List<Map<String, Object>> list = new ArrayList<>(chats.size());
                for (Chat e: chats)
                {
                    Map<String, Object> map = new HashMap<>();
                    map.put("self", e.getUID1().equals(user.getUID()));
                    map.put("message", e.getMessage());
                    map.put("sent", e.getSent());
                    list.add(map);
                }
                out.print(JSON.toJSON(DAOManager.getUserDAO().getUsername(friendID).getUsername(), list));
            }
            else
                out.print(404);
        }
    }
}