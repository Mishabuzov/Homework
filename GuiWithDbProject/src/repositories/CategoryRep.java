package repositories;

import db.Db;
import entities.Category;
import exceptions.DatabaseException;
import exceptions.InvalidInputException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail on 19.11.2015.
 */
public class CategoryRep {

    public static Integer getIdByName(String name) throws DatabaseException {
        if ("none".equals(name)) {
            return null;
        }
        Connection connection = Db.connect();
        try {
            ResultSet r = connection.createStatement().executeQuery("select id from category where name = " + "'" + name + "'");
            r.next();
            return r.getInt("id");
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    public static String getNameById(int id) throws DatabaseException {
        if (id == 0) {
            return "none";
        }
        Connection connection = Db.connect();
        try {
            ResultSet r = connection.createStatement().executeQuery("select name from category where id = " + "'" + id + "'");
            r.next();
            return r.getString("name");
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    public static void add(Category category) throws InvalidInputException, DatabaseException {
        try {
            check(category);
            PreparedStatement p = Db.connect().prepareStatement("insert into category (name, parent_id) values(?,?);");
            p.setString(1, category.getName());
            if (category.getParent() == null) {
                p.setNull(2, Types.INTEGER);
            } else {
                p.setInt(2, category.getParent());
            }
            p.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

    public static void check(Category category) throws InvalidInputException {
        if ("".equals(category.getName())) {
            throw new InvalidInputException("You must field Name!");
        }
    }

    public static List<Category> getAll() throws DatabaseException {
        try {
            ResultSet res = Db.connect().createStatement().executeQuery("select * from category;");

            List<Category> categories = new ArrayList<>();
            while (res.next()) {
                Integer parent = res.getObject(3) == null ? null : (Integer) res.getObject(3);
                categories.add(new Category(res.getString(2), parent));
            }

            return categories;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DatabaseException();
        }
    }

    public static String[] getAllCategoriesNames() throws DatabaseException {
        List<Category> info = getAll();
        String[] res = new String[info.size() + 1];
        res[0] = "none";
        int i = 1;
        for (Category c : info) {
            res[i] = c.getName();
            i++;
        }
        return res;
    }


}
