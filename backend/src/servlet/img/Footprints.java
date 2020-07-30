package servlet.img;
import bean.*;
import dao.*;
import util.JSON;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Footprints", value = "/footprints")
public class Footprints extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        if (user == null)
            out.print(JSON.toJSON("unauthorized"));
        else
        {
            List<Integer> list = DAOManager.getFootprintDAO().getImageIDByUID(user.getUID());
            List<Image> images = new ArrayList<>(list.size());
            ImageDAO imageDAO = DAOManager.getImageDAO();
            for (Integer e: list)
                images.add(imageDAO.getTitle(e));
            out.print(JSON.toJSON(images));
        }
    }
}