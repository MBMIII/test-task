package main;

import abstarction.Write;
import abstarction.WriteToDatabase;
import exception.IncorrectExpressionException;
import writeToFile.WriteToTXTFile;

public class Main {
    public static void main(String[] args) {

        example(new WriteToTXTFile("src\\main\\resources\\files\\example.txt"));
        //System.out.println("*****************SQL***********************");
        //example(new WriteToMYSQL(url, username, pass));

    }

    public static void example(Write write) {
        //correct expressions
        try {
            write.write("2+2+-1*(7+1)");
            write.write("12*12");
            write.write("12-12");
            write.write("12/12");
        } catch (IncorrectExpressionException e) {
            System.out.println(e.getMessage());
        }
        //checking result
        if (write instanceof WriteToDatabase writeToDatabase) {
            System.out.println("Result");
            writeToDatabase.getAll().forEach(System.out::println);
        }
        //incorrect expression
        try {
            write.write("(2+2-1*(7+1)");
        } catch (IncorrectExpressionException e) {
            System.out.println(e.getMessage());
        }
        //checking result
        if (write instanceof WriteToDatabase writeToDatabase) {
            System.out.println("Result");
            writeToDatabase.getAll().forEach(System.out::println);
        }
        //checking update functionality
        try {
            write.updateExpressionByID(1, "2+2");
        } catch (IncorrectExpressionException e) {
            System.out.println(e.getMessage());
        }
        //checking result
        if (write instanceof WriteToDatabase writeToDatabase) {
            System.out.println("Result");
            writeToDatabase.getAll().forEach(System.out::println);
        }
        //updating with incorrect expression
        try {
            write.updateExpressionByID(1, "2+2+1)");
        } catch (IncorrectExpressionException e) {
            System.out.println(e.getMessage());
        }
        //checking result
        if (write instanceof WriteToDatabase writeToDatabase) {
            System.out.println("Result");
            writeToDatabase.getAll().forEach(System.out::println);
        }
        //get expressions by value
        System.out.println(write.getExpressionByValue(">=", 4.0));
    }
}
