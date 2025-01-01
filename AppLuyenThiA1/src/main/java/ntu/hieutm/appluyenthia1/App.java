package ntu.hieutm.appluyenthia1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

  public static Stage primaryStage;

  @Override
  public void start(Stage stage) throws IOException {
    primaryStage = stage; // Gán Stage chính
    switchScene("fxml/view_home.fxml");
  }

  public static void switchScene(String fxmlFile) throws IOException {
    FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFile));
    Scene scene = new Scene(loader.load());

    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setScene(scene); // Sử dụng Stage chính
    primaryStage.show();
  }



  public static void main(String[] args) {
    launch(args);
  }
}
