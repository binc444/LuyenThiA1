package ntu.hieutm.appluyenthia1.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LamBaiController {

  @FXML
  private Button btn_cau1, btn_cau2, btn_cau3, btn_cau4, btn_cau5, btn_cau6;
  @FXML
  private Button btn_cau7, btn_cau8, btn_cau9, btn_cau10, btn_cau11, btn_cau12;
  @FXML
  private Button btn_cau13, btn_cau14, btn_cau15, btn_cau16, btn_cau17, btn_cau18;
  @FXML
  private Button btn_cau19, btn_cau20, btn_cau21, btn_cau22, btn_cau23, btn_cau24, btn_cau25;

  @FXML
  private Button btnDapAn1, btnDapAn2, btnDapAn3, btnDapAn4; // Các nút đáp án

  @FXML
  private Label lblLoaiGPLX, lblHoTen, lblNgaySinh, lblSoCCCD, lblDiaChi, lblSoBaoDanh;

  // Màu mặc định của các nút
  private static final String DEFAULT_COLOR = "#d3d3d3";
  // Màu khi nút được nhấn
  private static final String ACTIVE_COLOR = "#4CAF50";

  // Biến lưu trữ nút hiện tại đang được nhấn
  private Button currentActiveButton = null;
  // Biến lưu trữ nút đáp án hiện tại đang được nhấn
  private Button currentAnswerButton = null;

  private String currentSoBaoDanh; // Biến lưu số báo danh hiện tại

  @FXML
  public void initialize() {
    // Tạo mảng các nút câu hỏi
    Button[] buttons = {
        btn_cau1, btn_cau2, btn_cau3, btn_cau4, btn_cau5, btn_cau6,
        btn_cau7, btn_cau8, btn_cau9, btn_cau10, btn_cau11, btn_cau12,
        btn_cau13, btn_cau14, btn_cau15, btn_cau16, btn_cau17, btn_cau18,
        btn_cau19, btn_cau20, btn_cau21, btn_cau22, btn_cau23, btn_cau24, btn_cau25
    };

    // Đăng ký sự kiện cho tất cả các nút câu hỏi
    for (Button button : buttons) {
      registerButtonAction(button);
    }

    // Đăng ký sự kiện cho các nút đáp án
    registerAnswerButtonAction(btnDapAn1);
    registerAnswerButtonAction(btnDapAn2);
    registerAnswerButtonAction(btnDapAn3);
    registerAnswerButtonAction(btnDapAn4);
  }

  // Phương thức đăng ký hành động nhấn nút câu hỏi
  private void registerButtonAction(Button button) {
    button.setOnAction(event -> {
      // Nếu nút hiện tại đã được nhấn, reset lại màu của nó
      if (currentActiveButton != null && currentActiveButton != button) {
        currentActiveButton.setStyle("-fx-background-color: " + DEFAULT_COLOR + ";");
      }

      // Chuyển màu nút đang nhấn sang màu xanh lá
      button.setStyle("-fx-background-color: " + ACTIVE_COLOR + ";");

      // Cập nhật nút đang được nhấn
      currentActiveButton = button;
    });
  }

  // Phương thức đăng ký hành động nhấn nút đáp án
  private void registerAnswerButtonAction(Button button) {
    button.setOnAction(event -> {
      // Nếu có một nút đáp án khác đang được nhấn, reset lại màu của nó
      if (currentAnswerButton != null && currentAnswerButton != button) {
        currentAnswerButton.setStyle("-fx-background-color: " + DEFAULT_COLOR + ";");
      }

      // Chuyển màu nút đáp án đang nhấn sang màu xanh lá
      button.setStyle("-fx-background-color: " + ACTIVE_COLOR + ";");

      // Cập nhật nút đáp án đang được nhấn
      currentAnswerButton = button;
    });
  }

  // Phương thức nhận và hiển thị thông tin người dùng
  public void setUserData(String loaiGPLX, String hoTen, String ngaySinh, String soCCCD, String diaChi, String soBaoDanh) {
    lblLoaiGPLX.setText("Loại GPLX: " + loaiGPLX);
    lblHoTen.setText("Họ tên: " + hoTen);
    lblNgaySinh.setText("Ngày sinh: " + ngaySinh);
    lblSoCCCD.setText("Số CCCD: " + soCCCD);
    lblDiaChi.setText("Địa chỉ: " + diaChi);
    lblSoBaoDanh.setText("Số Báo Danh: " + soBaoDanh);
  }

}

