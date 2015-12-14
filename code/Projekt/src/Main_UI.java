import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main_UI extends Application
{
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
    Button showOverview, showAllOverview, login, logout, exit, edit;
    GridPane gridLayout;
    BorderPane bp;
    Scene scene;
    Stage theStage;
    ComboBox cb1, cb2 ,cb3, cb4;
    TextField cprTextField, firstNameTextField, lastNameTextField, phoneTextFiled, emailTextField;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(final Stage primaryStage) throws Exception
    {
        theStage = primaryStage;
        newScene(mainStage());
    }

    public Scene mainStage()
    {
        table = new TableView();

        DataBase dataBase = DataBase.getInstance();
        dataBase.createDB();

        // Load allocations from database into ObservableList
        dataBase.getAllocations(allocationList);

        setAllocationTable();
        setEmployeeTable();
        setClientTable();

        setTabPane();
        setMainLayout();


        Scene scene = new Scene(setMainLayout(), 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Projekt Allokerings System ");
        primaryStage.show();


    }

    public Pane setMainLayout()
    {
        showOverview = new Button("Show Overview");
        showOverview.setPrefSize(150, 20);

        showAllOverview = new Button("Show Full Overview");
        showAllOverview.setPrefSize(150, 20);


        gridLayout = new GridPane();
        gridLayout.setHgap(200);
        gridLayout.setVgap(10);
        gridLayout.setPadding(new Insets(50));
        gridLayout.setAlignment(Pos.CENTER);

        gridLayout.add(showOverview, 0, 1, 2, 1);
        gridLayout.add(showAllOverview, 0 ,2, 2, 1);

        bp = new BorderPane();
        bp.setStyle("-fx-background-color: dimgray");
        bp.setCenter(tabPane);
        bp.setLeft(gridLayout);
        bp.setTop(loginLine());
        bp.setBottom(bottomLine());

        return bp;
    }

    public void setTabPane()
    {
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
    }

    public void setClientTable()
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

        // Set items for TableViev (ObservableList)
        allocationTable.setItems(allocationList);


        return scene;
    }

    public void newScene(Scene nextScene)
    {
        theStage.setScene(nextScene);
        theStage.show();
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

    public HBox bottomLine1()
    {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        exit = new Button("Exit");
        exit.setPrefSize(100, 20);
        exit.setOnAction(e -> System.exit(0));

        edit = new Button("Edit");
        edit.setPrefSize(100, 20);
//        edit.setOnAction();

        hBox.getChildren().addAll(exit, edit);
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

    public VBox loginLine2()
    {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER_RIGHT);

        logout = new Button("Log out");
        logout.setPrefSize(100, 20);
        logout.setOnAction(event -> newScene(mainStage()));
        vBox.getChildren().add(nameLine());
        vBox.getChildren().add(logout);
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
        Stage loginBox = new Stage();
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

        submitBtn.setOnAction(event ->
        {
            theStage.setScene(AdminStage());
            loginBox.close();
        });

        Scene scene = new Scene(root, 400, 300);
        loginBox.setScene(scene);
        loginBox.show();
    }

    public Scene AdminStage()
    {
        table = new TableView();

        DataBase dataBase = DataBase.getInstance();

        dataBase.getAllocations(allocationList);

        gridLayout = new GridPane();
        gridLayout.setHgap(200);
        gridLayout.setVgap(10);
        gridLayout.setPadding(new Insets(50));
        gridLayout.setAlignment(Pos.CENTER);

        ObservableList<String> employee = FXCollections.observableArrayList(
                "Add Employee",
                "Delete Employee",
                "Edit Employee"
        );
        ObservableList<String> clients = FXCollections.observableArrayList(
                "Add Client",
                "Delete Client",
                "Edit Client"
        );
        ObservableList<String> project = FXCollections.observableArrayList(
                "Add Project",
                "Delete project",
                "Edit Project"
        );
        ObservableList<String> admin = FXCollections.observableArrayList(
                "Add Admin",
                "Delete Admin"
        );

        cb1 = new ComboBox(employee);
        cb1.setPrefSize(200, 20);
        cb1.setOnAction(event1 ->
        {
            if(cb1.getValue() == "Add Employee")
            {
                cb1.setOnAction(event -> addNewEmployee());
            }
        });
        cb1.setPromptText("Employee");
        cb2 = new ComboBox(clients);
        cb2.setPromptText("Client");
        cb2.setPrefSize(200, 20);
        cb3 = new ComboBox(project);
        cb3.setPromptText("Project");
        cb3.setPrefSize(200, 20);
        cb4 = new ComboBox(admin);
        cb4.setPromptText("Login");
        cb4.setPrefSize(200, 20);

        gridLayout.add(cb1, 0, 1, 2, 1);
        gridLayout.add(cb2, 0, 2, 2, 1);
        gridLayout.add(cb3, 0, 3, 2, 1);
        gridLayout.add(cb4, 0, 4, 2, 1);

        bp = new BorderPane();
        bp.setStyle("-fx-background-color: dimgray");
        bp.setCenter(table);
        bp.setLeft(gridLayout);
        bp.setTop(loginLine2());
        bp.setBottom(bottomLine());

        table.getColumns().add(employeeFirstName);
        employeeFirstName.setPrefWidth(100);
        table.getColumns().add(employeeLastName);
        employeeLastName.setPrefWidth(100);
        table.getColumns().add(client);
        client.setPrefWidth(100);
        table.getColumns().add(dateFrom);
        dateFrom.setPrefWidth(100);
        table.getColumns().add(dateTo);
        dateTo.setPrefWidth(100);
        table.getColumns().add(notes);
        notes.setPrefWidth(400);

        table.setItems(allocationList);

        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("employeeFirstName"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<>("employeeLastName"));
        client.setCellValueFactory(new PropertyValueFactory<>("client"));
        dateFrom.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
        dateTo.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        scene = new Scene(bp, 1200, 800);
        theStage.setScene(scene);
        theStage.setTitle("Projekt Allokerings System ");
        theStage.show();

        return scene;
    }

    public void addNewEmployee()
    {
        cprTextField = new TextField();
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        phoneTextFiled = new TextField();
        emailTextField = new TextField();

        Stage addNewEmployee = new Stage();
        addNewEmployee.setTitle("Opret ny medarbejder");
        addNewEmployee.initModality(Modality.APPLICATION_MODAL);

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
        cprTextField.setPromptText("DDMMYYYYXXXX");

        Label firstNameLbl = new Label("Firstname:");
        gridPane.setHalignment(firstNameLbl, HPos.RIGHT);


        Label lastNameLbl = new Label("Lastname:");
        gridPane.setHalignment(lastNameLbl, HPos.RIGHT);


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


        gridPane.add(titleLbl, 0, 0);
        gridPane.add(cprLbl, 0, 5);
        gridPane.add(cprTextField, 1, 5);
        gridPane.add(firstNameLbl, 0, 6);
        gridPane.add(firstNameTextField, 1,6);
        gridPane.add(lastNameLbl, 0,7);
        gridPane.add(lastNameTextField, 1,7);
        gridPane.add(phoneLbl, 0, 8);
        gridPane.add(phoneTextFiled, 1,8);
        gridPane.add(emailLbl, 0, 9);
        gridPane.add(emailTextField, 1, 9);
        gridPane.add(submitBtn, 1, 10);
        gridPane.add(cancelBtn, 3, 14);


        Scene scene = new Scene(gridPane, 400, 410);
        addNewEmployee.setScene(scene);
        addNewEmployee.show();

        addNewEmployee.setResizable(false);
        firstNameLbl.requestFocus();

        submitBtn.setOnAction(e -> {
            if (    cprTextField.getText().isEmpty() ||
                    firstNameTextField.getText().isEmpty() ||
                    lastNameTextField.getText().isEmpty())
            {
                setAlert("Input error", "You need to fill out all mandatory fields");

            } else if (isNumeric(cprTextField) == false) {

                setAlert("Input error", "CPR must be numeric values");

            } else {

                try {
                    //If inputfields are entered correctly call submitbuttonpressed();
                    submitButtonPressed();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });


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

    public void submitButtonPressed() throws SQLException
    {

        int cpr = Integer.parseInt(cprTextField.getText());
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int phone = Integer.parseInt(phoneTextFiled.getText());
        String email = emailTextField.getText();

        //Calls addEmployee method in DB class and inserts the entered input as parameters
        DataBase.getInstance().addEmployeeToDb(cpr, firstName, lastName, phone, email);
    }
}
