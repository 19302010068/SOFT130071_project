package dao;
import bean.Friends;
import java.util.List;

public interface FriendsDAO
{
    void addFriends(Friends friends);
    void removeFriends(Friends friends);
    List<Integer> getRequestingUIDsByUID2(Integer uID2);
    List<Integer> getFriendUIDsByUID(Integer uID);
    boolean arePending(Friends friends);
    boolean areFriends(Friends friends);
}