import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Lasse Jensen on 11-12-2015.
 */
public class Allocation {

    int eventId;
    int cpr;
    int cvr;

    //Properties to be used with TableViews in UI.
    SimpleStringProperty employeeFirstName = new SimpleStringProperty();
    SimpleStringProperty employeeLastName = new SimpleStringProperty();
    SimpleStringProperty client = new SimpleStringProperty();
    SimpleStringProperty dateFrom = new SimpleStringProperty();
    SimpleStringProperty dateTo = new SimpleStringProperty();
    SimpleStringProperty notes = new SimpleStringProperty();

    public Allocation(int eventId, int cpr, int cvr, String employeeFirstName, String employeeLastName, String client, String dateFrom, String dateTo, String notes) {

        this.eventId = eventId;
        this.cpr = cpr;
        this.cvr = cvr;

        this.employeeFirstName = new SimpleStringProperty(employeeFirstName);
        this.employeeLastName = new SimpleStringProperty(employeeLastName);
        this.client = new SimpleStringProperty(client);
        this.dateFrom = new SimpleStringProperty(dateFrom);
        this.dateTo = new SimpleStringProperty(dateTo);
        this.notes = new SimpleStringProperty(notes);
    }


    public int getEventId() {
        return eventId;
    }

    public int getCpr() {
        return cpr;
    }

    public int getCvr() {
        return cvr;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName.get();
    }

    public SimpleStringProperty employeeFirstNameProperty() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName.set(employeeFirstName);
    }

    public String getEmployeeLastName() {
        return employeeLastName.get();
    }

    public SimpleStringProperty employeeLastNameProperty() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName.set(employeeLastName);
    }

    public String getClient() {
        return client.get();
    }

    public SimpleStringProperty clientProperty() {
        return client;
    }

    public void setClient(String client) {
        this.client.set(client);
    }

    public String getDateFrom() {
        return dateFrom.get();
    }

    public SimpleStringProperty dateFromProperty() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom.set(dateFrom);
    }

    public String getDateTo() {
        return dateTo.get();
    }

    public SimpleStringProperty dateToProperty() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo.set(dateTo);
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }
}
