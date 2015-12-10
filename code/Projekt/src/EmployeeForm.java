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
 * Created by peterzohdy on 28/11/2015.
 */

public class EmployeeForm extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {


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
        TextField cprTxtField = new TextField();
        cprTxtField.setPromptText("DDMMYYYYXXXX");

        Label firstNameLbl = new Label("Firstname:");
        gridPane.setHalignment(firstNameLbl, HPos.RIGHT);
        TextField firstNameTxtFld = new TextField();


        Label lastNameLbl = new Label("Lastname:");
        gridPane.setHalignment(lastNameLbl, HPos.RIGHT);
        TextField lastNameTxtFld = new TextField();


        Label phoneLbl = new Label("Phone:");
        gridPane.setHalignment(phoneLbl, HPos.RIGHT);
        TextField phoneTxtFld = new TextField();;

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

            if (cprTxtField.getText().isEmpty() ||
                    firstNameTxtFld.getText().isEmpty() ||
                    lastNameTxtFld.getText().isEmpty())

            {
                setAlert("Input error", "You need to fill out all mandatory fields");

            } else if (isNumeric(cprTxtField) == false) {

                setAlert("Input error", "CPR must be numeric values");

            } else if (isNumeric(cprTxtField)) {

                String cprAsString = cprTxtField.getText();
                int cprAsInt = Integer.parseInt(cprAsString);

                System.out.println(cprAsInt);
            }
        });


        gridPane.add(titleLbl, 0, 0);
        gridPane.add(cprLbl, 0, 5);
        gridPane.add(cprTxtField, 1, 5);
        gridPane.add(firstNameLbl, 0, 6);
        gridPane.add(firstNameTxtFld, 1,6);
        gridPane.add(lastNameLbl, 0,7);
        gridPane.add(lastNameTxtFld, 1,7);
        gridPane.add(phoneLbl, 0, 8);
        gridPane.add(phoneTxtFld, 1,8);
        gridPane.add(emailLbl, 0, 9);
        gridPane.add(emailTxtFld, 1, 9);
        gridPane.add(submitBtn, 1, 10);
        gridPane.add(cancelBtn, 3, 14);


        Scene scene = new Scene(gridPane, 400, 410);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Opret ny medarbejder");
        primaryStage.setResizable(false);
        firstNameLbl.requestFocus();


    }

    public static void setAlert(String titleText,String headerText)
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

}
