package Software;

/**
 * Member contains all the data points for the member entity
 * @author Nolan McKivergan
 */
public class Member {
	/**
	 * Name of member
	 */
	public String memberName;
	
	/**
	 * Unique member ID numbers
	 */
	public String memberNumber;
	
	/**
	 * Address of member
	 */
	public String memberAddress;
	
	/**
	 * City of member
	 */
	public String memberCity;
	
	/**
	 * State of member
	 */
	public String memberState;
	
	/**
	 * ZIP code of member
	 */
	public String memberZIP;
	
	/**
	 * Status of member
	 */
	public String memberStatus;
	
	/**
	 * Constructor for the class
	 * @param name Name of member
	 * @param num Num of member
	 * @param address Address of member
	 * @param city City of member
	 * @param state State of member
	 * @param zip ZIP code of member
	 * @param status Status of member
	 */
	public Member(String name, String num, String address, String city, String state, String zip, String status) {
		memberName = name;
		memberNumber = num;
		memberAddress = address;
		memberCity = city;
		memberState = state;
		memberZIP = zip;
		memberStatus = status;
	}
}