package clob;

import java.io.FileInputStream;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.util.Properties;

public class WriteClob {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/database.properties"));

        String connectionUrl = properties.getProperty("connection_url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Connection connection = DriverManager.getConnection(connectionUrl, username, password);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO clob_demo VALUES(?)");

        try (FileReader fileReader = new FileReader("src/main/resources/text/to_database/sample.txt")) {
            statement.setCharacterStream(1, fileReader);
            statement.executeUpdate();
        }
    }
}
