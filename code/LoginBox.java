import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * Created by peterzohdy on 27/11/2015.
 */
public class LoginBox extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Button submitBtn = new Button("Log In");
        Button cancelBtn = new Button("Cancel");

        Label headingLbl = new Label("Enter Username & Password");
        Label usernameLbl = new Label("Username : ");
        Label passwordLbl = new Label("Password : ");
        Label helpLbl = new Label("Username: \"test\"\nPassword: \"test\"");

        PasswordField passwordField = new PasswordField();
        TextField usernameField = new TextField();

        headingLbl.setLayoutX(85);
        headingLbl.setLayoutY(45);
        headingLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        usernameField.setLayoutX(140);
        usernameField.setLayoutY(100);
        passwordField.setLayoutX(140);
        passwordField.setLayoutY(140);

        usernameLbl.setLayoutX(60);
        usernameLbl.setLayoutY(105);
        passwordLbl.setLayoutX(60);
        passwordLbl.setLayoutY(145);

        submitBtn.setLayoutX(140);
        submitBtn.setLayoutY(180);
        submitBtn.setPrefWidth(167);

        cancelBtn.setLayoutX(325);
        cancelBtn.setLayoutY(258);

        helpLbl.setLayoutX(15);
        helpLbl.setLayoutY(253);
        helpLbl.setTextFill(Color.RED);



        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(passwordField.getText().equals("test") && usernameField.getText().equals("test")) {

                    System.out.println("Loggin in");

                } else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Wrong input");
                            alert.setHeaderText("Wrong Username or Password");
                            alert.setContentText("Hint:\n\n Username: \"test\"\n Password:  \"test\"");
                            alert.show();
                    }
                passwordField.setText("");
            }

        });

        Group root = new Group();
        root.getChildren().addAll(
                headingLbl,
                usernameLbl,
                passwordLbl,
                passwordField,
                submitBtn,
                usernameField,
                cancelBtn,
                helpLbl);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Login");
        primaryStage.show();


    }
}
