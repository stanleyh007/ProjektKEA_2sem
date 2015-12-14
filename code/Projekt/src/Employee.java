import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Lasse Jensen on 10-12-2015.
 */
public class Employee {

    SimpleIntegerProperty cpr = new SimpleIntegerProperty();
    SimpleStringProperty firstName = new SimpleStringProperty();
    SimpleStringProperty lastName = new SimpleStringProperty();
    SimpleIntegerProperty phone = new SimpleIntegerProperty();
    SimpleStringProperty email = new SimpleStringProperty();

    public Employee(int cpr, String firstName, String lastName, int phone, String email)
    {

        this.cpr = new SimpleIntegerProperty(cpr);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleIntegerProperty(phone);
        this.email = new SimpleStringProperty(email);

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

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
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
