package ir.store.java.object.model.user;

import ir.store.java.object.model.user.Address;
import ir.store.java.object.model.user.Specification;
import ir.store.java.object.usermanagementfeature.impl.UserDAOImpl;
import ir.store.java.object.usermanagementfeature.usecase.UserDAO;

import java.util.Scanner;

public class User {
    private int userId;
    private String userName;
    private String passWord;
    private String role;
    private Address address;
    Scanner scanner=new Scanner(System.in);
    private Specification specification;
    public User signIn(){
        System.out.println("Sign in:");
        System.out.print("Username:");
        setUserName(scanner.next());
        //ToDo checking username
        System.out.print("Password:");
        setPassWord(scanner.next());
        //ToDo checking password
        UserDAO userDAO= new UserDAOImpl();
        return userDAO.login(userName,passWord);
    }
    public void signUp(){
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
        setUserName(scanner.next());
        setPassword();
        System.out.println("------------------------");
        System.out.println("Address");
        System.out.print("\tState:");
        address.setState(scanner.next());
        System.out.print("\tCity:");
        address.setCity(scanner.next());
        System.out.print("\tStreet:");
        System.out.print("\tEnter zipCode:");

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

    public void setPassWord(String passWord){
        this.passWord=passWord;
    }
    public void setPassword() {
        System.out.print("Password: ");
        passWord=scanner.next();
        if (checkPassword(passWord)) {
            setPassword();
        } else {
            System.out.println("Your password is okay.");
        }
    }

    private boolean checkPassword(String pwd){
        int c=0;
        if (pwd.toLowerCase()==pwd){ System.out.println("Your password doesn't have upper character.");c=1;}
        else if(pwd.toUpperCase()==pwd){ System.out.println("Your password doesn't have upper character.");c=1;}
        else if(pwd.length()<6) {System.out.println("Your password isn't long");c=1;}
        return c==0;

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

