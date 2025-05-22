package IMS.UI;

import IMS.Interfaces.UIPanelInterface;
import javax.swing.*;
import java.awt.*;

public abstract class GUI extends JPanel implements UIPanelInterface {
    protected void addLabelField(JPanel parent, String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        parent.add(panel);
    }
}
