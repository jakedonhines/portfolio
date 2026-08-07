import java.util.ArrayList;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.function.Function;

class AnimalSearchService {

    // Setting up Singleton instance of AnimalSearchService, because we only ever want one of them
    private static AnimalSearchService instance = null;

    private final AnimalRepository animals = AnimalRepository.getInstance(); // List of all the animals in our system

    // Private constructor for singleton
    private AnimalSearchService (){
    }

    // getInstance function will return the only instance of this class
    public static synchronized AnimalSearchService getInstance(){
        if (instance == null) instance = new AnimalSearchService(); // If an instance doesn't exist yet, create one
        return instance; // Return the instance
    }


    // *** Return lists based on specific critera ***

    // Return a list of every animal
    public ArrayList<RescueAnimal> getAllAnimals(){
        return animals.getAllAnimals();
    }

    // Return a list of all the dogs
    public ArrayList<Dog> getDogList(){
        // Make a temp list and add only dogs from the main list to it
        ArrayList<Dog> tempList = animals.getAllAnimals().stream().filter(Dog.class::isInstance)
            .map(Dog.class::cast).collect(Collectors.toCollection(ArrayList::new));

        // Return the new list
        return tempList;
    }

    // Return a list of all the monkeys
    public ArrayList<Monkey> getMonkeyList(){
        // Make a temp list and add only monkeys from the main list to it
        ArrayList<Monkey> tempList = animals.getAllAnimals().stream().filter(Monkey.class::isInstance)
            .map(Monkey.class::cast).collect(Collectors.toCollection(ArrayList::new));

        // Return the new list
        return tempList;
    }

    // Get a list of all available animals
    public ArrayList<RescueAnimal> getAvailableAnimals(){
        // Make a temp list of all the animals, we don't want to modify our main list
        ArrayList<RescueAnimal> tempList = new ArrayList<RescueAnimal>(animals.getAllAnimals());

        // Remove animals that don't fit our criteria
        // If the animal is currently reserved
        tempList.removeIf(animal -> animal.getReserved());
        // If the animal is not currently in service
        tempList.removeIf(animal -> !animal.getTrainingStatus().equalsIgnoreCase("in service"));

        // return this new list
        return tempList;
    }

    // Get a list of all animals that fit a requested criteria
    // The criteria is stored in a SearchCriteria class
    public ArrayList<RescueAnimal> searchWithCriteria(SearchCriteria criteria){
        if (criteria == null) return null; // If we have no criteria to look for, then there has probably been an error
        
        // Get the list of animals
        ArrayList<RescueAnimal> list = animals.getAllAnimals();

        // Filter the list of animals based on the search criteria
        filterStringMatch(list, criteria.getName(), RescueAnimal::getName, RescueAnimal.class);
        filterStringMatch(list, criteria.getSex(), RescueAnimal::getGender, RescueAnimal.class);
        list = filterRange(list, criteria.getAgeMin(), criteria.getAgeMax(), a -> Double.parseDouble(a.getAge()), RescueAnimal.class);
        list = filterRange(list, criteria.getWeightMin(), criteria.getWeightMax(), a -> Double.parseDouble(a.getWeight()), RescueAnimal.class);
        list = filterRange(list, stripDate(criteria.getAcquisitionDateMin()), stripDate(criteria.getAcquisitionDateMax()), 
            a -> Double.parseDouble(stripDate(a.getAcquisitionDate())), RescueAnimal.class);
        filterStringMatch(list, criteria.getTrainingStatus(), RescueAnimal::getTrainingStatus, RescueAnimal.class);
        filterStringMatch(list, criteria.getReserved().toString(), a -> Boolean.toString(a.getReserved()), RescueAnimal.class);
        filterStringMatch(list, criteria.getInServiceCountry(), RescueAnimal::getInServiceLocation, RescueAnimal.class);
        
        // Dog Specific Filters - If we do this on a list of only monkeys, the list will come back empty
        if (criteria.getAnimalType().equalsIgnoreCase("Dog")){
            filterStringMatch(list, criteria.getBreed(), Dog::getBreed, Dog.class);
        }
        // Monkey Specific Filters - If we do this on a list of only Dogs, the list will come back empty
        if (criteria.getAnimalType().equalsIgnoreCase("Monkey")){
            list = filterRange(list, criteria.getTailLengthMin(), criteria.getTailLengthMax(), m -> Double.parseDouble(m.getTailLength()), Monkey.class);
            list = filterRange(list, criteria.getHeightMin(), criteria.getHeightMax(), m -> Double.parseDouble(m.getHeight()), Monkey.class);
            list = filterRange(list, criteria.getBodyLengthMin(), criteria.getBodyLengthMax(), m -> Double.parseDouble(m.getBodyLength()), Monkey.class);
            filterStringMatch(list, criteria.getSpecies(), Monkey::getSpecies, Monkey.class);
        }

        // Return the list
        return list;
    }


    // *** Get Single Animal ***

    // Find a single animal by their ID
    // Since we are using a HashMap, the look up time is ~o(1)
    public RescueAnimal findByID(String id){
        return animals.getIDLookUpTable().get(id);
    }


    // *** Seach With Criteria Helper Functions ***

    // A helper function that will filter the list of animals by a given criteria
    // by looking at strings we have stored
    // ArrayList<RescueAnimal> list - an arraylist of all the animals
    // String target - the criteria we are looking for, if target is United States, 
    //                 and we are searching for animal's countries, then we will 
    //                 filter any country that isn't the us
    // Function<T, String> getter - the animal's getter function for the specific criteria.
    //                              For the country example, this would be getCountry()
    // Class<T> type - this is the type of animal obj we are working with. We encluded this so
    //                 we wouldn't need to have rewritten this function for monkeys or dogs
    private <T extends RescueAnimal> void filterStringMatch(ArrayList<RescueAnimal> list, String target, Function<T, String> getter, Class<T> type){
        // Look to see if we are working with a filter that is dog or monkey only
        if (!type.equals(RescueAnimal.class)){
            // If type isn't a rescue animal, then the list needs to be a dog or monkey
            // or we will have errors using the getter. We need to remove all instances
            // of animals that aren't the type we are working with.
            list.removeIf(a -> !(type.isInstance(a)));
        }
        if (target.isBlank()) return; // If target is blank, the user didn't want to search with this
                                      // criteria, and we should skip filtering it, or else the list
                                      // will be returned blank

        // Remove every animal from the list that does not fit our criteria
        // removeIf is done in linear time because it needs to iterate over every
        // animal in the list. Because of how we set up our functions, this time
        // will get smaller and smaller on every call because we will be removing
        // animals from the list, thus making the next list quicker to iterate through
        list.removeIf(a -> !getter.apply(type.cast(a)).equalsIgnoreCase(target));
    }

    // A helper function that will filter the list of animals that do not fit within
    // a given range by looking at the strings we have stored
    // ArrayList<RescueAnimal> list - an arraylist of all the animals
    // String min - the smallest value we want in our filtered list
    // String max - the largest value we want in our filtered list
    // ToDoubleFunction<T> getter - the getter for the criteria we are filtering by.
    //                              We need to make sure that the value we are getting
    //                              is a number, so we require that it can be cast to 
    //                              a double by using the ToDoubleFunction interface
    // Class<T> type - this is the type of animal obj we are working with. We included this so
    //                 we wouldn't need to have rewritten this function for monkeys or dogs
    private <T extends RescueAnimal> ArrayList<RescueAnimal> filterRange(
        ArrayList<RescueAnimal> list, String min, String max, ToDoubleFunction<T> getter, Class<T> type
    ){

        // Look to see if we are working with a filter that is dog or monkey only
        if (!type.equals(RescueAnimal.class)){
            // If type isn't a rescue animal, then the list needs to be a dog or monkey
            // or we will have errors using the getter. We need to remove all instances
            // of animals that aren't the type we are working with.
            list.removeIf(a -> !(type.isInstance(a))); 
        }
        if (min.isBlank() && max.isBlank()) return list; // If both min and max are blank, the user didn't want to search 
                                                         // with this criteria, and we should skip filtering it, or else 
                                                         // the list will be returned blank
        
        // We need to sort the list so we can properly filter it. This implimentation uses 
        // a binary search, which isn't as efficent here because we haven't sorted the list
        // yet. If this was sorted already, then it would be O(logn), this is more like O(n logn).
        // If we wanted to, we could get this working with linear time similarly as the other filter function
        // works, by looking at every animal and removing it if the search attribute isn't within the search
        // criteria. If we wanted to get this working in logarithmic time, then we would have to have a list
        // of animals specifically for each search criteria which would balloon out memory use.
        list.sort((a1, a2) -> Double.compare(getter.applyAsDouble(type.cast(a1)), getter.applyAsDouble(type.cast(a2))));

        // Variables that will tell us where the first and last valid animals are in the list by element number
        // These values default to the first or last animal incase the user didn't have a min or max.
        int minElement = 0;
        int maxElement = list.size() - 1;

        // Get the element numbers for the first and last valid animal using the binary search method
        if (!min.isBlank()) minElement = binarySearch(list, Double.parseDouble(min), getter, true, type);
        if (!max.isBlank()) maxElement = binarySearch(list, Double.parseDouble(max), getter, false, type);

        // Create a new list of animals using Java's subList function and return it.
        // minElement is inclusive, but maxElement is exclusive according to Java Docs
        return new ArrayList<RescueAnimal>(list.subList(minElement, maxElement+1));
    }

    // Helper function to perform a binary search over a list of animals. It will return the lowest or highest
    // valid animal based on the criteria
    // ArrayList<RescueAnimal> list - list of animals
    // double target - the value we are looking for (the min or max)
    // ToDoubleFunction<T> getter - the getter for the animal's attribute. This is essentially
    //                              getting passed through by the filterRange method
    // boolean min - true if we are looking for the smallest value, false if we are looking for the largest
    // Class<T> type - this is the type of animal obj we are working with.
    private <T extends RescueAnimal> int binarySearch(
        ArrayList<RescueAnimal> list, double target, ToDoubleFunction<T> getter, boolean min, Class<T> type
    ){

        int l = 0; // l is the low value
        int h = list.size() - 1; // h is the high value
        int mid = (l + h) / 2; // mid is the middle most value
        double g; // g is our current "guess"

        // Keep looping until l and h are the same or l is higher than h. Either happens
        // we have exhausted the entire list.
        while (l <= h){
            // If we are looking for the lowest value, we should set the mid point now
            // When I set the mid point here for the highest value, the last valid animal
            // would not be included
            if (min) mid = (l + h) / 2;
            g = getter.applyAsDouble(type.cast(list.get(mid)));

            // If g is the same as target, then we have found the value we are looking for!
            // We just need to check to make sure that we didn't skip any valid options.
            if (g == target){
                // If we are looking for the lowest value, then we should walk down the arraylist
                // until the next value isn't a valid option. Once that happens, we know we have
                // found the first valid animal
                if (min) {
                    while (mid > 0 && getter.applyAsDouble(type.cast(list.get(mid-1))) == target){
                        mid--;
                    }
                    break;
                // max works the same, but we walk up the arraylist instead.
                } else {
                    while (mid < list.size()-1 && getter.applyAsDouble(type.cast(list.get(mid+1))) == target){
                        mid++;
                    }
                    break;
                }
            // If g is larger than target, then we are too high on the list
            // so we will cut off half of it (from where we just looked), and
            // look again on the lower half.
            } else if (g > target){
                h = mid - 1;
            // If g is smaller than target, then we are too low on the list
            } else {
                l = mid + 1;
            }
            // Set the mid point if we are looking for the highest value.
            if (!min) mid = (l + h) / 2;
        }

        // Return the element number of "mid", which will be the lowest/highest element number that is
        // valid based on the criteria
        return mid;
    }

    // A helper function to allow us to cast the date format we are using to a double.
    private String stripDate(String date){
        // Remove any "-", and replace it with nothing. This will leave us with
        // just numbers. Because we are formting the dates as yyyy-mm-dd, the larger
        // the number, the new the date.
        return date.replace("-", "");
    }

}