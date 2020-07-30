package servlet.account;
import bean.User;
import dao.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        if (user != null)
            return;

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        if (!(username.matches("[\\w.-]{4,15}") && email.matches("\\w+@\\w+(\\.\\w+)*") && pass.matches(".{6,12}")))
            return;

        UserDAO userDAO = DAOManager.getUserDAO();
        String duplicate = userDAO.exists(username, email);
        if (duplicate == null)
        {
            user = userDAO.addUser(username, email, pass);
            session.setAttribute("user", user);
            out.print("ok");
        }
        else
            out.print(duplicate);
    }
}