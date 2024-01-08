package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import Software.ProviderController;
import Software.RecordService;

/**
 * @author of these Junit Test is Benjamin Haas * 
 */
public class BenjaminHaasTestCases {
	ProviderController providerController;
	RecordService serviceRecordDatabase;
	
	@Before
	public void setUp() throws Exception {
		providerController = new ProviderController();
		serviceRecordDatabase = new RecordService();
	}
	
	@Test
	public void testProviderControllerSuccess() {
		int serviceCode = 100014;
		double expectedPrice = 150.00;
		double returnedPrice = providerController.getPrice(serviceCode);
		
		assertEquals(expectedPrice, returnedPrice, .001);
			
	}
	
	
	@Test
	public void testProviderControllerFailure() {
		int serviceCode = 200000;
		String returnedService = providerController.getService(serviceCode);
		
		assertEquals(returnedService, null );
	}
	
	@Test
	public void getServiceRecordSuccess() {
		serviceRecordDatabase.clearDatabase();
		serviceRecordDatabase.addServiceRecord("12-3-2023", "000000001", "000000010", "100014", 150.00, "Appt. was succesful");
		serviceRecordDatabase.addServiceRecord("12-3-2023", "000000002", "000000011", "100010", 25.00, "Appt. was succesful");
		serviceRecordDatabase.addServiceRecord("12-3-2023", "000000003", "000000012", "100007", 50.00, "Appt. was succesful");
		serviceRecordDatabase.addServiceRecord("12-3-2023", "000000004", "000000013", "100015", 25.00, "Appt. was succesful");
		assertNotNull(serviceRecordDatabase.getServiceRecord(0));
		assertNotNull(serviceRecordDatabase.getServiceRecord(1));
		assertNotNull(serviceRecordDatabase.getServiceRecord(2));
		assertNotNull(serviceRecordDatabase.getServiceRecord(3));
		serviceRecordDatabase.clearDatabase();
	}
}
