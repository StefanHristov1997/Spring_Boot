package GetVillainsNames_01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum Connector {
    ;
   public static Connection getConnection() throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);
    }
}
