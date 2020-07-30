package servlet.img;
import bean.User;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "MyPhotos", value = "/my-photos")
public class MyPhotos extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        out.print(JSON.toJSON((user == null) ? "unauthorized" : DAOManager.getImageDAO().getImageIDByUID(user.getUID())));
    }
}