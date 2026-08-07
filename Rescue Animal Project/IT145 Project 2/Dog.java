
public class Dog extends RescueAnimal {

    // Instance variable
    private String breed;

    // Constructor
    public Dog(String name, String breed, String gender, String age,
    String weight, String acquisitionDate, String acquisitionCountry,
	String trainingStatus, boolean reserved, String inServiceCountry) {
        setName(name);
        setBreed(breed);
        setGender(gender);
        setAge(age);
        setWeight(weight);
        setAcquisitionDate(acquisitionDate);
        setAcquisitionLocation(acquisitionCountry);
        setTrainingStatus(trainingStatus);
        setReserved(reserved);
        setInServiceCountry(inServiceCountry);

    }

    // Accessor Method
    public String getBreed() {
        return breed;
    }

    // Mutator Method
    public void setBreed(String dogBreed) {
        breed = dogBreed;
    }

    // Custom toString method to print out all the dog's useful information
    public String toString (){
        StringBuilder dogInfo = new StringBuilder();

        // Add all the info to the string
        dogInfo.append(getName());
        dogInfo.append(": {Dog, ");
        dogInfo.append(getBreed());
        dogInfo.append(", ");
        dogInfo.append(getGender());
        dogInfo.append(", ");
        dogInfo.append(getAge());
        dogInfo.append(" y/o, "); // y/o -> "years old"
        dogInfo.append(getWeight());
        dogInfo.append(" kgs, acquired on: "); // Due to the international reach of this program, I'm assimung the weight would be entered in metric, so kilograms
        dogInfo.append(getAcquisitionDate());
        dogInfo.append(", acquired in: ");
        dogInfo.append(getAcquisitionLocation());
        dogInfo.append(", currently servicing: ");
        dogInfo.append(getInServiceLocation());
        dogInfo.append(", ");
        dogInfo.append(getTrainingStatus());
        dogInfo.append("} ");

        // Just "true" and "false" isn't a useful in this string, so instead of adding the longer
        // "Reserved: <reservation status>", I entered custom strings for the two options
        if (getReserved() == true){
            dogInfo.append("RESERVED!");
        } else {
            dogInfo.append("Available for Service");
        }

        // return the newly built string
        return dogInfo.toString();
    }

}
