package IMS.UI;

import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel{
    protected void addLabelField(JPanel parent, String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        parent.add(panel);
    }
}
