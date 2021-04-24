package me.VanadeysHaven.TimmyCore.Data.Database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class HikariManager {

    private static HikariDataSource hikari = null;

    public static void setup(String host, String port, String database, String user, String pass){
        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);

        hikari.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false");
        hikari.addDataSourceProperty("user", user);
        hikari.addDataSourceProperty("password", pass);
    }

    static Connection getConnection() throws SQLException {
        return hikari.getConnection();
    }

    public static void close(){
        hikari.close();
    }

}
