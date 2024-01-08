package Software;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Logs the user into their selected menu through the gui
 * 
 * @author Rowan Romanauskas
 */
public class LogIn {
	/**
	 * Verifies that the user has input a valid username and password
	 * @param username
	 * @param password
	 * @param userType
	 * @return
	 */
	public boolean VerifyUser(String username, String password, String userType) {
		boolean verified = false;
		BufferedReader reader;
		List<String> usernames = new ArrayList<String>();
		List<String> passwords = new ArrayList<String>();
		
		try {
			switch(userType) {
			case "Manager":
				reader  = new BufferedReader(new FileReader("src/Users/Managers.txt"));
				break;
			case "Provider":
				reader = new BufferedReader(new FileReader("src/Users/Providers.txt"));
				break;
			default:
				reader = new BufferedReader(new FileReader("src/Users/Operators.txt"));
			}
			
			String line = reader.readLine();
			while (line != null) {
				usernames.add(line);
				line = reader.readLine();
				passwords.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		int index = -1;
		if(usernames.contains(username)) {
			index = usernames.indexOf(username);
			if(passwords.get(index).equals(password)) {
				verified = true;
			} else {
				verified = false;
			}
		}
		
		
		return verified;
	}
	
	/**
	 * Adds the providers username and password to the file so they can log in
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean addProvider(String username, String password) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/Users/Providers.txt", true));
			writer.newLine();
			writer.write(username);
			writer.newLine();
			writer.write(password);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
