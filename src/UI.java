import javax.swing.*;

public class UI extends JFrame {

    public UI() {
        //Create Window
        setTitle("IMS by BNU Industry Solutions LTD");
        setSize(690,420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UI().setVisible(true));
    }
}