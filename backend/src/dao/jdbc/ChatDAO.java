package dao.jdbc;
import bean.Chat;
import util.JDBC;
import java.util.*;

public class ChatDAO implements dao.ChatDAO
{
    private static ChatDAO chatDAO = new ChatDAO();

    private ChatDAO() {}

    public static ChatDAO getInstance()
    {
        return chatDAO;
    }

    @Override
    public void addChat(Chat chat)
    {
        if (JDBC.update("INSERT INTO chats (UID1, UID2, Message) VALUES (?, ?, ?)", chat.getUID1(), chat.getUID2(), chat.getMessage()) != 1)
            throw new RuntimeException();
        Date sent = (Date)JDBC.queryScalar("SELECT Sent FROM chats WHERE UID1 = ? AND UID2 = ? AND Message = ? ORDER BY Sent DESC LIMIT 1", chat.getUID1(), chat.getUID2(), chat.getMessage());
        chat.setSent(sent);
    }

    @Override
    public Chat getChatByUIDs(Chat chat) 
    {
        return cast(JDBC.queryObjects(Chat.class, "SELECT * FROM chats WHERE UID1 = ? AND UID2 = ?", chat.getUID1(), chat.getUID2()));
    }

    @Override
    public List<Chat> getChatsByUIDsUnordered(Chat chat)
    {
        return toChatList(JDBC.queryObjects(Chat.class, "SELECT * FROM chats WHERE UID1 = ? AND UID2 = ? OR UID1 = ? AND UID2 = ?", chat.getUID1(), chat.getUID2(), chat.getUID2(), chat.getUID1()));
    }

    @Override
    public void removeChatsByUIDs(Chat chat)
    {
        JDBC.update("DELETE FROM chats WHERE UID1 = ? AND UID2 = ?", chat.getUID1(), chat.getUID2());
    }

    @Override
    public void removeChatsByUIDsUnordered(Chat chat)
    {
        JDBC.update("DELETE FROM chats WHERE UID1 = ? AND UID2 = ? OR UID1 = ? AND UID2 = ?", chat.getUID1(), chat.getUID2(), chat.getUID2(), chat.getUID1());
    }

    private List<Chat> toChatList(List<Object> list)
    {
        List<Chat> chatList = new ArrayList<>(list.size());
        for (Object e: list)
            chatList.add((Chat)e);
        return chatList;
    }

    private Chat cast(List<Object> list)
    {
        return (list.size() == 0) ? null : (Chat)list.get(0);
    }
}