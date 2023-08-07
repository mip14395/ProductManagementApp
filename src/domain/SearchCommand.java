package domain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.model.Product;
import presentation.ProductManagementUI;

public class SearchCommand implements Command {
    private Product modelRemote;
    private String condition;

    public SearchCommand(Product modelRemote) {
        this.modelRemote = modelRemote;
    }

    @Override
    public void execute() {
        condition = null;
        JFrame frame = new JFrame("Choose property to search by");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JButton idButton = new JButton("Search by ID");
        JButton nameButton = new JButton("Search by name");
        JButton amountButton = new JButton("Search by amount range");
        JButton priceButton = new JButton("Search by price range");
        JButton supplierButton = new JButton("Search by supplier");
        JButton saleGradeButton = new JButton("Search by sale grading");
        panel.add(idButton);
        panel.add(nameButton);
        panel.add(amountButton);
        panel.add(priceButton);
        panel.add(supplierButton);
        panel.add(saleGradeButton);
        frame.add(panel);
        frame.setSize(new Dimension(400, 180));
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocation(483, 294);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if (e.getSource() == idButton) {
                    String iD = "";
                    try {
                        iD = JOptionPane.showInputDialog(null, "Enter ID of the product that you want to search:",
                                "Enter ID",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (iD.isEmpty())
                            return;
                    } catch (NullPointerException ex) {
                        return;
                    }
                    condition = "ID= '" + iD + "'";
                    ((ProductManagementUI) modelRemote.getSubscribers().get(0)).setCondition(condition);
                    frame.dispose();
                } else if (e.getSource() == nameButton) {
                    String name = "";
                    try {
                        name = JOptionPane.showInputDialog(null, "Enter Name of the product that you want to search:",
                                "Enter name",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (name.isEmpty())
                            return;
                    } catch (NullPointerException ex) {
                        return;
                    }
                    condition = "Name LIKE '%" + name + "%'";
                    frame.dispose();
                    ((ProductManagementUI) modelRemote.getSubscribers().get(0)).setCondition(condition);
                } else if (e.getSource() == supplierButton) {
                    String supplier = "";
                    try {
                        supplier = JOptionPane.showInputDialog(null,
                                "Enter Supplier of the product that you want to search:",
                                "Enter supplier",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (supplier.isEmpty())
                            return;
                    } catch (NullPointerException ex) {
                        return;
                    }
                    condition = "Supplier LIKE '%" + supplier + "%'";
                    frame.dispose();
                    ((ProductManagementUI) modelRemote.getSubscribers().get(0)).setCondition(condition);
                } else if (e.getSource() == saleGradeButton) {
                    JFrame gradeFrame = new JFrame("Choose sale grading");
                    JButton bestSeller = new JButton("Best-Seller");
                    JButton average = new JButton("Average");
                    JButton obsolete = new JButton("Obsolete");
                    bestSeller.setBackground(new Color(200, 250, 230));
                    average.setBackground(new Color(200, 250, 230));
                    obsolete.setBackground(new Color(200, 250, 230));
                    JPanel gradePanel = new JPanel(new GridLayout(3, 1));
                    gradePanel.add(bestSeller);
                    gradePanel.add(average);
                    gradePanel.add(obsolete);
                    gradeFrame.add(gradePanel);
                    gradeFrame.setSize(new Dimension(200, 150));
                    gradeFrame.setVisible(true);
                    gradeFrame.setLocation(583, 309);
                    ActionListener actionListener = new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            gradeFrame.dispose();
                            List<Product> grade = new ArrayList<>();
                            for (Product product : ((ProductManagementUI) modelRemote.getSubscribers().get(0))
                                    .getControllerRemote().getProducts()) {
                                if (((JButton) e.getSource()).getText() == product.saleGrading()) {
                                    grade.add(product);
                                }
                            }
                            for (Product product : grade) {
                                if (grade.indexOf(product) > 0)
                                    condition += "OR ID= '" + product.getID() + "'";
                                else
                                    condition = "ID= '" + product.getID() + "'";
                            }
                            ((ProductManagementUI) modelRemote.getSubscribers().get(0)).setCondition(condition);
                        }

                    };
                    bestSeller.addActionListener(actionListener);
                    obsolete.addActionListener(actionListener);
                    average.addActionListener(actionListener);
                } else {
                    JFrame rangeFrame = new JFrame();
                    String title = "Search by ";
                    String by = "";
                    String Label1 = "Minimum ";
                    String Label2 = "Maximum ";
                    JPanel rangePanel = new JPanel(new GridLayout(3, 2));
                    JTextField minTextField = new JTextField();
                    JTextField maxTextField = new JTextField();
                    JButton searchButton = new JButton("Search");
                    JButton cancelButton = new JButton("Cancel");
                    rangeFrame.add(rangePanel);
                    rangeFrame.setSize(new Dimension(500, 150));
                    rangeFrame.setResizable(false);
                    if (e.getSource() == amountButton) {
                        by = "Amount";
                    } else {
                        by = "Price";
                    }
                    title += by;
                    Label1 += by + ":";
                    Label2 += by + ":";
                    final String value = by;
                    rangeFrame.setTitle(title);
                    rangePanel.add(new JLabel(Label1));
                    rangePanel.add(minTextField);
                    rangePanel.add(new JLabel(Label2));
                    rangePanel.add(maxTextField);
                    rangePanel.add(cancelButton);
                    rangePanel.add(searchButton);
                    cancelButton.setBackground(new Color(200, 250, 230));
                    cancelButton.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            rangeFrame.dispose();
                        }

                    });
                    searchButton.setBackground(new Color(200, 250, 230));
                    searchButton.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                            try {
                                condition = value + ">= " + Integer.parseInt(minTextField.getText()) + " AND " + value
                                        + "<= "
                                        + Integer.parseInt(maxTextField.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, value + " can only be integer. ",
                                        "INVALID INPUT(s)", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            if (Integer.parseInt(minTextField.getText()) > Integer.parseInt(maxTextField.getText())) {
                                JOptionPane.showMessageDialog(null,
                                        "Minimum " + value + " cannot be greater than Maximum " + value + ".",
                                        "INVALID INPUT(s)", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            rangeFrame.dispose();
                            ((ProductManagementUI) modelRemote.getSubscribers().get(0)).setCondition(condition);
                        }
                    });
                    rangeFrame.setVisible(true);
                    rangeFrame.setLocation(433, 309);
                }
            }

        };
        idButton.setBackground(new Color(200, 250, 230));
        nameButton.setBackground(new Color(200, 250, 230));
        amountButton.setBackground(new Color(200, 250, 230));
        priceButton.setBackground(new Color(200, 250, 230));
        saleGradeButton.setBackground(new Color(200, 250, 230));
        supplierButton.setBackground(new Color(200, 250, 230));
        idButton.addActionListener(actionListener);
        nameButton.addActionListener(actionListener);
        amountButton.addActionListener(actionListener);
        priceButton.addActionListener(actionListener);
        saleGradeButton.addActionListener(actionListener);
        supplierButton.addActionListener(actionListener);
    }
}
