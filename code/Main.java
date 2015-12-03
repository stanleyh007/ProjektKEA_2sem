import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Stormwind on 26/11/2015.
 */
public class Main extends Application
{
    ListView<String> list;
    Button showOverview, showAllOverview, login, exit;
    Text virksomhed;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(final Stage primaryStage) 
    {
        list = new ListView<>();

        GridPane gridLayout = new GridPane();
        gridLayout.setHgap(200);
        gridLayout.setVgap(10);
        gridLayout.setPadding(new Insets(50));
        gridLayout.setAlignment(Pos.CENTER);

        showOverview = new Button("Vis Oversigt");
        showOverview.setPrefSize(180, 20);
//        showOverview.setOnAction();

        showAllOverview = new Button("Vis Fuld Oversigt");
        showAllOverview.setPrefSize(180, 20);
//        showAllOverview.setOnAction();

        gridLayout.add(showOverview, 0, 1, 2, 1);
        gridLayout.add(showAllOverview, 0 ,2, 2, 1);

        BorderPane bp = new BorderPane();
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

        exit = new Button("Luk");
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

        login = new Button("Log ind");
        login.setPrefSize(100, 20);
//        login.setOnAction();

        virksomhed = new Text();
        virksomhed.setText("Appstract");
        virksomhed.setFont(Font.font("Times New Roman", 30));
        virksomhed.setFill(Color.NAVY);

        vBox.getChildren().add(nameLine());
        vBox.getChildren().add(login);
        return vBox;
    }

    public VBox nameLine()
    {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);

        virksomhed = new Text();
        virksomhed.setText("appstract");
        virksomhed.setFont(Font.font("Apple Chancery", 30));
        virksomhed.setFill(Color.NAVY);

        vBox.getChildren().add(virksomhed);
        return vBox;
    }
}
