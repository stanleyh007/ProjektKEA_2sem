/**
 * Created by Lasse Jensen on 10-12-2015.
 */
import javafx.collections.ObservableList;

import java.sql.*;

public class DataBase {
    private static DataBase dataBaseInstance = new DataBase();


    private Connection con;   // Connection for database
    private PreparedStatement statement;
    private ResultSet resultset;

    public static DataBase getInstance() {

        return dataBaseInstance;
    }

    private DataBase() {

        try {

            System.out.println("Starting up DataBase");

            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/dentistdb";

            // Connect to database
            con = DriverManager.getConnection(url, "root", "root");

            System.out.println("URL: " + url);

            System.out.println("Connection: " + con);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //Method creates appstract database with all of its tables and constraints.
    public void createDB()
    {

        try {

            //Creates database appstract_db
            statement = con.prepareStatement("CREATE database IF NOT EXISTS appstract_db");
            //Create IF statement to verify if database exists

            statement.executeUpdate();

            //Tells myql server to use appstract_db
            statement = con.prepareStatement("USE appstract_db");

            statement.executeUpdate();

            //Creates employee table in DB
            statement = con.prepareStatement ("CREATE TABLE IF NOT EXISTS employee (cpr INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                    "firstname VARCHAR (30) NOT NULL, lastname VARCHAR(30) NOT NULL, telephone INT, email VARCHAR (60))");

            statement.executeUpdate();

            //Creates client Table in DB
            statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS client (cvr INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                    "companyname VARCHAR (30) NOT NULL, telephone INT, email VARCHAR (60))");

            statement.executeUpdate();

            //Creates the date table in DB
            statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS duration (date_from DATE PRIMARY KEY NOT NULL, date_to DATE)");

            statement.executeUpdate();

            //Creates the alloction table in DB
            statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS allocation (event_id INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    " PRIMARY KEY (event_id), cpr INT UNSIGNED NOT NULL, cvr INT UNSIGNED NOT NULL, activity VARCHAR(40)," +
                    " date_from DATE, notes TEXT, FOREIGN KEY (cpr) REFERENCES employee(cpr), FOREIGN KEY (cvr) " +
                    "REFERENCES client(cvr), FOREIGN KEY (date_from) REFERENCES duration (date_from))");

            statement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable()
    {
        try {
            statement = con.prepareStatement("CREATE TABLE Employees (id INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                    "firstname VARCHAR (30) NOT NULL, lastname VARCHAR(30) NOT NULL");


        } catch (SQLException e) {
        e.printStackTrace();
    }
    }




}
