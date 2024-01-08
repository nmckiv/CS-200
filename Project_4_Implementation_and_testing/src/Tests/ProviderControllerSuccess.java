package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import Software.ProviderController;

/**
 * @author of these Junit Test is Benjamin Haas * 
 */
public class ProviderControllerSuccess {
	ProviderController providerController;
	
	@Before
	public void setUp() throws Exception {
		providerController = new ProviderController();
	}
	
	@Test
	public void testProviderControllerSuccess() {
		int serviceCode = 100014;
		double expectedPrice = 150.00;
		double returnedPrice = providerController.getPrice(serviceCode);
		
		assertEquals(expectedPrice, returnedPrice, .001);
			
	}
	
	@Test
	public void test2() {
		String service = "Physical";
		int serviceCode = 100014;
		String returnedService = providerController.getService(serviceCode);
		
		assertEquals(service, returnedService);
		
	}
	
	@Test
	public void testProviderControllerFailure() {
		
	}
}
