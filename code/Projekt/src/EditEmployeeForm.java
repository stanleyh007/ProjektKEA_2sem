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

/**
 * Created by Lasse Jensen on 15-12-2015.
 */
public class EditEmployeeForm implements Inputforms {

    Stage sceneStage = new Stage();
    Scene scene;

    TextField cprTextField = new TextField();
    TextField firstNameTextField = new TextField();
    TextField lastNameTextField = new TextField();
    TextField phoneTextFiled = new TextField();
    TextField emailTextField = new TextField();

    Employee employee;

    ObservableList<Employee> employeeList;

    public EditEmployeeForm(Employee employee, ObservableList<Employee> employeeList)
    {
        this.employee = employee;
        this.employeeList = employeeList;

        initializeScene();
        System.out.println("Allocation name = " + employee.getFirstname());
    }


    public void initializeScene()
    {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 0, 0, 65));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label titleLbl = new Label("     Insert Employee Information");
        gridPane.setHalignment(titleLbl, HPos.LEFT);
        gridPane.setColumnSpan(titleLbl, 4);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Label cprLbl = new Label("CPR No:");
        gridPane.setHalignment(cprLbl, HPos.RIGHT);
        //cprTextField.setPromptText("DDMMYYYYXXXX");
        cprTextField.setText(Integer.toString(employee.getCpr()));

        Label firstNameLbl = new Label("Firstname:");
        gridPane.setHalignment(firstNameLbl, HPos.RIGHT);
        firstNameTextField.setText(employee.getFirstname());


        Label lastNameLbl = new Label("Lastname:");
        gridPane.setHalignment(lastNameLbl, HPos.RIGHT);
        lastNameTextField.setText(employee.getLastname());


        Label phoneLbl = new Label("Phone:");
        gridPane.setHalignment(phoneLbl, HPos.RIGHT);
        phoneTextFiled.setText(Integer.toString(employee.getPhone()));

        Label emailLbl = new Label("Email:");
        gridPane.setHalignment(emailLbl, HPos.RIGHT);
        emailTextField.setText(employee.getEmail());

        Button changeBtn = new Button("Change");
        gridPane.setHalignment(changeBtn, HPos.LEFT);
        gridPane.setColumnSpan(changeBtn, 3);
        changeBtn.setPrefWidth(166);

        Button deleteBtn = new Button("Delete");
        gridPane.setHalignment(deleteBtn, HPos.LEFT);
        gridPane.setColumnSpan(deleteBtn, 3);
        deleteBtn.setPrefWidth(166);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(event -> close());


        gridPane.add(titleLbl, 0, 0);
        gridPane.add(cprLbl, 0, 5);
        gridPane.add(cprTextField, 1, 5);
        gridPane.add(firstNameLbl, 0, 6);
        gridPane.add(firstNameTextField, 1,6);
        gridPane.add(lastNameLbl, 0,7);
        gridPane.add(lastNameTextField, 1,7);
        gridPane.add(phoneLbl, 0, 8);
        gridPane.add(phoneTextFiled, 1,8);
        gridPane.add(emailLbl, 0, 9);
        gridPane.add(emailTextField, 1, 9);
        gridPane.add(changeBtn, 1, 10);
        gridPane.add(deleteBtn, 1, 11);
        gridPane.add(cancelBtn, 3, 14);

        scene = new Scene(gridPane, 400, 435);
        sceneStage.setScene(scene);

        sceneStage.initModality(Modality.APPLICATION_MODAL);

        firstNameLbl.requestFocus();

        changeBtn.setOnAction(e ->
        {
            if (    cprTextField.getText().isEmpty() ||
                    firstNameTextField.getText().isEmpty() ||
                    lastNameTextField.getText().isEmpty())
            {
                setAlert("Input error", "You need to fill out all mandatory fields");
            }
            else if (isNumeric(cprTextField) == false)
            {
                setAlert("Input error", "CPR must be numeric values");
            }
            else if (isNumeric(cprTextField) &&
                    !firstNameTextField.getText().isEmpty() &&
                    !lastNameTextField.getText().isEmpty())
            {
                setAlert("Saved", "Employee has been stored");
                try
                {
                    submitButtonPressed();

                }
                catch (Exception exception) {exception.printStackTrace();}
            }
        });

        deleteBtn.setOnAction(e ->{

            deleteBtnPressed();
    });

    }



    public void show()
    {
        sceneStage.setResizable(false);
        sceneStage.show();
    }

    public void close()
    {
        sceneStage.close();
    }


    public void setAlert(String titleText,String headerText)
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.show();
    }

    public static boolean isNumeric(TextField textField)
    {
        boolean isNumeric = true;

        try {
            Integer.parseInt(textField.getText());

        } catch (Exception e)

        {
            isNumeric = false;
        }

        return isNumeric;
    }

    public void submitButtonPressed()
    {
        System.out.println("Submitting");

        int cpr = Integer.parseInt(cprTextField.getText());
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int phone = Integer.parseInt(phoneTextFiled.getText());
        String email = emailTextField.getText();

        Employee employeeChanged = new Employee(cpr, firstName, lastName, phone, email);

                //Calls addEmployee method in DB class and inserts the entered input as parameters
        //DataBase.getInstance().addEmployeeToDb(cpr, firstName, lastName, phone, email);

        employeeList.clear();

        DataBase.getInstance().changeEmployee(cpr, employeeChanged);

        DataBase.getInstance().getEmployees(employeeList);

        close();
    }

    public void deleteBtnPressed()
    {
        int cpr = Integer.parseInt(cprTextField.getText());

        DataBase.getInstance().deleteEmployee(cpr);

        setAlert("Deleted", "Entry has been deleted");
    }

}

