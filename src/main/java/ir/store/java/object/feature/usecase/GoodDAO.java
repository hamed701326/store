package ir.store.java.object.feature.usecase;

import ir.store.java.object.core.annotation.InterfaceDAO;
import ir.store.java.object.model.Good;

import java.util.List;

@InterfaceDAO
public interface GoodDAO {
    public List<Good> getAllGood();

    public void addGood(Good good);

    public Good getGood(int goodId);

    public void updateGood(Good good);

    public void deleteGood(int goodId);
}
