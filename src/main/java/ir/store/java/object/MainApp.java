package ir.store.java.object;
import ir.store.java.object.feature.impl.BuyingBasketDAOImpl;
import ir.store.java.object.feature.impl.GoodDAOImpl;
import ir.store.java.object.feature.usecase.BuyingBasketDAO;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.Good;

import java.util.*;

public class MainApp {
    private static Scanner scanner=new Scanner(System.in);
    private static GoodDAO goodDAO=new GoodDAOImpl();
    public static void main(String[] args) {
        BuyingBasketDAO buyingBasketDAO=new BuyingBasketDAOImpl();

        // filling shopStore:
        /*
        //entering user step:
        System.out.print("---------------\tStore.com\t------------ \n \t 1.Sign in \n \t 2.Sign up \n Choose:");
        int option=scanner.nextInt();
        User user=new User();
        switch(option){
            case 1: user.signIn(); ;break;
            case 2:user.signUp();break;
            default: System.out.print("it's wrong!");
        }

        // todo creating menu {1.printresult 2.buy menu{shoe,ReadingCase,ElectricalVehicle} 3.}
        // Shopping Step:
        int shopStop=1;
        while(shopStop==1){
            int optionUser=0;
            List<Integer> options=new ArrayList<>();
            Collections.addAll(options,1,2,3,4);

            while(!options.contains(optionUser)) {
                System.out.print("what do you want to do?\n\t 1.Show List \n\t 2.Show buying basket\n\t 3.buy new thing " +
                        "\n\t 4.retrieve thing\n Choose option: ");
                optionUser = scanner.nextInt();
                switch (optionUser) {
                    case 1:
                        showStoreList();
                        break;
                    case 2:
                        showBuyingBasket();
                        break;
                    case 3:
                        buy();
                        break;
                    case 4:
                        retrieve();
                        break;
                    default:
                        System.out.println("it's wrong!");

                }
            }

            System.out.print("Do you wanna keep shopping?\n\t 0.exist\n\t 1.keep\n Enter:");
            shopStop= scanner.nextInt();*/
            showStoreList(goodDAO);
            //buy(buyingBasketDAO);
            buyingBasketDAO.deleteGood(0);
            showBuyingBasket(buyingBasketDAO);
    }



    private static void retrieve() {
    }

    private static void buy(BuyingBasketDAO buyingBasketDAO) {
        GoodDAO goodDAO=new GoodDAOImpl();
        System.out.println("Buy...");
        System.out.print("enter the Id:");
         int idgood=scanner.nextInt();
         Good good=goodDAO.getGood(idgood);
         good.setStock(good.getStock()-4);
         goodDAO.updateGood(good);
        good.setStock(4);
         buyingBasketDAO.addGood(good);
    }

    private static void showBuyingBasket(BuyingBasketDAO buyingBasketDAO) {

        List<Good> goods=buyingBasketDAO.getAllGoods();
        System.out.println("----------------\t Buying Basket \t ---------------");

        goods.stream().forEach(good -> System.out.println(good.getId()+"\t"
                +good.getName()+"\t"
                +good.getStock()+"\t"
                +good.getPrice()));
    }

    private static void showStoreList(GoodDAO goodDAO){
           List<Good> goods=goodDAO.getAllGoods();
           goods.stream().forEach(good -> System.out.println(good.getId()+"\t"
                   +good.getName()+"\t"
                   +good.getStock()+"\t"
                   +good.getPrice()));

    }
    public static void signIn(){
        System.out.println("Sign in:");
        System.out.print("Username:");
        String userName=scanner.next();
        //ToDo checking username
        System.out.println("Password:");
        String passWord=scanner.next();
        //ToDo checking password

    }
    public static void signUp(){
        System.out.println("Sign up:");
        System.out.print("Username:");
        String userName=scanner.next();
        System.out.print("password");
        String passWord=scanner.next();

    }
}
