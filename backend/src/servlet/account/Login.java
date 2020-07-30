package servlet.account;
import bean.User;
import dao.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        if (user != null)
        {
            out.print("ok");
            return;
        }

        UserDAO userDAO = DAOManager.getUserDAO();
        String pass = request.getParameter("pass"); //TODO: pass hash
        String username = request.getParameter("username");
        if (username != null)
            user = userDAO.getUserByUsername(username, pass);
        else
        {
            String email = request.getParameter("email");
            user = userDAO.getUserByEmail(email, pass);
        }

        if (user == null)
            out.print("unauthorized");
        else
        {
            userDAO.modifyUser(user);
            session.setAttribute("user", user);
            out.print("ok");
        }
    }
}