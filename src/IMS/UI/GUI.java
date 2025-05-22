package IMS.UI;

import IMS.Interfaces.UIPanelInterface;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class GUI extends JPanel implements UIPanelInterface {

    protected void addLabelField(JPanel parent, String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        parent.add(panel);
    }
    protected DefaultTableModel createNonEditTable(String[] columns){
        return new DefaultTableModel(columns, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    protected JLabel createHeader(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }
}
