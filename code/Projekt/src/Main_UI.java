import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Stormwind on 26/11/2015.
 */
public class Main_UI extends Application
{
    ListView<String> list;
    Button showOverview, showAllOverview, login, exit;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(final Stage primaryStage) throws Exception
    {
        list = new ListView<>();

        GridPane gridLayout = new GridPane();
        gridLayout.setHgap(200);
        gridLayout.setVgap(10);
        gridLayout.setPadding(new Insets(50));
        gridLayout.setAlignment(Pos.CENTER);

        ObservableList<String> options = FXCollections.observableArrayList(
                "Add Employee",
                "Delete Employee",
                "Edit Employee"
        );
        ComboBox comboBox = new ComboBox(options);
        comboBox.setPrefSize(180, 20);


        gridLayout.setGridLinesVisible(true);

        showOverview = new Button("Show Overview");
        showOverview.setPrefSize(180, 20);
//        showOverview.setOnAction();

        showAllOverview = new Button("Show Full Overview");
        showAllOverview.setPrefSize(180, 20);
//        showAllOverview.setOnAction();

        gridLayout.add(showOverview, 0, 1, 2, 1);
        gridLayout.add(showAllOverview, 0 ,2, 2, 1);
        gridLayout.add(comboBox, 0, 3, 2, 1);

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: deeppink");
        bp.setCenter(list);
        bp.setLeft(gridLayout);
        bp.setTop(loginLine());
        bp.setBottom(bottomLine());

        Scene scene = new Scene(bp, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Projekt Allokerings System ");
        primaryStage.show();

    }

    public HBox bottomLine()
    {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        exit = new Button("Exit");
        exit.setPrefSize(100, 20);
        exit.setOnAction(e -> System.exit(0));

        hBox.getChildren().add(exit);
        return hBox;
    }

    public VBox loginLine()
    {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER_RIGHT);

        login = new Button("Log in");
        login.setPrefSize(100, 20);
        login.setOnAction(event -> loginBox());

        vBox.getChildren().add(nameLine());
        vBox.getChildren().add(login);
        return vBox;
    }

    public VBox nameLine()
    {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);

        Image img = new Image("Logo.jpg");
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(54);
        imageView.setFitWidth(200);

        vBox.getChildren().add(imageView);
        return vBox;
    }

    public void loginBox()
    {
        final Stage loginBox = new Stage();
        loginBox.setTitle("Admin Login");
        loginBox.initModality(Modality.APPLICATION_MODAL);

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

        submitBtn.setOnAction(event -> {

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
        loginBox.setScene(scene);
        loginBox.show();
    }
}
