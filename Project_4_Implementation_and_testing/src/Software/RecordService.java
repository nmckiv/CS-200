package Software;


import java.util.ArrayList;
import java.util.List;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
* RecordService contains the methods to take input from the gui and Create a new service record.
*
* @author John Byrd
*/

public class RecordService{
	
	private String pathToFile;
	
	private BufferedWriter writer;
	
	private BufferedReader reader;
	
	// array list of service records
	public List<ServiceRecordEntity> serviceRecordList;
	
	private static int currRecNum = 1;
	
	/** Main function in class,
	 *  creates list and loads data from file/db
	 * 
	 */
	public RecordService() {
		//Set absolute path to file using current working directory
		pathToFile = (System.getProperty("user.dir") + "\\serviceRecordList.txt");
		
		serviceRecordList = new ArrayList<>(); // initializing the service record list
		
		//Load Service Record List from file
		loadFromFile();
	}
	


	String memNum = ""; // userinput from gui
	
	/**get memberNum input from gui, check member database for validation
	 * 
	 * @param memNum
	 * @return
	 */
	public Boolean validMember(String memNum){
		ModifyMemberDatabase valid = new ModifyMemberDatabase();
		if (valid.retrieveMember(memNum) != null) {
			return true;
		}
		return false;
	}


	
	String servCode = ""; //userInput from gui
	//get serviceCode from gui, display service code from dictionary
	/*public void displayServiceCode(String servCode){
		
	}*/
	
	
	/**get current date and time and return String of formatted date and time
	 * 
	 * @return
	 */
	public String getDateTime() {
		// Get the current date and time of CST
		ZoneId cstZone = ZoneId.of("America/Chicago");
		ZonedDateTime cstDateTime =ZonedDateTime.now(cstZone);
		        
		// format date and time
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
		String dateRec = cstDateTime.format(formatter);
		return dateRec;
	}
	
	/*public int displayBill(){
		
		return 0;
	}*/

	/** create a new service record
	 * @param dateServ
	 * @param memNum
	 * @param provNum
	 * @param servCode
	 * @param servFee
	 * @param comments
	 * @return
	 */
	public int addServiceRecord(String dateServ, String memNum, String provNum, String servCode, Double servFee, String comments) {
		try {
		serviceRecordList.add(new ServiceRecordEntity(currRecNum, getDateTime(), dateServ, memNum, provNum, servCode, servFee, comments));
		currRecNum++;
		saveToFile();
		//return success
		return 0;
		}
		
		catch(Exception e) {
			//return fail
			return 1;
		}
		
	}
	
	/*public void printAllServiceRecords() {
		if (serviceRecordList.size() == 0) {
			System.out.println("Service Record database is empty");
		}
		else {
			for (int x = 0; x < serviceRecordList.size(); x++) {
				System.out.println("=======Service Record " +serviceRecordList.get(x).servRecordNum + "=======");
				System.out.println("Date of Record:      " + serviceRecordList.get(x).dateOfRecord);
				System.out.println("Date of Service:     " + serviceRecordList.get(x).dateOfService);
				System.out.println("Member:              " + serviceRecordList.get(x).memberNumber);
				System.out.println("Provider:            " + serviceRecordList.get(x).providerNumber);
				System.out.println("Service:             " + serviceRecordList.get(x).serviceCode);
				System.out.println("Fee:                 " + serviceRecordList.get(x).serviceFee);
				System.out.println("Additional comments: " + serviceRecordList.get(x).inComments);
			}
			System.out.println("================================") ;
		}
	}*/
	
	
	/**Get service record in index for pearson's report
	 * 
	 * @param i
	 * @return
	 */
	public ServiceRecordEntity getServiceRecord(int i){
		return serviceRecordList.get(i);
	}
	
	/**Save local Service Record list to file
	 * 
	 */
	private void saveToFile() {
		
		try {
			writer = new BufferedWriter(new FileWriter(pathToFile));
			for (int x = 0; x < serviceRecordList.size(); x++) {
				serviceRecordList.get(x);
				writer.write(Integer.toString(serviceRecordList.get(x).servRecordNum));
				writer.write(",");
				writer.write(serviceRecordList.get(x).dateOfRecord);
				writer.write(",");
				writer.write(serviceRecordList.get(x).dateOfService);
				writer.write(",");
				writer.write(serviceRecordList.get(x).memberNumber);
				writer.write(",");
				writer.write(serviceRecordList.get(x).providerNumber);
				writer.write(",");
				writer.write(serviceRecordList.get(x).serviceCode);
				writer.write(",");
				writer.write(Double.toString(serviceRecordList.get(x).serviceFee));
				writer.write(",");
				writer.write(serviceRecordList.get(x).inComments);
				writer.write("\n");
			}
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Load files from pre-existing text file
	 * 
	 */
	private void loadFromFile() {
		//Load Service records from file into current Service record list
		try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
			String line;
			while((line = reader.readLine()) != null) {
				String[] dataPoints = line.split(",");
				double doubleValue = Double.parseDouble(dataPoints[6]);
				int intVal = Integer.parseInt(dataPoints[0]);
				serviceRecordList.add(new ServiceRecordEntity(intVal, dataPoints[1], dataPoints[2], dataPoints[3], dataPoints[4], dataPoints[5], doubleValue, dataPoints[6]));
				if (currRecNum <= intVal) {
					currRecNum = intVal + 1;
				}
			}
		}
		catch(IOException e) {
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**Clear file/db if it exists
	 * so that it can be updated
	 * 
	 */
	public int clearDatabase() {
		
		try {
			Files.newOutputStream(Path.of(pathToFile), StandardOpenOption.TRUNCATE_EXISTING);
			serviceRecordList.clear();
			currRecNum = 1;
			return 0;
		}
		catch (IOException e) {
			return 1;
		}
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}



}
