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

public class AddNewClientForm extends ClientForm /*implements Inputforms*/ {

    ObservableList<Client> clientList;

    public AddNewClientForm(ObservableList<Client> clientList) {
        super(clientList);

        this.clientList = clientList;

        initializeScene();
    }
}