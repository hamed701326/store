package ir.store.java.object;

import ir.store.java.object.core.annotation.Implementation;
import ir.store.java.object.feature.impl.GoodDAOImpl;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.Good;
import ir.store.java.object.model.user.*;
import ir.store.java.object.usermanagementfeature.impl.UserDAOImpl;
import ir.store.java.object.usermanagementfeature.usecase.UserDAO;

import javax.swing.text.TabableView;
import java.util.*;

public class MainApp {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--------------------- Welcome to Shop Website ---------------------- ");
        /*User user=new User();
        user.setRole("Manager");
        user.setUserName("hamed.sadeghi");user.setPassWord("14tiR1375");
        user.setAddress(new Address("kerman","manoojan","hazizi",7896666111L));
        System.out.println(user.getAddress().toString());
        user.setSpecification(new Specification("hamed",
                "sadeghi",
                "sadeghi0hamed",
                "09104961352"));

        UserDAO userDAO=new UserDAOImpl();
        userDAO.signUp(user);
        */
        System.out.print("\t1.Manager Account" +
                "\n\t2.User Account" +
                "\n please choose:");

        int optionPerson = scanner.nextInt();
        if (optionPerson == 1) {
            // entering for manager:
            System.out.println("-----------\tManager Account\t-------");
            Manager manager = new Manager();
            if (manager.signIn().getRole() == "Manager") {
                int option = 1;
                while (option != 4) {
                    System.out.print("what do you want to do?" +
                            "\n 1. insert" +
                            "\n 2. update(disable)" +
                            "\n 3. delete(disable)" +
                            "\n 4. exit" +
                            "please enter a number:");
                    option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            manager.insertData();
                            break;
                        case 2:
                            manager.updateData();
                            break;
                        case 3:
                            manager.deleteData();
                            break;
                        case 4:
                            System.out.print("you're exited.");
                            break;
                        default:
                            System.out.println("please enter the right number.");
                    }
                }
            } else {
                System.out.println("Username or password is not right.");
            }

        } else {
            //entering user step:
            System.out.println("Users Account:");

            int option = 0;
            while (option != 3) {
                System.out.print("\n \t 1.Sign in \n \t 2.Sign up\n \t 3.Exit \n Choose:");
                User user = new User();
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        user = user.signIn();

                        // Shopping Step for user:
                        if (user != null) {
                            System.out.println("you're enter in your account.");
                            System.out.println("User Name: '" + user.getUserName() + "\'\n" +
                                    "Role: '" + user.getRole() + '\'');
                            shoppingOperation(user);
                        } else System.out.print("your password or username is not right.");
                        break;

                    case 2:
                        user.signUp();
                        break;
                    case 3:
                        System.out.print("Exit...");
                        break;
                    default:
                        System.out.print("it's wrong!");
                }
            }

        }



         /*
        BuyingBasketDAO buyingBasketDAO=new BuyingBasketDAOImpl();
        System.out.println(buyingBasketDAO.getGood(11).toString());*/

    }

    public static void showStoreList() {
        GoodDAO goodDAO = new GoodDAOImpl();
        List<Good> goods = goodDAO.getAllGood();
        System.out.println("-----------\tStore List\t------------");
        System.out.println("-------------------------------------------------------------------------------------");
        String format = "|%1$-10s|%2$-25s|%3$-10s|%4$-10s|%5$-50s\n";
        System.out.format(format, "Id_Good", "Name", "Stock", "price", "Details");
        goods.stream().forEach(good -> System.out.format(format,
                good.getId(), good.getName(), good.getStock(), good.getPrice(), good.getDetails()));

    }

    private static void shoppingOperation(User user) {
        GoodDAO goodDAO = new GoodDAOImpl();
        int optionUser = 4;

        while (optionUser != 5) {
            System.out.print("what do you want to do?\n\t 1.Show List \n\t 2.Show buying basket\n\t 3.buy new thing " +
                    "\n\t 4.retrieve thing \n\t 5.exit\n Choose option: ");
            optionUser = scanner.nextInt();
            switch (optionUser) {
                case 1:
                    showStoreList();
                    break;
                case 2:
                    user.showBuyingBasket();
                    break;
                case 3:
                    if (user.getSizeBuy() >= 5) {
                        System.out.println("some of shopping is limited by 5. you can't buy more than.");
                    } else {
                        user.buy(goodDAO);
                    }
                    break;
                case 4:
                    user.retrieve(goodDAO);
                    break;
                case 5:
                    System.out.println("shopping is ended." +
                            "\n Thank you for shopping :):).");
                    break;
                default:
                    System.out.println("it's wrong!");

            }
        }

    }

}
