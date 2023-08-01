package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.model.Food;

public class FoodJdbcGateway implements Gateway<Food> {

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
    public int insert(Food t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO products (ID, Name, Amount, Price, Category, Supplier, MFG, EXP)"
                    + " VALUES ('" + t.getID() + "', '" + t.getName() + "', " + t.getAmount() + ", " + t.getPrice()
                    + ", 'Food', '" + t.getSupplier() + "', '" + t.getmFG() + "', '" + t.geteXP() + "')";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Food t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE products " + " SET " + "Name='" + t.getName() + "', Amount=" + t.getAmount()
                    + ", Price=" + t.getPrice() + ", Supplier= '" + t.getSupplier() + "', MFG="
                    + t.getmFG() + ", EXP=" + t.geteXP() + " WHERE ID='" + t.getID() + "'";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Food t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from products " + "WHERE ID='" + t.getID() + "'";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Food> selectAll() {
        List<Food> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from products WHERE Category='Food'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                int amount = rs.getInt("Amount");
                int price = rs.getInt("Price");
                Date mFG = rs.getDate("MFG");
                Date eXP = rs.getDate("EXP");
                String supplier = rs.getString("Supplier");

                result.add(new Food(id, name, amount, price, mFG, eXP, supplier));
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
