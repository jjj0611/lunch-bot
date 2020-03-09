package org.woowacourse.lunchbot.domain;

import org.hibernate.dialect.Dialect;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector extends Dialect {
    private static final String DATA_URL;
    private static final String RESTAURANT_TABLE;

    static {
        DATA_URL = "jdbc:sqlite:tests.db";
        RESTAURANT_TABLE = "restaurant";
    }

    public DBConnector() {
        registerColumnType(Types.BIT, "integer");
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        registerColumnType(Types.INTEGER, "integer");
        registerColumnType(Types.CHAR, "char");
        registerColumnType(Types.VARCHAR, "varchar");
        registerColumnType(Types.LONGVARCHAR, "longvarchar");
        registerColumnType(Types.DATE, "date");
        registerColumnType(Types.TIME, "time");
        registerColumnType(Types.TIMESTAMP, "timestamp");
        registerColumnType(Types.BINARY, "blob");
    }

    public void createRestaurnatTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + RESTAURANT_TABLE + "(\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "name VARCHAR(255) NOT NULL,\n" +
                "type VARCHAR(255) NOT NULL,\n" +
                "mainMenu VARCHAR(255),\n" +
                "price int,\n" +
                "url VARCHAR(255),\n" +
                "imageUrl VARCHAR(255)" +
                ")";

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(DATA_URL);
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스에서 오류가 발생했습니다." + e.getMessage());
        } finally {
            close(connection, statement);
        }
    }

    public void initRestaurantData() throws GeneralSecurityException, IOException, SQLException {
        String sql = "insert into " + RESTAURANT_TABLE
                + "(NAME, TYPE, mainmenu, price, url, imageurl) "
                + " VALUES " + "('%s', '%s',  '%s',  %s,  '%s', '%s')";

        List<List<Object>> values = new GSpreadFetcher().getData();

        Connection connection = DriverManager.getConnection(DATA_URL);
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("파일 읽기 실패");
        }

        Statement statement = connection.createStatement();
        for (List row : values) {
            String insert = String.format(sql, row.get(1), row.get(2), row.get(3), row.get(4), row.get(5), row.get(6));
            statement.execute(insert);
        }

        close(connection, statement);
    }

    public List<Restaurant> createRestaurant() throws SQLException {
        Connection connection = null;
        Statement statement  = null;
        ResultSet resultSet = null;

        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "select * from "+RESTAURANT_TABLE;
        try {
            connection = DriverManager.getConnection(DATA_URL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7));
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스에서 오류가 발생했습니다." + e.getMessage());
        } finally {
            close(connection, statement, resultSet);
        }
        return restaurants;
    }

    private static void close(Connection connection, Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    private static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        close(connection, statement);
    }
}
