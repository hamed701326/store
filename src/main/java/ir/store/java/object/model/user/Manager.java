package ir.store.java.object.model.user;

import ir.store.java.object.core.annotation.Entity;
import ir.store.java.object.feature.impl.ElectricalVehicleDAOImpl;
import ir.store.java.object.feature.impl.ReadingCaseDAOImpl;
import ir.store.java.object.feature.impl.ShoeDAOImpl;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.ElectricalVehicle;
import ir.store.java.object.model.Good;
import ir.store.java.object.model.ReadingCase;
import ir.store.java.object.model.Shoe;
import ir.store.java.object.model.user.User;

@Entity
public class Manager extends User {
    private GoodDAO electricalVehicleDAO = new ElectricalVehicleDAOImpl();
    private GoodDAO shoeDAO = new ShoeDAOImpl();
    private GoodDAO readingCaseDAO = new ReadingCaseDAOImpl();

    public Manager() {
        setRole("Manager");
    }

    public void insertData() {
        int option = 1;
        while (option != 4) {
            System.out.println("Inserting Data...");
            System.out.print("what do you want to insert? " +
                    "\n 1.Electrical Vehicles" +
                    "\n 2.Reading Cases" +
                    "\n 3.Shoes " +
                    "\n 4.End " +
                    "\n please enter:");
            option = scanner.nextInt();


            switch (option) {
                case 1:
                    electricalVehicleDAO.addGood(defineGood(1));
                    break;

                case 2:
                    readingCaseDAO.addGood(defineGood(2));
                    break;
                case 3:
                    shoeDAO.addGood(defineGood(3));
                    break;
                case 4:
                    System.out.println("Everything is done.");
                    break;
                default:
                    System.out.println("Please enter the right number:");
                    option = scanner.nextInt();

            }
        }

    }

    private Good defineGood(int option) {
        Good good = new Good();
        System.out.println("id:");
        good.setId(scanner.nextInt());
        System.out.print("name:");
        good.setName(scanner.next());
        System.out.print("Stock: ");
        good.setStock(scanner.nextInt());
        System.out.println("Price: ");
        good.setPrice(scanner.nextDouble());
        switch (option) {
            case 1:
                System.out.print("Type: ");
                ((ElectricalVehicle) good).setType(scanner.next());
                System.out.print("Power: ");
                ((ElectricalVehicle) good).setPower(scanner.nextInt());
                break;
            case 2:
                System.out.print("Type: ");
                ((ReadingCase) good).setType(scanner.next());
                System.out.print("Publication: ");
                ((ReadingCase) good).setPublication(scanner.next());
                System.out.println("Author:");
                ((ReadingCase) good).setAuthor(scanner.next());
                break;
            case 3:
                System.out.print("Type [formal/sport]: ");
                ((Shoe) good).setType(scanner.next());
                System.out.print("Size: ");
                ((Shoe) good).setSize(scanner.nextInt());
                break;
        }
        return good;
    }

    public void updateData() {
    }

    ;

    public void deleteData() {
    }

    ;
}
