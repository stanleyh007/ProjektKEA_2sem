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
public class EditClientForm extends ClientForm implements Inputforms {

    Client client;

    ObservableList<Client> clientList;


    public EditClientForm(Client client, ObservableList<Client> clientList) {
        super(clientList);

        this.client = client;
        this.clientList = clientList;

        setFields();

        initializeScene();
        setDeleteBtn();


    }

    public void setFields() {
        System.out.println("Setting fields");

        cvrTextField.setText(Integer.toString(client.getCvr()));
        phoneTextField.setText(Integer.toString(client.getPhone()));
        emailTextField.setText(client.getEmail());
        companyNameTextField.setText(client.getCompanyName());
    }

    public void setDeleteBtn() {
        Button deleteBtn = new Button("Delete");
        gridPane.setHalignment(deleteBtn, HPos.LEFT);
        gridPane.setColumnSpan(deleteBtn, 3);
        deleteBtn.setPrefWidth(166);
        gridPane.add(deleteBtn, 1, 10);


        deleteBtn.setOnAction(e -> {
            int cvr = Integer.parseInt(cvrTextField.getText());
            DataBase.getInstance().deleteClient(cvr);
            setAlert("Deleted", "Entry has been deleted");
        });


    }
}