package com.example.mytestapp.db;

import com.example.mytestapp.db.entities.Address1;
import com.example.mytestapp.db.entities.User;

import java.util.ArrayList;

import java.util.List;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] firstName = new String[]{
            "Kevin", "Michael"};
    private static final String[] lastName = new String[]{
            "Coppey", "Puglisi"};
    private static final String[] birthDate = new String[]{
            "24.07.1997", "08.01.1994"};
    private static final String[] address = new String[]{
            String.valueOf(new Address1("Avenue Rossfeld 21","Sierre")),String.valueOf(new Address1("Rue du petit château 5","Sion"))};
    private static final String[] phoneNumber = new String[]{
    "0793989228","0787343458"};
    private static final String[] email = new String[]{
            "kevin.coppey28@hotmail.com","mcpwko@hotmail.fr"};
    private static final String[] password = new String[]{
            "password","password"};


    public static List<User> generateUsers() {
        List<User> userList = new ArrayList<>(firstName.length);
        for (int i = 0; i < firstName.length; i++) {
                User user = new User();
                user.setFirstName(firstName[i]);
                user.setLastName(lastName[i]);
                user.setAddress(address[i]);
                user.setPhoneNumber(phoneNumber[i]);
                user.setEmail(email[i]);
                user.setPassword(password[i]);
                userList.add(user);
            }

        return userList;
    }
}