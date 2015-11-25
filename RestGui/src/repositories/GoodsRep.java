package repositories;

import entities.Product;
import exceptions.DatabaseException;
import exceptions.InvalidInputException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Created by Mikhail on 19.11.2015.
 */
public class GoodsRep {

    private static List<Product> goodsRes;
    private static String GOODS_URL = "http://localhost:3000/goods";

    public static void add(Product product) throws DatabaseException, InvalidInputException {
        check(product);
        try {
            URL obj = new URL(GOODS_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("id=").append(product.getId()).append("&");
            urlParameters.append("name=").append(URLEncoder.encode(product.getName(), "UTF8")).append("&");
            urlParameters.append("price=").append(product.getPrice()).append("&");
            urlParameters.append("weight=").append(product.getWeight()).append("&");
            urlParameters.append("country=").append(product.getCountry()).append("&");
            urlParameters.append("categoryId=").append(URLEncoder.encode(String.valueOf(product.getCategoryId()), "UTF8"));

            //sending
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(String.valueOf(urlParameters));
            wr.flush();
            wr.close();

            //response
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + GOODS_URL);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public static void check(Product product) throws InvalidInputException {

        if ("".equals(product.getName()) || "".equals(product.getCountry())) {
            throw new InvalidInputException("You must field Name!");
        }
    }

    private static String readAll() throws DatabaseException {
        StringBuilder data = new StringBuilder();
        try {
            HttpURLConnection con = (HttpURLConnection) ((new URL(GOODS_URL).openConnection()));
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

    public static List<Product> readGoodsFromJson() throws DatabaseException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = readAll();
            goodsRes = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            return goodsRes;
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public static String[][] getGoodsTable() throws DatabaseException {
        String[][] data = new String[goodsRes.size()][5];
        int i = 0;
        for (Product product : goodsRes) {
            data[i][0] = product.getName();
            data[i][1] = String.valueOf(product.getPrice());
            data[i][2] = String.valueOf(product.getWeight());
            data[i][3] = product.getCountry();
            data[i][4] = CategoryRep.getNameById(product.getCategoryId());
            i++;
        }
        return data;
    }

    public static int getGoodsId() throws DatabaseException {
        if (goodsRes.size() != 0) {
            return goodsRes.size() + 1;
        } else {
            return 1;
        }
    }

}
