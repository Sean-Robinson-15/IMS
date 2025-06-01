package IMS.UI;

import IMS.Managers.UserManager;
import IMS.Users.Supplier;
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

public class SuppliersUITest {

    @Mock
    private UserManager mockUserManager;
    private SuppliersUI suppliersUI;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ArrayList<Supplier> testSuppliers = new ArrayList<>();
        testSuppliers.add(new Supplier("S001", "Test Supplier", "101 Made Up Lane", "<EMAIL>", "Dep1"));
        testSuppliers.add(new Supplier("S002", "Test Supplier 2", "102 Made Up Lane", "<EMAIL>", "Dep2"));
        testSuppliers.add(new Supplier("S003", "Test Supplier 3", "103 Made Up Lane", "<EMAIL>" , "Dep3"));
        when (mockUserManager.getSuppliers()).thenReturn(testSuppliers);

        suppliersUI = new SuppliersUI(mockUserManager);
    }

    @Test
    void testCreateTablePanel() {
        JScrollPane scrollPane = suppliersUI.createTablePanel();

        assertNotNull(scrollPane);
        assertInstanceOf(JTable.class, scrollPane.getViewport().getView());

        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (Supplier supplier : mockUserManager.getSuppliers()) {
            model.addRow(new Object[]{supplier.getID(), supplier.getName(),
                    supplier.getAddress(), supplier.getEmail()});
        }

        assertEquals(4, model.getRowCount());
        assertEquals(5, model.getColumnCount());

        assertEquals("ID", model.getColumnName(0));
        assertEquals("Name", model.getColumnName(1));
        assertEquals("Address", model.getColumnName(2));
        assertEquals("Email", model.getColumnName(3));
        assertEquals("Department", model.getColumnName(4));

        assertFalse(model.isCellEditable(0, 0));

    }

    @Test
    void testCreateInputPanel() {
        JPanel inputPanel = suppliersUI.createInputPanel();

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
        JPanel buttonPanel = suppliersUI.createButtonPanel();

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
