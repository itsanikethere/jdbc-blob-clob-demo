package blob;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;

public class ReadBlob {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/database.properties"));

        String connectionUrl = properties.getProperty("connection_url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Connection connection = DriverManager.getConnection(connectionUrl, username, password);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM blob_demo");

        try (FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/image/from_database/sample.jpg")) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                InputStream inputStream = resultSet.getBinaryStream("image");

                byte[] buffer = new byte[1024];
                while (inputStream.read(buffer) > 0) {
                    fileOutputStream.write(buffer);
                }
            }
        }
    }
}
