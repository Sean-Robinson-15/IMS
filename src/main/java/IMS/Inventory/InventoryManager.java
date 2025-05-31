package IMS.Inventory;
import IMS.Orders.Purchase;
import IMS.Orders.Transaction;
import IMS.Products.Product;

import java.util.ArrayList;

public class InventoryManager {
    private final ProductManager productManager;
    private final UserManager userManager;
    private final TransactionManager transactionManager;
    private final BasketManager basketManager;
    public static final int DEFAULT_LOW_STOCK_THRESHOLD = 10;


    public InventoryManager(ProductManager productManager, BasketManager basketManager, UserManager userManager, TransactionManager transactionManager) {
        this.productManager = productManager;
        this.userManager = userManager;
        this.transactionManager = transactionManager;
        this.basketManager = basketManager;
    }

    public String checkoutBasket(String userID) {
        if (!userManager.userExists(userID)) {
            return "Error: User does not exist. Please try again.";
        }
        if(basketManager.isBasketEmpty()) {
            return "Error: Basket is empty. Please try again.";
        }

        String orderId = getNextOrderID(); // This should be generated properly
        Transaction transaction = basketManager.createTransaction(orderId);
        if (transaction instanceof Purchase) {
            for (Product item : basketManager.getBasket()) {
                productManager.addInTransitItem(item);
            }
        }
        String output = transactionManager.addTransaction(orderId, transaction);
        if (output.contains("Error:")) {
            return output;
        }
        basketManager.checkoutBasket();

        String totalCost = String.format("%.02f", transaction.getTotalCost());

        if (transaction instanceof Purchase) {
            return orderId + " Checked out for £" + totalCost +
                    ". Product moved to Receving.";
        }

        return orderId + " Checked out for £" + totalCost;

    }
    public String getNextOrderID() {
        int nextOrderID = transactionManager.getAllTransactions().size() + 1;
        return "T" + String.format("%03d",nextOrderID);
    }

    public void demoMode() {
        productManager.randomize((int)(Math.random()*10)+10);
        ArrayList<Product> inventoryArray = productManager.getAllItems();
        addDemoUsers();
        demoSale(inventoryArray, true);
        demoSale(inventoryArray, false);
        transactionManager.addTransaction("T002", new Purchase("T002", "S001", getRandomProducts(inventoryArray, (int)(Math.random()*10) + 1)));
        productManager.addInTransitItem( new Product("BNU001", "Prod1", (int)(Math.random()*100), (int)(Math.random()*60)));
    }

    public void demoSale(ArrayList<Product> inventoryArray, boolean checkout) {
        int index = getDemoProductIndex(inventoryArray);
        Product prod = inventoryArray.get(index);
        inventoryArray.remove(index);
        int quantity = (int)(Math.random()*prod.getQuantity());
        basketManager.addToBasket("C001", prod.getID(), prod.getName(), String.valueOf(quantity), prod.getPrice());
        if (checkout) {
            checkoutBasket("C001");
        }
    }

    public void addDemoUsers() {
        userManager.addCustomer("C001", "Customer 1", "101 Made Up Lane", "Customer1@gmail.com");
        userManager.addCustomer("C002","Customer 2", "202 Made Up Lane", "Customer2@gmail.com");
        userManager.addCustomer("C003","Customer 3", "303 Made Up Lane", "Customer3@gmail.com");
        userManager.addSupplier("S001", "Supplier 1", "101 Not Real Road", "Supplier1@gmail.com", "Sales");
        userManager.addSupplier("S002", "Supplier 2", "202 Not Real Road", "Supplier2@gmail.com", "Logistics");
        userManager.addSupplier("S003", "Supplier 3", "303 Not Real Road", "Supplier3@gmail.com", "General");
    }

    public int getDemoProductIndex(ArrayList<Product> inventoryArray) {
        return (int)(Math.random()*inventoryArray.size());
    }

    public ArrayList<Product> getRandomProducts(ArrayList<Product> products, int num) {
        ArrayList<Product> randomProducts = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int randomIndex = (int)(Math.random()*products.size());
            Product originalProduct = products.get(randomIndex);

            Product randomProduct = new Product(originalProduct.getID(),
                    originalProduct.getName(), (int)(Math.random()*originalProduct.getQuantity()),
                    originalProduct.getPrice());

            randomProducts.add(randomProduct);
        }
        return randomProducts;
    }

}
