package org.aquarngd.buyistic.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@RestController
public class ItemsController {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @RequestMapping("/get_item")
    @CrossOrigin(origins = "*")
    public String GetItem(@RequestParam("id") String id){
        CheckDatabaseStatus();
        SqlRowSet sqlRowSet=jdbcTemplate.queryForRowSet(String.format("SELECT * FROM `items` WHERE id = %s",id));
        JSONObject result = new JSONObject();
        if(sqlRowSet.next()){
            result.put("status","OK");
            result.put("result",new JSONObject(Map.ofEntries(
                    Map.entry("imgurl", sqlRowSet.getString("imgurl")),
                    Map.entry("nowprice", sqlRowSet.getFloat("nowprice")),
                    Map.entry("rawprice", sqlRowSet.getFloat("rawprice")),
                    Map.entry("title", sqlRowSet.getString("title")),
                    Map.entry("detail", sqlRowSet.getString("detail")),
                    Map.entry("id", sqlRowSet.getInt("id")),
                    Map.entry("categories",sqlRowSet.getString("categories")),
                    Map.entry("type",sqlRowSet.getInt("type")),
                    Map.entry("introductions",sqlRowSet.getString("introductions"))
            )));
        }else{
            result.put("status","ITEM_NOT_EXIST");
        }
        return result.toJSONString();
    }

    @RequestMapping("/get_items")
    @CrossOrigin(origins = "*")
    public String GetItems() {
        CheckDatabaseStatus();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM `items`");
        JSONObject result = new JSONObject();
        JSONArray resultSet = new JSONArray();
        while (sqlRowSet.next()) {
            resultSet.add(new JSONObject(Map.ofEntries(
                    Map.entry("imgurl", sqlRowSet.getString("imgurl")),
                    Map.entry("nowprice", sqlRowSet.getFloat("nowprice")),
                    Map.entry("rawprice", sqlRowSet.getFloat("rawprice")),
                    Map.entry("title", sqlRowSet.getString("title")),
                    Map.entry("detail", sqlRowSet.getString("detail")),
                    Map.entry("id", sqlRowSet.getInt("id")),
                    Map.entry("type",sqlRowSet.getInt("type"))
            )));
        }
        result.put("data", resultSet);
        return result.toJSONString();
    }

    @RequestMapping("/add_item")
    @CrossOrigin(origins = "*")
    public String AddItem(
            @RequestParam("imgurl") String imgurl,
            @RequestParam("title") String title,
            @RequestParam("detail") String detail,
            @RequestParam("nowprice") String nowprice,
            @RequestParam("rawprice") String rawprice,
            @RequestParam("categories") String categories,
            @RequestParam("type") String type,
            @RequestParam("introductions") String introductions) {
        CheckDatabaseStatus();
        JSONObject result = new JSONObject();
        try {
            jdbcTemplate.execute(String.format("""
                            INSERT INTO `items` (imgurl, title, detail, nowprice, rawprice, categories, type, introductions) VALUES
                            ('%s', '%s', '%s', %s, %s, '%s', %s, '%s')"""
                    , imgurl, title, detail, nowprice, rawprice,categories,type,introductions));
            result.put("result", "OK");
            return result.toJSONString();
        } catch (Exception e) {
            result.put("result", "ERR");
            result.put("error_msg", e.getMessage());
            return result.toJSONString();
        }

    }

    private void CheckDatabaseStatus() {
        if (!IsDatabaseExisted()) {
            jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS buyistic.items (
                    imgurl TEXT NOT NULL,
                    nowprice FLOAT NOT NULL,
                    rawprice FLOAT NOT NULL,
                    title TEXT NOT NULL,
                    detail TEXT NOT NULL,
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    categories TEXT NOT NULL,
                    type INT NOT NULL,
                    introductions TEXT NOT NULL
                    ) CHARACTER SET utf8mb4""");
        }
    }

    private boolean IsDatabaseExisted() {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            DatabaseMetaData data = connection.getMetaData();
            String[] types = {"TABLE"};
            rs = data.getTables(null, null, "items", types);
            if (rs.next()) return true;
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) rs.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
            }
        }
        return false;
    }
}
