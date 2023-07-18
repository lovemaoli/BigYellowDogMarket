package com.TropicalFlavor.dao;

import com.TropicalFlavor.po.*;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordDao {
    public boolean DeleteRecord(PurchaseRecord purchaseRecord);

    //public boolean SelectRecord(PurchaseRecord purchaseRecord);

    public boolean InsertRecord(@Param("UID") String UID, @Param("purchaseRecord") PurchaseRecord purchaseRecord);

    public boolean UpdateRecord(@Param("PID") String PID,@Param("IsSent") boolean IsSent,@Param("IsGot") boolean IsGot);

    public List<PurchaseRecord> ShowRecord(@Param("marketUser") User user,
                                           @Param("IsSent") boolean IsSent,@Param("IsGot") boolean IsGot);

    public List<TradeRecord> ShowAllRecord();

    // 添加新的方法，用于搜索销售记录
    public List<SaleRecord> SearchRecord(@Param("Gname") String Gname);
}
