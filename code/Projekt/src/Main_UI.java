import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class Main_UI extends Application
{
    //ListView<String> list;

    // TableView and column objects
    TableView<Allocation> allocationTable;
    TableView<Employee> employeeTable;
    TableView<Client> clientTable;
    TableColumn employeeFirstName = new TableColumn("First Name");
    TableColumn employeeLastName = new TableColumn("Last Name");
    TableColumn employeeCPR = new TableColumn("CPR");
    TableColumn clientName = new TableColumn("Company Name");
    TableColumn clientCVR = new TableColumn("CVR");
    TableColumn phone = new TableColumn("Phone");
    TableColumn email = new TableColumn("Email");
    TableColumn client = new TableColumn("Client");
    TableColumn dateFrom = new TableColumn("Date from");
    TableColumn dateTo = new TableColumn("Date to");
    TableColumn notes = new TableColumn("Notes");

    TabPane tabPane;
    Tab employeeTab, clientTab, allocationTab;

    // ObservableList for TableView
    ObservableList<Allocation> allocationList = FXCollections.observableArrayList();
    Button showOverview, showAllOverview, login, exit;



    public static void main(String[] args)
    {
        launch(args);

    }

    public void start(final Stage primaryStage) throws Exception
    {
        DataBase dataBase = DataBase.getInstance();
        dataBase.createDB();

        // Load allocations from database into ObservableList
        dataBase.getAllocations(allocationList);

        setAllocationTable();
        setEmployeeTable();
        setClient();

        //TabPane Setup
        tabPane = new TabPane();
        employeeTab = new Tab("Employees");
        allocationTab = new Tab("Allocations");
        clientTab = new Tab("Clients");
        //Add tabs to tabpane
        tabPane.getTabs().addAll(allocationTab, employeeTab, clientTab);
        //main views inside tabpane
        allocationTab.setContent(allocationTable);
        employeeTab.setContent(employeeTable);
        clientTab.setContent(clientTable);

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

        showOverview = new Button("Show Overview");
        showOverview.setPrefSize(180, 20);

        showAllOverview = new Button("Show Full Overview");
        showAllOverview.setPrefSize(180, 20);

        gridLayout.add(showOverview, 0, 1, 2, 1);
        gridLayout.add(showAllOverview, 0 ,2, 2, 1);
        gridLayout.add(comboBox, 0, 3, 2, 1);

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: dimgray");
        bp.setCenter(tabPane);
        bp.setLeft(gridLayout);
        bp.setTop(loginLine());
        bp.setBottom(bottomLine());


        // Set items for TableViev (ObservableList)
        allocationTable.setItems(allocationList);


        Scene scene = new Scene(bp, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Projekt Allokerings System ");
        primaryStage.show();


    }

    public void setClient()
    {
        clientTable = new TableView<>();

        clientTable.getColumns().addAll(
                clientCVR,
                clientName,
                phone,
                email
        );
        clientName.setPrefWidth(150);
        clientCVR.setPrefWidth(100);
        phone.setPrefWidth(100);
        email.setPrefWidth(200);

        clientCVR.setCellValueFactory(new PropertyValueFactory<>("CPR"));
        clientName.setCellValueFactory(new PropertyValueFactory<>("Company Name"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    public void setEmployeeTable()
    {
        employeeTable = new TableView<>();

        employeeTable.getColumns().addAll(
                employeeCPR,
                employeeFirstName,
                employeeLastName,
                phone,
                email
        );
        email.setPrefWidth(200);
        employeeCPR.setPrefWidth(100);
        employeeFirstName.setPrefWidth(100);
        employeeLastName.setPrefWidth(150);

        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("CPR"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("First Name"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("Last Name"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("Email"));

    }

    public void setAllocationTable()
    {
        allocationTable = new TableView();

        // Add collumns to TableView
        allocationTable.getColumns().add(employeeFirstName);
        employeeFirstName.setPrefWidth(100);
        allocationTable.getColumns().add(employeeLastName);
        employeeLastName.setPrefWidth(150);
        allocationTable.getColumns().add(client);
        client.setPrefWidth(150);
        allocationTable.getColumns().add(dateFrom);
        dateFrom.setPrefWidth(100);
        allocationTable.getColumns().add(dateTo);
        dateTo.setPrefWidth(100);
        allocationTable.getColumns().add(notes);
        notes.setPrefWidth(300);


        // Set table columns to update from ObservableList
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("employeeFirstName"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<>("employeeLastName"));
        client.setCellValueFactory(new PropertyValueFactory<>("client"));
        dateFrom.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
        dateTo.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));


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
