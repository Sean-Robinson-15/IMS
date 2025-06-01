package IMS.Inventory;

import IMS.Users.User;
import IMS.Users.Customer;
import IMS.Users.Supplier;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class UserData {
    private final Map<String, User> users = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public String addCustomer(String ID, String name, String address, String email) {
        users.put(ID, new Customer(ID, name, address, email));
        return "Customer " + name + " added with ID " + ID;
    }

    public String addSupplier(String ID, String name, String address, String email, String department) {
        users.put(ID, new Supplier(ID, name, address, email, department));
        return "Supplier " + name + " added with ID " + ID;
    }

    public String userExists(String ID) {
        if (users.containsKey(ID)) {
            if (ID.charAt(0) == 'C' || ID.charAt(0) == 'c') {
                return "Customer with ID " + ID + " exists.";
            } else if (ID.charAt(0) == 'S' || ID.charAt(0) == 's') {
                return "Supplier with ID " + ID + " exists.";
            } else {
                return "User with ID " + ID + " does not exist.";
            }
        } return "";
    }

    public String updateUser(String ID, String name, String address, String email, String department) {
        User user = users.get(ID);
        if (user != null) {

            if (!address.isEmpty()) {
                user.setAddress(address);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            if (!name.isEmpty()) {
                user.setName(name);
            }
            if (!department.isEmpty() && user instanceof Supplier supplier) {
                supplier.setDepartment(department);
                return ("User Updated: " + ID);
            }
            return ("User Updated: " + ID);
        }
        return ("Error: User ID [" + ID +"] doesnt exist.");
    }


    public String removeUser(String ID) {
        if (users.remove(ID) != null) {
            return "Item Removed : " + ID;
        } else {
            return "Error! User Code [" + ID +"] doesnt exist.";
        }
    }

    public ArrayList<Customer> getCustomers() {
        ArrayList<User> usersList = new ArrayList<>(users.values());
        ArrayList<Customer> customersList = new ArrayList<>();
        for (User user : usersList) {
            if (user instanceof Customer customer) {
                customersList.add(new Customer(
                        customer.getID(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getEmail()
                ));
            }
        }
        return customersList;
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
