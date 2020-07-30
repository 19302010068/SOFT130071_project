package servlet.input;
import bean.*;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Countries", value = "/countries")
public class Countries extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

        List<Continent> continents = DAOManager.getContinentDAO().dump();
        Map<String, List<Country>> map = new HashMap<>(continents.size());
        for (Continent e: continents)
            map.put(e.getContinentName(), DAOManager.getCountryDAO().getCountriesByContinentCode(e.getContinentCode()));
        out.print(JSON.toJSON(map));
    }
}