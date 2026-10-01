package cinema.service;

import cinema.staff.Manager;
import cinema.staff.Staff;
import cinema.staff.TicketSeller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    void sellerLoginShouldReturnTicketSeller() {

        LoginService loginService = new LoginService();

        Staff staff = loginService.login("s1", "s1");

        assertNotNull(staff);
        assertTrue(staff instanceof TicketSeller);
        assertEquals("Ticket Seller", staff.getRole());
    }

    @Test
    void secondSellerLoginShouldReturnTicketSeller() {

        LoginService loginService = new LoginService();

        Staff staff = loginService.login("s2", "s2");

        assertNotNull(staff);
        assertTrue(staff instanceof TicketSeller);
        assertEquals("Ticket Seller", staff.getRole());
    }

    @Test
    void thirdSellerLoginShouldReturnTicketSeller() {

        LoginService loginService = new LoginService();

        Staff staff = loginService.login("s3", "s3");

        assertNotNull(staff);
        assertTrue(staff instanceof TicketSeller);
        assertEquals("Ticket Seller", staff.getRole());
    }

    @Test
    void managerLoginShouldReturnManager() {

        LoginService loginService = new LoginService();

        Staff staff = loginService.login("m1", "m1");

        assertNotNull(staff);
        assertTrue(staff instanceof Manager);
        assertEquals("Manager", staff.getRole());
    }

    @Test
    void secondManagerLoginShouldReturnManager() {

        LoginService loginService = new LoginService();

        Staff staff = loginService.login("m2", "m2");

        assertNotNull(staff);
        assertTrue(staff instanceof Manager);
        assertEquals("Manager", staff.getRole());
    }

    @Test
    void wrongPasswordShouldFailLogin() {

        LoginService loginService = new LoginService();

        Staff staff = loginService.login("s1", "wrong");

        assertNull(staff);
    }

    @Test
    void unknownUsernameShouldFailLogin() {

        LoginService loginService = new LoginService();

        Staff staff = loginService.login("unknown", "unknown");

        assertNull(staff);
    }
}
