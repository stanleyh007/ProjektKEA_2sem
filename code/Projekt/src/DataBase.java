import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    //*Singleton* makes a new instance of itself in the same class
    private static DataBase dataBaseInstance = new DataBase();
    private Connection con;   // Connection for database
    private PreparedStatement statement;
    private ResultSet resultset;

    //*Singleton* returns the instance of itself and a new can only be made if it doesn't exist.
    public static DataBase getInstance() {

        if(dataBaseInstance == null)
            dataBaseInstance = new DataBase();
        return dataBaseInstance;
    }

    //Setting up the database connection in constructor
    private DataBase() {

        try {

            System.out.println("Starting up DataBase");

            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/";

            con = DriverManager.getConnection(url, "root", "root");

            System.out.println("URL: " + url);

            System.out.println("Connection: " + con);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //If the databse doesn't exist this method creates the "appstract_db" database with all of its tables.
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
            statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS allocation_other_activity (event_id INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "otheractivity ENUM ('Ferie', 'Kursus', 'Sygdom', 'Øvrigt', ''), datefrom DATE NOT NULL, " +
                    "dateto DATE NOT NULL, PRIMARY KEY (event_id), FOREIGN KEY (event_id) REFERENCES allocation_Project(event_id))");

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //SQL statement to insert an allocation into database. This method is called from AddNewAllocationForm class.
    public void allocateEmployee(int cpr, int cvr, Date date_from, Date dateto, String notes)
    {
        try{
            statement = con.prepareStatement("USE appstract_db");
            statement.executeUpdate();

            statement = con.prepareStatement("INSERT INTO allocation_project (cpr, cvr, date_from, dateto, notes) VALUES(?, ?, ?, ? , ?)");

            statement.setInt(1, cpr);
            statement.setInt(2, cvr);
            statement.setDate(3, date_from);
            statement.setDate(4, dateto);
            statement.setString(5, notes);

            statement.executeUpdate();

            System.out.println("Allocation added");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //SQL statement to insert an employee into database. This method is called from AddNewEmployeeForm.
    public void addEmployeeToDb(int cpr, String firstname, String lastname, int phone, String email) throws SQLException
    {
        try {
            statement = con.prepareStatement("USE appstract_db");

            statement.executeUpdate();

            statement = con.prepareStatement("INSERT INTO employee VALUES (?, ?, ?, ?, ?)");

            statement.setInt(1, cpr);
            statement.setString(2, firstname);
            statement.setString(3, lastname);
            statement.setInt(4, phone);
            statement.setString(5, email);

            statement.executeUpdate();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

            System.out.println("Employee :" + firstname + " " + lastname + " inserted into database");
    }

    //SQL statement to insert a client into database. This method is called from AddNewClientForm.
    public void addClientToDb(int cvr, String companyname, int phone, String email)
    {
        try {

            statement = con.prepareStatement("USE appstract_db");
            statement.executeUpdate();

            statement = con.prepareStatement("INSERT INTO client VALUES (?, ?, ?, ?)");

            statement.setInt(1, cvr);
            statement.setString(2, companyname);
            statement.setInt(3, phone);
            statement.setString(4, email);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Client : " + companyname + " inserted into database");
    }

    //SQL Query to get all the allocations from database.
    public void getAllocations(ObservableList<Allocation> allocationList) {
        try {
            statement = con.prepareStatement("SELECT * FROM allocation_project JOIN employee, client WHERE " +
                    "allocation_project.cpr=employee.cpr AND allocation_project.cvr=client.cvr");
            resultset = statement.executeQuery();

            //ResultSet is added to the observable list "allocationList" and used in Main_UI class to setup the tables
            while (resultset.next()) {
                allocationList.add(new Allocation(Integer.parseInt(resultset.getString("event_id")),
                        Integer.parseInt(resultset.getString("cpr")), Integer.parseInt(resultset.getString("cvr")),
                        resultset.getString("firstname"), resultset.getString("lastname"), resultset.getString("companyname"),
                        resultset.getString("date_from"), resultset.getString("dateto"), resultset.getString("notes")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SQL Query to get all the employees from database.
    public void getEmployees(ObservableList<Employee> employeeList) {
        try {
            statement = con.prepareStatement("SELECT * FROM employee");
            resultset = statement.executeQuery();

            //ResultSet is added to the observable list "employeeList" and used in Main_UI class to setup the tables
            while (resultset.next()) {
                employeeList.add(new Employee(resultset.getInt("cpr"), resultset.getString("firstname"),
                        resultset.getString("lastname"), resultset.getInt("telephone"), resultset.getString("email")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SQL Query to get all the clients from database.
    public void getClients(ObservableList<Client> clientList) {
        try {
            statement = con.prepareStatement("SELECT * FROM client");
            resultset = statement.executeQuery();

            //ResultSet is added to the observable list "clientList" and used in Main_UI class to setup the tables
            while (resultset.next()) {
                clientList.add(new Client(resultset.getInt("cvr"), resultset.getString("companyname"),
                        resultset.getInt("telephone"), resultset.getString("email")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //The resultset is added to the "employee" arraylist of type <Employee>. This is used in the combobox for employees
    //in "AddNewAllocationForm" class.
    public ArrayList<Employee> employeesToArrayList()
    {
        ArrayList<Employee> employees = new ArrayList();

        try {
            statement = con.prepareStatement("USE appstract_db");
            statement.executeUpdate();

            statement = con.prepareStatement("SELECT * FROM employee");
            resultset = statement.executeQuery();

            while (resultset.next())
            {
                employees.add(new Employee(resultset.getInt("cpr"), resultset.getString("firstname"), resultset.getString("lastname"),
                        resultset.getInt("telephone"), resultset.getString("email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    //The resultset is added to the "client" arraylist of type <Client>. This is used in the combobox for clients.
    //in "AddNewAllocationForm" class.
    public ArrayList<Client> clientsToArrayList()
    {
        ArrayList<Client> clients = new ArrayList();

        try {
            statement = con.prepareStatement("USE appstract_db");
            statement.executeUpdate();

            statement = con.prepareStatement("SELECT * FROM client");
            resultset = statement.executeQuery();

            while (resultset.next())
            {
                clients.add(new Client(resultset.getInt("cvr"), resultset.getString("companyname"),
                        resultset.getInt("telephone"), resultset.getString("email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    //SQL Statement to change the values of an existing employee - used in "EditEmployeeForm" class.
    public void changeEmployee(int cpr, Employee employee) {
        try {
            statement = con.prepareStatement("UPDATE employee SET firstname='" + employee.getFirstname() +"',lastName='" + employee.getLastname() +
                                            "',telephone=" + employee.getPhone() + ",email='" + employee.getEmail() + "'" +
                                            " WHERE cpr=" + cpr);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SQL Statment to change the values of an existing client- used in "EditClientForm" class.
    public void changeClient(int cvr, Client client) {
        try {
            statement = con.prepareStatement("UPDATE client SET companyname='" + client.getCompanyName() + "', telephone=" + client.getPhone() +
                                            ", email='" + client.getEmail() + "' WHERE cvr=" + cvr);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SQL Statement to change the values of an existing allocation - used in "EditAllocationForm" class.
    public void changeAllocation(int eventId, Allocation allocation) {
        try {
            statement = con.prepareStatement("UPDATE allocation_project SET cpr=" + allocation.getCpr() + ", cvr=" + allocation.getCvr() +
                                            ", date_from='" + allocation.getDateFrom() + "', dateto='" + allocation.getDateTo() + "', notes='" + allocation.getNotes() +
                                            "' WHERE event_id=" + eventId);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SQL Statement to delete an employee from databse. Called from "EditEmployeeForm" class
    public void deleteEmployee(int cpr)
    {
        try{
           statement = con.prepareStatement("DELETE from employee WHERE cpr = ('"+cpr+"')");
           statement.executeUpdate();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //SQL Statement to delete a client from database. Called from "EditClientForm" class
    public void deleteClient(int cvr)
    {
        try{
            statement = con.prepareStatement("DELETE from client WHERE cvr = ('"+cvr+"')");
            statement.executeUpdate();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //SQL Statement to delete an allocation from database. Called from "EditAllocationForm" class
    public void deleteAllocation(int cpr, int cvr)
    {
        try {
            String SQL = "DELETE from allocation_project WHERE cpr = ? AND cvr = ?";

            statement = con.prepareStatement(SQL);
            statement.setInt(1, cpr);
            statement.setInt(2, cvr);

            statement.executeUpdate();

        } catch (Exception e)
        {

        }
    }


}
