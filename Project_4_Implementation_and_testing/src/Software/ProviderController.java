package Software;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
/**
* ProviderController manages and has access to the services codes and is able to make a report of the codes to give to a provider
*
* @author Benjamin Haas
*/


public class ProviderController {

    public ArrayList<ServiceCodes> servicecodes;

    
    /** Adding ServiceCode objects to the ArrayList in the constructor
     * 
     */
    public ProviderController() {
        this.servicecodes = new ArrayList<ServiceCodes>();

        servicecodes.add(new ServiceCodes("Aerobics Class (30 Minutes)", 100000, 25.00));
    	servicecodes.add(new ServiceCodes("Aerobics Class (60 Minutes)", 100001, 50.00));
    	servicecodes.add(new ServiceCodes("Bike Rental", 100002, 20.00));    
    	servicecodes.add(new ServiceCodes("Boxing Lesson", 100003, 50.00));
    	servicecodes.add(new ServiceCodes("Cross Fit Session", 100004, 50.00));
    	servicecodes.add(new ServiceCodes("Dance Lesson", 100005, 50.00));
    	servicecodes.add(new ServiceCodes("Dentist Appointment", 100006, 100.00));
    	servicecodes.add(new ServiceCodes("Diet Consultation", 100007, 50.00));
    	servicecodes.add(new ServiceCodes("Doctor Appointment", 100008, 175.00));
    	servicecodes.add(new ServiceCodes("Gym Session", 100009, 50.00));
    	servicecodes.add(new ServiceCodes("Jazzercise Class (30 Minutes)", 100010, 25.00));
    	servicecodes.add(new ServiceCodes("Jazzercise Class (60 Minutes)", 100011, 50.00));
    	servicecodes.add(new ServiceCodes("Mixed Martial Arts Lesson", 100012, 50.00));
    	servicecodes.add(new ServiceCodes("Nutritionist appointment", 100013, 100.00));
    	servicecodes.add(new ServiceCodes("Physical", 100014, 150.00));
    	servicecodes.add(new ServiceCodes("Pilates Class (30 Minutes)", 100015, 25.00));
    	servicecodes.add(new ServiceCodes("Pilates Class (60 Minutes", 100016, 50.00));
    	servicecodes.add(new ServiceCodes("Yoga Class (30 Minutes)", 100017, 25.00));
    	servicecodes.add(new ServiceCodes("Yoga Class (60 Minutes)", 100018, 50.00));
    	servicecodes.add(new ServiceCodes("Zumba Class (30 Minutes)", 100019, 25.00));
    	servicecodes.add(new ServiceCodes("Zumba Class (60 Minutes)", 100020, 50.00));
        
    }
    /**Prints out the Provider Directory to ProviderDirectory.txt
     * 
     */
    public void printProviderDirectory() {
    	 
            
            //create file
            File generate = new File("report.txt");
            try {
            generate.createNewFile();
            }
            catch (IOException e) {System.out.println("an error occured");}
               
            try {
            //create filewriter and return it
            FileWriter report = new FileWriter(generate);
    
            
        for (int i = 0; i < servicecodes.size(); i++) {
        	
        	report.write("================================\n");
        	report.write("Service: " + servicecodes.get(i).service + "\n");
        	report.write("Service Code: " + servicecodes.get(i).serviceCode + "\n");
        	report.write("Price: " + servicecodes.get(i).price + "\n");
        	report.write("================================\n");
        }
            
        report.close();
            } 
            
            catch (IOException e) {System.out.println("an error occured");}
        
    }
    
    /**
     * Use a given service code to look up and returns the price of the service
     * @param checkCode
     * @return double price
     */
    public double getPrice(int checkCode) {
        for (int i = 0; i < servicecodes.size(); i++) {
            if (checkCode == servicecodes.get(i).serviceCode)
                return servicecodes.get(i).price;
        }
        System.out.println("Error - service code not found");
        return -1; 
    }
    /**
     * Use a given service code to look up the name of a service and returns it
     * @param checkCode
     * @return String service
     */
    public String getService(int checkCode) {
        for (int i = 0; i < servicecodes.size(); i++) {
            if (checkCode == servicecodes.get(i).serviceCode)
                return servicecodes.get(i).service;
        }
        System.out.println("Error - service code not found");
        return null; 
    }
}

