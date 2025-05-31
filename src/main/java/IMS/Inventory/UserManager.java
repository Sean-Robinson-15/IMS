package IMS.Inventory;

import IMS.Users.User;
import IMS.Users.Customer;
import IMS.Users.Supplier;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class UserManager {
    private final Map<String, User> users = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public String addCustomer(String ID, String name, String address, String email) {

        if (users.containsKey(ID)) {
            return "Customer with ID " + ID + " already exists.";
        }
        if (name.isEmpty() || address.isEmpty() || email.isEmpty()) {
            return "Please enter all necessary fields.";
        }
        users.put(ID, new Customer(ID, name, address, email));
        return "Customer " + name + " added with ID " + ID;
    }

    public String addSupplier(String ID, String name, String address, String email, String department) {
        if (users.containsKey(ID)) {
            return "Supplier with ID " + ID + " already exists.";
        }
        if (ID.isEmpty() || name.isEmpty() || address.isEmpty() || email.isEmpty()) {
            return "Please enter all necessary fields.";
        }
        users.put(ID, new Supplier(ID, name, address, email, department));
        return "Supplier " + name + " added with ID " + ID;
    }

    public String updateUser(String ID, String name, String address, String email) {
        User user = users.get(ID);
        if (user != null) {
            if (address.isEmpty() && email.isEmpty() && name.isEmpty()) {
                return ("Error: All fields are empty for [" + ID +"]");
            }
            if (!address.isEmpty()) {
                user.setAddress(address);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            if (!name.isEmpty()) {
                user.setName(name);
            }
            return ("User Updated: " + ID);
        }
        return ("Error: User ID [" + ID +"] doesnt exist.");
    }

    public String updateUser(String ID, String name, String address, String email, String department) {
        String output = updateUser(ID, name, address, email);
        if (output.contains("Error:") && department.isEmpty()) return output;
        Supplier user = (Supplier)users.get(ID);
        if ( user != null && !department.isEmpty()) {
            user.setDepartment(department);
            return ("User Updated: " + ID);
        }
        return ("Error: Supplier ID [" + ID +"] doesnt exist.");
    }

    public String removeUser(String ID) {
        if (users.remove(ID) != null) {
            return "Item Removed : " + ID;
        } else {
            return "Error! User Code [" + ID +"] doesnt exist.";
        }
    }

    public boolean userExists(String ID) {
        return users.containsKey(ID);
    }

    public ArrayList<Customer> getCustomers() {
        ArrayList<User> usersList = new ArrayList<>(users.values());
        ArrayList<Customer> CustomersList = new ArrayList<>();
        for (User i : usersList) {
            if (i instanceof Customer) {
                CustomersList.add((Customer) i);
            }
        }
        return CustomersList;
    }

    public ArrayList<Supplier> getSuppliers() {
        ArrayList<User> usersList = new ArrayList<>(users.values());
        ArrayList<Supplier> suppliersList = new ArrayList<>();
        for (User user : usersList) {
            if (user instanceof Supplier supplier) {
                suppliersList.add(new Supplier(
                    supplier.getID(),
                    supplier.getName(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getDepartment()
                ));
            }
        }
        return suppliersList;
    }

    public ArrayList<String> getAllCustomerIDs() {
        ArrayList<String> IDs = new ArrayList<>();
        for (User p : users.values()) {
            if (p instanceof Customer) {
                IDs.add(p.getID());
            }
        }
        return IDs;
    }

    public String getUserName(String ID) {
        return users.get(ID).getName();
    }
    public String getUserAddress(String ID) {
        return users.get(ID).getAddress();
    }
    public String getUserEmail(String ID) {
        return users.get(ID).getEmail();
    }
    public String getUserDepartment(String ID) {
        if (users.get(ID) instanceof Supplier supplier) {
            return supplier.getDepartment();
        }
        return "";
    }
}