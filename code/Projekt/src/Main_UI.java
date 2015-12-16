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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main_UI extends Application
{
        DataBase dataBase;

        // TableView and column objects
        TableView<Allocation> allocationTable;
        TableView<Employee> employeeTable;
        TableView<Client> clientTable;
        TableColumn employeeFirstName = new TableColumn("First Name");
        TableColumn employeeLastName = new TableColumn("Last Name");
        TableColumn allocationFirstName = new TableColumn("First Name");
        TableColumn allocationLastName = new TableColumn("Last Name");
        TableColumn employeeCPR = new TableColumn("CPR");
        TableColumn clientName = new TableColumn("Company Name");
        TableColumn clientCVR = new TableColumn("CVR");
        TableColumn clientPhone = new TableColumn("Phone");
        TableColumn employeePhone = new TableColumn("Phone");
        TableColumn employeeEmail = new TableColumn("Email");
        TableColumn clientEmail = new TableColumn("Email");
        TableColumn client = new TableColumn("Client");
        TableColumn dateFrom = new TableColumn("Date from");
        TableColumn dateTo = new TableColumn("Date to");
        TableColumn notes = new TableColumn("Notes");

        TabPane tabPane;
        Tab employeeTab, clientTab, allocationTab;

        // ObservableList for TableView
        ObservableList<Allocation> allocationList = FXCollections.observableArrayList();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        ObservableList<Client> clientList = FXCollections.observableArrayList();

        Button showOverview, showAllOverview, login, logout, exit, editEmployee, editAllocation, editClient, addEmployee, addClient, addProject, addLogin;
        GridPane gridLayout, sideLayout;
        BorderPane bp;
        Scene scene;
        Stage theStage;



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
            dataBase = DataBase.getInstance();
            dataBase.createDB();

            // Load allocations from database into ObservableList
            dataBase.getAllocations(allocationList);
            dataBase.getEmployees(employeeList);
            dataBase.getClients(clientList);

            setClientTable();
            setEmployeeTable();
            setAllocationTable();

            setTabPane();
            setMainLayout();

            scene = new Scene(setMainLayout(), 1200, 800);
            theStage.setScene(scene);
            theStage.setTitle("Project Allocation System");
            theStage.show();
            return scene;
        }

        public Pane setMainLayout()
        {
            showOverview = new Button("Show Overview");
            showOverview.setPrefSize(150, 20);

            showAllOverview = new Button("Show Full Overview");
            showAllOverview.setPrefSize(150, 20);

            gridLayout = new GridPane();
            gridLayout.setVgap(10);
            gridLayout.setPadding(new Insets(0, 30, 30, 30));
            gridLayout.setAlignment(Pos.TOP_CENTER);

            gridLayout.add(showOverview, 0, 1, 2, 1);
            gridLayout.add(showAllOverview, 0 ,2, 2, 1);

            bp = new BorderPane();
            bp.setStyle("-fx-background-color: dimgray");
            bp.setCenter(tabPane);
            bp.setLeft(gridLayout);
            bp.setRight(setSidePane());
            bp.setTop(loginLine());
            bp.setBottom(bottomLine());
            return bp;
        }

        public Pane setSidePane()
        {
            sideLayout = new GridPane();
            sideLayout.setVgap(10);
            sideLayout.setPadding(new Insets(0, 50, 50, 50));
            sideLayout.setAlignment(Pos.TOP_CENTER);

            login = new Button("Log in");
            login.setPrefSize(100, 20);
            login.setOnAction(event -> loginBox());

            exit = new Button("Exit");
            exit.setPrefSize(100, 20);
            exit.setOnAction(e -> System.exit(0));

            sideLayout.add(login, 0, 1, 2, 1);
            sideLayout.add(exit, 0, 2 ,2 ,1);
            return sideLayout;
        }

        public Pane setAdminPane()
        {
            sideLayout = new GridPane();
            sideLayout.setVgap(10);
            sideLayout.setPadding(new Insets(0, 30, 30, 30));
            sideLayout.setAlignment(Pos.TOP_CENTER);

            logout = new Button("Log out");
            logout.setPrefSize(100, 20);
            logout.setOnAction(event ->
            {
                dataBase = DataBase.getInstance();
                setAllocationTable();
                setEmployeeTable();
                setClientTable();

                setTabPane();
                setMainLayout();

                scene = new Scene(setMainLayout(), 1200, 800);
                theStage.setScene(scene);
                theStage.setTitle("Project Allocation System");
                theStage.show();
            });

            exit = new Button("Exit");
            exit.setPrefSize(100, 20);
            exit.setOnAction(e -> System.exit(0));

            editEmployee = new Button("Edit Employee");
            editEmployee.setPrefSize(130, 20);
            editEmployee.setOnAction(e -> editEmployee());

            editAllocation = new Button("Edit Allocation");
            editAllocation.setPrefSize(130, 20);
            editAllocation.setOnAction(e -> editAllocation());

            editClient = new Button("Edit Client");
            editClient.setPrefSize(130, 20);
            editClient.setOnAction(e -> editClient());

            sideLayout.add(logout, 0, 1, 2, 1);
            sideLayout.add(exit, 0, 2, 2, 1);
            sideLayout.add(editAllocation, 0, 8, 2, 1);
            sideLayout.add(editEmployee, 0, 10, 2, 1);
            sideLayout.add(editClient, 0 ,12, 2, 1);
            return sideLayout;
        }


        public void setTabPane()
        {
            //TabPane Setup
            tabPane = new TabPane();
            employeeTab = new Tab("Employees");
            employeeTab.setClosable(false);
            allocationTab = new Tab("Allocations");
            allocationTab.setClosable(false);
            clientTab = new Tab("Clients");
            clientTab.setClosable(false);
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
                    clientPhone,
                    clientEmail
            );
            clientName.setPrefWidth(150);
            clientCVR.setPrefWidth(100);
            clientPhone.setPrefWidth(100);
            clientEmail.setPrefWidth(200);

            clientCVR.setCellValueFactory(new PropertyValueFactory<>("cvr"));
            clientName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            clientPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            clientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

            clientTable.setItems(clientList);

        }

        public void setEmployeeTable()
        {
            employeeTable = new TableView<>();

            employeeTable.getColumns().addAll(
                    employeeCPR,
                    employeeFirstName,
                    employeeLastName,
                    employeePhone,
                    employeeEmail
            );
            employeeEmail.setPrefWidth(200);
            employeeCPR.setPrefWidth(100);
            employeeFirstName.setPrefWidth(100);
            employeeLastName.setPrefWidth(150);

            employeeCPR.setCellValueFactory(new PropertyValueFactory<>("cpr"));
            employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            employeeLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

            employeeTable.setItems(employeeList);

        }

        public void setAllocationTable()
        {
            allocationTable = new TableView();

            // Add collumns to TableView
            allocationTable.getColumns().add(allocationFirstName);
            employeeFirstName.setPrefWidth(100);
            allocationTable.getColumns().add(allocationLastName);
            employeeLastName.setPrefWidth(150);
            allocationTable.getColumns().add(client);
            client.setPrefWidth(150);
            allocationTable.getColumns().add(dateFrom);
            dateFrom.setPrefWidth(80);
            allocationTable.getColumns().add(dateTo);
            dateTo.setPrefWidth(80);
            allocationTable.getColumns().add(notes);
            notes.setPrefWidth(250);


            // Set table columns to update from ObservableList
            allocationFirstName.setCellValueFactory(new PropertyValueFactory<>("employeeFirstName"));
            allocationLastName.setCellValueFactory(new PropertyValueFactory<>("employeeLastName"));
            client.setCellValueFactory(new PropertyValueFactory<>("client"));
            dateFrom.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
            dateTo.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
            notes.setCellValueFactory(new PropertyValueFactory<>("notes"));

            // Set items for TableViev (ObservableList)
            allocationTable.setItems(allocationList);
        }

        public void newScene(Scene nextScene)
        {
            theStage.setScene(nextScene);
            theStage.show();
        }

        public HBox bottomLine()
        {
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(15, 15, 15, 50));
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER_RIGHT);

            return hBox;
        }

        public VBox loginLine()
        {
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(15));
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER_RIGHT);

            vBox.getChildren().add(nameLine());
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
            cancelBtn.setOnAction(event1 -> loginBox.close());

            helpLbl.setLayoutX(15);
            helpLbl.setLayoutY(253);
            helpLbl.setTextFill(Color.RED);

            submitBtn.setOnAction(event ->
            {
                if(passwordField.getText().equals("test") && usernameField.getText().equals("test"))
                {
                    System.out.println("Login in");
                }
                else
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
            setAllocationTable();
            setEmployeeTable();
            setClientTable();

            setTabPane();
            setAdminLayout();

            scene = new Scene(bp, 1200, 800);
            theStage.setScene(scene);
            theStage.setTitle("Project Allocation System");
            theStage.show();
            return scene;
        }

        public Pane setAdminLayout()
        {
            addClient = new Button("Add Client");
            addClient.setPrefSize(150, 20);
            addClient.setOnAction(event -> addClientAction());

            addEmployee = new Button("Add Employee");
            addEmployee.setPrefSize(150, 20);
            addEmployee.setOnAction(event -> addEmployeeAction());

            addProject = new Button("Project Allocate");
            addProject.setPrefSize(150, 20);
            addProject.setOnAction(event -> allocateEmployeeAction());

            addLogin = new Button("Add New Admin");
            addLogin.setPrefSize(150, 20);

            gridLayout = new GridPane();
            gridLayout.setVgap(10);
            gridLayout.setPadding(new Insets(0, 30, 30, 30));
            gridLayout.setAlignment(Pos.TOP_CENTER);

            gridLayout.add(addProject, 0, 1, 2, 1);
            gridLayout.add(addEmployee, 0 ,2, 2, 1);
            gridLayout.add(addClient, 0, 3, 2, 1);
            gridLayout.add(addLogin, 0, 4, 2, 1);

            bp = new BorderPane();
            bp.setStyle("-fx-background-color: dimgray");
            bp.setCenter(tabPane);
            bp.setLeft(gridLayout);
            bp.setRight(setAdminPane());
            bp.setTop(loginLine());
            bp.setBottom(bottomLine());
            return bp;
        }


        public void addEmployeeAction() {
            AddNewEmployeeForm addNewEmployeeForm = new AddNewEmployeeForm(employeeList);
            addNewEmployeeForm.show();
        }

        public void addClientAction()
        {
            AddNewClientForm addNewClientForm = new AddNewClientForm(clientList);
            addNewClientForm.show();
        }

        public void allocateEmployeeAction()
        {
            AllocateEmployeeForm allocateEmployeeForm = new AllocateEmployeeForm(allocationList);
            allocateEmployeeForm.show();
        }

        public void editEmployee() {

            EditEmployeeForm editEmployeeForm = new EditEmployeeForm(employeeTable.getSelectionModel().getSelectedItem(), employeeList);
            editEmployeeForm.show();
        }

        public void editClient() {

            EditClientForm editClientForm = new EditClientForm(clientTable.getSelectionModel().getSelectedItem(), clientList);
            editClientForm.show();
        }

        public void editAllocation() {

            EditAllocationForm editAllocationForm = new EditAllocationForm(allocationTable.getSelectionModel().getSelectedItem(), allocationList);
            editAllocationForm.show();
        }

}
