package ntu.hieutm.appluyenthia1.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ntu.hieutm.appluyenthia1.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {
  @FXML
  private TextField txtSoBaoDanh;
  @FXML
  private TextField txtHoTen;
  @FXML
  private TextField txtNgaySinh;
  @FXML
  private TextField txtSoCCCD;
  @FXML
  private TextField txtDiaChi;
  @FXML
  private TextField txtMatKhau;

  // Xử lý đăng ký tài khoản
  @FXML
  private void handleDangKy() {
    String soBaoDanh = txtSoBaoDanh.getText();
    String hoTen = txtHoTen.getText();
    String ngaySinh = txtNgaySinh.getText();
    String soCCCD = txtSoCCCD.getText();
    String diaChi = txtDiaChi.getText();
    String matKhau = txtMatKhau.getText();

    if (soBaoDanh.isEmpty() || hoTen.isEmpty() || ngaySinh.isEmpty() || soCCCD.isEmpty() || diaChi.isEmpty() || matKhau.isEmpty()) {
      // Hiển thị cảnh báo nếu có trường trống
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Thông báo");
      alert.setHeaderText("Thông tin không đầy đủ");
      alert.setContentText("Vui lòng điền đầy đủ thông tin.");
      alert.showAndWait();
    } else {
      // Tiến hành đăng ký tài khoản vào cơ sở dữ liệu
      try (Connection conn = DatabaseConnection.getConnnection("luyenthia1", "root", "")) {
        String sql = "INSERT INTO nguoidung (soBaoDanh, hoTen, ngaySinh, soCCCD, diaChi, matKhau) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setString(1, soBaoDanh);
          pstmt.setString(2, hoTen);
          pstmt.setString(3, ngaySinh);
          pstmt.setString(4, soCCCD);
          pstmt.setString(5, diaChi);
          pstmt.setString(6, matKhau);

          int rowsAffected = pstmt.executeUpdate();
          if (rowsAffected > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Đăng ký thành công");
            alert.setHeaderText("Thông tin đăng ký");
            alert.setContentText("Đăng ký tài khoản thành công!");
            alert.showAndWait();
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Đã xảy ra lỗi trong quá trình đăng ký");
        alert.setContentText("Vui lòng thử lại.");
        alert.showAndWait();
      }
    }
  }
}
