// Jake Hines
// IT - 145

public class Monkey extends RescueAnimal {

    // Monkey specific feilds
    private String tailLength;  // Length of the monkey's tail
    private String height;      // Monkey's height
    private String bodyLength;  // Monkey's Body Length
    private String species;     // Monkey's Species
    
    // constructor with the relevant information
    public Monkey(String name, String gender, String age,
    String weight, String acquisitionDate, String acquisitionCountry,
	String trainingStatus, boolean reserved, String inServiceCountry,
    String tailLength, String height, String bodyLength, String species) {
        // Parant class fields
        setName(name);
        setGender(gender);
        setAge(age);
        setWeight(weight);
        setAcquisitionDate(acquisitionDate);
        setAcquisitionLocation(acquisitionCountry);
        setTrainingStatus(trainingStatus);
        setReserved(reserved);
        setInServiceCountry(inServiceCountry);

        // Monkey specific feilds
        setTailLength(tailLength);
        setHeight(height);
        setBodyLength(bodyLength);
        setSpecies(species);
    }

    // Setters for each of the new fields
    public void setTailLength(String tailLength) {
        this.tailLength = tailLength;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public void setBodyLength(String bodyLength) {
        this.bodyLength = bodyLength;
    }
    public void setSpecies(String species) {
        this.species = species;
    }

    // Getters for each of the new fields
    public String getTailLength() {
        return tailLength;
    }
    public String getHeight() {
        return height;
    }
    public String getBodyLength() {
        return bodyLength;
    }
    public String getSpecies() {
        return species;
    }

    // Custom toString method to print out all the monkey's useful information
    public String toString (){
        StringBuilder monkeyInfo = new StringBuilder();

        // Add all the info to the string
        monkeyInfo.append(getName());
        monkeyInfo.append(": {Monkey, ");
        monkeyInfo.append(getSpecies());
        monkeyInfo.append(", ");
        monkeyInfo.append(getGender());
        monkeyInfo.append(", ");
        monkeyInfo.append(getAge());
        monkeyInfo.append(" y/o, "); // y/o -> "years old"
        monkeyInfo.append(getWeight());
        monkeyInfo.append(" kg, "); // Due to the international reach of this program, I'm assimung physical attributes would be measured in metric, so kilograms and meters
        monkeyInfo.append(getTailLength());
        monkeyInfo.append(" m long tail, ");
        monkeyInfo.append(getHeight());
        monkeyInfo.append(" m tall, acquired on:");
        monkeyInfo.append(getAcquisitionDate());
        monkeyInfo.append(", acquired in: ");
        monkeyInfo.append(getAcquisitionLocation());
        monkeyInfo.append(", currently servicing: ");
        monkeyInfo.append(getInServiceLocation());
        monkeyInfo.append(", ");
        monkeyInfo.append(getTrainingStatus());
        monkeyInfo.append("} ");

        // Just "true" and "false" isn't a useful in this string, so instead of adding the longer
        // "Reserved: <reservation status>", I entered custom strings for the two options
        if (getReserved() == true){
            monkeyInfo.append("RESERVED!");
        } else {
            monkeyInfo.append("Available for Service");
        }

        // return the newly built string
        return monkeyInfo.toString();
    }

}