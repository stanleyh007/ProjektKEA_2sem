import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peterzohdy on 26/11/2015.
 */
public class AllocateEmployeeForm implements Inputforms
{
    Stage sceneStage = new Stage();
    Scene scene;
    Pane root = new Pane();
    ComboBox<Employee> employeesCbox;
    ComboBox <Client> clientCBox;
    DatePicker dateToPicker;
    DatePicker dateFromPicker;
    TextArea textArea;

    ObservableList<Allocation> allocationList;

    public AllocateEmployeeForm(ObservableList<Allocation> allocationList) {

        this.allocationList = allocationList;

        initializeScene();
    }

    public void initializeScene()
    {
        List<String> list = new ArrayList<>();
        list.add("PROJECT ALLOCATION");
        list.add("---Vacation---");
        list.add("---Education---");
        list.add("---Maternity leave---");
        list.add("---Other---");

        ObservableList<String> observableList = FXCollections.observableArrayList(list);

        Label allocateLbl = new Label();
        Label employeeLbl = new Label();
        Label activityLbl = new Label();
        Label clientLbl = new Label();
        Label dateFromLbl = new Label();
        Label dateToLbl = new Label();
        Label txtBoxLbl = new Label();

        allocateLbl.setText("Allocate Employee");
        allocateLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        employeeLbl.setText("Choose Employee");
        activityLbl.setText("Choose Activity");
        clientLbl.setText("Choose Client");
        dateFromLbl.setText("Date from:");
        dateToLbl.setText("Date To:");
        txtBoxLbl.setText("Enter comment:");

        allocateLbl.setLayoutX(130);
        allocateLbl.setLayoutY(20);
        allocateLbl.fontProperty();
        clientLbl.setLayoutX(220);
        clientLbl.setLayoutY(140);

        employeeLbl.setPadding(new Insets(0, 0, 0, 25));
        activityLbl.setPadding(new Insets(0, 0, 0, -20));
        dateFromLbl.setPadding(new Insets(0, 0, 0, 25));
        dateToLbl.setPadding(new Insets(0, 0, 0, 30));
        txtBoxLbl.setPadding(new Insets(330, 0, 0, 100));

        Button submitBtn = new Button("Submit");
        submitBtn.setLayoutX(100);
        submitBtn.setLayoutY(455);
        submitBtn.setPrefWidth(200);
        Button backBtn = new Button("Cancel");
        backBtn.setLayoutX(325);
        backBtn.setLayoutY(455);
        backBtn.setOnAction(event1 -> close());

        employeesCbox = new ComboBox();
        employeesCbox.getItems().addAll(DataBase.getInstance().employeesToArrayList());

        clientCBox = new ComboBox();
        clientCBox.getItems().addAll(DataBase.getInstance().clientsToArrayList());
        clientCBox.setDisable(true);


        ComboBox<String> activityCbox = new ComboBox<String>(observableList);
        activityCbox.setPrefWidth(155);

        activityCbox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            System.out.println(newValue);
            if(newValue.equals("PROJECT ALLOCATION"))
            {
                clientCBox.setDisable(false);
            } else {
                clientCBox.setDisable(true);
            }
        });

        clientCBox.setLayoutX(220);
        clientCBox.setLayoutY(160);
        clientCBox.setPrefWidth(155);
        clientCBox.setPromptText("Not available");

        dateFromPicker = new DatePicker();
        dateFromPicker.setMinWidth(155);
        dateFromPicker.setPrefWidth(155);
        dateFromPicker.setOnAction(event ->
        {
            LocalDate dateFrom = dateFromPicker.getValue();
            System.out.println(dateFrom);
        });

        dateToPicker = new DatePicker();
        dateToPicker.setMinWidth(155);
        dateToPicker.setPrefWidth(155);
        dateToPicker.setOnAction(event ->
        {
            LocalDate dateTo = dateToPicker.getValue();
            System.out.println(dateTo);
        });

        textArea = new TextArea();
        textArea.setPadding(new Insets(0,0,0,0));
        textArea.setPrefWidth(200);
        textArea.setPrefHeight(100);

        HBox hBoxLbls = new HBox();
        hBoxLbls.setSpacing(100);
        hBoxLbls.setPadding(new Insets(60,0,0,0));
        hBoxLbls.getChildren().addAll(employeeLbl, activityLbl);

        HBox hBoxCboxs = new HBox();
        hBoxCboxs.setSpacing(40);
        hBoxCboxs.setPadding(new Insets(80,0,0,25));
        hBoxCboxs.getChildren().addAll(employeesCbox, activityCbox);

        HBox hboxDateLbls = new HBox();
        hboxDateLbls.setSpacing(100);
        hboxDateLbls.setPadding(new Insets(220, 0, 0, 0));
        hboxDateLbls.getChildren().addAll(dateFromLbl, dateToLbl);

        HBox hboxDatePickers = new HBox();
        hboxDatePickers.setSpacing(40);
        hboxDatePickers.setPadding(new Insets(240, 0, 0, 25));
        hboxDatePickers.getChildren().addAll(dateFromPicker, dateToPicker);

        VBox vBoxText = new VBox();
        vBoxText.setSpacing(0);
        vBoxText.setPadding(new Insets(350, 0,0,100));
        vBoxText.getChildren().addAll(textArea);

        root.getChildren().addAll(
                allocateLbl,
                txtBoxLbl,
                vBoxText,
                hboxDateLbls,
                hBoxLbls,
                hboxDatePickers,
                hBoxCboxs,
                submitBtn,
                backBtn,
                clientLbl,
                clientCBox
        );

        submitBtn.setOnAction(e -> {
            submitButtonPressed();
        });

        scene = new Scene(root, 400, 500);
        sceneStage.setScene(scene);

        sceneStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void submitButtonPressed()
    {

        int cpr = employeesCbox.getSelectionModel().getSelectedItem().getCpr();
        int cvr = clientCBox.getSelectionModel().getSelectedItem().getCvr();
        Date date_from = Date.valueOf(dateFromPicker.getValue());
        Date dateto = Date.valueOf(dateToPicker.getValue());
        String notes = textArea.getText();


        allocationList.clear();
        //Calls addEmployee method in DB class and inserts the entered input as parameters
        DataBase.getInstance().allocateEmployee(cpr, cvr, date_from, dateto, notes);

        setAlert("Allocation added", "Allocation successful!\n\n" + employeesCbox.getSelectionModel().getSelectedItem() +
        " is now allocated to " + clientCBox.getSelectionModel().getSelectedItem());

        DataBase.getInstance().getAllocations(allocationList);

        close();
    }

    public void show()
    {
        sceneStage.setResizable(false);
        sceneStage.show();
    }

    public void setAlert(String titleText,String headerText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.show();
    }

    public void close()
    {
        sceneStage.close();
    }

}

