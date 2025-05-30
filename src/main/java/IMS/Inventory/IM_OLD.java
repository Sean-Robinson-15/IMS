//package IMS.Inventory;
//import IMS.Orders.Purchase;
//import IMS.Orders.Sale;
//import IMS.Orders.Transaction;
//import IMS.Users.Customer;
//import IMS.Users.Supplier;
//import IMS.Users.User;
//import IMS.Products.*;
//
//import java.util.*;
//
//public class IM_OLD {
//    public static final int DEFAULT_LOW_STOCK_THRESHOLD = ;
//    private final Map<String, Product> inventory = new TreeMap<>();
//    private final Map<String, Product> inTransit = new TreeMap<>();
//    private final Map<String, Product> basket = new TreeMap<>();
//    private final Map<String, Transaction> transactions = new TreeMap<>();
//    private final Map<String, User> users = new TreeMap<>();
//    public int LOWSTOCKTHRESHOLD = 5;
//
//
//    public String addInventoryItem(String ID, String name, String quantityStr, String priceStr) {
//        ID=ID.toUpperCase();
//        TreeMap<String, String> inputList = new TreeMap<>(Map.of("ID",ID, "name",name, "quantityStr",quantityStr, "priceStr",priceStr));
//        if (!confirmInputs(inputList).isEmpty()) {
//            return confirmInputs(inputList);
//        }
//
//        ArrayList<String> IDs = getAllIDs();
//
//        int quantity = Integer.parseInt(quantityStr);
//        double price = Double.parseDouble(priceStr);
//        if (!IDs.contains(ID)) {
//            inventory.put(ID, new Product(ID, name, quantity, price));
//            return("Item added: " + ID + " " + name);
//        }
//        return "ID "+ID+" arleady exists";
//    }
//
//
//    public String addToBasket( String ID, String name, String quantityStr, double price) {
//        ID=ID.toUpperCase();
//        if ((ID.isEmpty())) {
//            return "No Product Entered. Please try again.";
//        }
//        if (quantityStr.isEmpty()) {
//            return "Invalid Quantity Entered. Please try again.";
//        }
//        if (name.isEmpty()) {
//            return "Invalid Name Entered. Please try again.";
//        }
//        int quantity = Integer.parseInt(quantityStr);
//        if (quantity > getQuantity(ID)) {
//            return "quantity greater than available. Please try again. \n";
//        }
//
//        Product prod = inventory.get(ID);
//        //change to use map/key combo on inventory
//        if (prod.getID().equals(ID)) {
//            if (basket.containsKey(ID)) {
//                int currentBasketQuantity = getBasketQuantity(ID);
//                basket.put(ID, new Product(ID, name, currentBasketQuantity + quantity, price));
//            } else {
//                basket.put(ID, new Product(ID, name, quantity, price));
//            }
//            updateItem(ID, prod.getQuantity() - quantity);
//        }
//        return ID + " added to basket. \n";
//    }
//
//    public String confirmInputs(TreeMap<String, String> inputs) {
//        ArrayList<String> emptyInputs = new ArrayList<>();
//        for (String key : inputs.keySet()) {
//            if (inputs.get(key).isEmpty()) {
//                emptyInputs.add(key);
//            }
//        }
//        if (!emptyInputs.isEmpty()) {
//            return "Please fill in all fields. Empty fields: " + emptyInputs;
//        }
//
//        String output = validateInt(inputs.get("quantityStr"), "Quantity");
//        if (!output.isEmpty()) return output;
//
//        output = validateDouble(inputs.get("priceStr"), "Price");
//        if (!output.isEmpty()) return output;
//
//        return "";
//    }
//
//    public String validateInt(String input, String variableName) {
//        try {
//            int quantity = Integer.parseInt(input);
//            if (quantity < 0) {
//                return variableName + " cannot be negative. Please try again.";
//            }
//        } catch (NumberFormatException e) {
//            return variableName + " must be an integer. Please try again.";
//        }
//        return "";
//    }
//
//    public String validateDouble(String input, String variableName) {
//        try {
//            double quantity = Double.parseDouble(input);
//            if (quantity < 0) {
//                return variableName + " cannot be negative. Please try again.";
//            }
//        } catch (NumberFormatException e) {
//            return variableName + " must be an integer. Please try again.";
//        }
//        return "";
//    }
//
//    public void testItems() {
//        randomize(inventory, 10);
//        ArrayList<Product> inventoryArray = getAllItems();
//        Product prod = inventoryArray.get((int)(Math.random()*inventoryArray.size()));
//        int quantity = (int)(Math.random()*prod.getQuantity());
//        addToBasket(prod.getID(), prod.getName(), String.valueOf(quantity), prod.getPrice());
//        users.put("C001", new Customer("C001", "Customer 1", "101 Made Up Lane" ,"Customer1@gmail.com" ));
//        users.put("S001", new Supplier("S001", "Supplier 1", "202 Not Real Road" , "Supplier1@gmail.com",  "Sales" ));
//        checkoutBasket("S001");
//        transactions.put("T002", new Purchase("T001", "C001", getRandomProducts(inventoryArray, (int)(Math.random()*10) + 1)));
//        inTransit.put("T002", new Product("BNU001", "Prod1", 69, 10));
//    }
//
//    public ArrayList<Product> getRandomProducts(ArrayList<Product> products, int num) {
//        ArrayList<Product> randomProducts = new ArrayList<>();
//        for (int i = 0; i < num; i++) {
//            int randomIndex = (int)(Math.random()*products.size());
//            Product randomProduct = products.get(randomIndex);
//            randomProduct.setQuantity((int)(Math.random()*randomProduct.getQuantity()));
//            randomProducts.add(products.get(randomIndex));
//        }
//        return randomProducts;
//    }
//    public void randomize(Map<String, Product> map, Integer num) {
//        String[] randomStr = {"rD9av","R11Xt","E6qa2","BOnbh","2oFxY","Xt31Q","eMPF2","bJJKZ","R870x","m3cwL","ZXlpU","m4hen","bhj7N","JNP3J","MtZ5P","EQOWO","uo9Vb","VeUIF","JTakW","SKRrl","s6NFt","FUvy7","Ponqd","poid1","Y1V","5","n7MgC","O2OfQ","DSgzo","wmoUb","Y64Fb"};
//        for (int i = 0; i < num; i++)
//        {
//            String ID = String.format("BNU%03d", (int)(Math.random()*1000));
//            String name = randomStr[(int)(Math.random()*randomStr.length)];
//            int quantity = (int)(Math.random()*50) +1;
//            double price = (double) Math.round(Math.random()*1000)/100;
//            map.put(ID, new Product(ID, name, quantity, price));
//        }
//    }
//
//
//    public void updateItem(String ID, int quantity) {
//        updateItem(ID, "", Integer.toString(quantity), "");
//    }
//
//
//    public String updateItem(String ID, String name, String quantityStr, String priceStr) {
//        ID=ID.toUpperCase();
//        Product product = inventory.get(ID);
//        if (product != null) {
//            String valid = validateInt(quantityStr, "Quantity");
//            if (!valid.isEmpty()) return valid;
//            int quantity = Integer.parseInt(quantityStr);
//            product.setQuantity(quantity);
//
//            valid = validateDouble(priceStr, "Price");
//            if (!valid.isEmpty()) return valid;
//            double price = Double.parseDouble(priceStr);
//            product.setPrice(price);
//
//            if (!name.isEmpty()) {
//                product.setName(name);
//            }
//            return ("Item Updated: " + ID + " " + name);
//        }
//        return ("Error: Product Code [" + ID +"] doesnt exist.");
//    }
//
//    public String updateUser(String ID, String name, String address, String email) {
//        ID=ID.toUpperCase();
//        User user = users.get(ID);
//        if (user != null) {
//            if (address.isEmpty() && email.isEmpty() && name.isEmpty()) {
//                return ("Error: All fields are empty for [" + ID +"]");
//            }
//            if (!address.isEmpty()) {
//                user.setAddress(address);
//            }
//            if (!email.isEmpty()) {
//                user.setEmail(email);
//            }
//            if (!name.isEmpty()) {
//                user.setName(name);
//            }
//            return ("User Updated: " + ID);
//        }
//        return ("Error: User ID [" + ID +"] doesnt exist.");
//    }
//
//    public String updateUser(String ID, String name, String address, String email, String department) {
//        ID=ID.toUpperCase();
//        String output = updateUser(ID, name, address, email);
//        if (output.contains("Error:") && department.isEmpty()) return output;
//        Supplier user = (Supplier)users.get(ID);
//        if ( user != null && !department.isEmpty()) {
//            user.setDepartment(department);
//            return ("User Updated: " + ID);
//        }
//        return ("Error: Supplier ID [" + ID +"] doesnt exist.");
//    }
//
//
//    public String removeItem(String ID) {
//        ID=ID.toUpperCase();
//        if (ID.isEmpty()) return ("Error: Product Code is empty");
//        if (inventory.remove(ID) != null) {
//            return ("Item Removed : " + ID);
//        } else {
//            return ("Error: Product Code [" + ID +"] doesnt exist.");
//        }
//    }
//    public String removeUser(String ID) {
//        ID=ID.toUpperCase();
//        if (users.remove(ID) != null) {
//            return "Item Removed : " + ID;
//        } else {
//            return "Error! User Code [" + ID +"] doesnt exist.";
//        }
//    }
//
//    public ArrayList<Product> getAllItems() {
//        return new ArrayList<>(inventory.values());
//    }
//
//    public ArrayList<Product> getBasket() {
//        return new ArrayList<>(basket.values());
//    }
//    public boolean isBasketEmpty() {
//        return basket.isEmpty();
//    }
//
//    public ArrayList<String> getAllIDs() {
//        ArrayList<String> IDs = new ArrayList<String>();
//        for (Product p : inventory.values()) {
//            IDs.add(p.getID());
//        }
//        return IDs;
//    }
//
//    public ArrayList<String> getAllCustomerIDs() {
//        ArrayList<String> IDs = new ArrayList<String>();
//        for (User p : users.values()) {
//            if (p instanceof Customer) {
//                IDs.add(p.getID());
//            }
//        }
//        return IDs;
//    }
//
//    public String getUserName(String ID) {
//        ID=ID.toUpperCase();
//        return users.get(ID).getName();
//    }
//    public String getUserAddress(String ID) {
//        ID=ID.toUpperCase();
//        return users.get(ID).getAddress();
//    }
//    public String getUserEmail(String ID) {
//        ID=ID.toUpperCase();
//        return users.get(ID).getEmail();
//    }
//    public String getUserDepartment(String ID) {
//        ID=ID.toUpperCase();
//        if (users.get(ID) instanceof Supplier supplier) {
//            return supplier.getDepartment();
//        }
//        return "";
//    }
//
//    public String getUserType(String ID) {
//        ID=ID.toUpperCase();
//        if (users.get(ID) instanceof Customer) {
//            return "Customer";
//        } else if (users.get(ID) instanceof Supplier) {
//            return "Supplier";
//        }
//        return "";
//    }
//
//    public ArrayList<String> getAllSupplierIDs() {
//        ArrayList<String> IDs = new ArrayList<String>();
//        for (User p : users.values()) {
//            if (p instanceof Supplier) {
//                IDs.add(p.getID());
//            }
//        }
//        return IDs;
//    }
//
//    public double getPrice(String ID){
//        ID=ID.toUpperCase();
//        return inventory.get(ID).getPrice();
//    }
//    public String getName(String ID){
//        ID=ID.toUpperCase();
//        return inventory.get(ID).getName();
//    }
//    public Integer getQuantity(String ID){
//        ID=ID.toUpperCase();
//        return inventory.get(ID).getQuantity();
//    }
//    public Integer getBasketQuantity(String ID){
//        ID=ID.toUpperCase();
//        return basket.get(ID).getQuantity();
//    }
//    public void resetQuantity(String ID, int baskQuan){
//        ID=ID.toUpperCase();
//        int currentQuan = getQuantity(ID);
//        updateItem(ID, currentQuan + baskQuan);
//    }
//
//    public void clearBasket() {
//        for (Product item : getBasket()) {
//            String ID = item.getID();
//            resetQuantity(ID, item.getQuantity());
//        }
//        basket.clear();
//    }
//
//    public String checkoutBasket(String userID) {
//        userID=userID.toUpperCase();
//        String orderID = "T001";// {RETURN} to change to order number creator
//        double totalPrice = 0.0;
//
//        if (isBasketEmpty() || userID.isEmpty()) {
//            return "Basket or Customer ID is empty. Please try again.";
//        }
//
//        // {RETURN} to write a function to contain these
//        Sale transaction = new Sale(orderID, userID, getBasket());
//        transactions.put(orderID, transaction);
//
//        basket.clear();
//        for (Product item : transaction.getProducts()) {
//            totalPrice += item.getPrice() * item.getQuantity();
//        }
//
//        return orderID+" Checked out for £"+totalPrice;
//    }
//
//    public String removeItemFromBasket(String ID) {
//        ID=ID.toUpperCase();
//        try {
//            int basketQuantity = getBasketQuantity(ID);
//            resetQuantity(ID, basketQuantity);
//            basket.remove(ID);
//            return "Item "+ID+" Removed";
//        } catch (NullPointerException e) {
//            return "Item "+ID+" Doesnt Exist in basket";
//        }
//    }
//
//    //{RETURN}
//    public void removeItemFromBasket(String ID, int Quantity) {
//        ID=ID.toUpperCase();
//        int basketQuantity = getBasketQuantity(ID);
//        resetQuantity(ID, Quantity);
//        basket.remove(ID);
//    }
//
//    public ArrayList<Supplier> getSuppliers() {
//        ArrayList<User> usersList = new ArrayList<>(users.values());
//        ArrayList<Supplier> suppliersList = new ArrayList<>();
//        for (User i : usersList) {
//            if (i instanceof Supplier) {
//                suppliersList.add((Supplier) i);
//            }
//        };
//        return suppliersList;
//    }
//    public ArrayList<Customer> getCustomers() {
//        ArrayList<User> usersList = new ArrayList<>(users.values());
//        ArrayList<Customer> CustomersList = new ArrayList<>();
//        for (User i : usersList) {
//            if (i instanceof Customer) {
//                CustomersList.add((Customer) i);
//            }
//        };
//        return CustomersList;
//    }
//
//    public String addSupplier(String ID, String name, String address, String email, String department) {
//        ID=ID.toUpperCase();
//        if (users.containsKey(ID)) {
//            return "Supplier with ID " + ID + " already exists.";
//        }
//        if (ID.isEmpty() || name.isEmpty() || address.isEmpty() || email.isEmpty()) {
//            return "Please enter all necessary fields.";
//        }
//        users.put(ID, new Supplier(ID, name, address, email, department));
//        return "Supplier " + name + " added with ID " + ID;
//    }
//
//    public String addCustomer(String ID, String name, String address, String email) {
//        ID=ID.toUpperCase();
//        if (users.containsKey(ID)) {
//            return "Customer with ID " + ID + " already exists.";
//        }
//        if (name.isEmpty() || address.isEmpty() || email.isEmpty()) {
//            return "Please enter all necessary fields.";
//        }
//        users.put(ID, new Customer(ID, name, address, email));
//        return "Customer " + name + " added with ID " + ID;
//    }
//
//    public TreeMap<String,Double> generateReport() {
//        TreeMap<String,Double> report = new TreeMap<>();
//        double sales = 0;
//        double purchases = 0;
//
//        for (Transaction transaction : transactions.values()) {
//            if (transaction instanceof Sale) {
//                sales += transaction.getTotalCost();
//
//            } else {
//                purchases += transaction.getTotalCost();
//            }
//        }
//
//        double profit = sales - purchases;
//        report.put("Revenue", sales);
//        report.put("Purchases", purchases);
//        report.put("Profit/Loss", profit);
//        return report;
//    }
//
//    public ArrayList<Product> getInTransit() {
//        return new ArrayList<>(inTransit.values());
//    }
//
//
//}