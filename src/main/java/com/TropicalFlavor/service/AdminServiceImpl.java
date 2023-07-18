package com.TropicalFlavor.service;

import com.TropicalFlavor.dao.RecordDao;
import com.TropicalFlavor.dao.SalesDao;
import com.TropicalFlavor.dao.UserDao;
import com.TropicalFlavor.po.User;
import com.TropicalFlavor.po.SaleRecord;
import com.TropicalFlavor.po.TradeRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Transactional
@Service
public class AdminServiceImpl implements AdminService{

    @Resource
    private UserDao userDao;

    @Resource
    private SalesDao salesDao;

    @Resource
    private RecordDao recordDao;
    @Override
    public boolean InsertMarketUser(User user) {
        if (!userDao.InsertUser(user)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean DeleteMarketUser(String UID) {
        if(!userDao.DeleteUser(userDao.SelectUser(UID))){
            return false;
        }
        return true;
    }

    //注销用户
    @Override
    public boolean logOffMarketUser(String UID)
    {
        User user = userDao.SelectUser(UID);
        user.setStatus(2);

        if(!userDao.ChangeInfo(user))
            return false;
        return true;
    }

    @Override
    public List<User> ShowUser() {
        List<User> userList = userDao.ShowUser();
        Iterator<User> i = userList.iterator();
        while (i.hasNext()){
            if(i.next().getUID().substring(0,4).equals("ADMI")){
                i.remove();
            }
        }
        return userList;
    }
    @Override
    public List<TradeRecord> ShowAllRecord()
    {
        List<TradeRecord> tradeRecords= recordDao.ShowAllRecord();
        return  tradeRecords;
    }
    //修改用户信息
    @Override
    public boolean ChangeUserInfo(User user) {
        if(!userDao.ChangeInfo(user))
            return false;
        return true;
    }

    //搜索销售记录
    @Override
    public List<SaleRecord> SearchRecord(String Gname) {
        return salesDao.SearchRecord(Gname);
    }
}
