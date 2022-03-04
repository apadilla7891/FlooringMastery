package com.ap.flooringmastery.userio;

import java.util.Scanner;

/**
 *
 * @author Andy Padilla
 */
public class UserIOConsoleImpl implements UserIO{
     private Scanner userInput = new Scanner(System.in);

    //prints out a given string
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    // prints prompt to console and awaits a user input
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return userInput.nextLine();
    }

    // prints prompt to user and awaits an int user input, loops until user gives a proper input
    @Override
    public int readInt(String prompt) {
        boolean incorrect = true;
        int num = 0;
        
        //prints out prompt to console and parses user input for an int. if no int is found error 
        //is caught and the user is reprompted until there is a vaild input
        while (incorrect) {
            try {
                System.out.println(prompt);
                num = Integer.parseInt(userInput.nextLine());
                incorrect = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again");
            }
        }
        return num;
    }

    // prints a prompt asking user for an int withing a range. continues to reprompt until an acceptable number is input
    @Override
    public int readInt(String prompt, int min, int max) {
        int result;
        do {
            result = readInt(prompt);
        } while (result < min || result > max);

        return result;
    }

    // prints prompt to user and awaits a long user input, loops until user gives a proper input
    @Override
    public long readLong(String prompt) {
        boolean incorrect = true;
        long num = 0;
        
        //prints out prompt to console and parses user input for a long. if no long is found error 
        //is caught and the user is reprompted until there is a vaild input
        while (incorrect) {
            try {
                System.out.println(prompt);
                num = Long.parseLong(userInput.nextLine());
                incorrect = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again");
            }
        }
        return num;
    }

    // prints a prompt asking user for a long withing a range. continues to reprompt until an acceptable number is input
    @Override
    public long readLong(String prompt, long min, long max) {
        long result;
        do {
            result = readLong(prompt);
        } while (result < min || result > max);

        return result;
    }

    // prints prompt to user and awaits a float user input, loops until user gives a proper input
    @Override
    public float readFloat(String prompt) {
        boolean incorrect = true;
        float num = 0;
        
        //prints out prompt to console and parses user input for a float. if no float is found error 
        //is caught and the user is reprompted until there is a vaild input
        while (incorrect) {
            try {
                System.out.println(prompt);
                num = Float.parseFloat(userInput.nextLine());
                incorrect = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again");
            }
        }
        return num;
    }

    // prints a prompt asking user for a float withing a range. continues to reprompt until an acceptable number is input
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    // prints prompt to user and awaits a double user input, loops until user gives a proper input
    @Override
    public double readDouble(String prompt) {
        boolean incorrect = true;
        double num = 0;
        
        //prints out prompt to console and parses user input for a double. if no double is found error 
        //is caught and the user is reprompted until there is a vaild input
        while (incorrect) {
            try {
                System.out.println(prompt);
                num = Double.parseDouble(userInput.nextLine());
                incorrect = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again");
            }
        }
        return num;
    }

    // prints a prompt asking user for a float withing a range. continues to reprompt until an acceptable number is input
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }
}