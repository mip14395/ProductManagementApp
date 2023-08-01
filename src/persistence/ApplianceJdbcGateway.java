package persistence;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import domain.model.Appliance;

public class ApplianceJdbcGateway implements Gateway<Appliance> {

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
    public int insert(Appliance t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO products (ID, Name, Amount, Price, Category, WarrantyMonths, Capacity)"
                    + " VALUES ('" + t.getID() + "', '" + t.getName() + "', " + t.getAmount() + ", " + t.getPrice()
                    + ", 'Appliance', " + t.getWarrantyMonths() + ", " + t.getCapacity() + ")";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Appliance t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE products " + " SET " + "Name='" + t.getName() + "', Amount=" + t.getAmount()
                    + ", Price=" + t.getPrice() + ", WarrantyMonths= " + t.getWarrantyMonths() + ", Capacity="
                    + t.getCapacity() + " WHERE ID='" + t.getID() + "'";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Appliance t) {
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
    public List<Appliance> selectAll() {
        List<Appliance> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from products WHERE Category='Appliance'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                int amount = rs.getInt("Amount");
                int price = rs.getInt("Price");
                int warrantyMonth = rs.getInt("WarrantyMonth");
                double capacity = rs.getDouble("Capacity");

                result.add(new Appliance(id, name, amount, price, warrantyMonth, capacity));
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
