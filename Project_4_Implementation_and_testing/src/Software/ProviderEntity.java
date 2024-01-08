package Software;

//import project4.String;

/**
 * Provider Entity class is an entity class that contains the attributes of a Provider
 * 
 * @author Girwan Dhakal
 */

public class ProviderEntity
{
	public String name, address, city, state, zip, providerNum;
	ProviderEntity(String name, String address, String city ,String state, String zip, String providerNum)//constructor
	{
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.providerNum = providerNum;
	}
}