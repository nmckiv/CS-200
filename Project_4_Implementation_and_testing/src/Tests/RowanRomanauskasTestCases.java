package Tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Software.Controller;
import Software.LogIn;
import Software.RecordService;

public class RowanRomanauskasTestCases {

	private LogIn logIn;
	private Controller controller;
	private RecordService servRecordDb;
	@Before
	public void setUp() throws Exception {
		logIn = new LogIn();
		controller = new Controller();
	}
	
	@Test
	void addServiceRecordTestSuccess() {
		servRecordDb.clearDatabase();
		assertEquals( 0, servRecordDb.addServiceRecord("12-3-2023", "000000001", "000000008", "123456", 59.99, "Appt. was succesful"));
		assertEquals( 0, servRecordDb.addServiceRecord("12-3-2023", "000000002", "000000005", "123678", 70.99, "Appt. was succesful"));
		assertEquals( 0, servRecordDb.addServiceRecord("12-3-2023", "000000003", "000000012", "123333", 29.99, "Appt. was succesful"));
	}

	@Test
	public void verifyProviderAddSuccess() {
		assertTrue(logIn.addProvider("skeleton", "password"));	
	}
	
	public void verifyUserTestFailuer() {
		assertFalse(logIn.VerifyUser("Not a username", "Not a password", "Manager"));
	}

}
