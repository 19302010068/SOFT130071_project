package servlet.img;
import bean.*;
import dao.DAOManager;
import dao.ImageDAO;
import util.*;
import java.io.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Edit", value = "/edit")
public class Edit extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        Image image = DAOManager.getImageDAO().getImage(Util.toInt(request.getParameter("image")));
        if (Util.authorOf(user, image))
        {
            Map<String, Object> map = Reflect.toMap(image);
            map.remove("uploaded");
            map.remove("uID");
            City city = DAOManager.getCityDAO().getCity(image.getCityID());
            map.put("cityName", city.getCityName());
            map.put("countryCode", city.getCountryCode());
            out.print(JSON.toJSON(map));
        }
        else
            out.print(404);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        ImageDAO imageDAO = DAOManager.getImageDAO();
        User user = (User)session.getAttribute("user");
        if (user == null)
        {
            out.print("unauthorized");
            return;
        }

        for (Cookie e: request.getCookies())
        {
            if (e.getName().equals("edit"))
            {
                Cookie cookie = new Cookie("edit", "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                Image image = (Image)session.getAttribute("edit-old");
                Image newImage = (Image)session.getAttribute("edit");
                if (Util.authorOf(user, newImage) && newImage.getPath().equals(e.getValue()))
                {
                    try
                    {
                        imageDAO.addImage(newImage);
                        Util.moveStars(image, newImage);
                        DAOManager.getPostDAO().changeImageID(image.getImageID(), newImage.getImageID());
                        imageDAO.removeImage(image.getImageID());
                        if (!new File(Util.getImgFolder() + image.getPath()).delete())
                            throw new RuntimeException();
                    }
                    catch (RuntimeException ex)
                    {
                        out.print("unauthorized");
                        return;
                    }

                    final int SIZE = 8192;
                    byte[] buffer = new byte[SIZE];
                    BufferedInputStream input = new BufferedInputStream(request.getInputStream());
                    File file = new File(Util.getImgFolder() + newImage.getPath());
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

        Integer imageID = Util.toInt(request.getParameter("image"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String[] contents = request.getParameterValues("contents[]");
        Integer visibility = Util.toInt(request.getParameter("visibility"));
        Integer city = Util.toInt(request.getParameter("city"));
        String file = request.getParameter("file");
        Image image = imageDAO.getImage(imageID);
        if (isValid(title) && isValid(description) && isValid(contents) && Util.authorOf(user, image))
        {
            Image newImage = new Image();
            String path = Util.getRandom();
            newImage.setTitle(title);
            newImage.setDescription(description);
            newImage.setContent1(contents[0]);
            if (contents.length > 1)
                newImage.setContent2(contents[1]);
            if (contents.length > 2)
                newImage.setContent3(contents[2]);
            if (contents.length > 3)
                newImage.setContent4(contents[3]);
            newImage.setVisibilityID(visibility);
            newImage.setCityID(city);
            newImage.setUID(user.getUID());

            if (file == null)
            {
                int pos = image.getPath().lastIndexOf('.');
                if (pos != -1)
                    path += image.getPath().substring(pos);
                newImage.setPath(path);
                try
                {
                    imageDAO.addImage(newImage);
                    Util.moveStars(image, newImage);
                    DAOManager.getPostDAO().changeImageID(imageID, newImage.getImageID());
                    imageDAO.removeImage(imageID);
                    if (!new File(Util.getImgFolder() + image.getPath()).renameTo(new File(Util.getImgFolder() + path)))
                        throw new RuntimeException();
                }
                catch (RuntimeException ex)
                {
                    out.print("unauthorized");
                    return;
                }
            }
            else
            {
                int pos = file.lastIndexOf('.');
                if (pos != -1)
                    path += file.substring(pos);
                newImage.setPath(path);
                Cookie cookie = new Cookie("edit", path);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                session.setAttribute("edit", newImage);
                session.setAttribute("edit-old", image);
            }

            out.print("ok");
        }
        else
            out.print("unauthorized");
    }

    private boolean isValid(String s)
    {
        return s != null && s.length() != 0;
    }

    private boolean isValid(String[] strings)
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