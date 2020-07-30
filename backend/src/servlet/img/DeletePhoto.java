package servlet.img;
import bean.*;
import dao.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "DeletePhoto", value = "/delete-photo")
public class DeletePhoto extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        ImageDAO imageDAO = DAOManager.getImageDAO();
        Image image = imageDAO.getImage(Util.toInt(request.getParameter("image")));
        if (Util.authorOf(user, image))
        {
            File file = new File(Util.getImgFolder() + image.getPath());
            if (file.delete())
            {
                imageDAO.removeImage(image.getImageID());
                out.print("ok");
                return;
            }
        }
        out.print("unauthorized");
    }
}