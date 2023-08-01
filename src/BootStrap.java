import javax.swing.SwingUtilities;

import presentation.ProductManagementApp;

public class BootStrap {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductManagementApp().setVisible(true);
            }
        });
    }
}
