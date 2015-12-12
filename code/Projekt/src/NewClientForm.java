import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by peterzohdy on 30/11/2015.
 */

public class NewClientForm extends Application {

    TextField cvrTextField = new TextField();
    TextField companyNameTextField = new TextField();
    TextField phoneTextField = new TextField();
    TextField emailTextField = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 0, 0, 35));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label titleLbl = new Label("              Insert Client Information");
        gridPane.setHalignment(titleLbl, HPos.LEFT);
        gridPane.setColumnSpan(titleLbl, 4);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Label companyRegNoLbl = new Label("Company CVR:");
        gridPane.setHalignment(companyRegNoLbl, HPos.RIGHT);
        cvrTextField.setPromptText("12345678");

        Label companyNameLbl = new Label("Company Name:");
        gridPane.setHalignment(companyNameLbl, HPos.RIGHT);

        Label phoneLbl = new Label("Phone:");
        gridPane.setHalignment(phoneLbl, HPos.RIGHT);

        Label emailLbl = new Label("Email:");
        gridPane.setHalignment(emailLbl, HPos.RIGHT);

        Button submitBtn = new Button("Submit");
        gridPane.setHalignment(submitBtn, HPos.LEFT);
        gridPane.setColumnSpan(submitBtn, 3);
        submitBtn.setPrefWidth(166);

        Button cancelBtn = new Button("Cancel");
        gridPane.setHalignment(cancelBtn, HPos.RIGHT);

        submitBtn.setOnAction(event -> {

            if (cvrTextField.getText().isEmpty() || companyNameTextField.getText().isEmpty()) {

                setAlert("Input error", "All mandatory fields must contain data");

            } else if
                    (isCVRValid(cvrTextField) && !companyNameTextField.getText().isEmpty())
            {
                setAlert("Saved", "Company has been stored");
                try {
                    submitBtnPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if(isCVRValid(cvrTextField) == false) {

                setAlert("Input error", "CVR no must be in the correct format");
            }

        });


        gridPane.add(titleLbl, 0, 0);
        gridPane.add(companyRegNoLbl, 0, 5);
        gridPane.add(cvrTextField, 1, 5);
        gridPane.add(companyNameLbl, 0,6);
        gridPane.add(companyNameTextField, 1, 6);
        gridPane.add(phoneLbl, 0, 7);
        gridPane.add(phoneTextField, 1, 7);
        gridPane.add(emailLbl, 0, 8);
        gridPane.add(emailTextField, 1, 8);
        gridPane.add(submitBtn, 1, 9);
        gridPane.add(cancelBtn, 2, 13);

        Scene scene = new Scene(gridPane, 400, 375);
        primaryStage.setScene(scene);
        submitBtn.requestFocus(); // Workaround to disable focus default
        primaryStage.show();
        primaryStage.setTitle("New Client");
        primaryStage.setResizable(false);
    }

    public boolean isCVRValid(TextField textField)
    {
        Boolean isCvrNumeric = true;
        Boolean isCvrLenghtValid;
        Boolean cvrValidation = false;

        if(textField.getText().length() == 8) {

            isCvrLenghtValid = true;

        } else {

            isCvrLenghtValid = false;
        }

        try {
            Integer.parseInt(textField.getText());

        } catch (Exception e) {

            isCvrNumeric = false;
        }

        if(isCvrNumeric == true && isCvrLenghtValid == true)
        {
            cvrValidation = true;
        }

        return cvrValidation;
    }

    public static void setAlert(String titleText,String headerText)
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.show();

    }

    public void submitBtnPressed() throws SQLException {

        int cvr = Integer.parseInt(cvrTextField.getText());
        String firstName = companyNameTextField.getText();
        int phone = Integer.parseInt(phoneTextField.getText());
        String email = emailTextField.getText();

        //Calls addEmployee method in DB class and inserts the entered input as parameters
        DataBase.getInstance().addClientToDb(cvr, firstName, phone, email);
    }
}
