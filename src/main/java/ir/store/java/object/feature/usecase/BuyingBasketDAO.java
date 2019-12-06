package ir.store.java.object.feature.usecase;

import ir.store.java.object.model.Good;

import java.util.List;

public interface BuyingBasketDAO {
    public List<Good> getAllGoods(int userId);
    public  void addGood(Good good,int userId);
    public Good getGood(int goodId,int userId);
    public void updateGood(Good good,int userId);
    public void deleteGood(int goodId,int userId);

}
