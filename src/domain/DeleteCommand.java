package domain;

import javax.swing.JOptionPane;

import domain.model.*;
import persistence.PotteryJdbcGateway;

public class DeleteCommand implements Command {
    private Product modelRemote;

    public DeleteCommand(Product modelRemote) {
        this.modelRemote = modelRemote;
    }

    @Override
    public void execute() {
        JOptionPane.showMessageDialog(null,
                "Deleted " + new ProductServiceImpl(new PotteryJdbcGateway()).delete(modelRemote)
                        + " product from database",
                "Deleted", JOptionPane.INFORMATION_MESSAGE);
    }

}
