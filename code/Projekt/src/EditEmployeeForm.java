import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Lasse Jensen on 15-12-2015.
 */
public class EditEmployeeForm extends EmployeeForm /*implements Inputforms*/ {
    Employee employee;

    ObservableList<Employee> employeeList;

    public EditEmployeeForm(Employee employee, ObservableList<Employee> employeeList) {
        super(employeeList);

        this.employee = employee;
        this.employeeList = employeeList;

        setFields();
        initializeScene();
        setDeleteBtn();
    }

    public void setFields() {
        cprTextField.setText(Integer.toString(employee.getCpr()));
        firstNameTextField.setText(employee.getFirstname());
        lastNameTextField.setText(employee.getLastname());
        phoneTextFiled.setText(Integer.toString(employee.getPhone()));
        emailTextField.setText(employee.getEmail());
    }

    public void setDeleteBtn() {
        Button deleteBtn = new Button("Delete");
        gridPane.setHalignment(deleteBtn, HPos.LEFT);
        gridPane.setColumnSpan(deleteBtn, 3);
        deleteBtn.setPrefWidth(166);
        gridPane.add(deleteBtn, 1, 11);


        deleteBtn.setOnAction(e -> {
            int cpr = Integer.parseInt(cprTextField.getText());
            DataBase.getInstance().deleteClient(cpr);
            setAlert("Deleted", "Entry has been deleted");
        });
    }

    @Override
    public void submitButtonPressed()  {
        int cpr = Integer.parseInt(cprTextField.getText());
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int phone = Integer.parseInt(phoneTextFiled.getText());
        String email = emailTextField.getText();


        employeeList.clear();

        Employee employeeChanged = new Employee(cpr, firstName, lastName, phone, email);

        DataBase.getInstance().changeEmployee(cpr, employeeChanged);

        DataBase.getInstance().getEmployees(employeeList);

        close();
    }
}