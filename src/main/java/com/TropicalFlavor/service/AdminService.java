package com.TropicalFlavor.service;

import com.TropicalFlavor.po.SelectVO;
import com.TropicalFlavor.po.User;
import com.TropicalFlavor.po.SaleRecord;
import com.TropicalFlavor.po.TradeRecord;

import java.util.List;

public interface AdminService {
    //增加用户
    public boolean InsertMarketUser(User user);
    //删除用户（包括与该用户有关的所有记录）
    public boolean DeleteMarketUser(String UID);

    //注销用户
    public boolean logOffMarketUser(String UID);

    //修改用户信息
    public boolean ChangeUserInfo(User user);
    //返回用户列表
    public List<User> ShowUser();
    //返回购买记录列表
    public List<TradeRecord> ShowAllRecord();

    //搜索销售记录
    public List<SaleRecord> SearchRecord(String Gname);
    List<TradeRecord> FindRecordById(SelectVO selectVO);
}
