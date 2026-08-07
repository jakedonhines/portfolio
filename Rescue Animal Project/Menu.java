import java.util.Scanner;

class Menu {

    // Setting up Singleton instance of Menu, because we only ever want one of them
    private static Menu instance = null;

    // Global Variables
    private final AnimalService animalService = AnimalService.getInstance(); // Our service class that will give the program its functionality
    Scanner scnr = new Scanner(System.in);
    String userInput = "";

    // Private Constructor for singleton
    private Menu(){
    }

    // getInstance function will return the only instance of this class
    public static synchronized Menu getInstance(){
        if (instance == null) instance = new Menu(); // If an instance doesn't exist yet, create one
        return instance; // Return the instance
    }

    // This function runs the program's menu
    public void runMenu(){
        // Print menu options
        displayMainMenu();

        // Get the user's input, and remove any leading or trailing white spaces 
        userInput = scnr.nextLine().trim();

        // Determine the proper actions based on the user's input. 
        switch (userInput) {
            case "1": // User wants to intake a new dog
                animalService.intakeNewDog(scnr);
                break;
            case "2": // User wants to intake a new monkey
                animalService.intakeNewMonkey(scnr);
                break;
            case "3": // User wants to reserve an animal
                animalService.reserveAnimal(scnr);
                break;
            case "4": // User wants a list of every animal
                animalService.printAnimals("all");
                break;
            case "5": // User wants to search the animal list
                searchMenu();
                break;
            case "6": // User wants to update an animal on the list
                animalService.updateAnimal(scnr);
                break;
            case "7": // User wants to remove an animal from the list
                animalService.deleteAnimal(scnr);
                break;
            case "t": // User wants to add the test animals
            case "T":
                animalService.addTestAnimals();
                break;
            // The following two cases are the same (just lowercase and uppercase 'q'), 
            // they quit the program, so we don't need to include anything in the first case including the break
            case "q":
            case "Q":
                System.out.println("Quitting the Program...");
                System.exit(0);
                break;
            // An invalid input was input, let the user know
            default:
                System.out.println("\n\n\n\nThere was an input error, try entering your choice again. Enter a number 1-7, t, or q\n");
                break;
        }
    }

    // This function prints the program's menu
    private void displayMainMenu(){
        System.out.println("\n\n");
        System.out.println("\t\t\t\tRescue Animal System Menu");
        System.out.println("[1] Intake a new dog");
        System.out.println("[2] Intake a new monkey");
        System.out.println("[3] Reserve an animal");
        System.out.println("[4] Print a list of all animals");
        System.out.println("[5] Search Animals");
        System.out.println("[6] Update Animal");
        System.out.println("[7] Delete Animal");
        System.out.println("[t] Add Test Animals");
        System.out.println("[q] Quit application");
        System.out.println();
        System.out.println("Enter a menu selection");
    }

    private void searchMenu(){
        // Display the search menu
        displaySearchMenu();

        // Get the user's input, and remove any leading or trailing white spaces 
        userInput = scnr.nextLine().trim();

        switch (userInput) {
            case "1": // User want to loop up an animal with a specific ID
                animalService.searchByID(scnr);
                break;
            case "2": // User wants a list of all available animals
                animalService.printAnimals("available");
                break;
            case "3": // User has specific criteria they want to look up
                animalService.searchWithCritera(scnr);
                break;
            case "q": // User wants to exit this menu
            case "Q":
                System.out.println("Heading back to the main menu...");
            default:
                System.out.println("\n\n\n\nThere was an input error, try entering your choice again. Enter a number 1-3 or q\n");
                break;
        }
    }

    private void displaySearchMenu(){
        System.out.println("\n\n\n\n");
        System.out.println("[1] Search for Animal by ID");
        System.out.println("[2] List all Available Animals");
        System.out.println("[3] List by Criteria/Attribute");
        System.out.println("[q] Return to main menu");
        System.out.println();
        System.out.println("Enter a menu selection");
    }

}