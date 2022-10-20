package org.example;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String[][] tasks = readFromFile(); // wczytanie danych z pliku do tablicy
        Menu(tasks);
        }

    public static String[][] removeRecord(String[][] taskBeforeRemove) {
        String[][] tasks = new String[0][2];
        int index = 0;
        System.out.println("Please select number to remove.");
        Scanner scanner = new Scanner(System.in);
        do {
            while (!scanner.hasNextInt()) {
                    System.out.println("Please select number to remove.");
                    scanner.next();
                }
                index = scanner.nextInt();
        }while (!((index >= 0) && (index < taskBeforeRemove.length))) ;

            for (int i = 0; i < taskBeforeRemove.length; i++) {
                if (i != index) {
                    tasks = addNewItem(tasks, taskBeforeRemove[i]);
                }
            }
            System.out.println("Value was successfully deleted.");
            return tasks;
        }

    public static void Menu(String[][] tasks){
        boolean isExit = false;
        do {
            Scanner scan = new Scanner(System.in);
            String option = null;

            System.out.println(ConsoleColors.BLUE + "Please select an option:");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "add");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "remove");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "list");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "exit");

            option = scan.nextLine();

            if (option.equals("add")) {
                tasks = addRecord(tasks);
            } else if (option.equals("remove")) {
                tasks = removeRecord(tasks);
            } else if (option.equals("list")) {
                listTable(tasks);
            } else if (option.equals("exit")) {
                exitMethod(tasks);
                isExit = true;
            }
        } while (isExit==false);
    }


    public static void exitMethod(String[][] tasks) {
        Path newFilePath = Paths.get("tasks.csv");
        String line = null;
        try (PrintWriter printWriter = new PrintWriter("tasks.csv")) {
            for (int i = 0; i < tasks.length; i++) {
                line = StringUtils.join(tasks[i], ", ");
                printWriter.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error write to file");
        }
    }


    public static String[][] addRecord(String[][] tasks) {
        System.out.println("Please add task description");
        String taskDescription = inputString("Please add task description", 0);
        System.out.println("Please add task due date");
        String taskDate = inputString("Please add task due date", 0);
        System.out.println("Is your task important: " + ConsoleColors.RED + "true/false");
        String taskImportant = inputString(ConsoleColors.WHITE_BRIGHT + "Is your task important: " + ConsoleColors.RED + "true/false", 1);
        System.out.println(ConsoleColors.RESET);

        String[] record= {taskDescription, taskDate,taskImportant};
        tasks = addNewItem(tasks, record);
        return tasks;
    }

    public static String inputString(String str, int i) {
//           metoda przyjmuje dwa argumenty
//           String str - tresc polecenia
//           int i: 0 - przyjmuje dowolna wartosc, nie przyjmie "enter-a" ale przymie wiele spacji itd
//           int i: 1 - przyjmie tylko wartosc true/false
        String line = null;
        do {
            do {
                Scanner scan = new Scanner(System.in);
                line = scan.nextLine();
                if (line.equals("")) {
                    System.out.println(str);
                }
            } while (line.equals(""));
            if (i == 0) {
                break;
            } else if (i == 1) {
                if ((line.equals("true")) || (line.equals("false"))) {
                    break;
                } else {
                    System.out.println(str);
                }
            }
        } while (true);
        return line;
    }


    public static String[][] readFromFile() {
        String[][] tasksFromFile = new String[0][2];
        File file = new File("tasks.csv");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String split[] = line.split(", ");
                tasksFromFile = addNewItem(tasksFromFile, split);
            }
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Warning !!!");
            System.out.println(ConsoleColors.RED + "Database file not found");
            System.out.println(ConsoleColors.RED + "Starting with empty database");
            System.out.println(ConsoleColors.WHITE_BRIGHT);
        }
        return tasksFromFile;
    }

    public static String[][] addNewItem(String[][] arr, String[] arr2) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = arr2;
        return arr;
    }

    public static void listTable(String[][] tasks) {
        System.out.println("list");
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                if (j < tasks[i].length - 1) {
                    System.out.print(ConsoleColors.WHITE_BRIGHT + tasks[i][j] + ", ");
                } else {
                    System.out.print(ConsoleColors.RED + tasks[i][j]);
                }
            }
            System.out.println(ConsoleColors.WHITE_BRIGHT + "");
        }
        System.out.println("");
    }

}
