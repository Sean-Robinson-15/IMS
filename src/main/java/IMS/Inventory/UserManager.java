package IMS.Inventory;
import IMS.Users.*;
import java.util.ArrayList;

public class UserManager {
    private final UserData userData;

    public UserManager() {
         userData = new UserData();
    }

//    private final Map<String, User> users = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private String validateAddUser(String ID, String name, String address, String email) {
        if (ID.isEmpty() || name.isEmpty() || address.isEmpty() || email.isEmpty()) {
            return "Please enter all necessary fields.";
        }
        if (!userData.userExists(ID).isEmpty()) {
            return userData.userExists(ID);
        }
        return "";
    }

    private String validateUpdateUser(String ID, String name, String address, String email, String department) {
        if (address.isEmpty() && email.isEmpty() && name.isEmpty() && department.isEmpty()) {
            return ("Error: All fields are empty for [" + ID +"]");
        }
        if (userData.userExists(ID).isEmpty()) {
            return "Error: User with ID [" + ID + "] does not exist.";
        }
        return "";
    }

    public String addCustomer(String ID, String name, String address, String email) {
        String validUser = validateAddUser(ID, name, address, email);
        if (!validUser.isEmpty()) {
            return validUser;
        }
        return userData.addCustomer(ID, name, address, email);

    }

    public String addSupplier(String ID, String name, String address, String email, String department) {
        String validAdd = validateAddUser(ID, name, address, email);
        if (!validAdd.isEmpty()) {
            return validAdd;
        }
        return userData.addSupplier(ID, name, address, email, department);
    }

    public String updateUser (String ID, String name, String address, String email) {
        String validUpdate = validateUpdateUser(ID, name, address, email, "");
        if (!validUpdate.isEmpty()) {
            return validUpdate;
        }
        return userData.updateUser(ID, name, address, email, "");
    }

    public String updateUser (String ID, String name, String address, String email, String department) {
        String validUpdate = validateUpdateUser(ID, name, address, email, department);
        if (!validUpdate.isEmpty()) {
            return validUpdate;
        }
        return userData.updateUser(ID, name, address, email, department);
    }

    public ArrayList<Customer> getCustomers() {
        return userData.getCustomers();
    }
    public ArrayList<Supplier> getSuppliers() {
        return userData.getSuppliers();
    }

    public boolean userExists(String ID) {
        // as this function returns messages if correct, empty is actually an error, so false.
        // so we use ! to flip the boolean
        return !userData.userExists(ID).isEmpty();
    }

    public String removeUser(String ID) {
        return userData.removeUser(ID);
    }

    public String getUserName(String ID) {
        return userData.getUserName(ID);
    }

    public String getUserAddress(String ID) {
        return userData.getUserAddress(ID);
    }

    public String getUserEmail(String ID) {
        return userData.getUserEmail(ID);
    }
    public String getUserDepartment(String ID) {
        return userData.getUserDepartment(ID);
    }

}