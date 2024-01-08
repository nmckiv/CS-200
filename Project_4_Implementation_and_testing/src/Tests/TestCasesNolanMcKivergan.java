package Tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Software.LogIn;
import Software.ModifyMemberDatabase;

class TestCasesNolanMcKivergan {
	private ModifyMemberDatabase memberDatabase;
	private LogIn logIn;
		@BeforeEach
		void setUp() throws Exception {
			memberDatabase = new ModifyMemberDatabase();
			logIn = new LogIn();
		}
		
		@Test
		void testRemoveMemberSuccess() {
			memberDatabase.clearDatabase();
			memberDatabase.addMember("Jack", "420 Main St.", "Jackson", "Nebraska", "12345", "Active");
			assertEquals(0, memberDatabase.removeMember("000000000"));
		}
		
		@Test
		void testEditMemberFailure() {
			memberDatabase.clearDatabase();
			memberDatabase.addMember("Jack", "420 Main St.", "Jackson", "Nebraska", "12345", "Active");
			assertEquals(1, memberDatabase.editMember("000000001", "Name", "Jim"));
		}
		
		@Test
		public void verifyUserTestSuccess() {
			assertTrue(logIn.VerifyUser("Rowan", "password", "Manager"));	
		}
}
