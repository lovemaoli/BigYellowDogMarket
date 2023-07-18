package com.TropicalFlavor.po;

import java.util.List;

public class ShoppingCarts {
    private List<com.TropicalFlavor.po.Goods> Goods;

    public ShoppingCarts(List<com.TropicalFlavor.po.Goods> goods)
    {
        Goods = goods;
    }

    public List<com.TropicalFlavor.po.Goods> getGoods()
    {
        return Goods;
    }

    public void setGoods(List<com.TropicalFlavor.po.Goods> goods)
    {
        Goods = goods;
    }

    public void insertGoods(com.TropicalFlavor.po.Goods goods) { Goods.add(goods); }

    public void deleteGoods(com.TropicalFlavor.po.Goods goods) { Goods.remove(goods); }

    @Override
    public String toString()
    {
        return "ShoppingCart{" +
                "Goods=" + Goods +
                '}';
    }
}
