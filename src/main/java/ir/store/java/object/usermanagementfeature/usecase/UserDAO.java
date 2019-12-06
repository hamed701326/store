package ir.store.java.object.usermanagementfeature.usecase;

import ir.store.java.object.model.user.User;

public interface UserDAO {
    User login(String username, String password);
}
