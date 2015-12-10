import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Created by peterzohdy on 30/11/2015.
 */
public class NewClientForm extends Application {
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
        TextField companyRegTxtField = new TextField();
        companyRegTxtField.setPromptText("12345678");

        Label companyNameLbl = new Label("Company Name:");
        gridPane.setHalignment(companyNameLbl, HPos.RIGHT);
        TextField companyNameTxtFld = new TextField();

        Label phoneLbl = new Label("Phone:");
        gridPane.setHalignment(phoneLbl, HPos.RIGHT);
        TextField phoneTxtFld= new TextField();

        Label emailLbl = new Label("Email:");
        gridPane.setHalignment(emailLbl, HPos.RIGHT);
        TextField emailTxtFld = new TextField();

        Button submitBtn = new Button("Submit");
        gridPane.setHalignment(submitBtn, HPos.LEFT);
        gridPane.setColumnSpan(submitBtn, 3);
        submitBtn.setPrefWidth(166);

        Button cancelBtn = new Button("Cancel");
        gridPane.setHalignment(cancelBtn, HPos.RIGHT);

        submitBtn.setOnAction(event -> {

            if (companyRegTxtField.getText().isEmpty() || companyNameTxtFld.getText().isEmpty()) {

                setAlert("Input error", "All mandatory fields must contain data");

            } else if
                    (isCVRValid(companyRegTxtField) && !companyNameTxtFld.getText().isEmpty())
            {
                setAlert("Saved", "Company has been stored");

            } else if(isCVRValid(companyRegTxtField) == false) {

                setAlert("Input error", "CVR no must be in the correct format");
            }

        });


        gridPane.add(titleLbl, 0, 0);
        gridPane.add(companyRegNoLbl, 0, 5);
        gridPane.add(companyRegTxtField, 1, 5);
        gridPane.add(companyNameLbl, 0,6);
        gridPane.add(companyNameTxtFld, 1, 6);
        gridPane.add(phoneLbl, 0, 7);
        gridPane.add(phoneTxtFld, 1, 7);
        gridPane.add(emailLbl, 0, 8);
        gridPane.add(emailTxtFld, 1, 8);
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
}
