package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import domain.model.Product;

public class ProductManagementController implements ActionListener {

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
                modelRemote = viewRemote.getAllTextFields();
                modelRemote.addSubscriber(viewRemote);
                for (Product product : viewRemote.getProducts()) {
                    try {
                        if (product.getID().equals(modelRemote.getID())) {
                            JOptionPane.showMessageDialog(null, "The ID you have just entered already existed.",
                                    "ID already exist", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } catch (NullPointerException ex) {
                        return;
                    }
                }
                command = new AddCommand(modelRemote);
            }
                break;
            case "Delete": {
                if (viewRemote.getSelectedRow() == null) {
                    JOptionPane.showMessageDialog(null, "Choose the product which you want to delete on the table",
                            "No chosen product", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                modelRemote = viewRemote.getSelectedRow();
                modelRemote.addSubscriber(viewRemote);
                command = new DeleteCommand(modelRemote);
            }
                break;
            case "Edit": {
                if (viewRemote.getSelectedRow() == null) {
                    JOptionPane.showMessageDialog(null, "Choose the product which you want to delete on the table",
                            "No chosen product", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                modelRemote = viewRemote.getSelectedRow();
                modelRemote.addSubscriber(viewRemote);
                command = new EditCommand(modelRemote);
            }
                break;
            case "Search": {
                command = new SearchCommand(modelRemote);
            }
                break;
            case "Show estimates": {
                command = new EstimateCommand();
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

}
