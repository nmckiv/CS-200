package Software;


/**
 * Storage method for the Services, service codes, and prices
 * 
 * 
 * @author Benjamin Haas
 */


public class ServiceCodes {
	public String service;
	public int serviceCode;
	public double price;
	/**
	 * Creates a 
	 */
	public ServiceCodes(String serviceUsed, int codeProv, double priceProv) {
		service = serviceUsed;
		serviceCode = codeProv;
		price = priceProv;
	}
}