package ntu.hieutm.appluyenthia1.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

  @FXML
  private void handleDangKy() {
    try {
      // Load FXML của giao diện đăng ký
      FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Register.fxml"));
      AnchorPane registerPane = loader.load();

      // Hiển thị giao diện đăng ký
      Stage stage = new Stage();
      stage.setScene(new Scene(registerPane));
      stage.setTitle("Đăng ký tài khoản thi");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
