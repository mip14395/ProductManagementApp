package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.ProductServiceImpl;
import domain.model.*;

public class ProductManagementUI extends JFrame implements Subscriber {
    private ProductServiceImpl productService;
    private Product modelRemote;
    private ProductManagementController controllerRemote;
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField amountTextField;
    private JTextField priceTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton searchButton;
    private JButton estimateButton;
    private JButton expireButton;
    private String currentCondition;

    public ProductManagementUI() {
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
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);
        idTextField = new JFormattedTextField();
        idTextField.setColumns(8);
        nameTextField = new JTextField();
        amountTextField = new JTextField();
        priceTextField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(7, 2));
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        searchButton = new JButton("Search");
        estimateButton = new JButton("Show estimates");
        expireButton = new JButton("Show products a week from expiration dates");
        panel.add(new JLabel("- ID:"));
        panel.add(idTextField);
        panel.add(new JLabel("- Name:"));
        panel.add(nameTextField);
        panel.add(new JLabel("- Amount:"));
        panel.add(amountTextField);
        panel.add(new JLabel("- Price:"));
        panel.add(priceTextField);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(editButton);
        panel.add(searchButton);
        panel.add(estimateButton);
        panel.add(expireButton);
        add(panel);
        pack();
        setVisible(true);

        modelRemote = new Product() {
            @Override
            public int calVAT() {
                throw new UnsupportedOperationException("Unimplemented method 'calVAT'");
            }

            @Override
            public String saleGrading() {
                throw new UnsupportedOperationException("Unimplemented method 'saleGrading'");
            }
        };
        modelRemote.addSubscriber(this);
        controllerRemote = new ProductManagementController(modelRemote, this);
        addButton.addActionListener(controllerRemote);
        deleteButton.addActionListener(controllerRemote);
        editButton.addActionListener(controllerRemote);
        searchButton.addActionListener(controllerRemote);
        estimateButton.addActionListener(controllerRemote);
        expireButton.addActionListener(controllerRemote);
        currentCondition = null;
        update();
    }

    public Product getSelectedRow() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String iD = (String) productTable.getValueAt(selectedRow, 0);
            String name = (String) productTable.getValueAt(selectedRow, 1);
            int amount = (int) productTable.getValueAt(selectedRow, 2);
            int price = (int) productTable.getValueAt(selectedRow, 3);
            if (!((String) productTable.getValueAt(selectedRow, 6)).equals("")) {
                String supplier = (String) productTable.getValueAt(selectedRow, 6);
                if (!(productTable.getValueAt(selectedRow, 7).toString()).equals("")) {
                    Date importDate = (Date) productTable.getValueAt(selectedRow, 7);
                    return new Pottery(iD, name, amount, price, importDate, supplier);
                }
                Date mFG = (Date) productTable.getValueAt(selectedRow, 8);
                Date eXP = (Date) productTable.getValueAt(selectedRow, 9);
                return new Food(iD, name, amount, price, mFG, eXP, supplier);
            }
            int warrantyMonths = (int) productTable.getValueAt(selectedRow, 10);
            double capacity = (double) productTable.getValueAt(selectedRow, 11);
            return new Appliance(iD, name, amount, price, warrantyMonths, capacity);
        }
        return null;
    }

    public void clearInputFields() {
        idTextField.setText("");
        nameTextField.setText("");
        amountTextField.setText("");
        priceTextField.setText("");
    }

    public Product getAllTextFields() {
        String iD = "";
        String name = "";
        int amount = 0;
        int price = 0;
        try {
            iD = idTextField.getText();
            name = nameTextField.getText();
            if (iD.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter ID and Name",
                        "INVALID INPUT(s)",
                        JOptionPane.ERROR_MESSAGE);
                return modelRemote;
            }
            amount = Integer.parseInt(amountTextField.getText());
            price = Integer.parseInt(priceTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount and Price can only be integers", "INVALID INPUT(s)",
                    JOptionPane.ERROR_MESSAGE);
            return modelRemote;
        }
        return (Product) new Pottery(iD, name, amount, price, new java.sql.Date(0), "null");
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        if (currentCondition == null) {
            productService = new ProductServiceImpl();
            products = productService.selectAll();
        } else {
            productService = new ProductServiceImpl();
            products = productService.search(currentCondition);
        }
        return products;
    }

    public void setCondition(String currentCondition) {
        this.currentCondition = currentCondition;
        update();
    }

    @Override
    public void update() {
        tableModel.setRowCount(0);
        List<Product> products = getProducts();
        for (Product product : products) {
            if (product instanceof Appliance) {
                Object[] rowData = { product.getID(), product.getName(), product.getAmount(), product.getPrice(),
                        product.calVAT(), product.saleGrading(), "", "", "", "",
                        ((Appliance) product).getWarrantyMonths(), ((Appliance) product).getCapacity() };
                tableModel.addRow(rowData);
            } else if (product instanceof Food) {
                Object[] rowData = { product.getID(), product.getName(), product.getAmount(), product.getPrice(),
                        product.calVAT(), product.saleGrading(), ((Food) product).getSupplier(), "",
                        ((Food) product).getmFG(), ((Food) product).geteXP(), "", "" };
                tableModel.addRow(rowData);
            } else {
                Object[] rowData = { product.getID(), product.getName(), product.getAmount(), product.getPrice(),
                        product.calVAT(), product.saleGrading(), ((Pottery) product).getSupplier(),
                        ((Pottery) product).getImportDate(), "", "", "", "" };
                tableModel.addRow(rowData);
            }
        }
        if (currentCondition != null)
            searchButton.setText("Back");
        else
            searchButton.setText("Search");
    }

}
