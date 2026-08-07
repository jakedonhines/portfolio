import java.lang.String;

public class RescueAnimal implements Comparable<RescueAnimal>{

    // Instance variables
	private String id;
    private String name;
    private String animalType;
    private String gender;
    private String age;
    private String weight;
    private String acquisitionDate;
    private String acquisitionCountry;
	private String trainingStatus;
    private boolean reserved;
	private String inServiceCountry;


    // Constructor
    public RescueAnimal() {
    }


	// *** Setters and Getters ***

	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAnimalType() {
		return animalType;
	}
	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}

	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAcquisitionDate() {
		return acquisitionDate;
	}
	public void setAcquisitionDate(String acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public String getAcquisitionLocation() {
		return acquisitionCountry;
	}
	public void setAcquisitionLocation(String acquisitionCountry) {
		this.acquisitionCountry = acquisitionCountry;
	}

	public boolean getReserved() {
		return reserved;
	}
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public String getInServiceLocation() {
		return inServiceCountry;
	}
	public void setInServiceCountry(String inServiceCountry) {
		this.inServiceCountry = inServiceCountry;
	}

	public String getTrainingStatus() {
		return trainingStatus;
	}
	public void setTrainingStatus(String trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	// compareTo sorts by ID
	@Override
	public int compareTo(RescueAnimal other){
		try{
			// Get integer values for the two id strings to make comparing them easier
			int thisID = Integer.parseInt(id);
			int otherID = Integer.parseInt(other.getID());

			// Return a value based on which value is large
			// 1 means this obj's ID is larger
			// -1 means the other obj's ID is larger
			// 0 means they are the same value
			if (thisID > otherID) return 1;
			if (thisID < otherID) return -1;
			return 0;
		} catch (Exception e){
			// If there is an error, then we cannot properly compare
			// the two IDs, so we will just say they are equal.
			return 0;
		}
	}
}
