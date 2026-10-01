package cinema.service;

import cinema.staff.Manager;
import cinema.staff.Staff;
import cinema.staff.TicketSeller;

import java.util.ArrayList;
import java.util.List;

public class LoginService {

    private final List<Staff> staffList;

    public LoginService() {
        staffList = new ArrayList<>();

        staffList.add(new TicketSeller("s1", "s1"));
        staffList.add(new TicketSeller("s2", "s2"));
        staffList.add(new TicketSeller("s3", "s3"));

        staffList.add(new Manager("m1", "m1"));
        staffList.add(new Manager("m2", "m2"));
    }

    public Staff login(String username, String password) {

        for (Staff staff : staffList) {

            if (staff.getUsername().equals(username)
                && staff.getPassword().equals(password)) {

                return staff;
            }
        }

        return null;
    }
}
