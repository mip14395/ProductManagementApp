package domain;

import javax.swing.JOptionPane;
import domain.model.Product;
import presentation.ProductManagementUI;

public class SearchCommand implements Command {
    private Product modelRemote;

    public SearchCommand(Product modelRemote) {
        this.modelRemote = modelRemote;
    }

    @Override
    public void execute() {
        String iD = "";
        try {
            iD = JOptionPane.showInputDialog(null, "Enter ID of the product that you want to search:",
                    "Enter ID",
                    JOptionPane.OK_CANCEL_OPTION);
            if (iD.isEmpty())
                return;
        } catch (NullPointerException e) {
            return;
        }
        ((ProductManagementUI) modelRemote.getSubscribers().get(0)).setCondition("ID='" + iD + "'");
    }

}
