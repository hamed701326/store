package ir.store.java.object.model.user;

import ir.store.java.object.MainApp;
import ir.store.java.object.feature.impl.BuyingBasketDAOImpl;
import ir.store.java.object.feature.usecase.BuyingBasketDAO;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.Good;
import ir.store.java.object.model.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypicalUser extends User {
    private static Set<Integer> setIdGood;

    private static BuyingBasketDAO buyingBasketDAO=new BuyingBasketDAOImpl();
    public TypicalUser(){
        setIdGood=new HashSet<Integer>();
    }
    public void retrieve(GoodDAO goodDAO) {
        System.out.println("... Main page ...");
        System.out.println("retrieve ...");
        showBuyingBasket();
        System.out.print("enter the Id:");
        int idgood=scanner.nextInt();
        Good good=goodDAO.getGood(idgood);
        Good good1=buyingBasketDAO.getGood(idgood,getUserId());
        // asking how many user want of this good:
        System.out.println("how many do you wanna retrieve of \n"+good.toString()+":");
        int numberGoods=scanner.nextInt();

        //updating basket
        int diff=good1.getStock()-numberGoods;
        if(diff>=0) {
            // updating goods table
            good.setStock(good.getStock()+numberGoods);
            goodDAO.updateGood(good);
            if(diff==0){
                buyingBasketDAO.deleteGood(idgood,getUserId());
                setIdGood.remove(idgood);
            }
            else {
                good1.setStock(diff);
                buyingBasketDAO.updateGood(good1,getUserId());
            }
        }
        else {
            System.out.println("there are only "+good1.getStock()+
                    " of this good in basket, you can't retrieve more than");
        }

    }

    public void buy(GoodDAO goodDAO) {
        System.out.println("... Main page ...");
        System.out.println("Buy...");
        MainApp.showStoreList();
        System.out.print("enter the Id for good:");

        int idgood=scanner.nextInt();
        //finding good
        Good good =goodDAO.getGood(idgood);

        //checking good
        while (good ==null){
            System.out.println("there aren't any good with id="+idgood);
            // asking another id:
            System.out.println("please enter the right Id:");
            idgood=scanner.nextInt();
            good =goodDAO.getGood(idgood);
        }
        Good good1=buyingBasketDAO.getGood(idgood,getUserId());
        // asking how many user want of this good:
        System.out.print("how many do you want of this\n"
                + good.toString()+" :");
        int numberGoods=scanner.nextInt();
        int diff= good.getStock()-numberGoods;
        // updating goods database
        if(diff>=0) {
            if(diff==0){
                goodDAO.deleteGood(idgood);
            }else{
                good.setStock(diff);
                goodDAO.addGood(good);
            }
            //updating basket
            good.setStock(numberGoods);
            if(good1!=null) {
                good.setStock(numberGoods + good1.getStock());
            }
            buyingBasketDAO.addGood(good,getUserId());
            setIdGood.add(idgood);
        }
        else {
            System.out.println("there are only "+good.getStock()+
                    " of this good in store, you can't buy more than");
        }
    }
    public void showBuyingBasket() {

        List<Good> goods=buyingBasketDAO.getAllGoods(getUserId());
        System.out.println("----------------\t Buying Basket \t ---------------");
        System.out.println("Id\tName\tStock\tPrice\tDetails");
        goods.stream().forEach(good -> System.out.println(good.getId()+"\t"
                +good.getName()+"\t"
                +good.getStock()+"\t"
                +good.getPrice()+"\t"
                +good.getDetails()));
    }
    public int getSizeBuy(){
        return setIdGood.size();
    }

}
