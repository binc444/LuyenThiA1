package ntu.hieutm.appluyenthia1.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ntu.hieutm.appluyenthia1.App;
import ntu.hieutm.appluyenthia1.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KetQuaController {
  @FXML
  private Label lblHoTen, lblNgaySinh, lblSoBaoDanh, lblSoCauDung, lblSoCauSai, lblSoCauChuaLam, lblKetQua;

  @FXML
  private Button btnLuuBaiThi, btnQuayLai;

  private String hoTen;
  private String ngaySinh;
  private String soBaoDanh;
  private int soCauDung;
  private int soCauSai;
  private int soCauChuaLam;
  private String ketQua;

  public void setResultData(String hoTen, String ngaySinh, String soBaoDanh, int soCauDung, int soCauSai, int soCauChuaLam, String ketQua) {
    this.hoTen = hoTen;
    this.ngaySinh = ngaySinh;
    this.soBaoDanh = soBaoDanh;
    this.soCauDung = soCauDung;
    this.soCauSai = soCauSai;
    this.soCauChuaLam = soCauChuaLam;
    this.ketQua = ketQua;

    lblHoTen.setText("Họ tên: " + hoTen);
    lblNgaySinh.setText("Ngày sinh: " + ngaySinh);
    lblSoBaoDanh.setText("Số Báo Danh: " + soBaoDanh);
    lblSoCauDung.setText("Số câu đúng: " + soCauDung);
    lblSoCauSai.setText("Số câu sai: " + soCauSai);
    lblSoCauChuaLam.setText("Số câu chưa làm: " + soCauChuaLam);
    lblKetQua.setText("Kết quả: " + ketQua);
  }

  @FXML
  private void luuBaiThi() {
    try (Connection conn = DatabaseConnection.getConnnection("luyenthi_a1", "root", "")) {
      // Kiểm tra nếu số báo danh đã tồn tại
      String checkQuery = "SELECT id FROM ket_qua_bai_thi WHERE so_bao_danh = ?";
      PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
      checkStmt.setString(1, soBaoDanh);
      ResultSet rs = checkStmt.executeQuery();

      if (rs.next()) {
        // Nếu đã tồn tại, cập nhật dữ liệu
        String updateQuery = "UPDATE ket_qua_bai_thi SET so_cau_dung = ?, so_cau_sai = ?, so_cau_chua_lam = ?, ket_qua = ? WHERE so_bao_danh = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
          updateStmt.setInt(1, soCauDung);
          updateStmt.setInt(2, soCauSai);
          updateStmt.setInt(3, soCauChuaLam);
          updateStmt.setString(4, ketQua);
          updateStmt.setString(5, soBaoDanh);
          updateStmt.executeUpdate();
        }
      } else {
        // Nếu chưa tồn tại, thêm mới dữ liệu
        String insertQuery = "INSERT INTO ket_qua_bai_thi (so_bao_danh, so_cau_dung, so_cau_sai, so_cau_chua_lam, ket_qua) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
          insertStmt.setString(1, soBaoDanh);
          insertStmt.setInt(2, soCauDung);
          insertStmt.setInt(3, soCauSai);
          insertStmt.setInt(4, soCauChuaLam);
          insertStmt.setString(5, ketQua);
          insertStmt.executeUpdate();
        }
      }

      rs.close();
      checkStmt.close();
      //Thông báo thành công
      javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
      alert.setTitle("Thông báo");
      alert.setHeaderText(null);
      alert.setContentText("Lưu bài thi thành công!");
      alert.showAndWait();

      System.out.println("Lưu bài thi thành công!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @FXML
  private void quayLaiTrangChu() {
    try {
      App.switchScene("fxml/view_home.fxml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
