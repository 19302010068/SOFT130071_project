package dao.jdbc;
import bean.Post;
import util.JDBC;
import java.math.BigInteger;
import java.util.*;

public class PostDAO implements dao.PostDAO
{
    private static PostDAO postDAO = new PostDAO();

    private PostDAO() {}

    public static PostDAO getInstance()
    {
        return postDAO;
    }

    @Override
    public void changeImageID(Integer oldImageID, Integer newImageID)
    {
        JDBC.update("UPDATE posts SET ImageID = ? WHERE ImageID = ?", newImageID, oldImageID);
    }

    @Override
    public Post addPost(Post post)
    {
        BigInteger postID = (BigInteger)JDBC.insert("INSERT INTO posts (ImageID, UID, Message) VALUES (?, ?, ?)", post.getImageID(), post.getUID(), post.getMessage());
        return cast(JDBC.queryObjects(Post.class, "SELECT * FROM posts WHERE PostID = ?", postID));
    }

    @Override
    public Post getImageID(Integer postID)
    {
        return cast(JDBC.queryObjects(Post.class, "SELECT PostID, ImageID FROM posts WHERE PostID = ?", postID));
    }

    @Override
    public List<Post> getPostsByImageIDKudosTime(Integer imageID)
    {
        List<Post> list1 = toPostList(JDBC.queryObjects(Post.class, "SELECT * FROM posts INNER JOIN kudos ON posts.PostID = kudos.PostID WHERE ImageID = ? GROUP BY kudos.PostID ORDER BY COUNT(*) DESC, Posted DESC", imageID));
        List<Post> list2 = toPostList(JDBC.queryObjects(Post.class, "SELECT * FROM posts WHERE ImageID = ? ORDER BY Posted DESC", imageID));
        list2.removeAll(list1);
        list1.addAll(list2);
        return list1;
    }

    private List<Post> toPostList(List<Object> list)
    {
        List<Post> postList = new ArrayList<>(list.size());
        for (Object e: list)
            postList.add((Post)e);
        return postList;
    }

    private Post cast(List<Object> list)
    {
        return (list.size() == 0) ? null : (Post)list.get(0);
    }
}