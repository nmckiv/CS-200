package Software;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
/**
* ModifyMemberDatabase contains the methods to add, remove and update the member database.
*
* @author Nolan McKivergan
*/

public class ModifyMemberDatabase {
	/**
	 * Keeps track of the lowest available member ID number
	 */
	private int currMemberNumber;
	
	/**
	 * Path to text file holding info for all members
	 */
	private String pathToFile;
	
	/**
	 * BufferedWriter object for writing to text file
	 */
	private BufferedWriter writer;
	
	/**
	 * BufferedReader object for reading from text file
	 */
	private BufferedReader reader;
	
	/**
	 * ArrayList of all members as member objects
	 */
	public ArrayList<Member> members;
	
	/**
	 * Constructor for the class
	 */
	public ModifyMemberDatabase() {
		//Set absolute path to file using current working directory
		pathToFile = (System.getProperty("user.dir") + "\\members.txt");
		
		//Initialize member list
		members = new ArrayList<Member>();
		currMemberNumber = 0;
		
		//Load member list from file
		loadFromFile();
	}

	/**
	 * retrieveMember searches for a member by number
	 * @param num
	 * @return returns matching member or null if not found
	 */
	public Member retrieveMember(String num) {
		for (int x = 0; x < members.size(); x++) {
			if (num.equals(members.get(x).memberNumber)) {
				return members.get(x);
			}
		}
		return null;
	}

	/**
	 * addMember adds a new member to the member list and saves to the file
	 * @param name Name of member
	 * @param address Address of member
	 * @param city City of member
	 * @param state State of member
	 * @param zip ZIP code of member
	 * @param status Status of member
	 * @return returns 0 if successful, 1 if failed
	 */
	public int addMember(String name, String address, String city, String state, String zip, String status) {
		//Add member to local member list
		try {
			members.add(new Member(name, generateMemberNumber(), address, city, state, zip, status));
			//Save updated member list to file
			saveToFile();
			
			//Return successful
			return 0;
		}
		catch (Exception e) {
			//Return failure
			return 1;
		}
	}

	/**
	 * This version of editMember finds a member by their number and changes one of their data points
	 * @param num unique 9-digit member number
	 * @param dataToChange identifies data point to edit
	 * @param newData identifies what to change data point to
	 * @return returns 0 if successful, 1 if failed
	 */
	public int editMember(String num, String dataToChange, String newData) {
		for (int x = 0; x < members.size(); x++) {
			if (Integer.parseInt(num) == Integer.parseInt(members.get(x).memberNumber)) {
				//Change local member list
				switch (dataToChange) {
					case "Name" : members.get(x).memberName = newData; break;
					case "Number" : members.get(x).memberNumber = newData; if (Integer.parseInt(newData) >= currMemberNumber) {currMemberNumber = Integer.parseInt(newData) + 1;} break;
					case "Address" : members.get(x).memberAddress = newData; break;
					case "City" : members.get(x).memberCity = newData; break;
					case "State" : members.get(x).memberState = newData; break;
					case "ZIP" : members.get(x).memberZIP = newData; break;
					case "Status" : members.get(x).memberStatus = newData; break;
					default : /*System.out.println("Invalid data identifier, options are Name, Number, Address, City, State, ZIP, Status");*/ return 1;
				}
				
				//Save updated member list to file
				saveToFile();
				
				//Return successful
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * This version of editMember finds a member by their number and overwrites all their data
	 * @param name New name of member
	 * @param num Number of member to change
	 * @param address New address of member
	 * @param city New city of member
	 * @param state New state of member
	 * @param zip New ZIP code of member
	 * @param status New status of member
	 * @return returns 0 if successful, 1 if failed
	 */
	public int editMember(String name, String num, String address, String city, String state, String zip, String status) {
		for (int x = 0; x < members.size(); x++) {
			if (Integer.parseInt(num) == Integer.parseInt(members.get(x).memberNumber)) {
				members.get(x).memberName = name;
				members.get(x).memberAddress = address;
				members.get(x).memberCity = city;
				members.get(x).memberState = state;
				members.get(x).memberZIP = zip;
				members.get(x).memberStatus = status;
				//Save updated member list to file
				saveToFile();
				return 0;//Return successful
			}
		}
		return 1;//Return fail
	}
	
	/**
	 * removeMember finds a member by their number, removes them from the members list, and saves to the file
	 * @param num Unique 9-digit Member Number
	 * @return returns 0 if successful, 1 if failed
	 */
	public int removeMember(String num) {
		for (int x = 0; x < members.size(); x++) {
			if (num.equals(members.get(x).memberNumber)) {
				//Remove member from local member list
				members.remove(x);
				
				//Save updated member list
				saveToFile();
				
				//Return successful
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * clearDatabase clears the text file and member list
	 * @return returns 0 if successful, 1 if failed
	 */
	public int clearDatabase() {
		
		//Clear file if it exists
		try {
			Files.newOutputStream(Path.of(pathToFile), StandardOpenOption.TRUNCATE_EXISTING);
			members.clear();
			currMemberNumber  = 0;
			return 0;
		}
		catch (IOException e) {
			return 1;
		}
	}
	
	/**
	 * generateMemberNumber generates an appropriate ID number for a new member
	 * @return returns the 9-digit string form of the generated number
	 */
	private String generateMemberNumber() {
		//Generate member number for a newly added member
		String num = Integer.toString(currMemberNumber++);
		return("000000000" + num).substring(num.length());
	}
	
	/**
	 * saveToFile saves all the members in the member list to the text file
	 */
	private void saveToFile() {
		//Save local member list to file
		try {
			writer = new BufferedWriter(new FileWriter(pathToFile));
			for (int x = 0; x < members.size(); x++) {
				writer.write(members.get(x).memberName);
				writer.write(",");
				writer.write(members.get(x).memberNumber);
				writer.write(",");
				writer.write(members.get(x).memberAddress);
				writer.write(",");
				writer.write(members.get(x).memberCity);
				writer.write(",");
				writer.write(members.get(x).memberState);
				writer.write(",");
				writer.write(members.get(x).memberZIP);
				writer.write(",");
				writer.write(members.get(x).memberStatus);
				writer.write("\n");
			}
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * loadFromFile loads all members from the text file into the member list
	 */
	private void loadFromFile() {
		//Load members from file into current member list
		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
			String line;
			while((line = reader.readLine()) != null) {
				String[] dataPoints = line.split(",");
				members.add(new Member(dataPoints[0], dataPoints[1], dataPoints[2], dataPoints[3], dataPoints[4], dataPoints[5], dataPoints[6]));
				if (currMemberNumber <= Integer.parseInt(dataPoints[1])) {
					currMemberNumber = Integer.parseInt(dataPoints[1]) + 1;
				}
			}
		}
		catch(IOException e) {
		}
	}
}