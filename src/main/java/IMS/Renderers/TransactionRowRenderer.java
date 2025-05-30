package IMS.Renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TransactionRowRenderer extends DefaultTableCellRenderer {
    public static final Color DARK_RED = new Color(0xffbfbf);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Object userIDObj = table.getModel().getValueAt(row, 1);
        String userID = userIDObj.toString();

        if (userID.startsWith("S")) {
            c.setBackground(DARK_RED);
        } else {
            c.setBackground(Color.WHITE);
        }

        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }

        c.setFont(new Font("Arial", Font.BOLD, 12));

        return c;
    }
}
