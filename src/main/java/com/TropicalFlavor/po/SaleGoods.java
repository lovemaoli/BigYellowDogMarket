package com.TropicalFlavor.po;

import java.util.List;

public class SaleGoods {
    private List<com.TropicalFlavor.po.Goods> Goods;

    public List<com.TropicalFlavor.po.Goods> getGoods()
    {
        return Goods;
    }

    public void setGoods(List<com.TropicalFlavor.po.Goods> goods)
    {
        Goods = goods;
    }

    public SaleGoods(List<com.TropicalFlavor.po.Goods> goods)
    {
        Goods = goods;
    }

    @Override
    public String toString()
    {
        return "SaleGoods{" +
                "Goods=" + Goods +
                '}';
    }
}
