package ir.store.java.object.usermanagementfeature.usecase;

import ir.store.java.object.core.annotation.InterfaceDAO;
import ir.store.java.object.model.user.User;

@InterfaceDAO
public interface UserDAO {
    User login(String username, String password);

    void signUp(User user);

    boolean check(String variable, String value);
}
