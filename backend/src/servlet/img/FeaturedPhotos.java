package servlet.img;
import dao.DAOManager;
import util.JSON;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "FeaturedPhotos", value = "/featured-photos")
public class FeaturedPhotos extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

        out.print(JSON.toJSON(DAOManager.getImageDAO().getFeaturedImages()));
    }
}