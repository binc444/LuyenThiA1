package ntu.hieutm.appluyenthia1.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ntu.hieutm.appluyenthia1.App;
import ntu.hieutm.appluyenthia1.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class XemBaiLamController {

  @FXML
  private TextField txtSoBaoDanh;

  @FXML
  private Label lblHoTen;

  @FXML
  private Label lblNgaySinh;

  @FXML
  private Label lblSoCauDung;

  @FXML
  private Label lblSoCauSai;

  @FXML
  private Label lblSoCauChuaLam;

  @FXML
  private Label lblKetQua;


  @FXML
  private void checkBaiLam() {
    String soBaoDanh = txtSoBaoDanh.getText().trim();

    if (soBaoDanh.isEmpty()) {
      showAlert("Lỗi", "Vui lòng nhập số báo danh!", AlertType.ERROR);
      return;
    }

    try (Connection conn = DatabaseConnection.getConnnection("luyenthi_a1", "root", "")) {
      // Truy vấn bảng `nguoi_dung`
      String queryNguoiDung = "SELECT ho_ten, ngay_sinh FROM nguoi_dung WHERE so_bao_danh = ?";
      PreparedStatement psNguoiDung = conn.prepareStatement(queryNguoiDung);
      psNguoiDung.setString(1, soBaoDanh);
      ResultSet rsNguoiDung = psNguoiDung.executeQuery();

      if (rsNguoiDung.next()) {
        String hoTen = rsNguoiDung.getString("ho_ten");
        String ngaySinh = rsNguoiDung.getString("ngay_sinh");

        lblHoTen.setText("Họ tên: " + hoTen);
        lblNgaySinh.setText("Ngày sinh: " + ngaySinh);

        // Truy vấn bảng `ket_qua_bai_thi`
        String queryKetQua = "SELECT so_cau_dung, so_cau_sai, so_cau_chua_lam, ket_qua FROM ket_qua_bai_thi WHERE so_bao_danh = ?";
        PreparedStatement psKetQua = conn.prepareStatement(queryKetQua);
        psKetQua.setString(1, soBaoDanh);
        ResultSet rsKetQua = psKetQua.executeQuery();

        if (rsKetQua.next()) {
          int soCauDung = rsKetQua.getInt("so_cau_dung");
          int soCauSai = rsKetQua.getInt("so_cau_sai");
          int soCauChuaLam = rsKetQua.getInt("so_cau_chua_lam");
          String ketQua = rsKetQua.getString("ket_qua");

          lblSoCauDung.setText("Số câu đúng: " + soCauDung);
          lblSoCauSai.setText("Số câu sai: " + soCauSai);
          lblSoCauChuaLam.setText("Số câu chưa làm: " + soCauChuaLam);
          lblKetQua.setText("Kết quả: " + ketQua);
        } else {
          showAlert("Thông báo", "Không tìm thấy kết quả bài thi cho số báo danh này!", AlertType.INFORMATION);
          clearLabels();
        }
      } else {
        showAlert("Thông báo", "Không tìm thấy thông tin người dùng với số báo danh này!", AlertType.INFORMATION);
        clearLabels();
      }
    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Lỗi", "Đã xảy ra lỗi khi truy vấn dữ liệu!", AlertType.ERROR);
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

  private void showAlert(String title, String content, AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private void clearLabels() {
    lblHoTen.setText("");
    lblNgaySinh.setText("");
    lblSoCauDung.setText("");
    lblSoCauSai.setText("");
    lblSoCauChuaLam.setText("");
    lblKetQua.setText("");
  }
}
