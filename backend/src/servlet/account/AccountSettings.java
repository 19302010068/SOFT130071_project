package servlet.account;
import bean.User;
import dao.*;
import util.JSON;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AccountSettings", value = "/account-settings")
public class AccountSettings extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Boolean hideFavourites = Util.toBoolean(request.getParameter("hideFavourites"));
        if (user == null || hideFavourites == null)
            out.print(JSON.toJSON("unauthorized"));
        else
        {
            HideFavouritesDAO hideFavouritesDAO = DAOManager.getHideFavouritesDAO();
            hideFavouritesDAO.remove(user.getUID());
            if (hideFavourites)
                hideFavouritesDAO.add(user.getUID());
            out.print("ok");
        }
    }
}