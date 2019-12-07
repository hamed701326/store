package ir.store.java.object.model.user;

import ir.store.java.object.MainApp;
import ir.store.java.object.core.annotation.Entity;
import ir.store.java.object.feature.impl.BuyingBasketDAOImpl;
import ir.store.java.object.feature.usecase.BuyingBasketDAO;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.Good;
import ir.store.java.object.model.user.Address;
import ir.store.java.object.model.user.Specification;
import ir.store.java.object.usermanagementfeature.impl.UserDAOImpl;
import ir.store.java.object.usermanagementfeature.usecase.UserDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Entity
public class User {
    private UserDAO userDAO = new UserDAOImpl();
    private int userId;
    private String userName;
    private String passWord;
    private String role;
    private Address address = new Address();
    Scanner scanner = new Scanner(System.in);
    private Specification specification = new Specification();
    private static Set<Integer> setIdGood;

    private static BuyingBasketDAO buyingBasketDAO = new BuyingBasketDAOImpl();

    public User() {
        setIdGood = new HashSet<Integer>();
        setRole("User");
    }

    public void retrieve(GoodDAO goodDAO) {
        System.out.println("... Main page ...");
        System.out.println("retrieve ...");
        showBuyingBasket();
        System.out.print("enter the Id:");
        int idgood = scanner.nextInt();
        Good good = goodDAO.getGood(idgood);
        Good good1 = buyingBasketDAO.getGood(idgood, getUserId());
        // asking how many user want of this good:
        System.out.print("how many do you wanna retrieve of ? :");
        int numberGoods = scanner.nextInt();

        //updating basket
        int diff = good1.getStock() - numberGoods;
        if (diff >= 0) {
            // updating goods table
            good.setStock(good.getStock() + numberGoods);
            goodDAO.updateGood(good);
            if (diff == 0) {
                buyingBasketDAO.deleteGood(idgood, getUserId());
                setIdGood.remove(idgood);
            } else {
                good1.setStock(diff);
                buyingBasketDAO.updateGood(good1, getUserId());
            }
        } else {
            System.out.println("there are only " + good1.getStock() +
                    " of this good in basket, you can't retrieve more than");
        }

    }

    public void buy(GoodDAO goodDAO) {
        System.out.println("... Main page ...");
        System.out.println("Buy...");
        MainApp.showStoreList();
        System.out.print("enter the Id for good:");

        int idgood = scanner.nextInt();
        //finding good
        Good good = goodDAO.getGood(idgood);

        //checking good
        while (good == null) {
            System.out.println("there aren't any good with id=" + idgood);
            // asking another id:
            System.out.println("please enter the right Id:");
            idgood = scanner.nextInt();
            good = goodDAO.getGood(idgood);
        }
        Good good1 = buyingBasketDAO.getGood(idgood, getUserId());
        // asking how many user want of this good:
        System.out.print("how many do you want to get of this good :");
        int numberGoods = scanner.nextInt();
        int diff = good.getStock() - numberGoods;
        // updating goods database
        if (diff >= 0) {
            if (diff == 0) {
                goodDAO.deleteGood(idgood);
            } else {
                good.setStock(diff);
                goodDAO.addGood(good);
            }
            //updating basket
            good.setStock(numberGoods);
            if (good1 != null) {
                good.setStock(numberGoods + good1.getStock());
            }
            buyingBasketDAO.addGood(good, getUserId());
            setIdGood.add(idgood);
        } else {
            System.out.println("there are only " + good.getStock() +
                    " of this good in store, you can't buy more than");
        }
    }

    public void showBuyingBasket() {

        List<Good> goods = buyingBasketDAO.getAllGoods(getUserId());
        System.out.println("----------------\t Buying Basket \t ---------------");
        String format = "|%1$-10s|%2$-25s|%3$-10s|%4$-10s|%5$-50s\n";
        System.out.flush();
        System.out.format(format, "Id_Good", "Name", "Stock", "price", "Details");
        goods.stream().forEach(good -> System.out.format(format,
                good.getId(),
                good.getName(),
                good.getStock(),
                good.getPrice(),
                good.getDetails()));
    }

    public int getSizeBuy() {
        return setIdGood.size();
    }

    public User signIn() {
        System.out.println("Sign in:");
        System.out.print("Username:");
        setUserName(scanner.next());
        //ToDo checking username
        System.out.print("Password:");
        setPassWord(scanner.next());
        //ToDo checking password

        return userDAO.login(userName, passWord);
    }

    public void signUp() {
        System.out.println("Create Account:");
        System.out.println("------------------------");
        System.out.print("\tName: ");
        specification.setName(scanner.next());
        System.out.print("\tLast-Name: ");
        specification.setLastName(scanner.next());
        System.out.print("\tPhone Number: ");
        specification.setPhoneNumber(scanner.next());
        System.out.print("\tEmail Address: ");
        specification.setEmailAddress(scanner.next());
        System.out.print("\tUsername:");
        String userName = scanner.next();
        //check user name
        while (!userDAO.check("username", userName)) {
            System.out.print("there are a user similar to this username\n" +
                    "Enter another Username:");
            userName = scanner.next();
        }
        setUserName(userName);
        setPassword();
        System.out.println("------------------------");
        System.out.println("Address");
        System.out.print("\tState:");
        address.setState(scanner.next());
        System.out.print("\tCity:");
        address.setCity(scanner.next());
        System.out.print("\tStreet:");
        address.setStreet(scanner.next());
        System.out.print("\tEnter zipCode:");
        address.setZipCode(scanner.nextLong());
        userDAO.signUp(this);

    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setPassword() {
        System.out.print("\tPassword: ");
        passWord = scanner.next();
        if (checkPassword(passWord)) {
            System.out.println("Your password is okay.");
        } else {
            setPassword();
        }
    }

    private boolean checkPassword(String pwd) {
        int c = 0;
        if (pwd.toLowerCase() == pwd) {
            System.out.println("Your password doesn't have upper character.");
            c = 1;
        } else if (pwd.toUpperCase() == pwd) {
            System.out.println("Your password doesn't have upper character.");
            c = 1;
        } else if (pwd.length() < 6) {
            System.out.println("Your password isn't long");
            c = 1;
        }
        return c == 0;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public Address getAddress() {
        return address;
    }

    public Specification getSpecification() {
        return specification;
    }
}

