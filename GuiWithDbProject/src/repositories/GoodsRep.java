package repositories;

import db.Db;
import entities.Product;
import exceptions.DatabaseException;
import exceptions.InvalidInputException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail on 19.11.2015.
 */
public class GoodsRep {
    public static void add(Product product) throws DatabaseException, InvalidInputException {
        check(product);
        try {
            String q = "insert into goods(name, price, weight, country, category_id) values(?,?,?,?,?);";
            PreparedStatement p = Db.connect().prepareStatement(q);
            p.setString(1, product.getName());
            p.setDouble(2, product.getPrice());
            p.setDouble(3, product.getWeight());
            p.setString(4, product.getCountry());
            if (product.getCategoryId() == null) {
                p.setNull(5, Types.INTEGER);
            } else {
                p.setInt(5, product.getCategoryId());
            }
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public static void check(Product product) throws InvalidInputException {

        if ("".equals(product.getName()) || "".equals(product.getCountry())) {
            throw new InvalidInputException("You must field Name!");
        }
    }

    public static List<Product> getAllGoods() throws DatabaseException {

        List<Product> goods = new ArrayList<>();

        try {
            ResultSet r = Db.connect().createStatement().executeQuery("select * from goods;");
            while (r.next()) {
                goods.add(new Product(
                                r.getString(2),
                                r.getDouble(3),
                                r.getDouble(4),
                                r.getString(5),
                                r.getInt(6)
                        )
                );
            }
            return goods;
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

    public static String[][] getGoodsTable() throws DatabaseException {
        List<Product> res = getAllGoods();
        String[][] data = new String[res.size()][5];
        int i = 0;
        for (Product product : res) {
            data[i][0] = product.getName();
            data[i][1] = String.valueOf(product.getPrice());
            data[i][2] = String.valueOf(product.getWeight());
            data[i][3] = product.getCountry();
            data[i][4] = CategoryRep.getNameById(product.getCategoryId());
            i++;
        }
        return data;
    }

}
