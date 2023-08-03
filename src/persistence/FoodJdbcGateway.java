package persistence;

import java.sql.*;

import domain.model.Product;
import domain.model.Food;

public class FoodJdbcGateway extends ProductJdbcGateway {

    private Connection connection;

    public FoodJdbcGateway() {
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
            String sql = "INSERT INTO products (ID, Name, Amount, Price, Category, Supplier, MFG, EXP)"
                    + " VALUES ('" + t.getID() + "', '" + t.getName() + "', " + t.getAmount() + ", " + t.getPrice()
                    + ", 'Food', '" + ((Food) t).getSupplier() + "', '" + ((Food) t).getmFG() + "', '"
                    + ((Food) t).geteXP() + "')";
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
                    + ", Price=" + t.getPrice() + ", Supplier= '" + ((Food) t).getSupplier() + "', MFG='"
                    + ((Food) t).getmFG() + "', EXP='" + ((Food) t).geteXP() + "' WHERE ID='" + t.getID() + "'";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        t.notifySubscribers();
        return result;
    }
}
