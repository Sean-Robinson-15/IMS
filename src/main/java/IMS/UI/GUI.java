package IMS.UI;

import IMS.Interfaces.UIPanelInterface;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class GUI extends JPanel implements UIPanelInterface {

    protected JPanel errorPanel;

    public GUI() {
        setLayout(new BorderLayout());
        errorPanel = new JPanel(new BorderLayout());
    }

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

    protected abstract JPanel createInputPanel();
    protected abstract JPanel createButtonPanel();


    protected void addPanels(JPanel northPanel, JComponent mainPanel, JPanel southPanel) {
        add(northPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    protected JPanel createSouthPanel() {
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);
        return southPanel;
    }

    protected JLabel createHeader(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    protected JPanel createNorthPanel(String pageTitle) {
        JPanel northPanel = new JPanel(new GridLayout());
        JLabel label = new JLabel(pageTitle);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        label.setBorder(border);
        northPanel.add(label);
        return northPanel;
    }

    protected void updatePanel(JPanel panel, String output) {
        panel.removeAll();
        panel.add(new JLabel(output));
        panel.updateUI();
    }
}
