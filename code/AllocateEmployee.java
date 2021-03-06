import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peterzohdy on 26/11/2015.
 */
public class AllocateEmployee extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        List<String> list = new ArrayList<>();
        list.add("Project Allocation");
        list.add("Vacation");
        list.add("Other");
        list.add("Test");

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

        ComboBox employeesCbox = new ComboBox();
        employeesCbox.getItems().addAll(
                "Peter Zohdy",
                "Xinhai Lee",
                "Lasse Jørgensen"
        );


        ComboBox clientCBox = new ComboBox();
        clientCBox.setDisable(true);


        clientCBox.getItems().addAll(

                        "Not available",
                        "HM",
                        "Dataløn",
                        "KEA"
        );

        ComboBox<String> activityCbox = new ComboBox<String>(observableList);
        activityCbox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                if(newValue.equals("Project Allocation"))
                {
                    clientCBox.setDisable(false);
                } else {
                    clientCBox.setDisable(true);
                }
            }
        });

        clientCBox.setLayoutX(220);
        clientCBox.setLayoutY(160);
        clientCBox.setPrefWidth(155);
        clientCBox.setValue("Not available");

        DatePicker dateFromPicker = new DatePicker();
        dateFromPicker.setMinWidth(155);
        dateFromPicker.setPrefWidth(155);
        dateFromPicker.setOnAction(event -> {
            LocalDate dateFrom = dateFromPicker.getValue();
            System.out.println(dateFrom);
        });

        DatePicker dateToPicker = new DatePicker();
        dateToPicker.setMinWidth(155);
        dateToPicker.setPrefWidth(155);
        dateToPicker.setOnAction(event -> {
            LocalDate dateTo = dateToPicker.getValue();
            System.out.println(dateTo);

        });

        TextArea textArea = new TextArea();
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

        Pane root = new Pane();
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
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
