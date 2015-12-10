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

        Label titleLbl = new Label("                  Insert Client Information");
        gridPane.setHalignment(titleLbl, HPos.LEFT);
        gridPane.setColumnSpan(titleLbl, 4);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Label companyRegNoLbl = new Label("Company VAT Number:");
        gridPane.setHalignment(companyRegNoLbl, HPos.RIGHT);
        TextField companyRegTxtField = new TextField();
        companyRegTxtField.setPromptText("ABC1234");

        Label companyNameLbl = new Label("Company Name:");
        gridPane.setHalignment(companyNameLbl, HPos.RIGHT);
        TextField companyNameTxtFld = new TextField();

        Label companyStreetLbl = new Label("Street:");
        gridPane.setHalignment(companyStreetLbl, HPos.RIGHT);
        TextField companyStreetTxtFld= new TextField();

        Label companyCityLbl = new Label("City:");
        gridPane.setHalignment(companyCityLbl, HPos.RIGHT);
        TextField companyCityTxtFld = new TextField();

        Label companyCountryLbl = new Label("Country:");
        gridPane.setHalignment(companyCountryLbl, HPos.RIGHT);
        TextField companyCountryTxtFld = new TextField();

        Label notesLbl = new Label("Notes:");
        gridPane.setHalignment(notesLbl, HPos.RIGHT);
        TextArea notesTxtArea = new TextArea();
        notesTxtArea.setPrefColumnCount(5);
        notesTxtArea.setPrefRowCount(5);
        gridPane.setColumnSpan(notesTxtArea, 1);
        gridPane.setRowSpan(notesTxtArea, 2);

        Button submitBtn = new Button("Submit");
        gridPane.setHalignment(submitBtn, HPos.LEFT);
        gridPane.setColumnSpan(submitBtn, 3);
        submitBtn.setPrefWidth(166);

        Button cancelBtn = new Button("Cancel");
        gridPane.setHalignment(cancelBtn, HPos.RIGHT);

        submitBtn.setOnAction(event -> {

            if      (companyRegTxtField.getText().isEmpty() ||
                    companyNameTxtFld.getText().isEmpty() ||
                    companyCityTxtFld.getText().isEmpty() ||
                    companyStreetTxtFld.getText().isEmpty() ||
                    companyCountryTxtFld.getText().isEmpty()) {

                setAlert("Input erro", "All fields must contain data");

            } else if
                    (isVatValid(companyRegTxtField) &&
                            !companyNameTxtFld.getText().isEmpty() &&
                            !companyCityTxtFld.getText().isEmpty() &&
                            !companyStreetTxtFld.getText().isEmpty() &&
                            !companyCountryTxtFld.getText().isEmpty())
            {
                setAlert("Saved", "Company has been stored");

            } else if(isVatValid(companyRegTxtField) == false) {
                setAlert("Input error", "VAT no must be in the correct format");
            }

        });


        gridPane.add(titleLbl, 0, 0);
        gridPane.add(companyRegNoLbl, 0, 5);
        gridPane.add(companyRegTxtField, 1, 5);
        gridPane.add(companyNameLbl, 0,6);
        gridPane.add(companyNameTxtFld, 1, 6);
        gridPane.add(companyStreetLbl, 0, 7);
        gridPane.add(companyStreetTxtFld, 1, 7);
        gridPane.add(companyCityLbl, 0, 8);
        gridPane.add(companyCityTxtFld, 1, 8);
        gridPane.add(companyCountryLbl, 0, 9);
        gridPane.add(companyCountryTxtFld, 1, 9);
        gridPane.add(notesLbl, 0, 10);
        gridPane.add(notesTxtArea, 1, 10);
        gridPane.add(submitBtn, 1, 12);
        gridPane.add(cancelBtn, 2, 14);

        Scene scene = new Scene(gridPane, 438, 500);
        primaryStage.setScene(scene);
        companyCityLbl.requestFocus();
        primaryStage.show();
        primaryStage.setTitle("New Client");
        primaryStage.setResizable(false);
    }

    public boolean isVatValid(TextField textField)
    {
        Boolean isVatValid = false;
        Boolean vatLenghtValid = false;
        String firstThree = null;
        String lastFour = null;

        if(textField.getText().length() == 7) {

            vatLenghtValid = true;
            firstThree = textField.getText().substring(0, 3);
            lastFour = textField.getText().substring(3, 7);

        } else {

            isVatValid = false;
        }

        if(vatLenghtValid && firstThree.matches("[a-z]*") && lastFour.matches("[0-9]+"))
        {
            isVatValid = true;
            String firstThreeToUpper = firstThree.toUpperCase();
            String companyCVR = firstThreeToUpper + lastFour;
            System.out.println(companyCVR);
        }

        return isVatValid;
    }

    public static void setAlert(String titleText,String headerText)
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.show();

    }
}
