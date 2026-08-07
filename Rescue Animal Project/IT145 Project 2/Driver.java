import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private static ArrayList<Dog> dogList = new ArrayList<Dog>();
    private static ArrayList<Monkey> monkeyList = new ArrayList<Monkey>();
    private static final String[] ALLOWED_MONKEY_SPECIES = {"cupuchin", "guenon", "macaque", "marmoset", "squirrel monkey", "tamarin"};

    public static void main(String[] args) {

        initializeDogList();
        initializeMonkeyList();

        // Run the program's menu, this loop will conintue indefanitly until the user enters the quit sequince in the menu
        while (true){
            runMenu();
        }

    }

    // This method runs the code required for the menu
    private static void runMenu() {
        Scanner scnr = new Scanner(System.in);
        String userInput = "";

        // Display the menu
        displayMenu();

        // Get the user's input, and remove any leading or trailing white spaces 
        // (so if the user enters something like "1 ", it should still count as them entering "1")
        userInput = scnr.nextLine().trim();

        // Determain the proper actions based on the user's input. 
        switch (userInput) {
            case "1": // User wants to intake a new dog
                intakeNewDog(scnr);
                break;
            case "2": // User wants to intake a new monkey
                intakeNewMonkey(scnr);
                break;
            case "3": // User wants to reserve an animal
                reserveAnimal(scnr);
                break;
            case "4": // User wants a list of all the dogs
                printAnimals("dog");
                break;
            case "5": // User wants a list of all the monkeys
                printAnimals("monkey");
                break;
            case "6": // User wants a list of all the available animals
                printAnimals("available");
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
                System.out.println("\n\n\n\nThere was an input error, try entering your choice again. Enter a number 1-6 or q\n");
                runMenu();
                break;
        }
    }

    // This method prints the menu options
    public static void displayMenu() {
        System.out.println("\n\n");
        System.out.println("\t\t\t\tRescue Animal System Menu");
        System.out.println("[1] Intake a new dog");
        System.out.println("[2] Intake a new monkey");
        System.out.println("[3] Reserve an animal");
        System.out.println("[4] Print a list of all dogs");
        System.out.println("[5] Print a list of all monkeys");
        System.out.println("[6] Print a list of all animals that are not reserved");
        System.out.println("[q] Quit application");
        System.out.println();
        System.out.println("Enter a menu selection");
    }

    // Adds dogs to a list for testing
    public static void initializeDogList() {
        Dog dog1 = new Dog("Spot", "German Shepherd", "male", "1", "25.6", "05-12-2019", "United States", "intake", false, "United States");
        Dog dog2 = new Dog("Rex", "Great Dane", "male", "3", "35.2", "02-03-2020", "United States", "Phase I", false, "United States");
        Dog dog3 = new Dog("Bella", "Chihuahua", "female", "4", "25.6", "12-12-2019", "Canada", "in service", true, "Canada");
        Dog dog4 = new Dog("Julia", "Chihuahua", "female", "4", "25.6", "12-12-2019", "Canada", "in service", false, "Canada");
        Dog dog5 = new Dog("Omara", "Chihuahua", "female", "4", "25.6", "12-12-2019", "Canada", "intake", true, "Canada");
        Dog dog6 = new Dog("Siggi", "Chihuahua", "female", "4", "25.6", "12-12-2019", "Canada", "intake", false, "Canada");

        dogList.add(dog1);
        dogList.add(dog2);
        dogList.add(dog3);
        dogList.add(dog4);
        dogList.add(dog5);
        dogList.add(dog6);
    }


    // Adds monkeys to a list for testing
    //Optional for testing
    public static void initializeMonkeyList() {
        Monkey monkey1 = new Monkey("Bob", "male", "10", "15.6", "06-04-2022", "United States", "intake", false, "United States", "0.5", "2.1", "1.4", "Tamarin");
        Monkey monkey2 = new Monkey("Ann", "female", "9", "12.3", "11-24-2021", "United States", "intake", false, "United States", "0.5", "1.9", "1.3", "Capuchin");
        Monkey monkey3 = new Monkey("Amelia", "female", "9", "12.3", "11-24-2021", "United States", "in service", true, "United States", "0.5", "1.9", "1.3", "Capuchin");
        Monkey monkey4 = new Monkey("Donna", "female", "9", "12.3", "11-24-2021", "United States", "in service", false, "United States", "0.5", "1.9", "1.3", "Capuchin");
        Monkey monkey5 = new Monkey("Jane", "female", "9", "12.3", "11-24-2021", "United States", "intake", true, "United States", "0.5", "1.9", "1.3", "Capuchin");

        monkeyList.add(monkey1);
        monkeyList.add(monkey2);
        monkeyList.add(monkey3);
        monkeyList.add(monkey4);
        monkeyList.add(monkey5);
    }


    // Complete the intakeNewDog method
    // The input validation to check that the dog is not already in the list
    // is done for you
    public static void intakeNewDog(Scanner scanner) {
        System.out.println("What is the dog's name?");
        String name = scanner.nextLine();
        for(Dog dog: dogList) {
            if(dog.getName().equalsIgnoreCase(name)) {
                System.out.println("\n\nThis dog is already in our system\n\n");
                return; //returns to menu
            }
        }

        // Get the dog's breed
        System.out.println("What is " + name + "'s breed?");
        String breed = scanner.nextLine();

        // Get the dog's gender
        System.out.println ("What gender is " + name + "?");
        String gender = scanner.nextLine();

        // Get the dog's age
        System.out.println ("How old is " + name + "?");
        String age = scanner.nextLine();

        // Get the dog's weight
        System.out.println ("How much does " + name + " weigh?");
        String weight = scanner.nextLine();

         // Get the date (this will serve as the acquisiton date because the monkey is just now being onboarded)
         System.out.println ("What is today's date? (mm-dd-yyyy)"); // Using American date format as given by the example animals above
         String date = scanner.nextLine();

         // // Get the acquisiong country, this will also be the service country aswell as the monkey will most likely be in training before being reserved by clients
         System.out.println ("What country was " + name + " acquired in?");
         String country = scanner.nextLine();

         // Create the dog object and add it to the dog list
         dogList.add (new Dog(name, breed, gender, age, weight, date, country, "intaking", false, country));
    }


        // This method will prompt the user for a new monkey's information, then if it can be onboarded,
        // will add it to the monkey list, if it cannot the method will end and send the user back to the menu
        public static void intakeNewMonkey(Scanner scanner) {
            // Get the monkey's name and check it to the list of monkeys
            System.out.println("What is the monkey's name?");
            String name = scanner.nextLine();
            // If the monkey's name is already in the list then it has already been onboarded
            for(Monkey monkey: monkeyList){
                if (monkey.getName().equalsIgnoreCase(name)) {
                    System.out.println ("\n\nThis monkey is already in out system\n\n");
                    return;
                }
            }

            // Get the monkey's species and make sure it is an allowed species
            System.out.println("What is " + name + "'s species?");
            String species = scanner.nextLine().toLowerCase();
            // Look to see if the species is on the list
            boolean speciesOnList = false;
            for (String speciesName: ALLOWED_MONKEY_SPECIES){
                if (speciesName.equalsIgnoreCase(species)){
                    speciesOnList = true;
                    break;
                }
            }
            // If the species is not on the list, then end the method.
            if (speciesOnList == false){
                System.out.println(species + " is not on the allowed species list");
                return;
            }

            // Get the monkey's gender
            System.out.println ("What gender is " + name + "?");
            String gender = scanner.nextLine();

            // Get the monkey's age
            System.out.println ("How old is " + name + "?");
            String age = scanner.nextLine();

            // Get the monkey's weight
            System.out.println ("How much does " + name + " weigh?");
            String weight = scanner.nextLine();

            // Get the monkey's tail length
            System.out.println ("How long is " + name + "'s tail?");
            String tailLength = scanner.nextLine();

            // Get the monkey's height
            System.out.println ("What is " + name + "'s height?");
            String height = scanner.nextLine();

            // Get the monkey's body length
            System.out.println ("What is " + name + "'s body length?");
            String bodyLength = scanner.nextLine();

            // Get the date (this will serve as the acquisiton date because the monkey is just now being onboarded)
            System.out.println ("What is today's date? (mm-dd-yyyy)"); // Using American date format as given by the example animals above
            String date = scanner.nextLine();

            // // Get the acquisiong country, this will also be the service country aswell as the monkey will most likely be in training before being reserved by clients
            System.out.println ("What country was " + name + " acquired in?");
            String country = scanner.nextLine();

            // create the monkey and add it to the list of monkeys
            monkeyList.add (new Monkey(name, gender, age, weight, date, country, "intaking", false, country, tailLength, height, bodyLength, species));
        }

        // Complete reserveAnimal
        // You will need to find the animal by animal type and in service country
        public static void reserveAnimal(Scanner scanner) {
            String targetAnimal = "";
            boolean invalidInput = false;

            // this loop will continue to display the reserve menu until the user enter's a valid option
            do {
                // Display the menu
                System.out.println("What kind of animal would you like to reserve?");
                System.out.println("[1] Dog");
                System.out.println("[2] Monkey");
                System.out.println("[q] back to main menu");

                // get their input
                String input = scanner.nextLine();

                // The switch statement will take the user's input and determain what they wanted to do
                switch (input){
                    // Case 1 and 2 (Dog and Monkey), will set the targetAnimal String to the proper animal
                    // and make sure that invalidInput is false to exit the loop (it will be true if the user 
                    // entered an invalid input earlier) before breaking from the switch statement
                    case "1":
                        invalidInput = false;
                        targetAnimal = "dog";
                        break;
                    case "2":
                        invalidInput = false;
                        targetAnimal = "monkey";
                        break;
                    // The user wants to quit and head back to the main menu, using the return statement here will skip
                    // the rest of the function
                    case "q":
                    case "Q":
                        System.out.println("\n\nHeading back to the main menu...\n");
                        invalidInput = false;
                        return;
                    // The user entered an invalid input, let them know and change the boolean to true so the loop goes again
                    default:
                        System.out.println("\n\n\n\nThere was an input error, try entering your choice again. Enter a number 1, 2 or q\n");
                        invalidInput = true;
                }
            } while (invalidInput == true); // The loop will only end when a valid input is entered

            // Ask and get the desired country
            System.out.println("What country are you looking to reserve an animal in?");
            String country = scanner.nextLine();

            // Once we have all the information, we can display the right animals
            // both the monkey and the dog case's call functions to make the code a little cleaner
            switch (targetAnimal){
                // Reserve a dog branch
                case "dog":
                    reserveDog(country, scanner);
                    break;
                // Reserve a monkey branch
                case "monkey":
                    reserveMonkey(country, scanner);
                    break;
                // Something went wrong with the program, this should not run in normal use, if it did
                // there is an error with the above switch statement assigning a value to the targetAnimal String
                default:
                    System.out.println("There has been an error while printing out your list");
                    System.out.println("Retrurning to the main menu...");
                    break;
            }

        }

        // This method will reserve the first dog it can find that is in a given country
        private static void reserveDog (String country, Scanner scanner){
            // Go threw the dog list, and reserve the first one that meets the requirments
            for (Dog dog : dogList) {
                if (dog.getReserved() == false 
                        && dog.getInServiceLocation().equalsIgnoreCase(country) 
                        && dog.getTrainingStatus().equalsIgnoreCase("in service")){
                        
                    // A dog was found! change it's reservation status, and then exit back to the main menu
                    System.out.println(dog.getName() + " is available, we are reserving them for you!");
                    dog.setReserved(true);
                    return;
                }
            }

            // If no dog was found, then tell the user that there isn't any animals within the user's perameters
            // and then head back to the main menu
            System.out.println("No dogs have been found in " + country);
            System.out.println("Returning to the main menu...");
        }

        // This method will reserve the first monkey it can find that is in a given country
        private static void reserveMonkey (String country, Scanner scanner){
            // Go threw the monkey list, and reserve the first one that meets the requirments
            for (Monkey monkey : monkeyList) {
                if (monkey.getReserved() == false 
                        && monkey.getInServiceLocation().equalsIgnoreCase(country) 
                        && monkey.getTrainingStatus().equalsIgnoreCase("in service")){
                        
                    // A monkey was found! change it's reservation status, and then exit back to the main menu
                    System.out.println(monkey.getName() + " is available, we are reserving them for you!");
                    monkey.setReserved(true);
                    return;
                }
            }

            // If no monkey was found, then tell the user that there isn't any animals within the user's perameters
            // and then head back to the main menu
            System.out.println("No monkeys have been found in " + country);
            System.out.println("Returning to the main menu...");
        }

        // Complete printAnimals
        // Include the animal name, status, acquisition country and if the animal is reserved.
	// Remember that this method connects to three different menu items.
        // The printAnimals() method has three different outputs
        // based on the listType parameter
        // dog - prints the list of dogs
        // monkey - prints the list of monkeys
        // available - prints a combined list of all animals that are
        // fully trained ("in service") but not reserved 
	// Remember that you only have to fully implement ONE of these lists. 
	// The other lists can have a print statement saying "This option needs to be implemented".
	// To score "exemplary" you must correctly implement the "available" list.
        public static void printAnimals(String listType) {
            
            System.out.println("\n\n");

            // using a switch statement to determain what to printout
            switch (listType){
                // Both the monkey and the dog lists just print out each of the animals in their respective lists,
                // without any filtering, using the in built forEach in the Arraylist's is a simple way to do that
                case "monkey":
                    monkeyList.forEach((monkey) -> System.out.println(monkey.toString() + "\n"));
                    break;
                case "dog":
                    dogList.forEach((dog) -> System.out.println(dog.toString() + "\n"));
                    break;

                // The available list will only display the animals which are "reservable", these animals
                // must not be currently reserved, and they must be in service
                case "available":
                    // Go threw all the dogs
                    System.out.println("Dogs Available:");
                    for (Dog dog : dogList){
                        // If a dog does not pass this if statement, then it is not currently reservable
                        if (dog.getTrainingStatus().equalsIgnoreCase("in service") && dog.getReserved() == false){
                            System.out.println(dog.toString());
                        }
                    }

                    // Go threw all the monkeys
                    System.out.println("\nMonkeys Available:");
                    for (Monkey monkey : monkeyList){
                        // If a monkey does not pass this if statement, then it is not currently reservable
                        if (monkey.getTrainingStatus().equalsIgnoreCase("in service") && monkey.getReserved() == false){
                            System.out.println(monkey.toString());
                        }
                    }
                    break;

                // An invalid parameter was entered into the switch statement
                default:
                    System.out.println("An Error has occurred while attempting to print your list...");
                    break;
            }

        }
}

