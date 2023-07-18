package com.TropicalFlavor.dao;

import com.TropicalFlavor.po.*;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao
{
    public String IsTrue(@Param("sno") String Sno, @Param("password") String password);

    public boolean ChangeInfo(User user);

    public boolean DeleteUser(User user);

    public boolean InsertUser(User user);

    public User SelectUser(String UID);

    public List<User> ShowUser();
}
