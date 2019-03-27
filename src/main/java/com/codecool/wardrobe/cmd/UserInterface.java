package com.codecool.wardrobe.cmd;

import com.codecool.wardrobe.api.User;

import java.util.Scanner;

public class UserInterface {

    Scanner scanner = new Scanner(System.in);

    public UserInterface() {
    }

    public void makeUser() {
        String USername = scanner.nextLine();
        System.out.println("Enter Username");
        User user = new User(USername);
    }


}

