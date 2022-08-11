package main;

import abstarction.Write;
import abstarction.WriteToDatabase;
import exception.IncorrectExpressionException;
import writeToFile.WriteToTXTFile;

public class Main {
    public static void main(String[] args) {

        try {
            example(new WriteToTXTFile("src\\main\\resources\\files\\example.txt"));
            //System.out.println("*****************SQL***********************");
            //example(new WriteToMYSQL(url, username, pass));
        } catch (IncorrectExpressionException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void example(Write write) throws IncorrectExpressionException {
        //correct expressions
        write.write("2+2+-1*(7+1)");
        write.write("12*12");
        write.write("12-12");
        write.write("12/12");
        write.write("1.25 + 0.71");
        //checking result
        if (write instanceof WriteToDatabase writeToDatabase) {
            System.out.println("Result");
            writeToDatabase.getAll().forEach(System.out::println);
        }
        try {
            //incorrect expression
            write.write("(2+2-1*(7+1)");
        } catch (IncorrectExpressionException e) {
            System.out.println(e.getMessage());
            //checking that expression was not added to list
            System.out.println("Result");
            if (write instanceof WriteToDatabase writeToDatabase) {
                writeToDatabase.getAll().forEach(System.out::println);
            } else {
                ((WriteToTXTFile) write).getAll().forEach(System.out::println);
            }
        }
        //checking update functionality
        write.updateExpressionByID(1, "2+2-1");
        //checking result
        if (write instanceof WriteToDatabase writeToDatabase) {
            System.out.println("Result");
            writeToDatabase.getAll().forEach(System.out::println);
        }
        try {
            //updating with incorrect expression
            write.updateExpressionByID(1, "2+2+1)");
        } catch (IncorrectExpressionException e) {
            System.out.println(e.getMessage());
            //checking that expression was not added to list
            System.out.println("Result");
            if (write instanceof WriteToDatabase writeToDatabase) {
                writeToDatabase.getAll().forEach(System.out::println);
            } else {
                ((WriteToTXTFile) write).getAll().forEach(System.out::println);
            }
        }
        //get expressions by value
        System.out.println(write.getExpressionByValue(">=", 3.0));
    }
}
