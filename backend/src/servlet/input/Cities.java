package servlet.input;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Cities", value = "/cities")
public class Cities extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

        String cityName = request.getParameter("cityName");
        String countryCode = request.getParameter("countryCode");
        out.print(JSON.toJSON(DAOManager.getCityDAO().getCitiesByCityName(cityName, countryCode)));
    }
}