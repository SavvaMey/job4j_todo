package store;

import model.User;

import java.util.Collection;

public interface UserStore {
    User createUser(User user);

    User findByEmailUser(String email);
}
