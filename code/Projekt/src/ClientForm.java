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

/**
 * Created by peterzohdy on 30/11/2015.
 */

public class ClientForm implements Inputforms
{
    Stage sceneStage = new Stage();
    Scene scene;

    TextField cvrTextField = new TextField();
    TextField companyNameTextField = new TextField();
    TextField phoneTextField = new TextField();
    TextField emailTextField = new TextField();

    GridPane gridPane;

    ObservableList<Client> clientList;

    //Takes the clientList with Client object in constructor
    public ClientForm(ObservableList<Client> clientList)
    {
        this.clientList = clientList;
        initializeScene();
    }

    //sets the UI scene
    public void initializeScene()
    {
        gridPane = new GridPane();
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
        cancelBtn.setOnAction(event1 -> close());

        submitBtn.setOnAction(event ->
        {
            checkInput();
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

        scene = new Scene(gridPane, 400, 400);
        sceneStage.setScene(scene);

        //To be able to have the window as a popup and not a new scene
        sceneStage.initModality(Modality.APPLICATION_MODAL);
        submitBtn.requestFocus(); // Workaround to disable focus default
    }

    //Validates the user input
    public void checkInput()
    {
        if (cvrTextField.getText().isEmpty() || companyNameTextField.getText().isEmpty())
        {
            setAlert("Input error", "All mandatory fields must contain data");
        }
        else if (isCVRValid(cvrTextField) && !companyNameTextField.getText().isEmpty())
        {
            setAlert("Saved", "Company has been stored");

            try {submitButtonPressed();

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(isCVRValid(cvrTextField) == false)
        {
            setAlert("Input error", "CVR no must be in the correct format");
        }
    }

    public void show() {
        sceneStage.setResizable(false);
        sceneStage.show();
    }

    public void close()
    {
        sceneStage.close();
    }

    //Validates the input
    public boolean isCVRValid(TextField textField)
    {
        Boolean isCvrNumeric = true;
        Boolean isCvrLenghtValid;
        Boolean cvrValidation = false;

        if(textField.getText().length() == 8)
        {
            isCvrLenghtValid = true;
        }
        else
        {
            isCvrLenghtValid = false;
        }

        try {
            //Tries to converts the textfield to an Integer. Exception is caught if the program fails to convert
            //and sets the boolean variable to false.

            Integer.parseInt(textField.getText());
        }
        catch (Exception e)
        {
            isCvrNumeric = false;
        }

        if(isCvrNumeric == true && isCvrLenghtValid == true)
        {
            cvrValidation = true;
        }
        return cvrValidation;
    }

    public void setAlert(String titleText,String headerText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.show();
    }

    public void submitButtonPressed()
    {

        int cvr = Integer.parseInt(cvrTextField.getText());
        String firstName = companyNameTextField.getText();
        int phone = Integer.parseInt(phoneTextField.getText());
        String email = emailTextField.getText();

        //clears the clientList to avoid the list being dublicated when a new value is inserted and the
        //method with the list is called again
        clientList.clear();

        DataBase.getInstance().addClientToDb(cvr, firstName, phone, email);

        //Needs to be called To be able to view the change directly without having to open and close the program
        DataBase.getInstance().getClients(clientList);

        close();
    }
}
