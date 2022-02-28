package eu.senla.myfirstapp.app.util;


import eu.senla.myfirstapp.model.people.Person;

import javax.servlet.http.HttpSession;

public class AppUtils {

    public static void storeLoginedUser(HttpSession session, Person loginedUser) {
        session.setAttribute("loginedUser", loginedUser);
    }

    public static Person getLoginedUser(HttpSession session) {
        return (Person) session.getAttribute("loginedUser");
    }
}
