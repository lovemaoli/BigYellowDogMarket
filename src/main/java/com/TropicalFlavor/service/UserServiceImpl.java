package com.TropicalFlavor.service;

import com.TropicalFlavor.dao.*;
import com.TropicalFlavor.util.Md5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.TropicalFlavor.po.*;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService
{
    @Resource
    private UserDao userDao;

    @Resource
    private SalesDao salesDao;

    @Resource
    private CarDao carDao;

    @Resource
    private RecordDao recordDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private SRecordDao sRecordDao;

    @Resource
    private UtilsDao utilsDao;

    @Override
    public User Login(String sno, String password, boolean IsAdmin) {
        //这里是根据用户名密码从数据库中查找，找到创建对象marketUser，返回true，否则返回false
        String UID = userDao.IsTrue(sno, Md5Util.getMD5(password));

        //System.out.println(UID);

        if((UID != null)&&(UID.substring(0,4).equals("ADMI"))==IsAdmin)
        {
            return userDao.SelectUser(UID);
        }
        //
        return null;
    }


    @Override
    public boolean ChangeInfo(User user) {
        //修改用户信息，这里要添加数据库修改
        return userDao.ChangeInfo(user);
        //
    }

    @Override
    public User WhoseGoods(String GID)
    {
        return userDao.SelectUser(salesDao.WhoseGoods(GID));
    }


    public String getGID()
    {
        long  GID=0;
        try
        {
            GID=utilsDao.selectMaxGID();
            return String.format("%d",GID+1);
        }
        catch (Exception e)
        {
            GID=1000000000;
            return String.format("%d",GID+1);
            //e.printStackTrace();
        }

    }
    public String getPID()
    {
        long  PID=0;
        try
        {
            PID=utilsDao.selectMaxPID();
            return String.format("%d",PID+1);
        }
        catch (Exception e)
        {
            PID=1000000000;
            return String.format("%d",PID+1);
            //e.printStackTrace();
        }
        //return String.format("%d",utilsDao.selectMaxPID()+1);
    }

    @Override
    public boolean PushSaleGoods(String UID, Goods goods) {
        //获取当前最小可用GID，并赋给marketGoods
        String GID = getGID();
        goods.setGID(GID);
        //这里要往数据库（售卖物品表和物品表）中添加物品
        if (!goodsDao.InsertGoods(goods)) {
            return false;
        }
        if(!salesDao.InsertGoods(UID, goods)){
            return false;
        }
        return true;
        //
    }

    @Override
    public boolean RemoveSaleGoods(String UID, Goods goods) {
        //从数据库（售卖物品表和物品表）中删除物品
        if (!salesDao.DeleteGoods(UID, goods)) {
            return false;
        }
        if (!goodsDao.DeleteGoods(goods)) {
            return false;
        }
        return true;
        //
    }

    @Override
    public boolean GoodsSoldOut(Goods goods) {
        return goodsDao.GoodsSoldOut(goods);
    }

    @Override
    public boolean PushShoppingCart(String UID, Goods goods) {
        //先从物品表中找到物品，再往数据库（购物车表）中添加物品
        return carDao.InsertGoods(UID, goods);
        //
    }

    @Override
    public boolean RemoveShoppingCart(String UID, Goods goods) {
        //从数据库（购物车表）中删除物品
        return carDao.DeleteGoods(UID, goods);
        //
    }

    @Override
    public boolean BuyFromShoppingCart(String UID, Goods goods, String Time, String Date, Double number)
    {
        //从数据库（购物车表）中删除物品
//        if(!carDao.DeleteGoods(UID,marketGoods))
//            return false;

        //更新购物车信息，而非删除购物车
        carDao.ChangeCart(UID, new Goods(goods.getGID(), goods.getName(), goods.getKind(), goods.getPrice(), goods.getNumber()-number));

        //如果购物车中此商品为0，从购物车中删除此商品
        if(goods.getNumber() - number < 1e-6)
            carDao.DeleteGoods(UID, goods);

        //往数据库（购买记录表）中添加记录
        String PID = getPID();

        goodsDao.ChangeInfo(new Goods(goods.getGID(), goods.getName(), goods.getKind(), goods.getPrice(), goods.getNumber()-number));

        if(!recordDao.InsertRecord(UID,new PurchaseRecord(PID,Date,Time, goods,number)))
            return false;
        //
        return true;
    }

    @Override
    public Integer BuyDirectly(String UID, Goods goods, String Time, String Date, Double number) {
        //先从物品表中找到物品，往数据库（购买记录表）中添加记录
        String PID = getPID();

        if(UID.equals(salesDao.WhoseGoods(goods.getGID())))
            return 1;

        if(number<= goods.getNumber()||Math.abs(number- goods.getNumber())<1e-5)
        {
            goodsDao.ChangeInfo(new Goods(goods.getGID(), goods.getName(), goods.getKind(), goods.getPrice(), goods.getNumber()-number));
            recordDao.InsertRecord(UID,new PurchaseRecord(PID,Date,Time, goods,number));
            return 2;
        }
        else {

            return 3;
        }
    }
    /*
    @Override
    public ShoppingCart ShowCar(MarketUser marketUser) {
        //从数据库中购物车表中取出数据
        return new ShoppingCart(carDao.ShowGoods(marketUser));
        //
    }*/

    @Override
    public ShoppingCarts ShowCar(User user)
    {
        ShoppingCarts shoppingCarts = new ShoppingCarts(carDao.ShowGoods(user));

        List<Goods> goodsList = shoppingCarts.getGoods();

        for(Goods each : goodsList)
        {
            Double goodsNumInCar = each.getNumber();
            Double goodsCurrentNum = goodsDao.SelectGoods(each.getGID()).getNumber();

            if(!goodsNumInCar.equals(goodsCurrentNum))
            {
                carDao.ChangeCart(user.getUID(), new Goods(each.getGID(),each.getName(), each.getKind(), each.getPrice(),goodsCurrentNum));
                if(goodsCurrentNum == 0.0)
                    carDao.DeleteGoods(user.getUID(), each);
            }
        }

        //从数据库中购物车表中取出数据
        return new ShoppingCarts(carDao.ShowGoods(user));
        //
    }


    @Override
    public PurchaseRecordLists ShowPRecord(User user, boolean IsSent, boolean IsGot)
    {
        return new PurchaseRecordLists(recordDao.ShowRecord(user,IsSent,IsGot));
    }

    @Override
    public boolean UpdateRecordStatus(String PID, boolean IsSent, boolean IsGot)
    {
        return recordDao.UpdateRecord(PID,IsSent,IsGot);
    }

    @Override
    public SaleGoods ShowSales(User user)
    {
        return new SaleGoods(salesDao.ShowGoods(user));
    }

    @Override
    public SaleRecordLists ShowSRecord(User user, boolean IsSent, boolean IsGot) {
        return new SaleRecordLists(sRecordDao.ShowRecord(user,IsSent,IsGot));
    }

    @Override
    public boolean Register(User user) {
        //往数据库（用户表）中添加信息
        user.setPassword(Md5Util.getMD5(user.getPassword()));
        return userDao.InsertUser(user);
        //
    }

    @Override
    public User WhichUser(String UID)
    {
        return userDao.SelectUser(UID);
    }
}
