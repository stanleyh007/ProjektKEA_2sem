/**
 * Created by Lasse Jensen on 10-12-2015.
 */
import java.sql.*;

public class DataBase {
    private static DataBase dataBaseInstance = new DataBase();

    // Connection for database
    private Connection con;

    public static DataBase getInstance() {
        return dataBaseInstance;
    }

    private DataBase() {
        try {
            System.out.println("Starting up DataBase");

            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/dentistdb";

            // Connect to database
            con = DriverManager.getConnection(url, "root", "doggyspy");

            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
