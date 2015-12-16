import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Lasse Jensen on 10-12-2015.
 */
public class Employee {
    SimpleIntegerProperty cpr = new SimpleIntegerProperty();
    SimpleStringProperty firstname = new SimpleStringProperty();
    SimpleStringProperty lastname = new SimpleStringProperty();
    SimpleIntegerProperty phone = new SimpleIntegerProperty();
    SimpleStringProperty email = new SimpleStringProperty();

    public Employee(int cpr, String firstName, String lastName, int phone, String email)
    {
        this.cpr = new SimpleIntegerProperty(cpr);
        this.firstname = new SimpleStringProperty(firstName);
        this.lastname = new SimpleStringProperty(lastName);
        this.phone = new SimpleIntegerProperty(phone);
        this.email = new SimpleStringProperty(email);
    }

    //To be able to return a full object in our allocation combobox, but only have the name displayed.
    public String toString()
    {
        return getFirstname() + " " + getLastname();
    }

    public int getCpr() {
        return cpr.get();
    }

    public SimpleIntegerProperty cprProperty() {
        return cpr;
    }

    public void setCpr(int cpr) {
        this.cpr.set(cpr);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getLastname() {
        return lastname.get();
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public int getPhone() {
        return phone.get();
    }

    public SimpleIntegerProperty phoneProperty() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
