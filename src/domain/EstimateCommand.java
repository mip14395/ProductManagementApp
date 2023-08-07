package domain;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.model.Product;
import presentation.ProductManagementUI;

public class EstimateCommand implements Command {
    Product modelRemote;

    public EstimateCommand(Product modelRemote) {
        this.modelRemote = modelRemote;
    }

    @Override
    public void execute() {
        List<Product> pottery = new ProductServiceImpl().search("Category = 'Pottery'");
        List<Product> appliance = new ProductServiceImpl().search("Category = 'Appliance'");
        List<Product> food = new ProductServiceImpl().search("Category = 'Food'");
        JFrame frame = new JFrame("Estimates");
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 10));
        JButton potteryButton = new JButton(String.valueOf(pottery.size()));
        JButton applianceButton = new JButton(String.valueOf(appliance.size()));
        JButton foodButton = new JButton(String.valueOf(food.size()));
        panel.add(new JLabel("Appliance:"));
        panel.add(applianceButton);
        panel.add(new JLabel("Food:"));
        panel.add(foodButton);
        panel.add(new JLabel("Pottery:"));
        panel.add(potteryButton);
        frame.add(panel);
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocation(533, 309);
        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String condition = "Category= ";
                if (e.getSource() == potteryButton)
                    condition += "'Pottery'";
                else if (e.getSource() == applianceButton)
                    condition += "'Appliance'";
                else
                    condition += "'Food'";
                frame.dispose();
                ((ProductManagementUI) modelRemote.getSubscribers().get(0)).setCondition(condition);
            }
        };
        potteryButton.addActionListener(actionListener);
        applianceButton.addActionListener(actionListener);
        foodButton.addActionListener(actionListener);
    }

}
