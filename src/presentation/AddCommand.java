package presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import domain.ProductServiceImpl;
import domain.model.Appliance;
import domain.model.Food;
import domain.model.Pottery;
import domain.model.Product;
import persistence.ApplianceJdbcGateway;
import persistence.FoodJdbcGateway;
import persistence.PotteryJdbcGateway;

public class AddCommand implements Command {

    private Product modelRemote;

    public AddCommand() {
    }

    public AddCommand(Product modelRemote) {
        this.modelRemote = modelRemote;
    }

    @Override
    public void execute() {
        if (modelRemote == null) {
            return;
        } else if (modelRemote.getID() != null && modelRemote.getName() != null) {
            String iD = modelRemote.getID().stripTrailing();
            String name = modelRemote.getName().trim();
            if (iD.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter ID and Name",
                        "INVALID INPUT(s)",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int amount = modelRemote.getAmount();
            int price = modelRemote.getPrice();
            JFrame frame = new JFrame("Choose product category");
            JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
            JButton applianceButton = new JButton("Appliance");
            JButton foodButton = new JButton("Food");
            JButton potteryButton = new JButton("Pottery");
            buttonsPanel.add(applianceButton);
            buttonsPanel.add(foodButton);
            buttonsPanel.add(potteryButton);
            frame.add(buttonsPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setResizable(false);
            frame.setSize(new Dimension(500, 70));
            frame.setVisible(true);

            JFrame infoFrame = new JFrame("Enter informations:");
            JLabel infoHeader = new JLabel("Product's informations: ");
            JLabel iLabel = new JLabel("- ID:");
            JLabel idLabel = new JLabel(iD);
            JLabel nLabel = new JLabel("- Name:");
            JLabel nameLabel = new JLabel(name);
            JLabel aLabel = new JLabel("- Amount:");
            JLabel amountLabel = new JLabel(String.valueOf(amount));
            JLabel pLabel = new JLabel("- Price:");
            JLabel priceLabel = new JLabel(String.valueOf(price));
            JButton aButton = new JButton("Add");
            JButton cButton = new JButton("Cancel");
            JPanel infoPanel = new JPanel();
            infoPanel.add(iLabel);
            infoPanel.add(idLabel);
            infoPanel.add(nLabel);
            infoPanel.add(nameLabel);
            infoPanel.add(aLabel);
            infoPanel.add(amountLabel);
            infoPanel.add(pLabel);
            infoPanel.add(priceLabel);
            cButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    infoFrame.dispose();
                }
            });
            infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            infoFrame.setResizable(false);

            applianceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    JLabel wLabel = new JLabel("- Warranty months:");
                    JTextField wTextField = new JTextField();
                    JLabel cLabel = new JLabel("- Capacity:");
                    JTextField cTextField = new JTextField();
                    infoPanel.setLayout(new GridLayout(7, 2));
                    infoPanel.add(wLabel);
                    infoPanel.add(wTextField);
                    infoPanel.add(cLabel);
                    infoPanel.add(cTextField);
                    infoPanel.add(cButton);
                    infoPanel.add(aButton);
                    infoFrame.add(infoHeader);
                    infoFrame.add(infoPanel);
                    infoFrame.setSize(new Dimension(600, 300));
                    infoFrame.setVisible(true);
                    aButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int warrantyMonth = 0;
                            double capacity = 0;
                            try {
                                try {
                                    warrantyMonth = Integer.parseInt(wTextField.getText());
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(infoFrame, "Warranty Months can only be an integer",
                                            "INVALID INPUT(s)",
                                            JOptionPane.ERROR_MESSAGE);
                                    infoFrame.dispose();
                                    return;
                                }
                                try {
                                    capacity = Double.parseDouble(cTextField.getText());
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(infoFrame, "Capacity can only be a double",
                                            "INVALID INPUT(s)",
                                            JOptionPane.ERROR_MESSAGE);
                                    infoFrame.dispose();
                                    return;
                                }
                            } catch (NullPointerException ex) {
                                JOptionPane.showMessageDialog(infoFrame, "Please enter all of the informations",
                                        "INVALID INPUT(s)",
                                        JOptionPane.ERROR_MESSAGE);
                                infoFrame.dispose();
                                return;
                            }
                            Appliance appliance = new Appliance(iD, name, amount, price, warrantyMonth, capacity);
                            for (Subscriber subscriber : modelRemote.getSubscribers()) {
                                appliance.addSubscriber(subscriber);
                                ((ProductManagementUI) subscriber).clearInputFields();
                            }
                            ProductServiceImpl applianceService = new ProductServiceImpl(new ApplianceJdbcGateway());
                            JOptionPane.showMessageDialog(infoFrame,
                                    "Added " + applianceService.insert(appliance) + " Appliance product into database",
                                    "Done!",
                                    JOptionPane.INFORMATION_MESSAGE);
                            infoFrame.dispose();
                        }
                    });
                }
            });

            potteryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    JLabel sLabel = new JLabel("- Supplier:");
                    JTextField sTextField = new JTextField();
                    JLabel impLabel = new JLabel("- Import Date:");
                    MaskFormatter formatter = new MaskFormatter();
                    try {
                        formatter = new MaskFormatter("####-##-##");
                    } catch (ParseException e1) {
                    }
                    formatter.setAllowsInvalid(false);
                    formatter.setOverwriteMode(true);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    formatter.setPlaceholder(format.format(new Date(System.currentTimeMillis())));
                    JFormattedTextField impTextField = new JFormattedTextField(formatter);
                    impTextField.setColumns(8);
                    infoPanel.setLayout(new GridLayout(7, 2));
                    infoPanel.add(sLabel);
                    infoPanel.add(sTextField);
                    infoPanel.add(impLabel);
                    infoPanel.add(impTextField);
                    infoPanel.add(cButton);
                    infoPanel.add(aButton);
                    infoFrame.add(infoPanel);
                    infoFrame.setSize(new Dimension(600, 300));
                    infoFrame.setVisible(true);

                    aButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String supplier = sTextField.getText();
                            Date importDate = null;
                            try {
                                importDate = Date.valueOf(impTextField.getText());
                            } catch (IllegalArgumentException ex) {
                                JOptionPane.showMessageDialog(infoPanel,
                                        "Please enter dates under the format (yyyy-MM-dd)!", "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            if (supplier.isEmpty()) {
                                JOptionPane.showMessageDialog(infoPanel, "Please enter suplier!", "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                            Pottery pottery = new Pottery(iD, name, amount, price, importDate, supplier);
                            for (Subscriber subscriber : modelRemote.getSubscribers()) {
                                pottery.addSubscriber(subscriber);
                                ((ProductManagementUI) subscriber).clearInputFields();
                            }
                            ProductServiceImpl potteryService = new ProductServiceImpl(new PotteryJdbcGateway());
                            JOptionPane.showMessageDialog(infoFrame,
                                    "Added " + potteryService.insert(pottery) + " Pottery product into database.",
                                    "Done!",
                                    JOptionPane.INFORMATION_MESSAGE);
                            infoFrame.dispose();
                        }
                    });
                }
            });

            foodButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    JLabel sLabel = new JLabel("- Supplier:");
                    JTextField sTextField = new JTextField();
                    JLabel mFGLabel = new JLabel("- MFG:");
                    JLabel eXPLabel = new JLabel("- EXP");
                    MaskFormatter formatter = new MaskFormatter();
                    MaskFormatter formatter2 = new MaskFormatter();
                    try {
                        formatter = new MaskFormatter("####-##-##");
                        formatter2 = new MaskFormatter("####-##-##");
                    } catch (ParseException e1) {
                    }
                    formatter.setAllowsInvalid(false);
                    formatter.setOverwriteMode(true);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    formatter.setPlaceholder(
                            format.format(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1) * 7)));
                    JFormattedTextField mFGTextField = new JFormattedTextField(formatter);
                    formatter2.setPlaceholder(
                            format.format(new Date(System.currentTimeMillis() + +TimeUnit.DAYS.toMillis(1) * 30)));
                    JFormattedTextField eXPTextField = new JFormattedTextField(formatter2);
                    mFGTextField.setColumns(8);
                    eXPTextField.setColumns(8);
                    infoPanel.setLayout(new GridLayout(8, 2));
                    infoPanel.add(sLabel);
                    infoPanel.add(sTextField);
                    infoPanel.add(mFGLabel);
                    infoPanel.add(mFGTextField);
                    infoPanel.add(eXPLabel);
                    infoPanel.add(eXPTextField);
                    infoPanel.add(cButton);
                    infoPanel.add(aButton);
                    infoFrame.add(infoPanel);
                    infoFrame.setSize(new Dimension(600, 300));
                    infoFrame.setVisible(true);
                    aButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String supplier = sTextField.getText();
                            Date mFG = null;
                            Date eXP = null;
                            try {
                                mFG = Date.valueOf(mFGTextField.getText());
                                eXP = Date.valueOf(eXPTextField.getText());
                            } catch (IllegalArgumentException ex) {
                                JOptionPane.showMessageDialog(infoPanel,
                                        "Please enter dates under the format (yyyy-MM-dd)!", "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            if (supplier.isEmpty()) {

                            }
                            Food food = new Food(iD, name, amount, price, mFG, eXP, supplier);
                            for (Subscriber subscriber : modelRemote.getSubscribers()) {
                                food.addSubscriber(subscriber);
                                ((ProductManagementUI) subscriber).clearInputFields();
                            }
                            ProductServiceImpl foodService = new ProductServiceImpl(new FoodJdbcGateway());
                            JOptionPane.showMessageDialog(infoFrame,
                                    "Added " + foodService.insert(food) + " Food product into database.",
                                    "Done!",
                                    JOptionPane.INFORMATION_MESSAGE);
                            infoFrame.dispose();
                        }
                    });
                }
            });
        }
    }
}
