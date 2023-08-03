package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.model.Appliance;
import domain.model.Food;
import domain.model.Pottery;
import domain.model.Product;

public abstract class ProductJdbcGateway implements Gateway<Product> {

    Connection connection;

    public ProductJdbcGateway() {
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
    public abstract int insert(Product t);

    @Override
    public abstract int update(Product t);

    @Override
    public int delete(Product t) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from products " + "WHERE ID='" + t.getID() + "'";
            result = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        t.notifySubscribers();
        return result;
    }

    @Override
    public List<Product> search(String condition) {
        List<Product> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from products WHERE " + condition;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                int amount = rs.getInt("Amount");
                int price = rs.getInt("Price");
                String category = rs.getString("Category");
                String supplier = rs.getString("Supplier");
                Date importDate = null;
                Date mFG = null;
                Date eXP = null;
                try {
                    importDate = rs.getDate("ImportDate");
                } catch (SQLException e) {
                }
                try {
                    mFG = rs.getDate("MFG");
                    eXP = rs.getDate("EXP");
                } catch (SQLException e) {
                }
                int warrantyMonths = rs.getInt("WarrantyMonths");
                double capacity = rs.getDouble("Capacity");

                switch (category) {
                    case "Appliance": {
                        result.add(new Appliance(id, name, amount, price, warrantyMonths, capacity));
                    }
                        break;
                    case "Food": {
                        result.add(new Food(id, name, amount, price, mFG, eXP, supplier));
                    }
                        break;
                    case "Pottery": {
                        result.add(new Pottery(id, name, amount, price, importDate, supplier));
                    }
                        break;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Product> selectAll() {
        List<Product> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from products";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                int amount = rs.getInt("Amount");
                int price = rs.getInt("Price");
                String category = rs.getString("Category");
                String supplier = rs.getString("Supplier");
                Date importDate = null;
                Date mFG = null;
                Date eXP = null;
                try {
                    importDate = rs.getDate("ImportDate");
                } catch (SQLException e) {
                }
                try {
                    mFG = rs.getDate("MFG");
                    eXP = rs.getDate("EXP");
                } catch (SQLException e) {
                }
                int warrantyMonths = rs.getInt("WarrantyMonths");
                double capacity = rs.getDouble("Capacity");

                switch (category) {
                    case "Appliance": {
                        result.add(new Appliance(id, name, amount, price, warrantyMonths, capacity));
                    }
                        break;
                    case "Food": {
                        result.add(new Food(id, name, amount, price, mFG, eXP, supplier));
                    }
                        break;
                    case "Pottery": {
                        result.add(new Pottery(id, name, amount, price, importDate, supplier));
                    }
                        break;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
