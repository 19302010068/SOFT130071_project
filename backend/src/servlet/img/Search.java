package servlet.img;
import bean.User;
import dao.*;
import util.JSON;
import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Search", value = "/search")
public class Search extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        List<Integer> list = null;
        String order = request.getParameter("order");
        String title = request.getParameter("title");
        ImageDAO imageDAO = DAOManager.getImageDAO();
        if (title == null)
        {
            String content = request.getParameter("content");
            if (content != null)
            {
                switch (order)
                {
                    case "stars":
                        list = imageDAO.getImageIDByContentStars(content);
                        break;
                    case "time":
                        list = imageDAO.getImageIDByContentTime(content);
                        break;
                }
            }
        }
        else
        {
            switch (order)
            {
                case "stars":
                    list = imageDAO.getImageIDByTitleStars(title);
                    break;
                case "time":
                    list = imageDAO.getImageIDByTitleTime(title);
                    break;
            }
        }

        if (list == null)
        {
            out.print(JSON.toJSON("unauthorized"));
            return;
        }

        User user = (User)session.getAttribute("user");
        for (int i = 0; i < list.size(); )
        {
            if (Util.hasAccess(user, imageDAO.getImage(list.get(i))))
                i++;
            else
                list.remove(i);
        }
        out.print(JSON.toJSON(list));
    }
}