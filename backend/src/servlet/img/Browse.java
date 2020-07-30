package servlet.img;
import bean.City;
import bean.Country;
import bean.User;
import dao.DAOManager;
import dao.ImageDAO;
import util.JSON;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Browse", value = "/browse")
public class Browse extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String name = null;
        List<Integer> list = null;
        ImageDAO imageDAO = DAOManager.getImageDAO();
        Integer authorID = Util.toInt(request.getParameter("author"));
        String content = request.getParameter("content");
        String countryCode = request.getParameter("location");
        Integer cityID = Util.toInt(countryCode);

        if (authorID != null)
        {
            User author = DAOManager.getUserDAO().getUsername(authorID);
            if (author == null)
                out.print(404);
            else
            {
                name = author.getUsername();
                list = imageDAO.getImageIDByUID(authorID);
            }
        }
        else if (content != null)
        {
            list = imageDAO.getImageIDByContent(content);
            if (list.size() == 0)
                out.print(404);
            else
                name = content;
        }
        else if (cityID != null)
        {
            City city = DAOManager.getCityDAO().getCity(cityID);
            if (city == null)
                out.print(404);
            else
            {
                name = city.getCityName();
                list = imageDAO.getImageIDByCityID(cityID);
            }
        }
        else if (countryCode != null)
        {
            Country country = DAOManager.getCountryDAO().getCountry(countryCode);
            if (country == null)
                out.print(404);
            else
            {
                name = country.getCountryName();
                list = imageDAO.getImageIDByCountryCode(countryCode);
            }
        }
        else
            out.print(404);

        if (name != null)
        {
            User user = (User)session.getAttribute("user");
            for (int i = 0; i < list.size(); )
            {
                if (Util.hasAccess(user, imageDAO.getImage(list.get(i))))
                    i++;
                else
                    list.remove(i);
            }
            out.print(JSON.toJSON(name, list));
        }
    }
}