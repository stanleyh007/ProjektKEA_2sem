import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by peterzohdy on 28/11/2015.
 */

public class AddNewEmployeeForm extends EmployeeForm {


    public AddNewEmployeeForm(ObservableList<Employee> employeeList) {

        //Calls the list of the inherited class ClientForm
        super(employeeList);
        this.employeeList = employeeList;

        initializeScene();
    }
}