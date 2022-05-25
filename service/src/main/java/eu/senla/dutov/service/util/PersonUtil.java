package eu.senla.dutov.service.util;

import eu.senla.dutov.model.people.User;

public class PersonUtil {

    private PersonUtil() {
    }

    public static void setPersonFields(User oldUser, User newUser) {
        String userName = newUser.getUserName();
        String password = newUser.getPassword();
        String name = newUser.getName();
        Integer age = newUser.getAge();
        if (userName != null) oldUser.setUserName(userName);
        if (password != null) oldUser.setPassword(password);
        if (name != null) oldUser.setName(name);
        if (age != null) oldUser.setAge(age);
    }
}
