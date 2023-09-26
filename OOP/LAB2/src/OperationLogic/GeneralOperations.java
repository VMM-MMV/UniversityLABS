package OperationLogic;

import DataBase.SaveData;
import Faculties.StudentFaculty;
import Templates.Student;
import Templates.StudyField;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GeneralOperations extends Storage {
    public static void startOperations() {
        // TODO close scanner
        String result;
        String userInput;

        Scanner scanner = new Scanner(System.in);
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
            case "hp" -> System.out.println("""
                General operations

                nf/<faculty name>/<faculty abbreviation>/<field> - create faculty
                ss/<student email> - search student and show faculty
                fd - display faculties
                df/<field> - display all faculties of a field

                bk - Back
                br - Exit and Save""");
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

        Storage.studentFaculties.add(new StudentFaculty(facultyName, facultyAbbreviation, new ArrayList<>(), studyField));
    }

    private static void searchStudent(String commandOperation) {
        var parts = commandOperation.split("/");
        if (parts.length < 2) {
            System.out.println("Incomplete command operation.");
            return;
        }

        String studentEmail = parts[1];

        for (StudentFaculty studentFaculty : Storage.studentFaculties) {
            for (Student student : studentFaculty.getStudents()) {
                if (Objects.equals(student.getEmail(), studentEmail)) {
                    System.out.println("Student is present in facility: " + studentFaculty.getName());
                    return;
                }
            }
        }

        System.out.println("Student is not present in any facility");
    }

    private static void displayFaculties() {
        for (StudentFaculty studentFaculty: Storage.studentFaculties) {
                System.out.println("Name: " + studentFaculty.getName() + " | Abbreviation: " + studentFaculty.getAbbreviation() + " | Field: " + studentFaculty.getStudyField());
        }
    }

    private static void displayFaculties(String commandOperation) {
        var parts = commandOperation.split("/");

        if (parts.length < 2) {
            displayFaculties();
            return;
        }

        StudyField studyField = StudyField.valueOf(parts[1]);

        for (StudentFaculty studentFaculty: Storage.studentFaculties) {
            if (studentFaculty.getStudyField() == studyField) {
                System.out.println("Name: " + studentFaculty.getName() + " | Abbreviation: " + studentFaculty.getAbbreviation() + " | Field: " + studentFaculty.getStudyField());
            }
        }
    }
}