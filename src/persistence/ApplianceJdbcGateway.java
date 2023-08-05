package persistence;

import java.sql.*;

import domain.model.Appliance;
import domain.model.Product;

public class ApplianceJdbcGateway extends ProductJdbcGateway {

    private Connection connection;

    public ApplianceJdbcGateway() {
        String dbUrl = "jdbc:mySQL://localhost:3306/products";
        String username = "root";
        String password = "";
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int insert(Product t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO products (ID, Name, Amount, Price, Category, WarrantyMonths, Capacity)"
                    + " VALUES ('" + t.getID() + "', '" + t.getName() + "', " + t.getAmount() + ", " + t.getPrice()
                    + ", 'Appliance', " + ((Appliance) t).getWarrantyMonths() + ", " + ((Appliance) t).getCapacity()
                    + ")";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        t.notifySubscribers();
        return result;
    }

    @Override
    public int update(Product t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE products " + " SET " + "Name='" + t.getName() + "', Amount=" + t.getAmount()
                    + ", Price=" + t.getPrice() + ", WarrantyMonths= " + ((Appliance) t).getWarrantyMonths()
                    + ", Capacity="
                    + ((Appliance) t).getCapacity() + " WHERE ID='" + t.getID() + "'";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        t.notifySubscribers();
        return result;
    }

}
