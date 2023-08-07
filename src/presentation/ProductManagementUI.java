package presentation;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import domain.model.*;

public class ProductManagementUI extends JFrame implements Subscriber {
    private Product modelRemote;
    private ProductManagementController controllerRemote;
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JFormattedTextField idTextField;
    private JTextField nameTextField;
    private JTextField amountTextField;
    private JTextField priceTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton searchButton;
    private JButton estimateButton;
    private JButton expireButton;
    // Điều kiện của những product được hiện lên table
    private String currentCondition;

    public ProductManagementUI() {
        setTitle("Product Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(850, 400));
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Mọi cell đều không được phép edit trực tiếp
                return false;
            }
        };
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
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);
        // Tạo formatter để chỉ có thể nhập id với dạng String 8 chữ số
        MaskFormatter formatter = new MaskFormatter();
        try {
            formatter = new MaskFormatter("########");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        idTextField = new JFormattedTextField(formatter);
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
        UIManager.put("Button.background", new Color(210, 250, 230));
        addButton.setBackground(new Color(200, 250, 230));
        deleteButton.setBackground(new Color(200, 250, 230));
        editButton.setBackground(new Color(200, 250, 230));
        searchButton.setBackground(new Color(200, 250, 230));
        estimateButton.setBackground(new Color(200, 250, 230));
        expireButton.setBackground(new Color(200, 250, 230));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(210, 250, 230));
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(new Color(210, 250, 230));
                return button;
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbDarkShadowColor = Color.black;
                this.trackColor = Color.white;
                this.thumbColor = new Color(210, 250, 230);
            }
        });
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
        add(panel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setLocation(258, 184);

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
        // Chọn textfield khác mà id chưa hợp lệ thì báo lỗi => đem con trỏ về id
        FocusListener idCorrectFormat = new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (idTextField.getText().trim().isEmpty()) {
                    idTextField.requestFocus();
                    JOptionPane.showMessageDialog(null, "ID can only be 8 digits.", "Please re-enter the ID",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        };
        nameTextField.addFocusListener(idCorrectFormat);
        amountTextField.addFocusListener(idCorrectFormat);
        priceTextField.addFocusListener(idCorrectFormat);
    }

    public Object[] getSelectedRow() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] objects = new Object[12];
            for (int i = 0; i < 12; i++) {
                objects[i] = tableModel.getValueAt(selectedRow, i);
            }
            return objects;
        }
        return null;
    }

    public void clearInputFields() {
        idTextField.setText("");
        nameTextField.setText("");
        amountTextField.setText("");
        priceTextField.setText("");
    }

    public JTextField[] getAllTextFields() {
        JTextField[] textFields = { idTextField, nameTextField, amountTextField, priceTextField };
        return textFields;
    }

    public void setCondition(String currentCondition) {
        this.currentCondition = currentCondition;
        update();
    }

    public String getCondition() {
        return this.currentCondition;
    }

    public ProductManagementController getControllerRemote() {
        return controllerRemote;
    }

    @Override
    public void update() {
        // Xóa tất cả hàng trên table
        tableModel.setRowCount(0);
        // Gán giá trị của các product trên database vào List products
        List<Product> products = controllerRemote.getProducts();
        // Nếu products rỗng thì báo không có product nào phù hợp với điều kiện
        // và set điều kiện = null
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There is no product with such property.", "Product(s) not found",
                    JOptionPane.WARNING_MESSAGE);
            setCondition(null);
        }
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
        if (products.size() > 1) {
            int sumAmount = 0;
            int sumPrice = 0;
            int sumVAT = 0;
            // Lấy giá trị tổng của các cột amount, price và VAT
            for (Product product : products) {
                sumAmount += (int) product.getAmount();
                sumPrice += (int) product.getPrice();
                sumVAT += (int) product.calVAT();
            }
            // Gán giá trị vào Array Object
            Object[] sumRow = { "TOTAL", "", sumAmount, sumPrice, sumVAT, "", "", "", "", "", "", "" };
            // Thêm Array Object ở trên vào tableModel
            tableModel.addRow(sumRow);
        }
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                c.setBackground(row % 2 == 0 ? new Color(200, 250, 230) : new Color(250, 250, 250));
                c.setForeground(new Color(0, 0, 0));
                if (isSelected)
                    setBackground(new Color(185, 215, 255));
                if (row == tableModel.getRowCount() - 1 && tableModel.getRowCount() > 1) {
                    c.setBackground(new Color(185, 185, 255));
                }
                return c;
            }
        });
        // Nếu có điều kiện cho các sản phẩm đang hiện (Đang không hiện tất cả sản phẩm
        // trong db)
        if (currentCondition != null)
            // Thì nút search biến thành back
            searchButton.setText("Back");
        else
            // Nếu ko có điều kiện thì nút back lại biến thành search
            searchButton.setText("Search");
    }

}
