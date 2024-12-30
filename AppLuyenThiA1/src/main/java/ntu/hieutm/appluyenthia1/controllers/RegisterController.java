package ntu.hieutm.appluyenthia1.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ntu.hieutm.appluyenthia1.App;
import ntu.hieutm.appluyenthia1.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

  @FXML
  private TextField txtHoTen, txtSoCCCD, txtDiaChi;
  @FXML
  private DatePicker txtNgaySinh;
  @FXML
  private TextField txtSoBaoDanh;
  @FXML
  private ComboBox<String> cmbLoaiGPLX;

  @FXML
  private void dangKy() {
    String hoTen = txtHoTen.getText().trim();
    String ngaySinh = (txtNgaySinh.getValue() != null) ? txtNgaySinh.getValue().toString() : "";
    String soCCCD = txtSoCCCD.getText().trim();
    String diaChi = txtDiaChi.getText().trim();
    String loaiGPLX = cmbLoaiGPLX.getValue();
    String soBaoDanh = txtSoBaoDanh.getText().trim(); // Lấy giá trị từ người dùng

    if (hoTen.isEmpty() || ngaySinh.isEmpty() || soCCCD.isEmpty() || diaChi.isEmpty() || loaiGPLX == null || soBaoDanh.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng nhập đầy đủ thông tin!", ButtonType.OK);
      alert.show();
      return;
    }

    try (Connection conn = DatabaseConnection.getConnnection("luyenthi_banglaixe", "root", "")) {
      String query = "INSERT INTO nguoi_dung (loai_gplx, ho_ten, ngay_sinh, so_cccd, dia_chi, so_bao_danh) VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setString(1, loaiGPLX);
      stmt.setString(2, hoTen);
      stmt.setString(3, ngaySinh);
      stmt.setString(4, soCCCD);
      stmt.setString(5, diaChi);
      stmt.setString(6, soBaoDanh);

      stmt.executeUpdate();

      Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đăng ký thành công! Số báo danh: " + soBaoDanh, ButtonType.OK);
      alert.show();

      App.switchScene("fxml/view_home");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @FXML
  private void quayLaiHome() {
    try {
      App.switchScene("fxml/view_home.fxml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
