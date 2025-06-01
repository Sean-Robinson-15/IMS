package IMS.UI;

import IMS.Managers.UserManager;
import IMS.Users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomersUITest {

    @Mock
    private UserManager mockUserManager;
    private CustomersUI customersUI;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ArrayList<Customer> testCustomers = new ArrayList<>();
        testCustomers.add(new Customer("C001", "Test Customer", "101 Made Up Lane", "<EMAIL>"));
        testCustomers.add(new Customer("C002", "Test Customer 2", "102 Made Up Lane", "<EMAIL>"));
        testCustomers.add(new Customer("C003", "Test Customer 3", "103 Made Up Lane", "<EMAIL>"));
        when (mockUserManager.getCustomers()).thenReturn(testCustomers);

        customersUI = new CustomersUI(mockUserManager);
    }

    @Test
    void testCreateTablePanel() {
        JScrollPane scrollPane = customersUI.createTablePanel();

        assertNotNull(scrollPane);
        assertInstanceOf(JTable.class, scrollPane.getViewport().getView());

        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (Customer customer : mockUserManager.getCustomers()) {
            model.addRow(new Object[]{customer.getID(), customer.getName(),
                    customer.getAddress(), customer.getEmail()});
        }

        assertEquals(4, model.getRowCount());
        assertEquals(4, model.getColumnCount());

        assertEquals("ID", model.getColumnName(0));
        assertEquals("Name", model.getColumnName(1));
        assertEquals("Email", model.getColumnName(2));
        assertEquals("Address", model.getColumnName(3));

        assertFalse(model.isCellEditable(0, 0));

    }

    @Test
    void testCreateInputPanel() {
        JPanel inputPanel = customersUI.createInputPanel();

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
        JPanel buttonPanel = customersUI.createButtonPanel();

        assertNotNull(buttonPanel);

        //Check has 3 buttons
        assertEquals(3, buttonPanel.getComponentCount());
        assertInstanceOf(GridLayout.class, buttonPanel.getLayout());

        //Assert Add Button
        assertInstanceOf(JButton.class, buttonPanel.getComponent(0));
        JButton addButton = (JButton) buttonPanel.getComponent(0);
        assertEquals("Add", addButton.getText());

        //Assert Delete Button
        assertInstanceOf(JButton.class, buttonPanel.getComponent(1));
        JButton updateButton = (JButton) buttonPanel.getComponent(1);
        assertEquals("Update", updateButton.getText());

        //Assert Edit Button
        assertInstanceOf(JButton.class, buttonPanel.getComponent(2));
        JButton removeButton = (JButton) buttonPanel.getComponent(2);
        assertEquals("Remove", removeButton.getText());

    }
}
