package servlet.friend;
import bean.*;
import dao.*;
import util.*;
import java.io.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "FriendRequests", value = "/friend-requests")
public class FriendRequests extends HttpServlet
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
            List<Integer> list = DAOManager.getFriendsDAO().getRequestingUIDsByUID2(user.getUID());
            List<Map<String, Object>> maps = new ArrayList<>(list.size());
            UserDAO userDAO = DAOManager.getUserDAO();
            ChatDAO chatDAO = DAOManager.getChatDAO();
            for (Integer e: list)
            {
                Map<String, Object> map = Reflect.toMap(userDAO.getUsername(e));
                Chat chat = new Chat();
                chat.setUID1(e);
                chat.setUID2(user.getUID());
                chat = chatDAO.getChatByUIDs(chat);
                if (chat != null)
                    map.put("message", chat.getMessage());
                maps.add(map);
            }
            out.print(JSON.toJSON(maps));
        }
    }
}