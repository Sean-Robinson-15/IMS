package IMS.Inventory;
import IMS.Orders.Purchase;
import IMS.Orders.Transaction;
import IMS.Products.Product;
import IMS.UI.InputValidator;

import java.util.ArrayList;

public class InventoryManager {
    private final ProductManager productManager;
    private final UserManager userManager;
    private final TransactionManager transactionManager;
    private final BasketManager basketManager;
    private final InputValidator validator;

    public static final int DEFAULT_LOW_STOCK_THRESHOLD = 10;


    public InventoryManager(ProductManager productManager, UserManager userManager, TransactionManager transactionManager) {
        this.productManager = productManager;
        this.userManager = userManager;
        this.transactionManager = transactionManager;
        this.basketManager = new BasketManager(productManager);
        this.validator = new InputValidator();
    }

    public String checkoutBasket(String userID) {
        if (!userManager.userExists(userID) || basketManager.isBasketEmpty()) {
            return "Basket or Customer ID is empty. Please try again.";
        }

        String orderId = "T001"; // This should be generated properly
        Transaction transaction = basketManager.createSale(orderId, userID);
        transactionManager.addTransaction(orderId, transaction);
        basketManager.clearBasket();

        return orderId + " Checked out for £" + transaction.getTotalCost();

    }

    public void testItems() {
        productManager.randomize(10);
        ArrayList<Product> inventoryArray = productManager.getAllItems();
        Product prod = inventoryArray.get((int)(Math.random()*inventoryArray.size()));
        int quantity = (int)(Math.random()*prod.getQuantity());
        basketManager.addToBasket(prod.getID(), prod.getName(), String.valueOf(quantity), prod.getPrice());
        userManager.addCustomer("C001", "Customer 1", "101 Made Up Lane", "Customer1@gmail.com");
        userManager.addSupplier("S001", "Supplier 1", "202 Not Real Road", "Supplier1@gmail.com", "Sales");
        checkoutBasket("S001");
        transactionManager.addTransaction("T002", new Purchase("T001", "C001", getRandomProducts(inventoryArray, (int)(Math.random()*10) + 1)));
        productManager.addInTransitItem( new Product("BNU001", "Prod1", 69, 10));
    }

    public ArrayList<Product> getRandomProducts(ArrayList<Product> products, int num) {
        ArrayList<Product> randomProducts = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int randomIndex = (int)(Math.random()*products.size());
            Product randomProduct = products.get(randomIndex);
            randomProduct.setQuantity((int)(Math.random()*randomProduct.getQuantity()));
            randomProducts.add(products.get(randomIndex));
        }
        return randomProducts;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public BasketManager getBasketManager() {
        return basketManager;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }


}
