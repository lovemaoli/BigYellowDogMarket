package com.TropicalFlavor.service;

import com.TropicalFlavor.po.Goods;

import java.util.List;

public interface GoodsService {
    //修改物品信息
    public boolean ChangeGoodsInfo(Goods goods);
    //根据关键词查找物品，关键词可以是名称，也可以是种类，返回一个列表
    public List<Goods> SearchGoods(String Keywords);

    //按种类搜索
    public List<Goods> SearchGoodsByKind(String Keywords);

    //获取所有商品数量
    public Integer getGoodsSum();

    //找到物品的主人
    public String WhoseGoods(String GID);

//    //从数据库中拉出10个物品，用于在商品页面展示
//    public List<MarketGoods> selectTenGoods(Map map);

    //从数据库中拉出10个物品，用于在商品页面展示
    public List<Goods> selectTenGoods(Integer begin);

    //根据主键查找物品，并返回对象
    public Goods SelectGoodsByPK(String GID);

    //物品排序接口（根据价格排序）
    public List<Goods> SortBySortKind(Integer begin, Integer sortKind);

    //对ListMarketGoods做排序操作
    public List<Goods> SortList(Integer sortKind, List<Goods> goodsList);
}
