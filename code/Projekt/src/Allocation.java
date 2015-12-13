import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Lasse Jensen on 11-12-2015.
 */
public class Allocation {

    SimpleStringProperty employeeFirstname = new SimpleStringProperty();
    SimpleStringProperty employeeLastName = new SimpleStringProperty();
    SimpleStringProperty client = new SimpleStringProperty();
    SimpleStringProperty dateFrom = new SimpleStringProperty();
    SimpleStringProperty dateTo = new SimpleStringProperty();
    SimpleStringProperty notes = new SimpleStringProperty();

    public Allocation(String employeeFirstName, String employeeLastName, String client, String dateFrom, String dateTo, String notes) {
        this.employeeFirstname = new SimpleStringProperty(employeeFirstName);
        this.employeeLastName = new SimpleStringProperty(employeeLastName);
        this.client = new SimpleStringProperty(client);
        this.dateFrom = new SimpleStringProperty(dateFrom);
        this.dateTo = new SimpleStringProperty(dateTo);
        this.notes = new SimpleStringProperty(notes);
    }

    public String getEmployeeFirstName() {
        return employeeFirstname.get();
    }

    public String getEmployeeLastName() {
        return employeeLastName.get();
    }

    public String getClient() {
        return client.get();
    }

    public String getDateFrom() {
        return dateFrom.get();
    }

    public String getDateTo() {
        return dateTo.get();
    }

    public String getNotes() {
        return notes.get();
    }

}
