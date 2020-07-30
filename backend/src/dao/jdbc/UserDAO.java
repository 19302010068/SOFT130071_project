package dao.jdbc;
import bean.User;
import util.JDBC;
import java.math.BigInteger;
import java.util.List;

public class UserDAO implements dao.UserDAO
{
    private static UserDAO userDAO = new UserDAO();

    private UserDAO() {}

    public static UserDAO getInstance()
    {
        return userDAO;
    }

    @Override
    public User getUser(Integer uID)
    {
        return cast(JDBC.queryObjects(User.class, "SELECT * FROM users WHERE UID = ?", uID));
    }

    @Override
    public User getUserByUsername(String username, String pass)
    {
        return cast(JDBC.queryObjects(User.class, "SELECT * FROM users WHERE Username = ? AND Pass = ?", username, pass));
    }

    @Override
    public User getUserByEmail(String email, String pass)
    {
        return cast(JDBC.queryObjects(User.class, "SELECT * FROM users WHERE Email = ? AND Pass = ?", email, pass));
    }

    @Override
    public User addUser(String username, String email, String pass)
    {
        BigInteger uID = (BigInteger)JDBC.insert("INSERT INTO users (Username, Email, Pass) VALUES (?, ?, ?)", username, email, pass);
        return getUser(uID.intValueExact());
    }

    @Override
    public User getUID(String username)
    {
        return cast(JDBC.queryObjects(User.class, "SELECT UID, Username FROM users WHERE Username = ?", username));
    }

    @Override
    public User getUsername(Integer uID)
    {
        return cast(JDBC.queryObjects(User.class, "SELECT UID, Username FROM users WHERE UID = ?", uID));
    }

    @Override
    public void modifyUser(User user)
    {
        if (JDBC.update("UPDATE users SET LastModified = CURRENT_TIMESTAMP WHERE UID = ?", user.getUID()) != 1)
            throw new RuntimeException();
        user.setLastModified(getUser(user.getUID()).getLastModified());
    }

    @Override
    public String exists(String username, String email)
    {
        if ((long)JDBC.queryScalar("SELECT COUNT(*) FROM users WHERE Username = ?", username) != 0)
            return "username";
        else if ((long)JDBC.queryScalar("SELECT COUNT(*) FROM users WHERE Email = ?", email) != 0)
            return "email";
        else
            return null;
    }

    private User cast(List<Object> list)
    {
        return (list.size() == 0) ? null : (User)list.get(0);
    }
}