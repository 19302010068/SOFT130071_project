package dao;

public interface HideFavouritesDAO
{
    void add(Integer uID);
    void remove(Integer uID);
    boolean exists(Integer uID);
}