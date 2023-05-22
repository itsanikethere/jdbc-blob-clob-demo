package clob;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;

public class ReadClob {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/database.properties"));

        String connectionUrl = properties.getProperty("connection_url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Connection connection = DriverManager.getConnection(connectionUrl, username, password);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM clob_demo");

        try (FileWriter fileWriter = new FileWriter("src/main/resources/text/from_database/sample.txt")) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Reader reader = resultSet.getCharacterStream("text");

                char[] buffer = new char[1024];
                while (reader.read(buffer) > 0) {
                    fileWriter.write(buffer);
                }
            }
        }
    }
}
