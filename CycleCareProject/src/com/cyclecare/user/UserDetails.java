package com.cyclecare.user;

import java.util.Scanner;

public class UserDetails {
    private String userName;
    private int userAge;
    private int userWeight;
    private int userHeight;
    private String userEmail;

    private int avgCycleLength;
    private int avgPeriodLength;

    public UserDetails() {
        // default constructor
    }

    public void inputUserDetails() {

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
            sc.nextLine(); // consume leftover newline

            System.out.print("Enter email: ");
            userEmail = sc.nextLine();

            System.out.print("Enter average cycle length in days: ");
            avgCycleLength = sc.nextInt();

            System.out.print("Enter average period length in days: ");
            avgPeriodLength = sc.nextInt();

        } catch (Exception e) {
            System.out.println("Error: Invalid input. Try again.");
        }
    }

    public String getName() {
        return userName;
    }
    public int getAge() {
        return userAge; }
    public int getWeight() {
        return userWeight;
    }
    public int getHeight() {
        return userHeight;
    }
    public String getEmail() {
        return userEmail;
    }
    public int getAvgCycleLength() {
        return avgCycleLength;
    }
    public int getAvgPeriodLength() {
        return avgPeriodLength;
    }

    @Override
    public String toString() {
        return "\nUSER DETAILS" +
                "\nName: " + userName +
                "\nAge: " + userAge +
                "\nWeight: " + userWeight + " kg" +
                "\nHeight: " + userHeight + " cm" +
                "\nEmail: " + userEmail +
                "\nAverage Cycle Length: " + avgCycleLength + " days" +
                "\nAverage Period Length: " + avgPeriodLength + " days";
    }
}
