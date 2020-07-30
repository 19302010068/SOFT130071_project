package dao;
import bean.User;

public interface UserDAO
{
    User getUser(Integer uID);
    User getUserByUsername(String username, String pass);
    User getUserByEmail(String email, String pass);
    User addUser(String username, String email, String pass);
    User getUID(String username);
    User getUsername(Integer uID);
    void modifyUser(User user);
    String exists(String username, String email);
}