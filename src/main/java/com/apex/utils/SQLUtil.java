package com.apex.utils;

import com.apex.config.DataBaseInfo;
import com.apex.config.FrameworkException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.apex.config.Constants.QUERIES_FOLDER;

public class SQLUtil {
    private static Connection connection;
    private static Statement statement;

    private static Connection getDBConnection(String dbName) {
        try {
            return DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%s/%s?useSSL=false",
                            DataBaseInfo.DBHOST.getValue(),
                            DataBaseInfo.DBPORT.getValue(),
                            dbName),
                    DataBaseInfo.DBUSERNAME.getValue(),
                    DataBaseInfo.DBPASSWORD.getValue()
            );
        } catch (SQLException e) {
            throw new FrameworkException("Error establishing database connection", e);
        }
    }

    private static Statement getStatement(String dbName) {
        return Optional.ofNullable(statement)
                .orElseGet(() -> {
                    try {
                        statement = getDBConnection(dbName)
                                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        return statement;
                    } catch (SQLException e) {
                        throw new FrameworkException("Error creating statement", e);
                    }
                });
    }

    private static String readSqlFile(String sqlFile) {
        try (Stream<String> lines = Files.lines(Paths.get(QUERIES_FOLDER.resolve(sqlFile).toString()))) {
            return lines.collect(Collectors.joining(" "));
        } catch (IOException e) {
            throw new FrameworkException("Error reading SQL file: " + sqlFile, e);
        }
    }

    public static ResultSet getResultSet(String sqlFile, String dbName) {
        return Optional.ofNullable(getStatement(dbName))
                .map(stmt -> {
                    try {
                        return stmt.executeQuery(readSqlFile(sqlFile));
                    } catch (SQLException e) {
                        throw new FrameworkException("Error executing SQL query", e);
                    }
                })
                .orElseThrow(() -> new FrameworkException("Statement could not be created"));
    }

    public static void closeConnection() {
        Optional.ofNullable(statement).ifPresent(stmt -> {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new FrameworkException("Error closing statement", e);
            }
        });

        Optional.ofNullable(connection).ifPresent(conn -> {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new FrameworkException("Error closing connection", e);
            }
        });
    }
}