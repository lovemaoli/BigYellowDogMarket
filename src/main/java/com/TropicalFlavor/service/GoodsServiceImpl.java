package com.TropicalFlavor.service;

import com.TropicalFlavor.dao.*;
import com.TropicalFlavor.po.Goods;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional
@Service
public class GoodsServiceImpl implements GoodsService{

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private SalesDao salesDao;

    @Resource
    private UserDao userDao;

    @Override
    public boolean ChangeGoodsInfo(Goods goods)
    {
        return goodsDao.ChangeInfo(goods);
    }

    @Override
    public List<Goods> SearchGoods(String Keywords) {
        List<String> KeywordsList = new ArrayList<String>(Arrays.asList(Keywords.split("\\s+")));
        Map<String, Goods> TempMap = new HashMap<>();
        Set<Goods> tempListWithGoods = new HashSet<>();
        for (String s : KeywordsList) {
            tempListWithGoods.addAll(goodsDao.SelectGoodsByName(s));
        }
        for (String s : KeywordsList) {
            tempListWithGoods.addAll(goodsDao.SelectGoodsByKind(s));
        }
        for(Goods i: tempListWithGoods)
        TempMap.put(i.getGID(),i);
        return new ArrayList<>(TempMap.values());
    }

    @Override
    public List<Goods> SearchGoodsByKind(String Keywords) {
        List<String> KeywordsList = new ArrayList<>(Arrays.asList(Keywords.split("\\s+")));
        List<Goods> tempListWithGoods = new ArrayList<>();
        for (String s : KeywordsList) {
            tempListWithGoods.addAll(goodsDao.SelectGoodsByKind(s));
        }
        return tempListWithGoods;
    }

    @Override
    public Integer getGoodsSum()
    {
        return goodsDao.getGoodsSum();
    }

    @Override
    public String WhoseGoods(String GID)
    {
        return salesDao.WhoseGoods(GID);
    }

    @Override
    public List<Goods> selectTenGoods(Integer begin)
    {
        return goodsDao.selectByGID(begin);
    }

    @Override
    public Goods SelectGoodsByPK(String GID)
    {
        return goodsDao.SelectGoods(GID);
    }

    @Override
    public List<Goods> SortBySortKind(Integer begin, Integer sortKind) {
        switch (sortKind){
            case 0:
                return goodsDao.selectByPrice(true, begin);
            case 1:
                return goodsDao.selectByPrice(false, begin);
            case 2:
                return goodsDao.selectByNumber(true, begin);
            case 3:
                return goodsDao.selectByNumber(false, begin);
            case 4:
                return goodsDao.selectByGID(begin);
        }
        return null;
    }

    @Override
    public List<Goods> SortList(Integer sortKind, List<Goods> goodsList) {
        switch (sortKind){
            case 0: goodsList.sort(new Comparator<Goods>() {
                @Override
                public int compare(Goods o1, Goods o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            });
                break;
            case 1: goodsList.sort(new Comparator<Goods>() {
                @Override
                public int compare(Goods o1, Goods o2) {
                    return -o1.getPrice().compareTo(o2.getPrice());
                }
            });
                break;
            case 2: goodsList.sort(new Comparator<Goods>() {
                @Override
                public int compare(Goods o1, Goods o2) {
                    return o1.getNumber().compareTo(o2.getNumber());
                }
            });
                break;
            case 3: goodsList.sort(new Comparator<Goods>() {
                @Override
                public int compare(Goods o1, Goods o2) {
                    return -o1.getNumber().compareTo(o2.getNumber());
                }
            });
                break;
            case 4: goodsList.sort(new Comparator<Goods>() {
                @Override
                public int compare(Goods o1, Goods o2) {
                    return o1.getGID().compareTo(o2.getGID());
                }
            });
                break;
            default: break;
        }
        return goodsList;
    }
}
