package IMS.UI;

import IMS.Inventory.ProductManager;
import IMS.Products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReceivingUITest {

    @Mock
    private ProductManager mockProductManager;
    private ReceivingUI receivingUI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ArrayList<Product> testProducts = new ArrayList<>();
        testProducts.add(new Product("P001", "Test Product", 10, 90));
        testProducts.add(new Product("P002", "Test Product 2", 20, 80));
        testProducts.add(new Product("P003", "Test Product 3", 30 , 70));
        when (mockProductManager.getInTransit()).thenReturn(testProducts);

        receivingUI = new ReceivingUI(mockProductManager);
    }

    @Test
    void testCreateTablePanel() {
        JScrollPane scrollPane = receivingUI.createTablePanel();

        assertNotNull(scrollPane);
        assertInstanceOf(JTable.class, scrollPane.getViewport().getView());

        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (Product product : mockProductManager.getInTransit()) {
            model.addRow(new Object[]{product.getID(), product.getName(),
                    product.getQuantity() });
        }

        assertEquals(4, model.getRowCount());
        assertEquals(3, model.getColumnCount());

        assertEquals("ID", model.getColumnName(0));
        assertEquals("Product", model.getColumnName(1));
        assertEquals("Quantity", model.getColumnName(2));

        assertFalse(model.isCellEditable(0, 0));

    }

    @Test
    void testCreateInputPanel() {
        JPanel inputPanel = receivingUI.createInputPanel();

        assertNotNull(inputPanel);

        //Check has 2 panels
        assertEquals(2, inputPanel.getComponentCount());
        assertInstanceOf(GridLayout.class, inputPanel.getLayout());

        //Assert Top Panel
        assertInstanceOf(JPanel.class, inputPanel.getComponent(0));
        //Assert Bottom Panel
        assertInstanceOf(JPanel.class, inputPanel.getComponent(1));

    }

    @Test
    void testCreateButtonPanel() {
        JPanel buttonPanel = receivingUI.createButtonPanel();

        assertNotNull(buttonPanel);

        //Check has 1 button
        assertEquals(1, buttonPanel.getComponentCount());
        assertInstanceOf(GridLayout.class, buttonPanel.getLayout());

        //Assert Add Button
        assertInstanceOf(JButton.class, buttonPanel.getComponent(0));
        JButton receiveButton = (JButton) buttonPanel.getComponent(0);
        assertEquals("Receive", receiveButton.getText());

    }

    @Test
    void testReceiveProduct() throws NoSuchFieldException, IllegalAccessException {
        Field idField = ReceivingUI.class.getDeclaredField("productIDField");
        Field quantityField = ReceivingUI.class.getDeclaredField("productQuantityField");
        idField.setAccessible(true);
        quantityField.setAccessible(true);

        JTextField productIDField = (JTextField) idField.get(receivingUI);
        JTextField productQuantityField = (JTextField) quantityField.get(receivingUI);

        //Init
        productIDField.setText("P001");
        productQuantityField.setText("10");

        when(mockProductManager.receiveItem("P001", "10")).thenReturn("Success");

        JPanel buttonPanel = receivingUI.createButtonPanel();
        JButton receiveButton = (JButton) buttonPanel.getComponent(0);
        receiveButton.doClick();

        verify(mockProductManager).receiveItem("P001", "10");
        verify(mockProductManager, atLeastOnce()).getInTransit();

    }

    @Test
    void testRefreshTable() throws NoSuchFieldException, IllegalAccessException {
        //Init
        JScrollPane scrollPane = receivingUI.createTablePanel();
        assertNotNull(scrollPane);
        assertInstanceOf(JTable.class, scrollPane.getViewport().getView());
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (Product product : mockProductManager.getInTransit()) {
            model.addRow(new Object[]{product.getID(), product.getName(),
                    product.getQuantity(), product.getPrice() });
        }


        receivingUI.refreshTable();

        assertEquals(3, model.getRowCount());
        assertEquals("P001", model.getValueAt(0, 0));
        assertEquals("Test Product", model.getValueAt(0, 1));
        assertEquals(10, model.getValueAt(0, 2));

        assertEquals("P002", model.getValueAt(1, 0));
        assertEquals("Test Product 2", model.getValueAt(1, 1));
        assertEquals(20, model.getValueAt(1, 2));

        verify(mockProductManager, atLeastOnce()).getInTransit();

    }
}
