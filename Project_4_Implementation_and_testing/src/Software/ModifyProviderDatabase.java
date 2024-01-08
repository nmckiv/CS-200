package Software;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileNotFoundException;


/**
* ModifyProviderDatabase contains the methods to add, remove and update the provider database.
*
* @author Girwan Dhakal
*/
public class ModifyProviderDatabase
{
	private int count; // count of provider entities
	
	private String filePath;  // current directory
	
	public ArrayList<ProviderEntity> providerDatabase; // list of provider entities
	
	private LogIn logIn; //access login methods 
	
	public ModifyProviderDatabase() 
	{
		count = 0; // count initialised
		providerDatabase = new ArrayList<ProviderEntity>();
		filePath = System.getProperty("user.dir") + "/providerDatabase.txt"; //file path for the database file
		File file = new File(filePath);
		if(!file.exists()) // if file does not exist
		{
			try {
				if(file.createNewFile())//creates a new file
				{
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else//if it does exist
		{
			loadFromFile(); // then it loads from the file into the provider list
		}
	}
	
	/**
	 * Loads the data from database file into the provider list.
	 * @return returns 1 if load failed and 0 if load successful
	 */
	private void loadFromFile()
	{
		try {
			count = 0;
		providerDatabase.clear();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		String[] data;
			while((line = reader.readLine()) != null)
			{
				data = line.split(",");
				if(data.length >= 6)
				{
				ProviderEntity p = new ProviderEntity(data[0], data[1], data[2], data[3], data[4], data[5]);
				count++;
				providerDatabase.add(p);
//				if (count <= Integer.parseInt(data[5])) {
//					count = Integer.parseInt(data[5]) + 1;
//				}
				}
				else
				{
					return;
				}
			
			}
			
			reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } 
	}
	/**
	 * Updates the file with the providerDatabase list data.
	 * @return returns 0 if update successful
	 */
	
	private void updateFile()
	{
		//String currentDirectory = System.getProperty("user.dir");
		try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		Iterator<ProviderEntity> it = providerDatabase.iterator();
		for(int i = 0; i < providerDatabase.size(); i++)
		{
			writer.write(providerDatabase.get(i).name);
			writer.write(",");
			writer.write(providerDatabase.get(i).address);
			writer.write(",");
			writer.write(providerDatabase.get(i).city);
			writer.write(",");
			writer.write(providerDatabase.get(i).state);
			writer.write(",");
			writer.write(providerDatabase.get(i).zip);
			writer.write(",");
			writer.write(providerDatabase.get(i).providerNum);
			writer.write("\n");
		}
		count = providerDatabase.size();
		writer.close();
		}catch(IOException e) {
	        e.printStackTrace();
	    } 
	}
	
	/**
	 * Searches and returns a provider entity
	 * returns null if could not find the provider
	 * @param num Number of member
	 */
	public ProviderEntity retrieveProvider(String num) {
		for (int x = 0; x < providerDatabase.size(); x++) {
			if (num.equals(providerDatabase.get(x).providerNum)) {
				return providerDatabase.get(x);
			}
		}
		//System.out.println("Error - member not found");
		return null;
	}
	
	/**
	 * 
	 * @param name Name of provider
	 * @param address Address of provider
	 * @param city City of provider
	 * @param state State of provider
	 * @param zip ZIP code of provider
	 */
	public void addProvider(String name, String address, String city, String state, String zip)
	{
//		try {
		String providerNum = createProviderNum(count); 
		ProviderEntity p = new ProviderEntity(name, address, city, state, zip, providerNum);
		providerDatabase.add(p);
		logIn = new LogIn();
		logIn.addProvider(name, providerNum);
		count++;
		updateFile();
//		}catch(IllegalArgumentException e){
//		}
	}
	/**
	 * This removes a provider from the record.
	 * @param providerNum unique 9-digit Provider Number
	 */
	public void removeProvider(String providerNum)
	{
		//try {
		if(providerNum.length() != 9)
		{
			throw new IllegalArgumentException("Provider Num should be 9 digits");
		}
		int i = 0;
		while(i < providerDatabase.size())
		{
			if(providerDatabase.get(i).providerNum.equals(providerNum))
			{
				providerDatabase.remove(i);
			}
			i++;
		}
		updateFile();
		//}catch(IllegalArgumentException e){
		//}
	}
	/**
	 * This clears the database file.
	 */
	
	public void clearDatabase() {
		
		//Clear file if it exists
		try {
			Path path = Paths.get(filePath);
			Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING);
			providerDatabase.clear();
			//count = 0;
			updateFile();
		}
		catch (IOException e) {
		}
	}
	
	/**
	 * editProvider() updates the database file with the provider list.
	 * @param providerNum unique 9-digit Provider Number
	 * @param atttributeToChange the provider attribute you want to change
	 * @param cmd the new attribute you want to add
	 * @return 1 if update failed and 0 if update successful
	 */
	// this one does not use the terminal to get input
	public int editProvider(String providerNum, String attributeToChange, String cmd)
	{
		try {
		int index = getIndex(providerNum, providerDatabase);
		if(index == -1)
		{
			return 1;	
		}
		else
		{
		switch(attributeToChange)
		{
		case("Name"):
			providerDatabase.get(index).name = cmd;
			break;
		case("Address"):
			providerDatabase.get(index).address = cmd;
			break;
		case("City"):
			providerDatabase.get(index).city = cmd;
			break;
		case("State"):
			providerDatabase.get(index).state = cmd;
			break;
		case("ZIP"):
			providerDatabase.get(index).zip = cmd;
			break;
		default:
			return 1;
		}
		}
		updateFile();
		}catch(IllegalArgumentException e){
			return 1;
		}
		return 0;
	}
	/**
	 * 
	 * This overloaded method can be used to change all the provider details at once. 
	 * 
	 * @param num provider number
	 * @param name new provider name
	 * @param address new provider address
	 * @param city new city name
	 * @param zip new zip code
	 * @return returns 1 if update failed and 0 if update successful
	 */
	public int editProvider(String num, String name, String address, String city, String state, String zip)
	{
		try
		{
		int index = getIndex(num, providerDatabase);
		if(index == -1)
		{
			return 1;
		}
		providerDatabase.get(index).name = name;
		providerDatabase.get(index).address = address;
		providerDatabase.get(index).city = city;
		providerDatabase.get(index).state = state;
		providerDatabase.get(index).zip = zip;
		return 0;
		}catch(IllegalArgumentException e){
			return 1;
		}
		
	}
	/**
	 * This returns the index of the providerNum specified from the provider list.
	 * @param providerNum unique 9-digit Provider Number 
	 * @param list provider database ArrayList
	 * @return integer -1 if couldnt find the provider in the list.
	 */
	private int getIndex(String providerNum, ArrayList<ProviderEntity> list)
	{
		int i = 0;
		int index = -1;
		while(i < list.size())
		{
			if(list.get(i).providerNum.equals(providerNum))
			{
				index = i;
			}
			i++;
		}
		return index;
	}
	/**
	 * This creates a provider num based on the current number of providers.
	 * @param count counter of number of providers put into list
	 * @return returns the provider number as a String
	 */
	private String createProviderNum(int count)
	{
		String providerNum = String.format("%09d", count);
		return providerNum;
	}
}
