package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import domain.ApplianceServiceImpl;
import domain.FoodServiceImpl;
import domain.PotteryServiceImpl;
import domain.model.Appliance;
import domain.model.Food;
import domain.model.Pottery;

public class ProductManagementApp extends JFrame {
    private PotteryServiceImpl potteryService;
    private ApplianceServiceImpl applianceService;
    private FoodServiceImpl foodService;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField amountTextField;
    private JTextField priceTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton getSumButton;
    private JButton expireButton;

    public ProductManagementApp() {
        setTitle("Product Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setPreferredSize(new Dimension(850, 400));
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Price");
        tableModel.addColumn("VAT");
        tableModel.addColumn("Grade");
        tableModel.addColumn("Supplier");
        tableModel.addColumn("Import date");
        tableModel.addColumn("MFG");
        tableModel.addColumn("EXP");
        tableModel.addColumn("Warranty months");
        tableModel.addColumn("Capacity");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        idTextField = new JTextField();
        nameTextField = new JTextField();
        amountTextField = new JTextField();
        priceTextField = new JTextField();
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        addButton = new JButton("Add a product");
        deleteButton = new JButton("Delete a product");
        editButton = new JButton("Edit a product");
        getSumButton = new JButton("Get amount of a category");
        expireButton = new JButton("Seek products a week from expiration");
        inputPanel.add(new JLabel("- ID:"));
        inputPanel.add(idTextField);
        inputPanel.add(new JLabel("- Name:"));
        inputPanel.add(nameTextField);
        inputPanel.add(new JLabel("- Amount:"));
        inputPanel.add(amountTextField);
        inputPanel.add(new JLabel("- Price:"));
        inputPanel.add(priceTextField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(editButton);
        inputPanel.add(getSumButton);
        inputPanel.add(expireButton);
        add(inputPanel);
        pack();
        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProduct();
            }
        });
        getSumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSumProduct();
            }
        });
        expireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expiredProduct();
            }
        });
    }

    private void addProduct() {
        try {
            if (idTextField.getText().isEmpty() || nameTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter ID and Name",
                        "INVALID INPUT(s)",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Integer.parseInt(amountTextField.getText());
            Integer.parseInt(priceTextField.getText());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Please enter all of the informations", "INVALID INPUT(s)",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount and Price can only be integers", "INVALID INPUT(s)",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFrame frame = new JFrame("Choose product category");
        JLabel header = new JLabel("Choose the category of the product that you want to add:");
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton applianceButton = new JButton("Appliance");
        JButton foodButton = new JButton("Food");
        JButton potteryButton = new JButton("Pottery");
        buttonsPanel.add(applianceButton);
        buttonsPanel.add(foodButton);
        buttonsPanel.add(potteryButton);
        frame.add(header);
        frame.add(buttonsPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setSize(new Dimension(500, 70));
        frame.setVisible(true);

        applianceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Frame frame : JFrame.getFrames()) {
                    if (frame.isAlwaysOnTop())
                        frame.dispose();
                }
                JFrame frame = new JFrame("Enter informations:");
                JLabel header = new JLabel("Appliance product's informations: ");
                JLabel iLabel = new JLabel("- ID:");
                JLabel idLabel = new JLabel(idTextField.getText());
                JLabel nLabel = new JLabel("- Name:");
                JLabel nameLabel = new JLabel(nameTextField.getText());
                JLabel aLabel = new JLabel("- Amount:");
                JLabel amountLabel = new JLabel(amountTextField.getText());
                JLabel pLabel = new JLabel("- Price:");
                JLabel priceLabel = new JLabel(priceTextField.getText());
                JLabel wLabel = new JLabel("- Warranty months:");
                JTextField wTextField = new JTextField();
                JLabel cLabel = new JLabel("- Capacity:");
                JTextField cTextField = new JTextField();
                JButton cButton = new JButton("Cancel");
                JButton aButton = new JButton("Add");
                JPanel panel = new JPanel(new GridLayout(7, 2));
                panel.add(iLabel);
                panel.add(idLabel);
                panel.add(nLabel);
                panel.add(nameLabel);
                panel.add(aLabel);
                panel.add(amountLabel);
                panel.add(pLabel);
                panel.add(priceLabel);
                panel.add(wLabel);
                panel.add(wTextField);
                panel.add(cLabel);
                panel.add(cTextField);
                panel.add(cButton);
                panel.add(aButton);
                frame.add(header);
                frame.add(panel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setAlwaysOnTop(true);
                frame.setResizable(false);
                frame.setSize(new Dimension(600, 300));
                frame.setVisible(true);

                cButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
                aButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Frame frame = new Frame();
                        for (Frame f : JFrame.getFrames()) {
                            if (f.isAlwaysOnTop())
                                frame = f;
                        }
                        String iD = idTextField.getText();
                        String name = nameTextField.getText();
                        int amount = Integer.parseInt(amountTextField.getText());
                        int price = Integer.parseInt(priceTextField.getText());
                        int warrantyMonth = 0;
                        double capacity = 0;
                        try {
                            try {
                                warrantyMonth = Integer.parseInt(wTextField.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Warranty Months can only be an integer",
                                        "INVALID INPUT(s)",
                                        JOptionPane.ERROR_MESSAGE);
                                frame.dispose();
                                return;
                            }
                            try {
                                capacity = Double.parseDouble(cTextField.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Capacity can only be a double",
                                        "INVALID INPUT(s)",
                                        JOptionPane.ERROR_MESSAGE);
                                frame.dispose();
                                return;
                            }
                        } catch (NullPointerException ex) {
                            JOptionPane.showMessageDialog(frame, "Please enter all of the informations",
                                    "INVALID INPUT(s)",
                                    JOptionPane.ERROR_MESSAGE);
                            frame.dispose();
                            return;
                        }
                        applianceService = new ApplianceServiceImpl();
                        Appliance appliance = new Appliance(iD, name, amount, price, warrantyMonth, capacity);
                        JOptionPane.showMessageDialog(frame,
                                "Added " + applianceService.insert(appliance) + " Pottery product into database",
                                "Done!",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                });
            }
        });

        potteryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Frame frame : JFrame.getFrames()) {
                    if (frame.isAlwaysOnTop())
                        frame.dispose();
                }
                JFrame frame = new JFrame("Enter informations:");
                JLabel header = new JLabel("Appliance product's informations: ");
                JLabel iLabel = new JLabel("- ID:");
                JLabel idLabel = new JLabel(idTextField.getText());
                JLabel nLabel = new JLabel("- Name:");
                JLabel nameLabel = new JLabel(nameTextField.getText());
                JLabel aLabel = new JLabel("- Amount:");
                JLabel amountLabel = new JLabel(amountTextField.getText());
                JLabel pLabel = new JLabel("- Price:");
                JLabel priceLabel = new JLabel(priceTextField.getText());
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
                JButton cButton = new JButton("Cancel");
                JButton aButton = new JButton("Add");
                JPanel panel = new JPanel(new GridLayout(7, 2));
                panel.add(iLabel);
                panel.add(idLabel);
                panel.add(nLabel);
                panel.add(nameLabel);
                panel.add(aLabel);
                panel.add(amountLabel);
                panel.add(pLabel);
                panel.add(priceLabel);
                panel.add(sLabel);
                panel.add(sTextField);
                panel.add(impLabel);
                panel.add(impTextField);
                panel.add(cButton);
                panel.add(aButton);
                frame.add(header);
                frame.add(panel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setAlwaysOnTop(true);
                frame.setResizable(false);
                frame.setSize(new Dimension(600, 300));
                frame.setVisible(true);

                cButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
                aButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Frame frame = new Frame();
                        for (Frame f : JFrame.getFrames()) {
                            if (f.isAlwaysOnTop())
                                frame = f;
                        }
                        String iD = idTextField.getText();
                        String name = nameTextField.getText();
                        int amount = Integer.parseInt(amountTextField.getText());
                        int price = Integer.parseInt(priceTextField.getText());
                        String supplier = sTextField.getText();
                        Date importDate = Date.valueOf(impTextField.getText());
                        if (supplier.isEmpty()) {

                        }
                        Pottery pottery = new Pottery(iD, name, amount, price, importDate, supplier);
                        potteryService = new PotteryServiceImpl();
                        JOptionPane.showMessageDialog(frame,
                                "Added " + potteryService.insert(pottery) + " Pottery product into database",
                                "Done!",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                });
            }
        });

        foodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Frame frame : JFrame.getFrames()) {
                    if (frame.isAlwaysOnTop())
                        frame.dispose();
                }
                JFrame frame = new JFrame("Enter informations:");
                JLabel header = new JLabel("Appliance product's informations: ");
                JLabel iLabel = new JLabel("- ID:");
                JLabel idLabel = new JLabel(idTextField.getText());
                JLabel nLabel = new JLabel("- Name:");
                JLabel nameLabel = new JLabel(nameTextField.getText());
                JLabel aLabel = new JLabel("- Amount:");
                JLabel amountLabel = new JLabel(amountTextField.getText());
                JLabel pLabel = new JLabel("- Price:");
                JLabel priceLabel = new JLabel(priceTextField.getText());
                JLabel sLabel = new JLabel("- Supplier:");
                JTextField sTextField = new JTextField();
                JLabel mFGLabel = new JLabel("- MFG:");
                JLabel eXPLabel = new JLabel("- eXP");
                MaskFormatter formatter = new MaskFormatter();
                try {
                    formatter = new MaskFormatter("####-##-##");
                } catch (ParseException e1) {
                }
                formatter.setAllowsInvalid(false);
                formatter.setOverwriteMode(true);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                formatter.setPlaceholder(format.format(new Date(System.currentTimeMillis())));
                JFormattedTextField mFGTextField = new JFormattedTextField(formatter);
                JFormattedTextField eXPTextField = new JFormattedTextField(formatter);
                mFGTextField.setColumns(8);
                eXPTextField.setColumns(8);
                JButton cButton = new JButton("Cancel");
                JButton aButton = new JButton("Add");
                JPanel panel = new JPanel(new GridLayout(7, 2));
                panel.add(iLabel);
                panel.add(idLabel);
                panel.add(nLabel);
                panel.add(nameLabel);
                panel.add(aLabel);
                panel.add(amountLabel);
                panel.add(pLabel);
                panel.add(priceLabel);
                panel.add(sLabel);
                panel.add(sTextField);
                panel.add(mFGLabel);
                panel.add(mFGTextField);
                panel.add(eXPLabel);
                panel.add(eXPTextField);
                panel.add(cButton);
                panel.add(aButton);
                frame.add(header);
                frame.add(panel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setAlwaysOnTop(true);
                frame.setResizable(false);
                frame.setSize(new Dimension(600, 300));
                frame.setVisible(true);

                cButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
                aButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Frame frame = new Frame();
                        for (Frame f : JFrame.getFrames()) {
                            if (f.isAlwaysOnTop())
                                frame = f;
                        }
                        String iD = idTextField.getText();
                        String name = nameTextField.getText();
                        int amount = Integer.parseInt(amountTextField.getText());
                        int price = Integer.parseInt(priceTextField.getText());
                        String supplier = sTextField.getText();
                        Date mFG = Date.valueOf(mFGTextField.getText());
                        Date eXP = Date.valueOf(eXPTextField.getText());
                        if (supplier.isEmpty()) {

                        }
                        Food food = new Food(iD, name, amount, price, mFG, eXP, supplier);
                        foodService = new FoodServiceImpl();
                        JOptionPane.showMessageDialog(frame,
                                "Added " + foodService.insert(food) + " Food product into database",
                                "Done!",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                });
            }
        });
    }

    private void editProduct() {
    }

    private void deleteProduct() {

    }

    private void getSumProduct() {

    }

    private void expiredProduct() {

    }

}
