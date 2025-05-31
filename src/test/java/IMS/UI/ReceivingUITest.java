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
import java.util.Arrays;

public class ReceivingUITest {

    @Mock
    private ProductManager mockProductManager;
    private ReceivingUI receivingUI;
}
