package OperationLogic;

import Templates.Faculty;
import Templates.Student;
import DataBase.SaveData;
import Templates.StudyField;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GeneralOperations extends CommonOperationObjects {
    public static void startOperations() {
        String result;
        String userInput;

        do {
            System.out.println("G: Enter command:");

            userInput = scanner.nextLine();
            result = doOperations(userInput);
        } while (!result.equals("bk"));
    }

    private static String doOperations(String userInput) {
        if (userInput.length() < 2) {
            System.out.println("String is too short!");
            return "";
        }

        String commandCall = userInput.substring(0, 2);
        String commandOperation = userInput.substring(2);


        switch (Objects.requireNonNull(commandCall)) {
            case "nf" -> newFaculty(commandOperation);
            case "ss" -> searchStudent(commandOperation);
            case "df" -> displayFaculties(commandOperation);
            case "br" -> { new SaveData(); System.exit(0); }
            case "bk" -> { return "bk"; }
            case "dh" -> displayHelp();
            default -> System.out.println("No such command");
        }
        return "";
    }

    private static void newFaculty(String commandOperation) {
        var parts = commandOperation.split("/");

        if (parts.length < 4) {
            System.out.println("Incomplete command operation.");
            return;
        }

        String facultyName = parts[1];
        String facultyAbbreviation = parts[2];
        StudyField studyField = StudyField.valueOf(parts[3]);

        allFacultiesList.add(new Faculty(facultyName, facultyAbbreviation, new ArrayList<>(), studyField));
    }

    private static void searchStudent(String commandOperation) {
        var parts = commandOperation.split("/");
        if (parts.length < 2) {
            System.out.println("Incomplete command operation.");
            return;
        }

        String studentEmail = parts[1];

        for (Faculty faculty : allFacultiesList) {
            for (Student student : faculty.getStudents()) {
                if (Objects.equals(student.getEmail(), studentEmail)) {
                    System.out.println("Student is present in faculty: " + faculty.getName());
                    return;
                }
            }
        }

        System.out.println("Student is not present in any faculty");
    }

    private static void displayFaculties() {
        for (Faculty faculty: allFacultiesList) {
                System.out.println("Name: " + faculty.getName() + " | Abbreviation: " + faculty.getAbbreviation() + " | Field: " + faculty.getStudyField());
        }
    }

    private static void displayFaculties(String commandOperation) {
        var parts = commandOperation.split("/");

        if (parts.length < 2) {
            displayFaculties();
            return;
        }

        StudyField studyField = StudyField.valueOf(parts[1]);

        for (Faculty faculty: allFacultiesList) {
            if (faculty.getStudyField() == studyField) {
                System.out.println("Name: " + faculty.getName() + " | Abbreviation: " + faculty.getAbbreviation() + " | Field: " + faculty.getStudyField());
            }
        }
    }
    private static void displayHelp() {
        System.out.println("""
                General operations
                
                nf/<faculty name>/<faculty abbreviation>/<field> - create faculty
                ss/<student email> - search student and show faculty
                fd - display faculties
                df/<field> - display all faculties of a field

                bk - back
                br - exit and save
                df - display help""");
    }
}
