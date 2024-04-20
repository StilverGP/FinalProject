package com.github.StilverGP.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IO {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString(String msg) {
        String input;
        System.out.print(msg);
        input = scanner.nextLine();
        if (input.isEmpty() || input.isBlank()) {
            do {
                input = scanner.nextLine();
            } while (input.isEmpty() || input.isBlank());
        }
        return input;
    }

    public static int readInt(String message) {
        boolean isValidInput = false;
        int value = 0;

        while (!isValidInput) {
            try {
                System.out.print(message + " ");
                value = scanner.nextInt();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error, Ingresa un número válido.");
                scanner.nextLine();
            }
        }
        return value;
    }

    public static LocalDate readDate(String message) {
        LocalDate inputDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        boolean fechaValida = false;

        while (!fechaValida) {
            try {
                System.out.println("Utiliza el formato dd-MM-yyyy");
                System.out.print(message);
                String dateString = scanner.nextLine();

                inputDate = LocalDate.parse(dateString, formatter);

                if (inputDate.isAfter(LocalDate.now())) {
                    fechaValida = true;
                } else {
                    System.out.println("Error: La fecha no puede ser anterior a la fecha actual.");
                }
            } catch (Exception e) {
            }
        }

        return inputDate;
    }

    public static LocalDate readFinalDate(String message, LocalDate startDate) {
        LocalDate inputDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        boolean fechaValida = false;

        while (!fechaValida) {
            try {
                System.out.print(message);
                String dateString = scanner.nextLine();

                inputDate = LocalDate.parse(dateString, formatter);

                if (!inputDate.isBefore(startDate)) {
                    fechaValida = true;
                } else {
                    System.out.println("Error: La fecha no puede ser anterior a la fecha de checkIn.");
                }
            } catch (Exception e) {
            }
        }

        return inputDate;
    }
}
