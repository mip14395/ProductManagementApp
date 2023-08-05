package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domain.*;
import domain.model.Appliance;
import domain.model.Food;
import domain.model.Pottery;
import domain.model.Product;

public class ProductManagementController implements ActionListener {

    private ProductServiceImpl productService;
    private Product modelRemote;
    private ProductManagementUI viewRemote;
    Command command;

    public ProductManagementController(Product modelRemote, ProductManagementUI viewRemote) {
        this.modelRemote = modelRemote;
        this.viewRemote = viewRemote;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton actionSource = new JButton();
        actionSource = (JButton) e.getSource();
        switch (actionSource.getText()) {
            case "Add": {
                JTextField[] textFields = viewRemote.getAllTextFields();
                // Kiểm tra xem có textfield nào bị bỏ trống ko
                for (JTextField textField : textFields) {
                    if (textField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter all informations", "Invalid input",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                String iD = "";
                String name = "";
                int amount = 0;
                int price = 0;
                try {
                    iD = textFields[0].getText();
                    name = textFields[1].getText();
                    amount = Integer.parseInt(textFields[2].getText());
                    price = Integer.parseInt(textFields[3].getText());
                    // Amount ko được là số âm
                    if (amount < 0) {
                        JOptionPane.showMessageDialog(null, "Amount can only be negative!", "INVALID INPUT(s)",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Price phải lớn hơn 0
                    if (price < 1) {
                        JOptionPane.showMessageDialog(null, "Price must be greater than 0!", "INVALID INPUT(s)",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Amount and Price can only be integers", "INVALID INPUT(s)",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (Product product : getProducts()) {
                    try {
                        if (product.getID().equals(iD)) {
                            JOptionPane.showMessageDialog(null, "The ID you have just entered already existed.",
                                    "ID already exist", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } catch (NullPointerException ex) {
                        return;
                    }
                }
                // Gán giá trị từ textfield vào product mới & gán giá trị của nó vào modelRemote
                modelRemote = (Product) new Appliance(iD, name, amount, price, 0, 0);
                // Thêm viewRemote vào danh sách subscriber của modelRemote vì modelRemote mới
                modelRemote.addSubscriber(viewRemote);
                command = new AddCommand(modelRemote);
            }
                break;
            case "Delete": {
                if (rowToProduct() == null) {
                    JOptionPane.showMessageDialog(null, "Choose the product which you want to delete on the table",
                            "No chosen product", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                modelRemote = rowToProduct();
                modelRemote.addSubscriber(viewRemote);
                command = new DeleteCommand(modelRemote);
            }
                break;
            case "Edit": {
                if (rowToProduct() == null) {
                    JOptionPane.showMessageDialog(null, "Choose the product which you want to edit on the table",
                            "No chosen product", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                modelRemote = rowToProduct();
                modelRemote.addSubscriber(viewRemote);
                command = new EditCommand(modelRemote);
            }
                break;
            case "Search": {
                command = new SearchCommand(modelRemote);
            }
                break;
            case "Show estimates": {
                command = new EstimateCommand(modelRemote);
            }
                break;
            case "Show products a week from expiration dates": {
                command = new CloseToExpirationCommand(modelRemote);
            }
                break;
            case "Back": {
                command = null;
                viewRemote.setCondition(null);
            }
                break;
        }
        execute(command);
    }

    private void execute(Command command) {
        if (command != null)
            command.execute();
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        // Nếu đk của các product hiện trên table là null thì trả về tất cả product
        if (viewRemote.getCondition() == null) {
            productService = new ProductServiceImpl();
            products = productService.selectAll();
        } else {
            // Nếu có điều kiện thì chỉ trả về các product phù hợp với đk
            productService = new ProductServiceImpl();
            products = productService.search(viewRemote.getCondition());
        }
        return products;
    }

    private Product rowToProduct() {
        if (viewRemote.getSelectedRow() == null) {
            return null;
        }
        Object[] objects = viewRemote.getSelectedRow();
        String iD = (String) objects[0];
        if (iD.equals("TOTAL"))
            return null;
        String name = (String) objects[1];
        int amount = (int) objects[2];
        int price = (int) objects[3];
        if (!((String) objects[6]).equals("")) {
            String supplier = (String) objects[6];
            if (!(objects[7].toString()).equals("")) {
                Date importDate = (Date) objects[7];
                return new Pottery(iD, name, amount, price, importDate, supplier);
            }
            Date mFG = (Date) objects[8];
            Date eXP = (Date) objects[9];
            return new Food(iD, name, amount, price, mFG, eXP, supplier);
        }
        int warrantyMonths = (int) objects[10];
        double capacity = (double) objects[11];
        return new Appliance(iD, name, amount, price, warrantyMonths, capacity);
    }

}
