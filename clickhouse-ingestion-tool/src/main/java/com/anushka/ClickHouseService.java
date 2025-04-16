package com.anushka;


import com.clickhouse.jdbc.ClickHouseDataSource;
import org.springframework.stereotype.Service;
import java.sql.*;

@Service
public class ClickHouseService {
    private Connection connection;

    public void connect(String host, int port, String database,
            String username, String jwtToken) throws SQLException {
                String jdbcUrl = String.format(
                    "jdbc:ch://%s:%d/%s?user=%s&password=%s",
                    host, port, database, username, jwtToken);
        this.connection = new ClickHouseDataSource(jdbcUrl).getConnection();
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(query);
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}