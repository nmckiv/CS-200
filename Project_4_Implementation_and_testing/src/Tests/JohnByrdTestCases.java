package Tests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Software.ModifyMemberDatabase;
import Software.ModifyProviderDatabase;
import Software.RecordService;

class JohnByrdTestCases {
	private RecordService servRecordDb;
	private ModifyMemberDatabase memDb;
	private ModifyProviderDatabase provCon;
	
	@BeforeEach
	void setUp() throws Exception {
		servRecordDb = new RecordService();
		memDb = new ModifyMemberDatabase();
		provCon = new ModifyProviderDatabase();
	}
	
	@Test
	void addServiceRecordTestSuccess() {
		servRecordDb.clearDatabase();
		assertEquals( 0, servRecordDb.addServiceRecord("12-3-2023", "000000001", "000000008", "123456", 59.99, "Appt. was succesful"));
		assertEquals( 0, servRecordDb.addServiceRecord("12-3-2023", "000000002", "000000005", "123678", 70.99, "Appt. was succesful"));
		assertEquals( 0, servRecordDb.addServiceRecord("12-3-2023", "000000003", "000000012", "123333", 29.99, "Appt. was succesful"));
	}
	
	@Test
	void validMemberFailure() {
		memDb.clearDatabase();
		memDb.addMember("Jack", "420 Main St.", "Jackson", "Nebraska", "12345", "Active");
		assertEquals( false, servRecordDb.validMember("000000001"));
		memDb.clearDatabase();
	}
	
	@Test
	public void testRetrieveProviderFailure() {
		provCon.clearDatabase();
		assertNull(provCon.retrieveProvider("000000000")); // tries to retrieve on empty database
	}
	

}
