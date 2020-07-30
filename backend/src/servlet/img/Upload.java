package servlet.img;
import bean.*;
import dao.DAOManager;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Upload", value = "/upload")
public class Upload extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        if (user == null)
        {
            out.print("unauthorized");
            return;
        }

        for (Cookie e: request.getCookies())
        {
            if (e.getName().equals("upload"))
            {
                Cookie cookie = new Cookie("upload", "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                Image image = (Image)session.getAttribute("upload");
                if (Util.authorOf(user, image) && image.getPath().equals(e.getValue()))
                {
                    try
                    {
                        DAOManager.getImageDAO().addImage(image);
                    }
                    catch (RuntimeException ex)
                    {
                        out.print("unauthorized");
                        return;
                    }

                    final int SIZE = 8192;
                    byte[] buffer = new byte[SIZE];
                    BufferedInputStream input = new BufferedInputStream(request.getInputStream());
                    File file = new File(Util.getImgFolder() + image.getPath());
                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                    while (true)
                    {
                        int num = input.read(buffer);
                        if (num > 0)
                            output.write(buffer, 0, num);
                        else
                        {
                            input.close();
                            output.close();
                            break;
                        }
                    }

                    out.print("ok");
                }
                else
                    out.print("unauthorized");
                return;
            }
        }

        String file = request.getParameter("file");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String[] contents = request.getParameterValues("contents[]");
        Integer visibility = Util.toInt(request.getParameter("visibility"));
        Integer city = Util.toInt(request.getParameter("city"));
        if (isValid(file) && isValid(title) && isValid(description) && isValid(contents))
        {
            Image image = new Image();
            String path = Util.getRandom();
            int pos = file.lastIndexOf('.');
            if (pos != -1)
                path += file.substring(pos);
            image.setPath(path);
            image.setTitle(title);
            image.setDescription(description);
            image.setContent1(contents[0]);
            if (contents.length > 1)
                image.setContent2(contents[1]);
            if (contents.length > 2)
                image.setContent3(contents[2]);
            if (contents.length > 3)
                image.setContent4(contents[3]);
            image.setVisibilityID(visibility);
            image.setCityID(city);
            image.setUID(user.getUID());
            session.setAttribute("upload", image);
            Cookie cookie = new Cookie("upload", path);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            out.print("ok");
        }
        else
            out.print("unauthorized");
    }

    private static boolean isValid(String s)
    {
        return s != null && s.length() != 0;
    }

    private static boolean isValid(String[] strings)
    {
        if (strings == null || strings.length == 0 || strings.length > 4)
            return false;
        for (String e: strings)
        {
            if (e.trim().length() != e.length())
                return false;
        }
        return true;
    }
}