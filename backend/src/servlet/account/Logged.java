package servlet.account;
import bean.User;
import util.JSON;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Logged", value = "/logged")
public class Logged extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        if (user == null)
            out.print(JSON.toJSON("no"));
        else
            out.print(JSON.toJSON("yes", user.getUsername()));
    }
}