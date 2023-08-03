import javax.swing.SwingUtilities;

import presentation.ProductManagementUI;

public class BootStrap {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductManagementUI().setVisible(true);
            }
        });
    }
}
