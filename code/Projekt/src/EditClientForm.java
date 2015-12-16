import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Button;

/**
 * Created by Lasse Jensen on 15-12-2015.
 */


public class EditClientForm extends ClientForm {

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

    //Setting these fields j
    public void setFields() {

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

    //Overriding the submitButtonPressed method to be able to call a different method in Database class.
    @Override
    public void submitButtonPressed()
    {
        int cvr = Integer.parseInt(cvrTextField.getText());
        String firstName = companyNameTextField.getText();
        int phone = Integer.parseInt(phoneTextField.getText());
        String email = emailTextField.getText();

        Client changedClient = new Client(cvr, firstName, phone, email);

        clientList.clear();

        DataBase.getInstance().changeClient(cvr, changedClient);

        DataBase.getInstance().getClients(clientList);

        close();
    }
}