import java.util.Scanner;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AnimalService {

    // Setting up Singleton instance of the Animal Service because we only ever want one of them.
    private static AnimalService instance = null;
    
    // Global variables
    private final AnimalRepository animalRepository = AnimalRepository.getInstance(); // The list of animals in our system
    private final AnimalSearchService animalSearchService = AnimalSearchService.getInstance(); // A service used when trying to get lists or individual animals from the animal repository
    private final ValidationService validator = ValidationService.getInstance(); // A service we made to test that user inputs are valid

    // Private constructor for singleton
    private AnimalService() {
    }

    // getInstance function will return the only instance of this class
    public static synchronized AnimalService getInstance(){
        if (instance == null) instance = new AnimalService(); // If an instance doesn't exist yet, create one
        return instance; // Return the instance
    }

    // This function will ask the user for all the info we need to intake a new dog
    public void intakeNewDog(Scanner scnr){
        try {
            // Get the dog's name
            String name = promptUntilValid(scnr, "What is the dog's name?", validator::validateName, "Invalid Name");

            // Get the dog's ID number
            String id = getID(scnr, name);
            // if id is null, then the ID the user entered already existed
            if (id == null) return;

            // Collect all the other info we need
            String breed = promptUntilValid(scnr, "What is "+name+"'s breed?", validator::validateBreed, "Invalid Breed");
            String sex = promptUntilValid(scnr, "What sex is "+name+"?", validator::validateSex, "Sex must be Male, Female, or Unknown");
            String age = promptUntilValid(scnr, "How old is "+name+"? (in years)", validator::validateAge, "Age must be a non-negative whole number");
            String weight = promptUntilValid(scnr, "How much does "+name+" weigh? (in kg)", validator::validateMeasurement, "Weight must be greater than 0");
            String date = getCurrentDate();
            String country = promptUntilValid(scnr, "What country was "+name+" acquired in?", validator::validateCountry, "Please enter a valid country");

            // Create a new dog object and add it to the animal lists
            animalRepository.add(new Dog(id, name, breed, sex, age, weight, date, country, "intaking", false, country));

            // Tell the user that their dog has been added to the system!
            System.out.println(name+" has been added!");
        } catch (Exception e) {
            // If there was an error, most likely because the dog is already in the system, tell the user
            System.err.println("Error intaking new Dog!");
            System.err.println(e.getMessage());
        }
    }

    // This function will ask the user for all the info we need to intake a new monkey
    public void intakeNewMonkey(Scanner scnr){
        try {
            // Get the monkey's name
            String name = promptUntilValid(scnr, "What is the monkey's name?", validator::validateName, "Invalid Name");

            // Get the monkey's ID number
            String id = getID(scnr, name);
            // if id is null, then the ID the user entered already existed
            if (id == null) return;

            // Collect all the other info we need
            String species = promptUntilValid(scnr, "What is "+name+"'s species?", validator::validateSpecies, "Invalid Species, " + validator.validSpecies());
            String sex = promptUntilValid(scnr, "What sex is "+name+"?", validator::validateSex, "Sex must be Male, Female, or Unknown");
            String age = promptUntilValid(scnr, "How old is "+name+"? (in years)", validator::validateAge, "Age must be a non-negative whole number");
            String weight = promptUntilValid(scnr, "How much does "+name+" weigh? (in kg)", validator::validateMeasurement, "Weight must be greater than 0");
            String tailLength = promptUntilValid(scnr, "How long is "+name+"'s tail? (in cm)", validator::validateTailLength, "Length must be positive");
            String height = promptUntilValid(scnr, "How tall is "+name+"? (in cm)", validator::validateMeasurement, "Height must be greater than 0");
            String bodyLength = promptUntilValid(scnr, "How long is "+name+"? (in cm)", validator::validateMeasurement, "Length must be greater than 0");
            String date = getCurrentDate();
            String country = promptUntilValid(scnr, "What country was "+name+" acquired in?", validator::validateCountry, "Please enter a valid country");

            // Create a new monkey object and add it to the animal lists
            animalRepository.add (new Monkey(id, name, sex, age, weight, date, country, "intaking", false, country, tailLength, height, bodyLength, species));
        
            // Tell the user that their monkey has been added to the system!
            System.out.println(name+" has been added!");
        } catch (Exception e) {
            // If there was an error, most likely because the monkey is already in the system, tell the user
            System.err.println("Error intaking new Monkey!");
            System.err.println(e.getMessage());
        }
    }

    // This helper function will keep asking the user for an input until the input is valid.
    // When a valid input is made, it will return the input as a string.
    // Scanner scnr - the global Scanner object we are using
    // String prompt - the question we are asking the user
    // Predicate<String> validatorFunction - the function we need to use to see if the user's input is valid,
    //                                       these all take in a string (the user's input we are testing) 
    //                                       and return a boolean (true for valid, false for invalid)
    // String errorMessage - A message we want to pritn if the user's input is invalid
    private String promptUntilValid(Scanner scnr, String prompt, Predicate<String> validatorFunction, String errorMessage){
        // We are using "true" in the while loop since we want to ask the user over and over the prompt until
        // we get a valid answer. We have a return statement inside the loop to exit it when we do get a valid input.
        while (true){
            // Prompt the user for an input
            System.out.println(prompt);
            // Collect the input
            String input = scnr.nextLine().trim();
            // Check to see if the input is valid, if it is, then this will return the input
            if (validatorFunction.test(input)) return input;
            // If we didn't return, then it was invalid, we need to tell the user something is wrong with it
            System.out.println(errorMessage);
        }
    }

    // This helper function will keep asking the user for an input until the input is valid.
    // When a valid input is made, it will return the input as a string.
    // Scanner scnr - the global Scanner object we are using
    // String prompt - the question we are asking the user
    // Predicate<String> validatorFunction - the function we need to use to see if the user's input is valid,
    //                                       these all take in a string (the user's input we are testing) 
    //                                       and return a boolean (true for valid, false for invalid)
    // String errorMessage - A message we want to pritn if the user's input is invalid
    private String promptUntilValidOrBlank(Scanner scnr, String prompt, Predicate<String> validatorFunction, String errorMessage){
        // We are using "true" in the while loop since we want to ask the user over and over the prompt until
        // we get a valid answer. We have a return statement inside the loop to exit it when we do get a valid input.
        while (true){
            // Prompt the user for an input
            System.out.println(prompt);
            // Collect the input
            String input = scnr.nextLine().trim();
            // Check to see if input is blank, if it is then we will return it
            if (input.isBlank() || input == null) return ""; // Returning "" incase input is null
            // Check to see if the input is valid, if it is, then this will return the input
            if (validatorFunction.test(input)) return input;
            // If we didn't return, then it was invalid, we need to tell the user something is wrong with it
            System.out.println(errorMessage);
        }
    }

    private Boolean promptUntilValidOrBlankBool(Scanner scnr, String prompt, Predicate<String> validatorFunction, String errorMessage){
        // We are using "true" in the while loop since we want to ask the user over and over the prompt until
        // we get a valid answer. We have a return statement inside the loop to exit it when we do get a valid input.
        while (true){
            // Prompt the user for an input
            System.out.println(prompt);
            // Collect the input
            String input = scnr.nextLine().trim();
            // Check to see if input is blank, if it is then we will return it
            if (input.isBlank() || input == null) return null; // Returning "" incase input is null
            // Check to see if the input is valid, if it is, then this will return the input as a boolean
            if (validatorFunction.test(input)){
                if (input.equalsIgnoreCase("true")) return true;
                else return false;
            }
            // If we didn't return, then it was invalid, we need to tell the user something is wrong with it
            System.out.println(errorMessage);
        }
    }

    // A method to get an ID number for intaking an animal
    private String getID(Scanner scnr, String name){
        // Ask the user if the animal already has an ID number. In most cases, this should be no,
        // otherwise the animal would already be in the system.
        String input = promptUntilValid(scnr, "Does "+name+" already have an ID, or do they need a new one?\n[1] Has an ID\n[2] Generate New ID", this::getIDMenuValidator, "Invalid Input");
        
        String id = null;
        if (input.equalsIgnoreCase("1")){ // The animal does have an ID number
            // Get the ID number
            id = promptUntilValid(scnr, "What is "+name+"'s ID number?", validator::validateIDNum, "Invalid ID Number, IDs should be 6 Digits");
        } else { // The animal does not have an ID number
            // We need to generate an new number
            id = animalRepository.generateID();
            System.out.println(name+"'s ID is: "+id); // The user needs to know what the ID is
        }

        // Make sure there isn't any conflicts
        if (id != null && !idIsFree(id)) {
            // Tell the user if there is a conflict. Currently we are using a very small
            // number (6 digits) to make it easier to test. The less digits, the less animals
            // can be in the system before having issues with conflicts.
            System.out.println("We found a matching id, "+id+" must already exists in our system!");
            id = null;
        }
        
        return id; // Return the id number
    }

    // Helper function to make sure that the ID menu input was valid
    // We didn't put this in the validator class because it is solely
    // used for this menu. If the menu changes, this validator function
    // because useless.
    private boolean getIDMenuValidator(String input){
        if (input.equalsIgnoreCase("1") || input.equals("2")) return true;
        return false;
    }

    // Helper function to see if an ID is free or not
    // Returns true if the id is free, false if it is not
    private boolean idIsFree(String input){
        return (!animalRepository.contains(input));
    }

    // Return the current date. We are using year-month-day.
    private String getCurrentDate(){
        // Get the date
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Return the date
        return date.format(formatter);
    }

    // This function will reserve an animal. It calls its own helper functions.
    public void reserveAnimal(Scanner scnr){
        try {
            // Ask and get the desired country
            String country = promptUntilValid(scnr, "What country are you looking to reserve an animal in?", validator::validateCountry, "Please enter a valid country");

            boolean validInput = false;
            // this loop will continue to display the reserve menu until the user enter's a valid option
            do {
                // Display the menu
                validInput = reserveAnimalMenu(scnr, country);
            } while (!validInput); // The loop will only end when a valid input is entered

        } catch (Exception e) {
            // We don't throw any exceptions in this function or the helper functions,
            // but with this catch block we can fail gracefully and allow the user to
            // continue to use the program instead of it crashing.
            System.err.println ("An error occured while trying to reserve an animal!!");
            System.err.println (e.getMessage());
        }
    }

    // This is a helper function for reserveAnimal()
    // It runs the menu for the user to decide what kind of animal they want to reserve
    // This function will return true if the user tried to to a valid operation, or false if they did not.
    private boolean reserveAnimalMenu(Scanner scnr, String country){
        // Print the menu
        displayReserveAnimalMenu();
        
        // get their input
        String input = scnr.nextLine();

        // The switch statement will take the user's input and determain what they wanted to do
        switch (input){
            case "1": // User wants to reserve a dog
                reserveDog(country);
                return true;
            case "2": // User wants to reserve a monkey
                reserveMonkey(country);
                return true;
            // The user wants to quit and head back to the main menu, using the return statement here will skip
            // the rest of the function
            case "q":
            case "Q":
                System.out.println("\n\nHeading back to the main menu...\n");
                return true;
            // The user entered an invalid input, let them know and change the boolean to true so the loop goes again
            default:
                System.out.println("\n\n\n\nThere was an input error, try entering your choice again. Enter a number 1, 2 or q\n");
                return false;
        }
    }

    // Display the reserve animal menu
    private void displayReserveAnimalMenu(){
        System.out.println("What kind of animal would you like to reserve?");
        System.out.println("[1] Dog");
        System.out.println("[2] Monkey");
        System.out.println("[q] back to main menu");
    }

    // Wrapper function for reserving a dog
    private void reserveDog (String country) {
        // Call the function to actually reserve an animal. We need to give it the dog list
        reserveFromList(animalSearchService.getDogList(), "Dog", country);
    }

    // Wrapper function for reserving a monkey
    private void reserveMonkey (String country) {
        // Call the function to actually reserve an animal. We need to give it the monkey list
        reserveFromList(animalSearchService.getMonkeyList(), "Monkey", country);
    }

    // The function to reserve an animal
    // ArrayList<T> animalList - a list of animals of type T (Dog or Monkey)
    // String animalType - a string of the animal's type. We use this for the error message if we cannot reserve one
    // String country - the country the user wants to reserve the animal in
    private <T extends RescueAnimal> void reserveFromList(ArrayList<T> animalList, String animalType, String country){
        // We need to look at each animal in the given list to see if it meets our requirments
        for (T animal:animalList) {
            // Check to see if the animal meets our requirments (not currently reserved, is in the user's desired country, and is in service)
            if (!animal.getReserved()
                && animal.getInServiceLocation().equalsIgnoreCase(country)
                && animal.getTrainingStatus().equalsIgnoreCase("in service")){
                    
                // An animal was found! Change it's reservation status, and then exit back to the main menu
                System.out.println(animal.getName() + " is available, we are reserving them for you!");
                animal.setReserved(true);
                return;
            }
        }

        // If no animal was found, then tell the user that there isn't any animals within the user's perameters
        // and then head back to the main menu
        System.out.println("We couldn't find a " + animalType + " available in " + country);
        System.out.println("Returning to the main menu...");
    }

    // Print a list of animals. The printed list will be based on the listType string.
    // listType:
    // monkey - prints out all the monkeys
    // dog - prints out all the dogs
    // available - prints out all the available animals, regardless if they are a monkey or dog
    public void printAnimals(String listType){
        System.out.println("\n\n");

        // using a switch statement to determain what to printout
        switch (listType){
            case "all":
                animalSearchService.getAllAnimals().forEach((animal) -> System.out.println(animal.toString() + "\n"));
                break;
            case "monkey": // Gets a list of every monkey and print it
                animalSearchService.getMonkeyList().forEach((monkey) -> System.out.println(monkey.toString() + "\n"));
                break;
            case "dog": // Gets a list of every dog and print it
                animalSearchService.getDogList().forEach((dog) -> System.out.println(dog.toString() + "\n"));
                break;

            // The available list will only display the animals which are "reservable", these animals
            // must not be currently reserved, and they must be in service
            case "available":
                animalSearchService.getAvailableAnimals().forEach((animal) -> System.out.println(animal.toString() + "\n"));
                break;
            // An invalid parameter was entered into the switch statement
            default:
                System.out.println("An Error has occurred while attempting to print your list...");
                break;
        }
    }

    // Prints the information for a single animal after looking it up by an ID
    public void searchByID(Scanner scnr){
        // Get what id the user is looking for
        String id = promptUntilValid(scnr, "Enter ID Number", validator::validateIDNum, "Invalid ID Number, IDs should be 6 Digits");
        // Find the animal
        RescueAnimal animal = animalSearchService.findByID(id);

        if (animal == null){
            System.out.println("There is no animal in the system matching that ID. ID: "+id);
            return;
        }

        System.out.println(animal.toString());
    }

    // Search for a list of animals that meet a specific criteria
    public void searchWithCritera(Scanner scnr){
        // Create the search criteria, then get a list based on it
        SearchCriteria searchCriteria = searchCriteriaForm(scnr);
        ArrayList<RescueAnimal> results = animalSearchService.searchWithCriteria(searchCriteria);
        
        // If the list returned is empty, let the user know. Without this
        // they would get no output and that might be confusing.
        if (results == null || results.isEmpty()){
            System.out.println("No animals could be found with the given criteria");
            return;
        }

        // Print the results
        System.out.println("Results:");
        results.forEach(a -> System.out.println(a.toString()));
    }

    // This method will generate a searchCriteria obj based on the user's prefrences
    // so we can search for a list of animals that match certain criteria
    private SearchCriteria searchCriteriaForm(Scanner scnr){
        SearchCriteria criteria = new SearchCriteria();

        // Give the user some instructions, and what criteria they can search by.
        System.out.println("Which Criteria do you want to search with?");
        System.out.println("Name, Animal Type, Sex, Age Range, Weight Range, Acquisition Date Range, " + 
            "Acquisition County, Training Status, Reservation Status, Current Service Country");
        System.out.println("Dog Only Criteria: Breed");
        System.out.println("Monkey Only Criteria: Tail Length Range, Height Range, Body Length Range, Species");
        System.out.println("Leave any attributes blank to ignore it.");
        System.out.println("If Min or Max is left blank but the other isn't, then the output will be everything above or below respectively");

        // Generate the form by getting the various criteria
        criteria.setName(promptUntilValidOrBlank(scnr, "Name: ", validator::validateName, "Invalid Name"));
        criteria.setAnimalType(promptUntilValidOrBlank(scnr, "Animal Type (Dog/Monkey): ", validator::validateAnimalType, "Invalid Animal Type"));
        criteria.setSex(promptUntilValidOrBlank(scnr, "Sex (Male, Female, Unknown): ", validator::validateSex, "Invalid Sex"));
        criteria.setAgeMin(promptUntilValidOrBlank(scnr, "Age Min: ", validator::validateAge, "Invalid Age"));
        criteria.setAgeMax(promptUntilValidOrBlank(scnr, "Age Max: ", validator::validateAge, "Invalid Age"));
        criteria.setWeightMin(promptUntilValidOrBlank(scnr, "Weight Min: ", validator::validateMeasurement, "Invalid Measurement"));
        criteria.setWeightMax(promptUntilValidOrBlank(scnr, "Weight Max: ", validator::validateMeasurement, "Invalid Measurement"));
        criteria.setAcquisitionDateMin(promptUntilValidOrBlank(scnr, "Min Acquisition Date (yyyy-mm-dd): ", validator::validateDate, "Invalid Date (Please use \"-\")"));
        criteria.setAcquisitionDateMax(promptUntilValidOrBlank(scnr, "Max Acquisition Date (yyyy-mm-dd): ", validator::validateDate, "Invalid Date (Please use \"-\")"));
        criteria.setAcquisitionCountry(promptUntilValidOrBlank(scnr, "Acquisition Country: ", validator::validateCountry, "Invalid Country"));
        criteria.setTrainingStatus(promptUntilValidOrBlank(scnr, "Training Status: ", validator::validateTrainingStatus, "Invalid Training Status. " + validator.validTrainingStatus()));
        criteria.setReserved(promptUntilValidOrBlank(scnr, "Reservation Status (true or false)", validator::validateTrueFalse, "Invalid Answer"));
        criteria.setInServiceCountry(promptUntilValidOrBlank(scnr, "Current Service Country: ", validator::validateCountry, "Invalid Country"));

        // Dog only criteria
        if (criteria.getAnimalType().equalsIgnoreCase("Dog")) {
            criteria.setBreed(promptUntilValidOrBlank(scnr, "Dog Breed: ", validator::validateBreed, "Invalid Breed"));
        }

        // Monkey only criteria
        if (criteria.getAnimalType().equalsIgnoreCase("Monkey")) {
            criteria.setTailLengthMin(promptUntilValidOrBlank(scnr, "Tail Length Min: ", validator::validateTailLength, "Invalid Age"));
            criteria.setTailLengthMax(promptUntilValidOrBlank(scnr, "Tail Length Max: ", validator::validateTailLength, "Invalid Age"));
            criteria.setHeightMin(promptUntilValidOrBlank(scnr, "Height Min: ", validator::validateMeasurement, "Invalid Age"));
            criteria.setHeightMax(promptUntilValidOrBlank(scnr, "Height Max: ", validator::validateMeasurement, "Invalid Age"));
            criteria.setBodyLengthMin(promptUntilValidOrBlank(scnr, "Body Length Min: ", validator::validateMeasurement, "Invalid Age"));
            criteria.setBodyLengthMax(promptUntilValidOrBlank(scnr, "Body Length Max: ", validator::validateMeasurement, "Invalid Age"));
            criteria.setSpecies(promptUntilValidOrBlank(scnr, "Monkey Species: ", validator::validateSpecies, "Invalid Species. " + validator.validSpecies()));
        }

        return criteria;
    }

    // Method for updating an animal's attribute
    public void updateAnimal(Scanner scnr){
        // Get the animal's ID numbers
        String id = promptUntilValidOrBlank(scnr, "Which animal do you want to update? (Leave blank to cancel)", validator::validateIDNum, "Please enter a valid ID!");
        if (id == null || id.isBlank()) return; // If we don't have an ID number, then the user changed their mind

        // Get the animal we want to update
        RescueAnimal animal = animalSearchService.findByID(id);
        if (animal == null){
            // The hashmap will return null if the animal can't be found
            System.out.println(id + " cannot be found!");
            return; // Since the animal can't be found, return from the function
        }

        // Tell the user what they can update
        System.out.println("What do you want to update?");
        System.out.println("Name, Sex, Age, Weight, Acquisition Date, Acquisition Country, Training Status, Reservation Status, Service Country");
        System.out.println("Dog Only: Breed");
        System.out.println("Monkey Only: Tail Length, Height, Body Length, Species");
        System.out.println("Leave Blank to keep the existing data");

        // Go through each attribute an ask the user if they want to update it or not, and if they do, what data they want to replace the existing info with
        String name = promptUntilValidOrBlank(scnr, "Name: ", validator::validateName, "Invalid Name");
        if (name != null && !name.isBlank()) animalRepository.update(animal, RescueAnimal::setName, "name", name);
        String sex = promptUntilValidOrBlank(scnr, "Sex: ", validator::validateSex, "Invalid Sex");
        if (sex != null && !sex.isBlank()) animalRepository.update(animal, RescueAnimal::setGender, "gender", sex);
        String age = promptUntilValidOrBlank(scnr, "Age: ", validator::validateAge, "Invalid Age");
        if (age != null && !age.isBlank()) animalRepository.update(animal, RescueAnimal::setAge, "age", age);
        String weight = promptUntilValidOrBlank(scnr, "Weight (kg): ", validator::validateMeasurement, "Invalid Weight");
        if (weight != null && !weight.isBlank()) animalRepository.update(animal, RescueAnimal::setWeight, "weight", weight);
        String acquisitionDate = promptUntilValidOrBlank(scnr, "Acquisition Date (yyyy-mm-dd): ", validator::validateDate, "Invalid Date");
        if (acquisitionDate != null && !acquisitionDate.isBlank()) animalRepository.update(animal, RescueAnimal::setAcquisitionDate, "acquisitionDate", acquisitionDate);
        String acquisitionCountry = promptUntilValidOrBlank(scnr, "Acquisition Country: ", validator::validateCountry, "Invalid Country");
        if (acquisitionCountry != null && !acquisitionCountry.isBlank()) animalRepository.update(animal, RescueAnimal::setAcquisitionLocation, "acquisitionCountry", acquisitionCountry);
        String trainingStatus = promptUntilValidOrBlank(scnr, "Training Status: ", validator::validateTrainingStatus, "Invalid Training Status. " + validator.validTrainingStatus());
        if (trainingStatus != null && !trainingStatus.isBlank()) animalRepository.update(animal, RescueAnimal::setTrainingStatus, "trainingStatus", trainingStatus);
        Boolean reserved = promptUntilValidOrBlankBool(scnr, "Reservation Status (True/False): ", validator::validateTrueFalse, "Invalid Input");
        if (reserved != null) animalRepository.update(animal, RescueAnimal::setReserved, "reserved", reserved);
        String serviceCountry = promptUntilValidOrBlank(scnr, "Service Country: ", validator::validateCountry, "Invalid Country");
        if (serviceCountry != null && !serviceCountry.isBlank()) animalRepository.update(animal, RescueAnimal::setInServiceCountry, "inServiceCountry", serviceCountry);

        // Dog specific attributes
        if (animal instanceof Dog){
            Dog dog = (Dog)animal;
            String breed = promptUntilValidOrBlank(scnr, "Breed: ", validator::validateBreed, "Invalid Breed");
            if (breed != null && !breed.isBlank()) animalRepository.update(dog, Dog::setBreed, "breed", breed);
        }

        // Monkey specific attributes
        if (animal instanceof Monkey){
            Monkey monkey = (Monkey)animal;
            String tailLength = promptUntilValidOrBlank(scnr, "Tail Length (cm): ", validator::validateTailLength, "Invalid Length");
            if (tailLength != null && !tailLength.isBlank()) animalRepository.update(monkey, Monkey::setTailLength, "tailLength", tailLength);
            String height = promptUntilValidOrBlank(scnr, "Height (cm): ", validator::validateMeasurement, "Invalid Height");
            if (height != null && !height.isBlank()) animalRepository.update(monkey, Monkey::setHeight, "height", height);
            String bodyLength = promptUntilValidOrBlank(scnr, "Body Length (cm): ", validator::validateMeasurement, "Invalid Length");
            if (bodyLength != null && !bodyLength.isBlank()) animalRepository.update(monkey, Monkey::setBodyLength, "bodyLength", bodyLength);
            String species = promptUntilValidOrBlank(scnr, "Species: ", validator::validateSpecies, "Invalid Species. " + validator.validSpecies());
            if (species != null && !species.isBlank()) animalRepository.update(monkey, Monkey::setSpecies, "species", species);
        }
    }

    // Method to delete an animal
    public void deleteAnimal(Scanner scnr){
        // Get the animal the user wants to remove
        String id = promptUntilValidOrBlank(scnr, "Which animal do you want to remove from the list? (Leave blank to cancel)", validator::validateIDNum, "Please enter a valid ID!");
        if (id.isBlank() || id == null) return; // If id is blank then the user changed their mind

        // Remove the animal. If there isn't an animal matching the id, then the search will\
        // return null and nothing will be removed
        animalRepository.delete(animalSearchService.findByID(id));
    }

    // Method for adding test animals
    public void addTestAnimals(){
        animalRepository.addTestAnimals();
    }
}