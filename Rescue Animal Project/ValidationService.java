import java.util.Locale;
import java.util.stream.Collectors;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.Set;

class ValidationService {

    // Set up a singleton instance of ValidationService because we only ever need one of them
    private static ValidationService instance = null;

    // Sets of potential inputs we will use for validation
    // All of the valid monkey species
    private static final Set<String> ALLOWED_MONKEY_SPECIES = Set.of("capuchin", "guenon", "macaque", "marmoset", "squirrel monkey", "tamarin");
    // All ISO countries. We get this with java Locale
    private static final Set<String> COUNTRIES = Arrays.stream(Locale.getISOCountries())
        .map(code -> Locale.of("", code).getDisplayCountry(Locale.ENGLISH))
        .map(String::toLowerCase).collect(Collectors.toSet());
    // The possible training statuses for the animals for validation
    // I don't remember the exact possibilities from the original assignment, 
    private static final Set<String> TRAINING_STATUSES = Set.of("intaking", "training", "in service");

    // Private constructor for singleton
    private ValidationService (){
    }

    // getInstance function will return the only instance of this class
    public static synchronized ValidationService getInstance(){
        if (instance == null) instance = new ValidationService(); // If an instance doesn't exist yet, create one
        return instance; // return the instance
    }

    // *** Input Validation Functions ***
    //     All of these functions will take in a string (input from the user)
    //     and output a boolean. True means the string was valid, false means it wasn't
    //     Each of these test to see if the string is null or blank first

    // Validate ID numbers (ID numbers are currently 6 digits)
    public boolean validateIDNum(String idStr){
        // Check to see if name is null or empty
        if (nullOrBlank(idStr)) return false;

        // ID numbers should be 6 characters long
        if (idStr.length() != 6) return false;

        // ID numbers should be only numbers
        if (!idStr.chars().allMatch(Character::isDigit)) return false;

        // If the following tests were successful, then the string is a valid ID
        return true;
    }

    // Validate the name (No special requirments)
    public boolean validateName(String name){
        // Check to see if name is null or empty
        if (nullOrBlank(name)) return false;

        // Since names can be anything, we will assume the name entered is correct.
        return true;
    }

    // Validate the animal Type (Dog or Monkey)
    public boolean validateAnimalType(String type){
        // Check to see if animal type is null or empty
        if (nullOrBlank(type)) return false;

        // Check to see if type is either dog or monkey
        if (type.equalsIgnoreCase("Dog")) return true;
        if (type.equalsIgnoreCase("Monkey")) return true;

        // Animal type doesn't match dog or monkey, it is invalid
        return false;
    }

    // Validate the breed (No special requirments)
    public boolean validateBreed(String breed){
        // Check to see if breed is null or empty
        if (nullOrBlank(breed)) return false;

        // Since there are so many breeds of dogs, we will assume the breed entered is correct.
        // If we were to try an validate this, we could set up a Set with all the kinds of breeds,
        // similar to how we did the allowed monkey species. Ideally this list would be stored in a
        // seperate file so it is easier to add new breeds later.
        return true;
    }

    // Validate the Species (using the allowed species list)
    public boolean validateSpecies(String species){
        // Check to see if species is null or empty
        if (nullOrBlank(species)) return false;

        // Check to see if species is in the allowed species list
        return ALLOWED_MONKEY_SPECIES.contains(species.toLowerCase());
    }

    // Validate sex (Male, Female, Unknown)
    public boolean validateSex(String sex){
        // Check to see if sex is null or empty
        if (nullOrBlank(sex)) return false;

        // Check to see if sex is male, female, or unknown
        if (sex.equalsIgnoreCase("Male") || sex.equalsIgnoreCase("Female")) {
            return true;
        } else if (sex.equalsIgnoreCase("Unknown")) {
            // Seperating the Unknown clause to make it easier to edit later,
            // such as instances where we want to give a warning, or perform
            // a different function in these cases.
            return true;
        } else {
            return false;
        }
    }

    // Validate Age (needs to be positive)
    public boolean validateAge(String ageStr){
        // Check to see if age is null or empty
        if (nullOrBlank(ageStr)) return false;

        // Check to see if ageString is a number
        int ageNum;
        try{
            ageNum = Integer.parseInt(ageStr);
        } catch (Exception e){
            return false;
        }

        // Check to make sure that age isn't negative
        if (ageNum < 0) return false;

        // If we pass all the checks, then age is a valid number.
        // Since there is theoretically no upper bound to age,
        // we didn't check to see if it is too large. If we wanted
        // to check for this, the current record for oldest dog is
        // about 30, and about 82 for apes/monkeys
        return true;
    }

    // Validate monkey tail length (needs to be positive)
    // We aren't using the validateMeasurement function for this one because tail length could be zero
    public boolean validateTailLength(String lengthStr){
        // Check to see if length is null or empty
        if (nullOrBlank(lengthStr)) return false;

        // Check to see if length is a number
        double legnthNum;
        try{
            legnthNum = Double.parseDouble(lengthStr);
        } catch (Exception e){
            return false;
        }

        // Check to make sure that length is positive
        if (legnthNum < 0) return false;

        // If we pass all the checks, then length is a valid number.
        // Since there is no theroetical upp bound for tail length, we are not checking to see if the number is too large
        return true;
    }

    // Validate measurements [Height, Weight, Length, etc...] (needs to be more than 0)
    public boolean validateMeasurement(String measurementStr){
        // Check to see if measurement is null or empty
        if (nullOrBlank(measurementStr)) return false;

        // Check to see if measurementString is a number
        double measurementNum;
        try{
            measurementNum = Double.parseDouble(measurementStr);
        } catch (Exception e){
            return false;
        }

        // Check to make sure that measurement is above 0
        if (measurementNum <= 0.0) return false;

        // If we pass all the checks, then measurement is a valid number.
        // Since we are using this function for multiple different
        // measurements, and there is theroletically no upper bound
        // for them, we are not checking to see if the number is too large.
        return true;
    }

    // Validate date (yyyy-mm-dd)
    public boolean validateDate(String date){
        // Check to see if measurement is null or empty
        if (nullOrBlank(date)) return false;

        // See if it is a valid date
        try {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("uuuu-MM-dd").toFormatter().withResolverStyle(ResolverStyle.STRICT);

            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeException e){
            return false;
        }
    }

    // Validate country (using the list of counties from Java Locale)
    public boolean validateCountry(String country){
        // Check to see if country is null or empty
        if (nullOrBlank(country)) return false;

        // Check to see if country is in the countries set
        // of ISO countries, obtained using java locale
        return COUNTRIES.contains(country.trim().toLowerCase());
    }

    // Validate animal status
    public boolean validateTrainingStatus(String status){
        // Check to see if status is null or empty
        if (nullOrBlank(status)) return false;

        // Check to see if species is in the training status list
        return TRAINING_STATUSES.contains(status.toLowerCase());
    }

    // Validate for true or false input
    public boolean validateTrueFalse(String input){
        // Check to see if status is null or empty
        if (nullOrBlank(input)) return false;

        // Check if input is true or false
        if (input.equalsIgnoreCase("true")) return true;
        if (input.equalsIgnoreCase("false")) return true;
        
        return false;
    }


    // Returns a list of all the valid species
    public String validSpecies(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Valid Monkey Species are: ");
        ALLOWED_MONKEY_SPECIES.forEach(m -> stringBuilder.append(m + ", "));
        // Delete the last two characters (which are ", ")
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    public String validTrainingStatus(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Valid Training Statuses are: ");
        TRAINING_STATUSES.forEach(t -> stringBuilder.append(t + ", "));
        // Delete the last two characters (which are ", ")
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    // Helper function to see if the string is null or blank
    private boolean nullOrBlank(String string){
        return (string == null || string.isBlank());
    }

}