  package Tests;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import Software.ModifyProviderDatabase;
import Software.ProviderEntity;
import Software.ProviderController;

public class GirwanDhakalTestCases {
	ModifyProviderDatabase d = new ModifyProviderDatabase();
	
	ProviderController providerController = new ProviderController(); 

	@Before
	public void setup() {
		//ModifyProviderDatabase d = new ModifyProviderDatabase();
		d.clearDatabase();
	}
	
	@Test//for success
	public void testRemoveProvider() {
		d.addProvider("Girwan", "230 Main St", "Tuscaloosa", "AL", "34921");
		d.removeProvider("000000000");
		ProviderEntity output = d.retrieveProvider("000000000");
		assertNull(output);
	}

	@Test // for faliure
	public void testEditProviderStringStringString() {
		d.addProvider("Girwan", "230 Main St", "Tuscaloosa", "AL", "34921");
		assertEquals(1,d.editProvider("0000000000","Name","Winter"));//10 digit member number
	}

	@Test // for faliure
	public void testEditProviderStringStringStringStringStringString() {
		assertEquals(1,d.editProvider("0000", "Girwan", "32 St", "Jamestown", "KY", "23241"));//try to edit a member on an empty list
	}
	
	@Test
    public void test2() {
        String service = "Physical";
        int serviceCode = 100014;
        String returnedService = providerController.getService(serviceCode);

        assertEquals(service, returnedService);

    }

}
