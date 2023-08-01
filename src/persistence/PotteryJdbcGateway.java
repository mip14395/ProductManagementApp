package persistence;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import domain.model.Pottery;

public class PotteryJdbcGateway implements Gateway<Pottery> {

    private Connection connection;

    public PotteryJdbcGateway() {
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
    public int insert(Pottery t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO products (ID, Name, Amount, Price, Category, Supplier, ImportDate)"
                    + " VALUES ('" + t.getID() + "', '" + t.getName() + "', " + t.getAmount() + ", " + t.getPrice()
                    + ", 'Pottery', '" + t.getSupplier() + "', '" + t.getImportDate() + "')";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Pottery t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE products " + " SET " + "Name='" + t.getName() + "', Amount=" + t.getAmount()
                    + ", Price=" + t.getPrice() + ", Supplier= '" + t.getSupplier() + "', ImportDate="
                    + t.getImportDate() + " WHERE ID='" + t.getID() + "'";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Pottery t) {
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
    public List<Pottery> selectAll() {
        List<Pottery> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from products WHERE Category='Pottery'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                int amount = rs.getInt("Amount");
                int price = rs.getInt("Price");
                String supplier = rs.getString("Supplier");
                Date importDate = rs.getDate("ImportDate");

                result.add(new Pottery(id, name, amount, price, importDate, supplier));
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
