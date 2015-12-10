import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Lasse Jensen on 10-12-2015.
 */
public class Employee {
    SimpleStringProperty name = new SimpleStringProperty();

    public Employee(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }

}
