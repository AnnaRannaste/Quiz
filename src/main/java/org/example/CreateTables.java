package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class CreateTables {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS Topics ("
                    + "topic_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(255) NOT NULL"
                    + ")");
            System.out.println("Topics table created successfully.");

            statement.execute("CREATE TABLE IF NOT EXISTS Questions ("
                    + "question_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "topic_id INT NOT NULL,"
                    + "difficulty INT NOT NULL,"
                    + "content TEXT NOT NULL"
                    + ")");
            System.out.println("Questions table created successfully.");

            statement.execute("CREATE TABLE IF NOT EXISTS Responses ("
                    + "response_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "question_id INT NOT NULL,"
                    + "text TEXT NOT NULL,"
                    + "is_correct BOOLEAN NOT NULL,"
                    + "FOREIGN KEY (question_id) REFERENCES Questions(question_id)"
                    + ")");
            System.out.println("Responses table created successfully.");

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}



