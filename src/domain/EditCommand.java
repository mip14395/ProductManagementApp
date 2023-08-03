package domain;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import domain.model.*;
import persistence.ApplianceJdbcGateway;
import persistence.FoodJdbcGateway;
import persistence.PotteryJdbcGateway;
import presentation.Subscriber;

public class EditCommand implements Command {
    private Product modelRemote;

    public EditCommand(Product modelRemote) {
        this.modelRemote = modelRemote;
    }

    @Override
    public void execute() {
        JFrame editFrame = new JFrame("Edit informations");
        JLabel iLabel = new JLabel("- ID:");
        JLabel idLabel = new JLabel(modelRemote.getID());
        JLabel nLabel = new JLabel("- Name:");
        JTextField nameTextField = new JTextField(modelRemote.getName());
        JLabel aLabel = new JLabel("- Amount:");
        JTextField amountTextField = new JTextField(String.valueOf(modelRemote.getAmount()));
        JLabel pLabel = new JLabel("- Price:");
        JTextField priceTextField = new JTextField(String.valueOf(modelRemote.getPrice()));
        JButton eButton = new JButton("Edit");
        JButton cButton = new JButton("Cancel");
        JPanel infoPanel = new JPanel();
        infoPanel.add(iLabel);
        infoPanel.add(idLabel);
        infoPanel.add(nLabel);
        infoPanel.add(nameTextField);
        infoPanel.add(aLabel);
        infoPanel.add(amountTextField);
        infoPanel.add(pLabel);
        infoPanel.add(priceTextField);
        editFrame.add(infoPanel);
        editFrame.setSize(new Dimension(600, 300));
        editFrame.setVisible(true);
        if (modelRemote instanceof Appliance) {
            infoPanel.setLayout(new GridLayout(7, 2));
            JLabel wLabel = new JLabel("- Warranty months:");
            JTextField wTextField = new JTextField(String.valueOf(((Appliance) modelRemote).getWarrantyMonths()));
            JLabel cLabel = new JLabel("- Capacity:");
            JTextField cTextField = new JTextField(String.valueOf(((Appliance) modelRemote).getCapacity()));
            infoPanel.add(wLabel);
            infoPanel.add(wTextField);
            infoPanel.add(cLabel);
            infoPanel.add(cTextField);
            eButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String iD = idLabel.getText();
                    String name = "";
                    int amount = 0;
                    int price = 0;
                    int warrantyMonths = 0;
                    double capacity = 0;
                    try {
                        name = nameTextField.getText();
                        if (name.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please enter Name.",
                                    "INVALID INPUT(s)",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        amount = Integer.parseInt(amountTextField.getText());
                        price = Integer.parseInt(priceTextField.getText());
                        warrantyMonths = Integer.parseInt(wTextField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Amount, Price and Warranty months can only be integers.",
                                "INVALID INPUT(s)", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    try {
                        capacity = Double.parseDouble(cTextField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Capacity can only be a double.",
                                "INVALID INPUT(s)", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Appliance appliance = new Appliance(iD, name, amount, price, warrantyMonths, capacity);
                    for (Subscriber subscriber : modelRemote.getSubscribers()) {
                        appliance.addSubscriber(subscriber);
                    }
                    ;
                    JOptionPane.showMessageDialog(editFrame,
                            "Updated " + new ProductServiceImpl(new ApplianceJdbcGateway()).update(appliance)
                                    + " Appliance product into database",
                            "Done!",
                            JOptionPane.INFORMATION_MESSAGE);
                    editFrame.dispose();
                }
            });
        } else if (modelRemote instanceof Pottery) {
            infoPanel.setLayout(new GridLayout(7, 2));
            JLabel supLabel = new JLabel("- Supplier:");
            JTextField supTextField = new JTextField(((Pottery) modelRemote).getSupplier());
            JLabel impLabel = new JLabel("- Import Date:");
            MaskFormatter formatter = new MaskFormatter();
            try {
                formatter = new MaskFormatter("####-##-##");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formatter.setPlaceholder(((Pottery) modelRemote).getImportDate().toString());
            JFormattedTextField impTextField = new JFormattedTextField(formatter);
            infoPanel.add(supLabel);
            infoPanel.add(supTextField);
            infoPanel.add(impLabel);
            infoPanel.add(impTextField);
            eButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String iD = idLabel.getText();
                    String name = "";
                    int amount = 0;
                    int price = 0;
                    String supplier = "";
                    Date importDate = null;
                    try {
                        name = nameTextField.getText();
                        supplier = supTextField.getText();
                        if (name.isEmpty() || supplier.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please enter Name and Supplier.",
                                    "INVALID INPUT(s)",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        amount = Integer.parseInt(amountTextField.getText());
                        price = Integer.parseInt(priceTextField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Amount, Price and Warranty months can only be integers.",
                                "INVALID INPUT(s)", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    try {
                        importDate = Date.valueOf(impTextField.getText());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(infoPanel,
                                "Please enter dates under the format (yyyy-MM-dd)!", "Invalid input",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Pottery pottery = new Pottery(iD, name, amount, price, importDate, supplier);
                    for (Subscriber subscriber : modelRemote.getSubscribers()) {
                        pottery.addSubscriber(subscriber);
                    }
                    JOptionPane.showMessageDialog(editFrame,
                            "Updated " + new ProductServiceImpl(new PotteryJdbcGateway()).update(pottery)
                                    + " Pottery product into database",
                            "Done!",
                            JOptionPane.INFORMATION_MESSAGE);
                    editFrame.dispose();
                }
            });
        } else {
            infoPanel.setLayout(new GridLayout(8, 2));
            JLabel supLabel = new JLabel("- Supplier:");
            JTextField supTextField = new JTextField(((Food) modelRemote).getSupplier());
            JLabel mFGLabel = new JLabel("- Manufacturing Date:");
            MaskFormatter formatter = new MaskFormatter();
            MaskFormatter formatter2 = new MaskFormatter();
            try {
                formatter = new MaskFormatter("####-##-##");
                formatter2 = new MaskFormatter("####-##-##");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formatter.setPlaceholder(((Food) modelRemote).getmFG().toString());
            JFormattedTextField mFGTextField = new JFormattedTextField(formatter);
            JLabel eXPLabel = new JLabel("- Expiration Date:");
            formatter2.setPlaceholder(((Food) modelRemote).geteXP().toString());
            JFormattedTextField eXPTextField = new JFormattedTextField(formatter2);
            infoPanel.add(supLabel);
            infoPanel.add(supTextField);
            infoPanel.add(mFGLabel);
            infoPanel.add(mFGTextField);
            infoPanel.add(eXPLabel);
            infoPanel.add(eXPTextField);
            eButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String iD = idLabel.getText();
                    String name = "";
                    int amount = 0;
                    int price = 0;
                    String supplier = "";
                    Date mFG = null;
                    Date eXP = null;
                    try {
                        name = nameTextField.getText();
                        supplier = supTextField.getText();
                        if (name.isEmpty() || supplier.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please enter Name and Supplier.",
                                    "INVALID INPUT(s)",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        amount = Integer.parseInt(amountTextField.getText());
                        price = Integer.parseInt(priceTextField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Amount, Price and Warranty months can only be integers.",
                                "INVALID INPUT(s)", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    try {
                        mFG = Date.valueOf(mFGTextField.getText());
                        eXP = Date.valueOf(eXPTextField.getText());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(infoPanel,
                                "Please enter dates under the format (yyyy-MM-dd)!", "Invalid input",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Food food = new Food(iD, name, amount, price, mFG, eXP, supplier);
                    for (Subscriber subscriber : modelRemote.getSubscribers()) {
                        food.addSubscriber(subscriber);
                    }
                    JOptionPane.showMessageDialog(editFrame,
                            "Updated " + new ProductServiceImpl(new FoodJdbcGateway()).update(food)
                                    + " Food product into database",
                            "Done!",
                            JOptionPane.INFORMATION_MESSAGE);
                    editFrame.dispose();
                }
            });
        }
        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editFrame.dispose();
            }
        });
        infoPanel.add(cButton);
        infoPanel.add(eButton);
    }

}
