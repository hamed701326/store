package ir.store.java.object.feature.usecase;

import ir.store.java.object.model.Shoe;

import java.util.List;

public interface ShoeDAO {

    public List<Shoe> getAllShoe();
    public  void addShoe(Shoe shoe);
    public Shoe getShoe(int shoeId);
    public void updateShoe(Shoe shoe);
    public void deleteShoe(int shoeId);


}
