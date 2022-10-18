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

        do {
            if (chooseMethod(tasks) == true) {
                break;
            }
            chooseMethod(tasks);
        } while (true);
    }

    public static String[][] removeRecord(String[][] taskBeforeRemove) {
        String[][] tasks = new String[0][2];
        int index = 0;
        System.out.println("Please select number to remove.");
        Scanner scanner = new Scanner(System.in);
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Please select number to remove.");
                scanner.nextLine();
            }
            index = scanner.nextInt();
            if ((index >= 0) && (index < taskBeforeRemove.length)) {
                break;
            }
            System.out.println("Please select number to remove.");
        } while (true);
        for (int i = 0; i < taskBeforeRemove.length; i++) {
            if (i < index) {
                tasks = addNewItem(tasks, taskBeforeRemove[i]);
            } else {
                if (i < taskBeforeRemove.length - 1) {
                    tasks = addNewItem(tasks, taskBeforeRemove[i + 1]);
                }
            }
        }
        System.out.println("Value was successfully deleted.");
        return tasks;
    }

    public static boolean chooseMethod (String [][] tasks){
        // metoda zwraca true w przypadku wybrania opcji "exit" i zapisania danych do pliku
        do {
            String option = Menu();
            if (option.equals("add")){
               tasks = addRecord(tasks);
            }else if(option.equals("remove")){
                tasks = removeRecord(tasks);
            }else if(option.equals("list")){
                listTable(tasks);
            }else if(option.equals("exit")){
                exitMethod(tasks);
                break;
            }
        }while (true);
        return true;
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


    public static String[][] addRecord(String[][] tasks){
        System.out.println("Please add task description");
        String taskDescription = inputString("Please add task description", 0);
        System.out.println("Please add task due date");
        String taskDate = inputString("Please add task due date", 0);
        System.out.println("Is your task important: " + ConsoleColors.RED + "true/false");
        String taskImportant = inputString(ConsoleColors.WHITE_BRIGHT+ "Is your task important: " + ConsoleColors.RED + "true/false",1);
        System.out.println(ConsoleColors.RESET);

        String line = taskDescription +" "+ taskDate +" "+ taskImportant;
        String split[] = line.split(" ");
        tasks = addNewItem(tasks, split);
        return tasks;
    }

      public static String inputString(String str, int i){
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
            if ( i == 0) {
                break;
            }else if( i==1 ){
                if ((line.equals("true"))||(line.equals("false"))){
                    break;
                }else{
                    System.out.println(str);
                }
            }
        }while (true);
        return line;
     }



    public static String[][] readFromFile(){
        String[][] tasksFromFile = new String[0][2];
        File file = new File("tasks.csv");
        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
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

    public static String Menu() {
        Scanner scan = new Scanner(System.in);
        String line = null;
        while (true) {
            System.out.println(ConsoleColors.BLUE + "Please select an option:");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "add");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "remove");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "list");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "exit");
            line = scan.nextLine();
            if ((line.equals("add")) || (line.equals("remove")) || (line.equals("list")) || (line.equals("exit"))) {
                break;
            }
        }
        return line;
    }




    /**
     * https://stackoverflow.com/a/45444716
     */
    public class ConsoleColors {
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset

        // Regular Colors
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
        public static final String GREEN = "\033[0;32m";   // GREEN
        public static final String YELLOW = "\033[0;33m";  // YELLOW
        public static final String BLUE = "\033[0;34m";    // BLUE
        public static final String PURPLE = "\033[0;35m";  // PURPLE
        public static final String CYAN = "\033[0;36m";    // CYAN
        public static final String WHITE = "\033[0;37m";   // WHITE

        // Bold
        public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
        public static final String RED_BOLD = "\033[1;31m";    // RED
        public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
        public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
        public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
        public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
        public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
        public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

        // Underline
        public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
        public static final String RED_UNDERLINED = "\033[4;31m";    // RED
        public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
        public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
        public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
        public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
        public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
        public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

        // Background
        public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
        public static final String RED_BACKGROUND = "\033[41m";    // RED
        public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
        public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
        public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
        public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
        public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
        public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

        // High Intensity
        public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
        public static final String RED_BRIGHT = "\033[0;91m";    // RED
        public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
        public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
        public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
        public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
        public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
        public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

        // Bold High Intensity
        public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
        public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
        public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
        public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
        public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
        public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
        public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
        public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

        // High Intensity backgrounds
        public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
        public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
        public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
        public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
        public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
        public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
        public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
        public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
    }
}