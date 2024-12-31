package ntu.hieutm.appluyenthia1.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import ntu.hieutm.appluyenthia1.App;
import ntu.hieutm.appluyenthia1.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomeController {

  @FXML
  private ComboBox<String> cmbDonVi, cmbKhoa, cmbHangGPLX;
  @FXML
  private TextField txtSoBaoDanh;
  @FXML
  private Label lb_thongTinNguoiDung, lblLoaiGPLX, lblHoTen, lblNgaySinh, lblSoCCCD, lblDiaChi;

  private boolean daKiemTraThongTin = false; // Trạng thái kiểm tra thông tin

  // Xử lý khi nhấn nút "Đăng ký tài khoản thi"
  @FXML
  private void moManHinhDangKy() {
    try {
      App.switchScene("fxml/register.fxml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Xử lý khi nhấn nút "Kiểm tra thông tin thi"
  @FXML
  private void checkThongTin() {
    String soBaoDanh = txtSoBaoDanh.getText().trim();
    if (soBaoDanh.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng nhập số báo danh!", ButtonType.OK);
      alert.show();
      return;
    }

    try (Connection conn = DatabaseConnection.getConnnection("luyenthi_banglaixe", "root", "")) {
      String query = "SELECT * FROM nguoi_dung WHERE so_bao_danh = ?";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setString(1, soBaoDanh);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        lblLoaiGPLX.setText("Loại GPLX: " + rs.getString("loai_gplx"));
        lblHoTen.setText("Họ tên: " + rs.getString("ho_ten"));
        lblNgaySinh.setText("Ngày sinh: " + rs.getDate("ngay_sinh"));
        lblSoCCCD.setText("Số CCCD: " + rs.getString("so_cccd"));
        lblDiaChi.setText("Địa chỉ: " + rs.getString("dia_chi"));

        lb_thongTinNguoiDung.setTextFill(Color.BLACK);
        lb_thongTinNguoiDung.setText("Thông tin người dùng đã được kiểm tra.");
        daKiemTraThongTin = true;
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Không tìm thấy thông tin với số báo danh này!", ButtonType.OK);
        alert.show();
        daKiemTraThongTin = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Xử lý khi nhấn nút "Vào ôn luyện"
  @FXML
  private void vaoLamBai() {
    if (!daKiemTraThongTin) {
      lb_thongTinNguoiDung.setTextFill(Color.RED);
      lb_thongTinNguoiDung.setText("Vui lòng kiểm tra thông tin người dùng trước khi vào ôn luyện!");

      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Thông báo");
      alert.setHeaderText("Yêu cầu kiểm tra thông tin");
      alert.setContentText("Bạn cần nhấn nút 'Kiểm tra thông tin thi' trước khi vào ôn luyện.");
      alert.showAndWait();
    } else {
      try {
        // Lấy thông tin từ các Label trong HomeController
        String loaiGPLX = lblLoaiGPLX.getText().replace("Loại GPLX: ", "");
        String hoTen = lblHoTen.getText().replace("Họ tên: ", "");
        String ngaySinh = lblNgaySinh.getText().replace("Ngày sinh: ", "");
        String soCCCD = lblSoCCCD.getText().replace("Số CCCD: ", "");
        String diaChi = lblDiaChi.getText().replace("Địa chỉ: ", "");
        String soBaoDanh = txtSoBaoDanh.getText(); // Lấy giá trị từ TextField

        // Load fxml "view_lambai.fxml"
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/view_lambai.fxml"));
        Parent root = loader.load();

        // Lấy LamBaiController từ FXMLLoader
        LamBaiController lamBaiController = loader.getController();

        // Truyền thông tin người dùng vào LamBaiController
        lamBaiController.setUserData(loaiGPLX, hoTen, ngaySinh, soCCCD, diaChi, soBaoDanh);

        // Chuyển sang màn hình mới
        Scene scene = new Scene(root);
        App.primaryStage.setScene(scene);
        App.primaryStage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  // Xử lý khi nhấn nút "Hủy bỏ"
  @FXML
  private void closeApp() {
    // Hiển thị hộp thoại xác nhận trước khi đóng ứng dụng
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Xác nhận");
    alert.setHeaderText("Bạn có chắc chắn muốn thoát?");
    alert.setContentText("Nhấn OK để đóng ứng dụng, hoặc Cancel để quay lại.");

    if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
      System.exit(0); // Đóng ứng dụng
    }
  }

}
