package ntu.hieutm.appluyenthia1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ntu.hieutm.appluyenthia1.utils.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;

public class App extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/view_home.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("APP luyện thi A1");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    //launch();
    Connection cnn = DatabaseConnection.getConnnection("luyenthia1","root","");
    System.out.println(cnn);
  }
}

