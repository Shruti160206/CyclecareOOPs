package com.cyclecare.user;

import java.util.Scanner;

public class UserDetails {
    private String userName;
    private int userAge;
    private int userWeight;
    private int userHeight;
    private String user_email;
    private int avgCycleLength;
    private int avgPeriodLength;

    public UserDetails() {
        // default constructor
    }

    // Method for user details
    public void userInformation() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter name: ");
            userName = sc.nextLine();

            System.out.print("Enter age: ");
            userAge = sc.nextInt();

            System.out.print("Enter weight (kg): ");
            userWeight = sc.nextInt();

            System.out.print("Enter height (cm): ");
            userHeight = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter email: ");
            user_email = sc.nextLine();

            System.out.print("Please Enter average cycle length in days: ");
            avgCycleLength = sc.nextInt();

            System.out.print("Please Enter average period length in days: ");
            avgPeriodLength = sc.nextInt();

        } catch (Exception e) {
            System.out.println("Sorry,Invalid input. Try again!");
        }
    }


    public String getName() {
        return userName;
    }
    public int getAge() {
        return userAge;
    }
    public int getWeight() { return
            userWeight;
    }
    public int getHeight() {
        return userHeight;
    }
    public String getEmail() {
        return user_email;
    }
    public int getAvgCycleLength() {
        return avgCycleLength;
    }
    public int getAvgPeriodLength() {
        return avgPeriodLength;
    }

    @Override
    public String toString() {
        return "\nUSER DETAILS" + "\nName: " + userName + "\nAge: " + userAge + "\nWeight: " + userWeight + " kg" + "\nHeight: " + userHeight + " cm" + "\nEmail: " + user_email + "\nAverage Cycle Length: " + avgCycleLength + " days" + "\nAverage Period Length: " + avgPeriodLength + " days";
    }

}
