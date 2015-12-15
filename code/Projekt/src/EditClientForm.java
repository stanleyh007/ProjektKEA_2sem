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
public class EditClientForm
{
    Stage sceneStage = new Stage();
    Scene scene;

    TextField cvrTextField = new TextField();
    TextField companyNameTextField = new TextField();
    TextField phoneTextField = new TextField();
    TextField emailTextField = new TextField();

    Client client;

    ObservableList<Client> clientList;

    public EditClientForm(Client client, ObservableList<Client> clientList)
    {
        this.client = client;
        this.clientList = clientList;

        initialize();
    }

    public void initialize()
    {
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
        //cvrTextField.setPromptText("12345678");
        cvrTextField.setText(Integer.toString(client.getCvr()));

        Label companyNameLbl = new Label("Company Name:");
        gridPane.setHalignment(companyNameLbl, HPos.RIGHT);
        companyNameTextField.setText(client.getCompanyName());

        Label phoneLbl = new Label("Phone:");
        gridPane.setHalignment(phoneLbl, HPos.RIGHT);
        phoneTextField.setText(Integer.toString(client.getPhone()));

        Label emailLbl = new Label("Email:");
        gridPane.setHalignment(emailLbl, HPos.RIGHT);
        emailTextField.setText(client.getEmail());

        Button submitBtn = new Button("Change");
        gridPane.setHalignment(submitBtn, HPos.LEFT);
        gridPane.setColumnSpan(submitBtn, 3);
        submitBtn.setPrefWidth(166);

        Button cancelBtn = new Button("Cancel");
        gridPane.setHalignment(cancelBtn, HPos.RIGHT);
        cancelBtn.setOnAction(e -> close());

        Button deleteBtn = new Button("Delete");
        gridPane.setHalignment(deleteBtn, HPos.LEFT);
        gridPane.setColumnSpan(deleteBtn, 3);
        deleteBtn.setPrefWidth(166);

        deleteBtn.setOnAction(e -> {
            deleteBtnPressed();
        });

        submitBtn.setOnAction(event ->
        {
            if (cvrTextField.getText().isEmpty() || companyNameTextField.getText().isEmpty())
            {
                setAlert("Input error", "All mandatory fields must contain data");
            }
            else if (isCVRValid(cvrTextField) && !companyNameTextField.getText().isEmpty())
            {
                setAlert("Saved", "Company has been stored");
                try {submitBtnPressed();}
                catch (SQLException e) {e.printStackTrace();}
            }
            else if(isCVRValid(cvrTextField) == false)
            {
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
        gridPane.add(deleteBtn, 1, 10);
        gridPane.add(cancelBtn, 4, 16);

        scene = new Scene(gridPane, 420, 430);
        sceneStage.setScene(scene);

        sceneStage.initModality(Modality.APPLICATION_MODAL);
        submitBtn.requestFocus(); // Workaround to disable focus default
    }

    public void show() {
        sceneStage.setResizable(false);
        sceneStage.show();
    }

    public void close()
    {
        sceneStage.close();
    }

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

        try {Integer.parseInt(textField.getText());}
        catch (Exception e) {isCvrNumeric = false;}

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

    public void submitBtnPressed() throws SQLException
    {
        int cvr = Integer.parseInt(cvrTextField.getText());
        String firstName = companyNameTextField.getText();
        int phone = Integer.parseInt(phoneTextField.getText());
        String email = emailTextField.getText();

        Client changedClient = new Client(cvr, firstName, phone, email);

        //Calls addEmployee method in DB class and inserts the entered input as parameters
        //DataBase.getInstance().addClientToDb(cvr, firstName, phone, email);
        clientList.clear();

        DataBase.getInstance().changeClient(cvr, changedClient);

        DataBase.getInstance().getClients(clientList);


    }

    public void deleteBtnPressed()
    {
        int cvr = Integer.parseInt(cvrTextField.getText());

        DataBase.getInstance().deleteClient(cvr);

        setAlert("Deleted", "Entry has been deleted");
    }
}
