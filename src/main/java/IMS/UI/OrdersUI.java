package IMS.UI;
import IMS.Inventory.*;
import IMS.Products.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrdersUI extends GUI  {
    private final InventoryManager inventoryManager;
    private final ProductManager productManager;
    private final BasketManager basketManager;
    private final DefaultTableModel inventoryTable;
    private final DefaultTableModel basketTable;
    private JTextField productIDField;
    private JTextField userIDField;
    private JTextField productQuantityField;

    public OrdersUI(InventoryManager inventoryManager, ProductManager productManager, BasketManager basketManager) {
        this.inventoryManager = inventoryManager;
        this.productManager = productManager;
        this.basketManager = basketManager;

        //Create Default Table
        inventoryTable = createNonEditTable(new String[]{"ID","Product", "Quantity", "Price"});
        basketTable = createNonEditTable(new String[]{"ID", "Product", "Quantity", "Price"});

        JSplitPane mainPanel = createMainPanel();
        JPanel northPanel = createNorthPanel("Orders");
        JPanel southPanel = createSouthPanel();
        
        addPanels(northPanel, mainPanel, southPanel);
        refreshTable();
    }

    private JPanel createTablePanel(JTable table, String header) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JLabel headerLabel = createHeader(header);
        panel.add(headerLabel, BorderLayout.NORTH);
        return panel;
    }

    private JSplitPane createMainPanel() {
        JTable table = new JTable(inventoryTable);
        JTable basket = new JTable(basketTable);
        JPanel stockPane = createTablePanel(table, "Inventory");
        JPanel basketPane = createTablePanel(basket, "Basket");
        basketPane.setMinimumSize(new Dimension(200,200));
        JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,stockPane,basketPane);
        mainPanel.setDividerLocation(450);
        mainPanel.setResizeWeight(1);
        return mainPanel;
    }
    @Override
    public JPanel createSouthPanel() {
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);

        return southPanel;
    }
    @Override
    public JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2 , 1));
        JPanel topHalf = new JPanel(new GridLayout(1 , 1));
        JPanel bottomHalf = new JPanel(new GridLayout(1 , 1));

        productIDField = new JTextField(10);
        userIDField = new JTextField(10);
        productQuantityField = new JTextField(5);

        addLabelField(topHalf, "   Customer/Supplier ID :  ", userIDField);
        inputPanel.add(topHalf);

        addLabelField(bottomHalf, "   Product ID :  ", productIDField);
        addLabelField(bottomHalf, "  Quantity :  ", productQuantityField);
        inputPanel.add(bottomHalf);

        return inputPanel;
    }

    @Override
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout());

        JButton addButton = new JButton("Add To Basket/Update");
        JButton removeItemButton = new JButton("Remove from Basket");
        JButton removeAllButton = new JButton("Remove All");
        JButton checkoutButton = new JButton("Checkout");

        addButton.addActionListener(e -> {
            updatePanel(errorPanel, "");
            String ID = productIDField.getText();
            String name = productManager.getName(ID);
            double price = productManager.getPrice(ID);
            String quantityStr = productQuantityField.getText();
            String userID = userIDField.getText();
            String output = basketManager.addToBasket(userID, ID, name, quantityStr, price);
            updatePanel(errorPanel, output);
            refreshTable();

        });

        checkoutButton.addActionListener(e -> {
            String userID = userIDField.getText();
            String output = inventoryManager.checkoutBasket(userID);
            updatePanel(errorPanel, output);
            refreshTable();
        });

        removeItemButton.addActionListener(e -> {
            String ID = productIDField.getText();
            String quantityStr = productQuantityField.getText();
            String output;

            if (quantityStr.isEmpty()) {
                output = basketManager.removeItemFromBasket(ID);
            } else {
                output = basketManager.removeItemFromBasket(ID, quantityStr);
            }

            updatePanel(errorPanel, output);
            refreshTable();
        });

        removeAllButton.addActionListener(e -> {
            basketManager.emptyBasket();
            refreshTable();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(removeAllButton);
        buttonPanel.add(checkoutButton);

        return buttonPanel;
    }

    @Override
    public void refreshTable() {
        inventoryTable.setRowCount(0);
        basketTable.setRowCount(0);
        System.out.println("Refreshing");
        for (Product item : productManager.getAllItems()) {
            inventoryTable.addRow(new Object[]{item.getID(), item.getName(), item.getQuantity(), item.getPrice()});
        }
        for (Product item : basketManager.getBasket()) {
            int quantity = item.getQuantity();
            if (quantity <0) {
                quantity *= -1;
            }
            basketTable.addRow(new Object[]{ item.getID(), item.getName(), quantity, item.getPrice()});
        }
    }
}