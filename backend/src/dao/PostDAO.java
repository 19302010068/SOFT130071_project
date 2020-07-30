package dao;
import bean.Post;
import java.util.List;

public interface PostDAO
{
    void changeImageID(Integer oldImageID, Integer newImageID);
    Post addPost(Post post);
    Post getImageID(Integer postID);
    List<Post> getPostsByImageIDKudosTime(Integer imageID);
}