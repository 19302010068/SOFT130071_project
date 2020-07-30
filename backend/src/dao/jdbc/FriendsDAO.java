package dao.jdbc;
import bean.Friends;
import util.JDBC;
import java.util.List;

public class FriendsDAO implements dao.FriendsDAO
{
    private static FriendsDAO friendsDAO = new FriendsDAO();

    private FriendsDAO() {}

    public static FriendsDAO getInstance()
    {
        return friendsDAO;
    }

    @Override
    public void addFriends(Friends friends)
    {
        if (JDBC.update("INSERT INTO friends (UID1, UID2, Status) VALUES (?, ?, ?)", friends.getUID1(), friends.getUID2(), friends.getStatus()) != 1)
            throw new RuntimeException();
    }

    @Override
    public void removeFriends(Friends friends)
    {
        JDBC.update("DELETE FROM friends WHERE UID1 = ? AND UID2 = ?", friends.getUID1(), friends.getUID2());
    }

    @Override
    public List<Integer> getRequestingUIDsByUID2(Integer uID2)
    {
        return JDBC.queryIntegers("SELECT UID1 FROM friends WHERE UID2 = ? AND Status = 0", uID2);
    }

    @Override
    public List<Integer> getFriendUIDsByUID(Integer uID)
    {
        List<Integer> list1 = JDBC.queryIntegers("SELECT UID2 FROM friends WHERE UID1 = ? AND Status > 0", uID);
        List<Integer> list2 = JDBC.queryIntegers("SELECT UID1 FROM friends WHERE UID2 = ? AND Status > 0", uID);
        list1.addAll(list2);
        return list1;
    }

    @Override
    public boolean arePending(Friends friends)
    {
        return (long)JDBC.queryScalar("SELECT COUNT(*) FROM friends WHERE UID1 = ? AND UID2 = ? AND Status = 0", friends.getUID1(), friends.getUID2()) != 0;
    }

    @Override
    public boolean areFriends(Friends friends)
    {
        return (long)JDBC.queryScalar("SELECT COUNT(*) FROM friends WHERE Status > 0 AND (UID1 = ? AND UID2 = ? OR UID1 = ? AND UID2 = ?)", friends.getUID1(), friends.getUID2(), friends.getUID2(), friends.getUID1()) != 0;
    }
}