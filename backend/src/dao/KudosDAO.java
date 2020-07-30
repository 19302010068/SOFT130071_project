package dao;
import bean.Kudos;

public interface KudosDAO
{
    void addKudos(Kudos kudos);
    long count(Integer postID);
    void toggle(Kudos kudos);
    boolean exists(Kudos kudos);
}