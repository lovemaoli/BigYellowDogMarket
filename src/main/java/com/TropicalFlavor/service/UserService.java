package com.TropicalFlavor.service;

import com.TropicalFlavor.po.*;

public interface UserService {
    //注册函数
    boolean Register(User user);
    //登录函数
    public User Login(String userName, String password, boolean IsAdmin);
    //找到物品的主人
    public User WhoseGoods(String GID);
    //改变个人信息函数
    public boolean ChangeInfo(User user);
    //新增售卖物品
    public boolean PushSaleGoods(String UID, Goods goods);
    //删除售卖物品
    public boolean RemoveSaleGoods(String UID, Goods goods);
    //物品售罄
    public boolean GoodsSoldOut(Goods goods);
    //添加物品到购物车
    public boolean PushShoppingCart(String UID, Goods goods);
    //从购物车中删除
    public boolean RemoveShoppingCart(String UID, Goods goods);
    //从购物车中结算
    public boolean BuyFromShoppingCart(String UID, Goods goods, String Time, String Date, Double number);
    //直接购买
    public Integer BuyDirectly(String UID, Goods goods, String Time, String Date, Double number);
    //显示购物车
    public ShoppingCarts ShowCar(User user);
    //显示订单
    public PurchaseRecordLists ShowPRecord(User user, boolean IsSent, boolean IsGot);
    //更新订单状态：设置已发货、已确认收货
    public boolean UpdateRecordStatus(String PID,boolean IsSent,boolean IsGot);
    //显示售卖表
    public SaleGoods ShowSales(User user);
    //显示售卖记录
    public SaleRecordLists ShowSRecord(User user, boolean IsSent, boolean IsGot);
    //根据UID返回MarketUser对象
    public User WhichUser(String UID);

}
