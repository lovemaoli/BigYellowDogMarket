package com.TropicalFlavor.dao;

import com.TropicalFlavor.po.*;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalesDao
{
    //public boolean ChangeInfo(@Param("UID") String UID, @Param("marketGoods") MarketGoods marketGoods);

    public boolean DeleteGoods(@Param("UID") String UID, @Param("marketGoods") Goods goods);

    public boolean InsertGoods(@Param("UID") String UID, @Param("marketGoods") Goods goods);

    public String WhoseGoods(String GID);

    public List<Goods> ShowGoods(User user);

    // 添加的新方法
    public List<SaleRecord> SearchRecord(String Gname);
}
