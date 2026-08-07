import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.sql.*;

class AnimalRepository {

    // Set up singleton instance because we only ever want one repository
    private static AnimalRepository instance = null;

    // URLs for the SQLite databases
    String dogURL = "jdbc:sqlite:dog.db";
    String monkeyURL = "jdbc:sqlite:monkey.db";

    // List of all animals, this list should be sorted by ID
    private final ArrayList<RescueAnimal> animals = new ArrayList<RescueAnimal>();  
    // Look up table by ID
    private final HashMap<String, RescueAnimal> animalsByID = new HashMap<>();

    // Private constructor for singleton
    private AnimalRepository (){
        createTables();
        initializeLists();
    }

    // Method for connecting to SQL database
    // If the tables don't exist, new ones will be generated
    private void createTables(){
        // Try to connect to the Dog Table
        try (Connection conn = DriverManager.getConnection(dogURL)) {
            if (conn != null){
                System.out.println("Connection to Dog Database Successfull!");

                // The table needs fields for every attribute a dog has
                String createTableSQL = "CREATE TABLE IF NOT EXISTS dogs ("
                    + " id INTEGER PRIMARY KEY,"
                    + " name TEXT NOT NULL,"
                    + " gender TEXT NOT NULL,"
                    + " age TEXT NOT NULL,"
                    + " weight TEXT NOT NULL,"
                    + " acquisitionDate TEXT NOT NULL,"
                    + " acquisitionCountry TEXT NOT NULL,"
                    + " trainingStatus TEXT NOT NULL,"
                    + " reserved INTEGER,"
                    + " inServiceCountry TEXT NOT NULL,"
                    + " breed TEXT NOT NULL"
                    + ");";
                
                // Make/Connect to the table
                try (Statement stmt = conn.createStatement()){
                    stmt.execute(createTableSQL);
                    System.out.println("Table 'dogs' verified/created");
                }
            }
        } catch (SQLException e){
            // Tell the user if there was an error
            System.err.println("Database Error: " + e.getMessage());
        }

        // Try to connect to the monkey table
        try (Connection conn = DriverManager.getConnection(monkeyURL)) {
            if (conn != null){
                System.out.println("Connection to Monkey Database Successfull!");

                // Like the dog table, we need fields for every attribute a monkey has
                String createTableSQL = "CREATE TABLE IF NOT EXISTS monkeys ("
                    + " id INTEGER PRIMARY KEY,"
                    + " name TEXT NOT NULL,"
                    + " gender TEXT NOT NULL,"
                    + " age TEXT NOT NULL,"
                    + " weight TEXT NOT NULL,"
                    + " acquisitionDate TEXT NOT NULL,"
                    + " acquisitionCountry TEXT NOT NULL,"
                    + " trainingStatus TEXT NOT NULL,"
                    + " reserved INTEGER,"
                    + " inServiceCountry TEXT NOT NULL,"
                    + " tailLength TEXT NOT NULL,"
                    + " height TEXT NOT NULL,"
                    + " bodyLength TEXT NOT NULL,"
                    + " species TEXT NOT NULL"
                    + ");";
                
                // Make/Connect to the table
                try (Statement stmt = conn.createStatement()){
                    stmt.execute(createTableSQL);
                    System.out.println("Table 'monkeys' verified/created");
                }
            }
        } catch (SQLException e){
            // Tell the user if there was an error
            System.err.println("Database Error: " + e.getMessage());
        }
    }

    // getInstance function will return the only instance of this class
    public static synchronized AnimalRepository getInstance() {
        if (instance == null) instance = new AnimalRepository(); // If an instance doesn't exist yet, create one
        return instance; // Return the instance
    }

    // *** Database CRUD Functions ***

    // Add an animal to the animal list - C of CRUD
    public void add (RescueAnimal animal){
        if (contains(animal.getID())) return;

        int rows = 0; // A variable we will use to see if any tables were changed

        // Since we have both dogs and monkeys, we need to seperate the logic for adding them
        if (animal instanceof Dog){
            Dog dog = (Dog)animal;

            // SQL statement for adding a dog to the dog table
            String sql = "INSERT INTO dogs "
                + "(id,name,gender,age,weight,acquisitionDate,acquisitionCountry,"
                + "trainingStatus,reserved,inServiceCountry,breed)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            try (Connection conn = DriverManager.getConnection(dogURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){

                // Fill in the "?" in the SQL statement so we actually save the info we want to save
                pstmt.setString(1, dog.getID());
                pstmt.setString(2, dog.getName());
                pstmt.setString(3, dog.getGender());
                pstmt.setString(4, dog.getAge());
                pstmt.setString(5, dog.getWeight());
                pstmt.setString(6, dog.getAcquisitionDate());
                pstmt.setString(7, dog.getAcquisitionLocation());
                pstmt.setString(8, dog.getTrainingStatus());
                pstmt.setBoolean(9, dog.getReserved());
                pstmt.setString(10, dog.getInServiceLocation());
                pstmt.setString(11, dog.getBreed());

                rows += pstmt.executeUpdate(); // Add the dog to the table and get the number of rows that have changed
                System.out.println(dog.getID() + " has been added!");
            } catch (SQLException e){
                System.err.println("Error Adding Dog! " + e.getMessage());
            }
        }

        // Add a monkey logic
        if (animal instanceof Monkey){
            Monkey monkey = (Monkey)animal;

            // SQL statement for adding a monkey to the monkey table
            String sql = "INSERT INTO monkeys "
                + "(id,name,gender,age,weight,acquisitionDate,acquisitionCountry,trainingStatus,"
                + "reserved,inServiceCountry,tailLength,height,bodyLength,species)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try (Connection conn = DriverManager.getConnection(monkeyURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){

                // Fill in the "?"s in the SQL statement so we save the data we need to save
                pstmt.setString(1, monkey.getID());
                pstmt.setString(2, monkey.getName());
                pstmt.setString(3, monkey.getGender());
                pstmt.setString(4, monkey.getAge());
                pstmt.setString(5, monkey.getWeight());
                pstmt.setString(6, monkey.getAcquisitionDate());
                pstmt.setString(7, monkey.getAcquisitionLocation());
                pstmt.setString(8, monkey.getTrainingStatus());
                pstmt.setBoolean(9, monkey.getReserved());
                pstmt.setString(10, monkey.getInServiceLocation());
                pstmt.setString(11, monkey.getTailLength());
                pstmt.setString(12, monkey.getHeight());
                pstmt.setString(13, monkey.getBodyLength());
                pstmt.setString(14, monkey.getSpecies());

                rows += pstmt.executeUpdate(); // Add the monkey to the table and get the number of ros that have changed
                System.out.println(monkey.getID() + " has been added!");
            } catch (SQLException e){
                System.err.println("Error Adding Monkey! " + e.getMessage());
            }
        }
        
        // If the number of rows that have been changed is more than 0, then we will assume that
        // we added our animal successfully and we can add it to our in memory cache.
        if (rows > 0){
            animals.add(animal);
            animalsByID.put(animal.getID(), animal);
            sortList(); // Sort the list so the new animal is in the right place
        } else {
            // If the number is 0 then we know nothing changed and we shouldn't cache the animal
            // If its smaller than 0 then something went wrong.
            System.out.println("Error adding animal");
        }
    }

    // Method to get all the animals in the database and put them into our cache so
    // our program can use them. - R of CRUD
    private void initializeLists(){
        // We want to get all the dogs in the dog database
        String sql = "SELECT * FROM dogs";
        // Try to get a list of dogs
        try (Connection conn = DriverManager.getConnection(dogURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            // We will go through every dog in the table and create a new dog object using
            // the information in the table's fields.
            while(rs.next()){
                Dog dog = new Dog(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("breed"),
                    rs.getString("gender"),
                    rs.getString("age"),
                    rs.getString("weight"),
                    rs.getString("acquisitionDate"),
                    rs.getString("acquisitionCountry"),
                    rs.getString("trainingStatus"),
                    rs.getBoolean("reserved"),
                    rs.getString("inServiceCountry")
                );

                // Add the new dog to the cache
                animals.add(dog);
                animalsByID.put(dog.getID(), dog);
            }
        } catch(SQLException e){
            System.err.println("Error Reading Dog Database! " + e.getMessage());
        }

        // We will do the same thing we did for the dogs for the monkeys
        sql = "SELECT * FROM monkeys";
        try (Connection conn = DriverManager.getConnection(monkeyURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
                
            while(rs.next()){
                Monkey monkey = new Monkey(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("age"),
                    rs.getString("weight"),
                    rs.getString("acquisitionDate"),
                    rs.getString("acquisitionCountry"),
                    rs.getString("trainingStatus"),
                    rs.getBoolean("reserved"),
                    rs.getString("inServiceCountry"),
                    rs.getString("tailLength"),
                    rs.getString("height"),
                    rs.getString("bodyLength"),
                    rs.getString("species")
                );

                animals.add(monkey);
                animalsByID.put(monkey.getID(), monkey);
            }
        } catch(SQLException e){
            System.err.println("Error Reading Dog Database! " + e.getMessage());
        }

        // We need to sort the cache now that everything has been added
        sortList();
    }
          
    // Method to update an animal - U of CRUD
    //      T animal - the animal we are updating
    //      BiConsumer setter - the setter function of the attribute we are updating
    //      String attribute - the name of the attribute we are changing
    //      V newValue - the value we are changing the attribute too
    public <T extends RescueAnimal, V> void update(T animal, BiConsumer<T, V> setter, String attribute, V newValue){
        // Get the animal's type (Monkey or Dog)
        String animalType = null;
        if (animal instanceof Dog) animalType = "dogs";
        else if (animal instanceof Monkey) animalType = "monkeys";

        // If there is an error getting the animal type, then we should end the function
        if (animalType == null || animalType.isBlank()) return;

        // Update SQL statement
        String sql = "UPDATE "+animalType+" SET "+attribute+"=? WHERE id=?";
        int rows = 0; // Like the add method, we are going to keep track how many rows were changed so we know if we updated the table successfully

        // If the animal is a dog, we need to make sure we are updating the dog table
        if (animalType.equalsIgnoreCase("dogs")){
            try(Connection conn = DriverManager.getConnection(dogURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){

                // Most attributtes are strings, but reserved is a boolean, so we need to make sure that we are saving them properly
                if (newValue instanceof String) pstmt.setString(1, (String)newValue);
                else if (newValue instanceof Boolean) pstmt.setBoolean(1, (Boolean)newValue);
                else throw new IllegalArgumentException("Unsupported update type");
                pstmt.setString(2, animal.getID());

                rows += pstmt.executeUpdate();
            } catch (SQLException e){
                System.err.println("ERROR Updating Dog " + animal.getID());
                System.err.println(e.getMessage());
            }

        // For when updating a monkey    
        } else if (animalType.equalsIgnoreCase("monkeys")){
            try(Connection conn = DriverManager.getConnection(monkeyURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){

                // Same as dogs, reserved is a boolean, everything else is a string
                if (newValue instanceof String) pstmt.setString(1, (String)newValue);
                else if (newValue instanceof Boolean) pstmt.setBoolean(1, (Boolean)newValue);
                else throw new IllegalArgumentException("Unsupported update type");
                pstmt.setString(2, animal.getID());

                rows += pstmt.executeUpdate();
            } catch (SQLException e){
                System.err.println("ERROR Updating Monkey " + animal.getID());
                System.err.println(e.getMessage());
            }
        }

        // If rows is larger than 0, then the update was successful and we can and should edit the cache
        if (rows > 0){
            setter.accept(animal, newValue);
        }
    }

    // method to remove an animal - D of CRUD
    //      RescueAnimal animal - the animal we want to delete
    public void delete(RescueAnimal animal){
        // Get the animal type
        String animalType = null;
        if (animal instanceof Dog) animalType = "dogs";
        else if (animal instanceof Monkey) animalType = "monkeys";
        // If we can't get the animal type then theres an error and we should return
        if (animalType == null || animalType.isBlank()) return;

        // SQL statement to delete something from a table
        String sql = "DELETE FROM "+animalType+" WHERE id=?";
        int rows = 0;

        // If we are removing a dog, then we need to remove it from the dog table
        if (animalType.equalsIgnoreCase("dogs")){
            try(Connection conn = DriverManager.getConnection(dogURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, animal.getID());

                rows += pstmt.executeUpdate();
            } catch(SQLException e){
                System.err.println("ERROR Deleting Dog " + animal.getID());
                System.err.println(e.getMessage());
            }
        }

        // If we are removing a monkey, then we need to remove it from the monkey table
        if (animalType.equalsIgnoreCase("monkeys")){
            try(Connection conn = DriverManager.getConnection(monkeyURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, animal.getID());

                rows += pstmt.executeUpdate();
            } catch(SQLException e){
                System.err.println("ERROR Deleting Monkey " + animal.getID());
                System.err.println(e.getMessage());
            }
        }

        // If we updated rows, then we can assume that we removed the animal from the tables and our cache should reflect that
        if (rows > 0){
            animals.remove(animal);
            animalsByID.remove(animal.getID());
        }
    }

    // *** Repository Interface for Animal Service ***

    // This function will return an ID that has not been used yet.
    public String generateID(){
        int max = 0;

        // Get the list of animals
        ArrayList<RescueAnimal> tempList = getAllAnimals();

        // Look to see what the highest id number of any animal in our system is
        for (RescueAnimal a : tempList){
            max = Math.max(max, Integer.parseInt(a.getID()));
        }

        // Once we have the highest ID number, return the next possible ID number
        // with the proper number of zeros in front of it.
        return String.format("%06d", max + 1);
    }

    // Return a list of all animals
    public ArrayList<RescueAnimal> getAllAnimals(){
        // We need to make a copy of our list so we don't expose our actual list
        ArrayList<RescueAnimal> tempList = new ArrayList<RescueAnimal>(animals);

        // return the new list
        return tempList;
    }

    // Return a Look Up table of all animals
    public HashMap<String, RescueAnimal> getIDLookUpTable(){
        // We need to make a copy of the table so we don't expose the actual table
        HashMap<String, RescueAnimal> tempTable = new HashMap<String, RescueAnimal>(animalsByID);

        // Return the new table
        return tempTable;
    }

    // Returns true or false base on if there is an animal whose id matches id
    public boolean contains (String id){
        // Look in the HashMap to see if we have a matching Key
        return animalsByID.containsKey(id);
    }

    // Sort the list of animals using the toCompare function in RescueAnimals
    // This will sort them by ID number
    private void sortList(){
        Collections.sort(animals);
    }

    // *** Test Animals ***

    // Add test animals
    public void addTestAnimals(){
        initializeDogList();
        initializeMonkeyList();
    }

    // Adds dogs to a list for testing
    private void initializeDogList() {
        Dog dog1 = new Dog("000001", "Spot", "German Shepherd", "male", "1", "25.6", "219-05-12", "United States", "intake", false, "United States");
        Dog dog2 = new Dog("000002", "Rex", "Great Dane", "male", "3", "35.2", "2020-02-03", "United States", "training", false, "United States");
        Dog dog3 = new Dog("000003", "Bella", "Chihuahua", "female", "4", "25.6", "2019-12-12", "Canada", "in service", true, "Canada");
        Dog dog4 = new Dog("000004", "Julia", "Chihuahua", "female", "4", "25.6", "2019-12-12", "Canada", "in service", false, "Canada");
        Dog dog5 = new Dog("000005", "Omara", "Chihuahua", "female", "4", "25.6", "2019-12-12", "Canada", "intake", true, "Canada");
        Dog dog6 = new Dog("000006", "Siggi", "Chihuahua", "female", "4", "25.6", "2019-12-12", "Canada", "intake", false, "Canada");

        add(dog1);
        add(dog2);
        add(dog3);
        add(dog4);
        add(dog5);
        add(dog6);
    }

    // Adds monkeys to a list for testing
    //Optional for testing
    private void initializeMonkeyList() {
        Monkey monkey1 = new Monkey("000007", "Bob", "male", "10", "15.6", "2022-06-04", "United States", "intake", false, "United States", "0.5", "2.1", "1.4", "Tamarin");
        Monkey monkey2 = new Monkey("000008", "Ann", "female", "9", "12.3", "2021-11-24", "United States", "intake", false, "United States", "0.5", "1.9", "1.3", "Capuchin");
        Monkey monkey3 = new Monkey("000009", "Amelia", "female", "9", "12.3", "2021-11-24", "United States", "in service", true, "United States", "0.5", "1.9", "1.3", "Capuchin");
        Monkey monkey4 = new Monkey("000010", "Donna", "female", "9", "12.3", "2021-11-24", "United States", "in service", false, "United States", "0.5", "1.9", "1.3", "Capuchin");
        Monkey monkey5 = new Monkey("000011", "Jane", "female", "9", "12.3", "2021-11-24", "United States", "intake", true, "United States", "0.5", "1.9", "1.3", "Capuchin");

        add(monkey1);
        add(monkey2);
        add(monkey3);
        add(monkey4);
        add(monkey5);
    }

}