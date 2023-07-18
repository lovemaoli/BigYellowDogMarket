package com.TropicalFlavor.dao;
import com.TropicalFlavor.po.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsDao
{
    public boolean GoodsSoldOut(Goods goods);

    public boolean ChangeInfo(Goods goods);

    public boolean DeleteGoods(Goods goods);

    public List<Goods> SelectGoodsByName(String KeyWords);

    public List<Goods> SelectGoodsByKind(String KeyWords);

    public boolean InsertGoods(Goods goods);

    public Integer getGoodsSum();

    public Goods SelectGoods(String GID);
    public List<Goods> selectTenGoods(Map map);

    public List<Goods> selectByPrice(@Param("ASC")Boolean ASC, @Param("begin") Integer begin);

    public List<Goods> selectByNumber(@Param("ASC")Boolean ASC, @Param("begin") Integer begin);

    public List<Goods> selectByGID(Integer begin);
}
