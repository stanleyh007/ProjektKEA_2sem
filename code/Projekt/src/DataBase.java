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

            String url = "jdbc:mysql://localhost:3306/";

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
            statement = con.prepareStatement ("CREATE TABLE IF NOT EXISTS employee (cpr INT NOT NULL PRIMARY KEY," +
                    "firstname VARCHAR (25) NOT NULL, lastname VARCHAR(30) NOT NULL, telephone INT, email VARCHAR (50))");

            statement.executeUpdate();

            //Creates client Table in DB
            statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS client (cvr INT NOT NULL PRIMARY KEY," +
                    "companyname VARCHAR (50) NOT NULL, telephone INT, email VARCHAR (50))");

            statement.executeUpdate();

            //Creates the alloction table in DB
            statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS allocation_project (event_id INT UNSIGNED NOT NULL AUTO_INCREMENT, " +
                    "cpr INT NOT NULL, cvr INT NOT NULL, " + " date_from DATE NOT NULL , dateto DATE NOT NULL, " +
                    "notes TEXT, PRIMARY KEY (event_id), FOREIGN KEY (cpr) REFERENCES employee(cpr), FOREIGN KEY (cvr) REFERENCES client(cvr))");

            statement.executeUpdate();

            //Creates the date table in DB
            statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS allocation_otherActivity (event_id INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "otheractivity ENUM ('Ferie', 'Kursus', 'Sygdom', 'Øvrigt', ''), datefrom DATE NOT NULL, " +
                    "dateto DATE NOT NULL, PRIMARY KEY (event_id), FOREIGN KEY (event_id) REFERENCES allocation_Project(event_id))");

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeToDb(int cpr, String firstname, String lastname, int phone, String email) throws SQLException
    {

        try {
            statement = con.prepareStatement("USE appstract_db");

            statement.executeUpdate();

            statement = con.prepareStatement("INSERT INTO employee VALUES (?, ?, ?, ?, ?)");

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
            statement.setInt(1, cpr);
            statement.setString(2, firstname);
            statement.setString(3, lastname);
            statement.setInt(4, phone);
            statement.setString(5, email);

            statement.executeUpdate();


            System.out.println("Employee :" + firstname + " " + lastname + " inserted into database");
    }

    public void addClientToDb(int cvr, String companyname, int phone, String email) throws SQLException
    {

        try {
            statement = con.prepareStatement("USE appstract_db");

            statement.executeUpdate();

            statement = con.prepareStatement("INSERT INTO client VALUES (?, ?, ?, ?)");

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        statement.setInt(1, cvr);
        statement.setString(2, companyname);
        statement.setInt(3, phone);
        statement.setString(4, email);

        statement.executeUpdate();


        System.out.println("Client : " + companyname + " inserted into database");
    }

    public void getAllocations(ObservableList<Allocation> allocationList) {
        try {
            statement = con.prepareStatement("SELECT * FROM allocation_project JOIN employee, client WHERE allocation_project.cpr=employee.cpr" +
                                                " AND allocation_project.cvr=client.cvr");
            resultset = statement.executeQuery();

            while (resultset.next()) {
                allocationList.add(new Allocation(resultset.getString("firstname"), resultset.getString("lastname"), resultset.getString("companyname"), resultset.getString("date_from"), resultset.getString("dateto"), resultset.getString("notes")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
