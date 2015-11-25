package repositories;

import entities.Category;
import exceptions.DatabaseException;
import exceptions.InvalidInputException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Mikhail on 19.11.2015.
 */
public class CategoryRep {

    private static String CATEGORIES_URL = "http://localhost:3000/categories";
    private static List<Category> categoriesRes;

    public static int getIdByName(String name) throws DatabaseException {
        if ("none".equals(name)) {
            return 0;
        }
        for (Category c : categoriesRes) {
            if(c.getName().equals(name)){
                return c.getId();
            }
        }
        throw new DatabaseException();
    }

    public static String getNameById(int id) throws DatabaseException {
        if (id == 0) {
            return "none";
        }
        for (Category c : categoriesRes) {
            if(c.getId() == id){
                return c.getName();
            }
        }
        throw new DatabaseException();
    }

    public static void add(Category category) throws InvalidInputException, DatabaseException {
        check(category);
        try {
            URL obj = new URL(CATEGORIES_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("id=").append(category.getId()).append("&");
            urlParameters.append("name=").append(URLEncoder.encode(category.getName(), "UTF8")).append("&");
            urlParameters.append("parent=").append(category.getParent());

            //sending
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(String.valueOf(urlParameters));
            wr.flush();
            wr.close();

            //response
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + CATEGORIES_URL);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public static void check(Category category) throws InvalidInputException {
        if ("".equals(category.getName())) {
            throw new InvalidInputException("You must field Name!");
        }
    }

    private static String readAll() throws DatabaseException {
        StringBuilder data = new StringBuilder();
        try {
            HttpURLConnection con = (HttpURLConnection) ((new URL(CATEGORIES_URL).openConnection()));
            con.setRequestMethod("GET");

            con.setDoInput(true);
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((s = in.readLine()) != null) {
                data.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return data.toString();
    }

    public static List<Category> readCategoriesFromJson() throws DatabaseException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = readAll();
            categoriesRes = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Category.class));
            return categoriesRes;
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public static String[] getAllCategoriesNames() throws DatabaseException {
        String[] res = new String[categoriesRes.size() + 1];
        res[0] = "none";
        int i = 1;
        for (Category c : categoriesRes) {
            res[i] = c.getName();
            i++;
        }
        return res;
    }

    public static int getCategoriesId() throws DatabaseException {
        if (categoriesRes.size() != 0) {
            return categoriesRes.size() + 1;
        } else {
            return 1;
        }
    }


}
