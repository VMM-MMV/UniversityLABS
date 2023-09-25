import java.util.Objects;
import java.util.Scanner;

public class FacultyOperations {
    public FacultyOperations() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter command:");
        String userInput = scanner.nextLine();
    }
    private void checkInput(String userInput) {
        String command = null;
        if(userInput.length() >= 2) {
            command = userInput.substring(0, 2);
        } else {
            System.out.println("String is too short!");
        }

        switch (Objects.requireNonNull(command)) {
            case "ns" -> newStudent();
            case "gs" -> graduateStudent();
            case "de" -> displayEnrolled();
            case "dg" -> displayGraduated();
            case "bf" -> belongsToFaculty();
        }
    }
    private void newStudent() {

    }

    private void graduateStudent() {

    }

    private void displayEnrolled() {

    }

    private void displayGraduated() {

    }

    private void belongsToFaculty() {

    }
}
