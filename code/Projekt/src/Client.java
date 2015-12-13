import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by peterzohdy on 12/12/2015.
 */
public class Client {

    SimpleIntegerProperty cvr = new SimpleIntegerProperty();
    SimpleStringProperty companyName = new SimpleStringProperty();
    SimpleIntegerProperty phone = new SimpleIntegerProperty();
    SimpleStringProperty email = new SimpleStringProperty();

    public Client (int cvr, String companyName, int phone, String email)
    {
        this.cvr = new SimpleIntegerProperty(cvr);
        this.companyName = new SimpleStringProperty(companyName);
        this.phone = new SimpleIntegerProperty(phone);
        this.email = new SimpleStringProperty(email);
    }

    public int getCvr() {
        return cvr.get();
    }

    public SimpleIntegerProperty cvrProperty() {
        return cvr;
    }

    public void setCvr(int cvr) {
        this.cvr.set(cvr);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
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