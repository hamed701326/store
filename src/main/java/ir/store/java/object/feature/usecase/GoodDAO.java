package ir.store.java.object.feature.usecase;

import ir.store.java.object.model.Good;

import java.util.List;

public interface GoodDAO {
    public List<Good> getAllGoods();
    public  void addGood(Good good);
    public Good getGood(int goodId);
    public void updateGood(Good good);
    public void deleteGood(int goodId);
}
