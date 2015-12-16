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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peterzohdy on 15/12/2015.
 */
public class EditAllocationForm implements Inputforms {

    Stage sceneStage = new Stage();
    Scene scene;
    Pane root = new Pane();
    ComboBox<Employee> employeesCbox;
    ComboBox <Client> clientCBox;
    DatePicker dateToPicker;
    DatePicker dateFromPicker;
    TextArea textArea;

    Allocation allocation;

    ObservableList<Allocation> allocationList;

    public EditAllocationForm(Allocation allocation, ObservableList<Allocation> allocationList)
    {
        this.allocation = allocation;
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

        Button submitBtn = new Button("Change");
        submitBtn.setLayoutX(150);
        submitBtn.setLayoutY(425);
        submitBtn.setPrefWidth(100);
        Button backBtn = new Button("Cancel");
        backBtn.setLayoutX(325);
        backBtn.setLayoutY(460);
        backBtn.setOnAction(event1 -> close());
        Button deleteBtn = new Button("Delete Allocation");
        deleteBtn.setLayoutX(125);
        deleteBtn.setLayoutY(460);
        deleteBtn.setPrefWidth(150);
        deleteBtn.setOnAction(event -> {
            deleteBtnPressed();
        });

        employeesCbox = new ComboBox();
        employeesCbox.getItems().addAll(DataBase.getInstance().employeesToArrayList());
        //employeesCbox.setItems();


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
        dateFromPicker.setValue(LocalDate.parse(allocation.getDateFrom()));

        dateToPicker = new DatePicker();
        dateToPicker.setMinWidth(155);
        dateToPicker.setPrefWidth(155);
        dateToPicker.setValue(LocalDate.parse(allocation.getDateTo()));

        textArea = new TextArea();
        textArea.setPadding(new Insets(0,0,0,0));
        textArea.setPrefWidth(200);
        textArea.setPrefHeight(100);
        textArea.setText(allocation.getNotes());

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
        vBoxText.setPadding(new Insets(315, 0,0,100));
        vBoxText.getChildren().addAll(textArea);

        root.getChildren().addAll(
                allocateLbl,
                txtBoxLbl,
                vBoxText,
                hboxDateLbls,
                hBoxLbls,
                hboxDatePickers,
                hBoxCboxs,
                deleteBtn,
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
        int eventId = allocation.getEventId();
        int cpr = employeesCbox.getSelectionModel().getSelectedItem().getCpr();
        int cvr = clientCBox.getSelectionModel().getSelectedItem().getCvr();
        String dateFrom = dateFromPicker.getValue().toString();
        String dateTo = dateToPicker.getValue().toString();
        String notes = textArea.getText();


        Allocation changedAllocation = new Allocation(1, cpr, cvr, "", "", "", dateFrom, dateTo, notes);

        allocationList.clear();

        DataBase.getInstance().changeAllocation(eventId, changedAllocation);

        DataBase.getInstance().getAllocations(allocationList);

        close();
    }

    public void setAlert(String titleText,String headerText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.show();
    }

    public void show()
    {
        sceneStage.setResizable(false);
        sceneStage.show();
    }

    public void close()
    {

        sceneStage.close();
    }

    public void deleteBtnPressed()
    {
        int cpr = employeesCbox.getSelectionModel().getSelectedItem().getCpr();
        int cvr = clientCBox.getSelectionModel().getSelectedItem().getCvr();

        DataBase.getInstance().deleteAllocation(cpr, cvr);

        setAlert("Deleted", "Entry has been deleted");


    }

}

