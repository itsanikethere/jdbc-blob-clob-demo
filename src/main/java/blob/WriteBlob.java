package blob;

import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.util.Properties;

public class WriteBlob {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/database.properties"));

        String connectionUrl = properties.getProperty("connection_url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Connection connection = DriverManager.getConnection(connectionUrl, username, password);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO blob_demo VALUES(?)");

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/image/to_database/sample.jpg")) {
            statement.setBinaryStream(1, fileInputStream);
            statement.executeUpdate();
        }
    }
}
