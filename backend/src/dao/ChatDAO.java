package dao;
import bean.Chat;
import java.util.List;

public interface ChatDAO
{
    void addChat(Chat chat);
    Chat getChatByUIDs(Chat chat);
    List<Chat> getChatsByUIDsUnordered(Chat chat);
    void removeChatsByUIDs(Chat chat);
    void removeChatsByUIDsUnordered(Chat chat);
}