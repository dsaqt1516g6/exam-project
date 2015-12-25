package edu.upc.eetac.dsa.beeter.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Database {
    private static Database instance = null;
    private DataSource ds;

    private Database() {
        PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("beeter");

        HikariConfig config = new HikariConfig(prb.getString("hikari_config_path"));
        ds = new HikariDataSource(config);
    }

    private final static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public final static Connection getConnection() throws SQLException {
        return getInstance().ds.getConnection();
    }
}
