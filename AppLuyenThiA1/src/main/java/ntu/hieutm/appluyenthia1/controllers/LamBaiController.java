package ntu.hieutm.appluyenthia1.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import ntu.hieutm.appluyenthia1.App;
import ntu.hieutm.appluyenthia1.BLL.QuestionBLL;
import ntu.hieutm.appluyenthia1.models.dtos.QuestionDTO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LamBaiController {

  // Các nút câu hỏi
  @FXML
  private Button btn_cau1, btn_cau2, btn_cau3, btn_cau4, btn_cau5, btn_cau6;
  @FXML
  private Button btn_cau7, btn_cau8, btn_cau9, btn_cau10, btn_cau11, btn_cau12;
  @FXML
  private Button btn_cau13, btn_cau14, btn_cau15, btn_cau16, btn_cau17, btn_cau18;
  @FXML
  private Button btn_cau19, btn_cau20, btn_cau21, btn_cau22, btn_cau23, btn_cau24, btn_cau25;

  // Các nút đáp án
  @FXML
  private Button btnDapAn1, btnDapAn2, btnDapAn3, btnDapAn4;
  // Nút kết thúc
  @FXML
  private Button btnKetThuc;

  // Các Label thông tin người dùng
  @FXML
  private Label lblLoaiGPLX, lblHoTen, lblNgaySinh, lblSoCCCD, lblDiaChi, lblSoBaoDanh;

  // Label hiển thị câu hỏi, thời gian và ảnh
  @FXML
  private Label lbl_CauHoi, lblThoiGianConLai;
  @FXML
  private ImageView imgCauHoi;

  private static final String DEFAULT_COLOR = "#d3d3d3";
  private static final String ACTIVE_COLOR = "#4CAF50";

  private Button currentActiveButton = null;
  private Button currentAnswerButton = null;

  private int[] userAnswers;
  private final QuestionBLL questionBLL = new QuestionBLL();
  private List<QuestionDTO> dsCauHoi;
  private Button[] questionButtons;

  private int remainingTime = 19 * 60; // 19 phút
  private Timeline timeline;

  @FXML
  public void initialize() {
    try {
      // Tạo mảng các nút câu hỏi
      questionButtons = new Button[]{
          btn_cau1, btn_cau2, btn_cau3, btn_cau4, btn_cau5, btn_cau6,
          btn_cau7, btn_cau8, btn_cau9, btn_cau10, btn_cau11, btn_cau12,
          btn_cau13, btn_cau14, btn_cau15, btn_cau16, btn_cau17, btn_cau18,
          btn_cau19, btn_cau20, btn_cau21, btn_cau22, btn_cau23, btn_cau24, btn_cau25
      };

      userAnswers = new int[25];
      for (int i = 0; i < userAnswers.length; i++) {
        userAnswers[i] = -1; // -1 nghĩa là chưa chọn đáp án
      }

      for (int i = 0; i < questionButtons.length; i++) {
        int index = i;
        questionButtons[i].setOnAction(event -> loadCauHoi(index));
      }

      registerAnswerButtonAction(btnDapAn1);
      registerAnswerButtonAction(btnDapAn2);
      registerAnswerButtonAction(btnDapAn3);
      registerAnswerButtonAction(btnDapAn4);

      // Gọi câu hỏi ban đầu
      dsCauHoi = questionBLL.layCauHoi();
      if (dsCauHoi == null || dsCauHoi.isEmpty()) {
        System.out.println("Danh sách câu hỏi rỗng!");
      } else {
        loadCauHoi(0);
      }

      boDemThoiGian();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void boDemThoiGian() {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      if (remainingTime <= 0) {
        timeline.stop();
        endBaiThi();
      } else {
        remainingTime--;
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        lblThoiGianConLai.setText(String.format("Thời gian còn lại: %02d:%02d", minutes, seconds));
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void endBaiThi() {
    int soCauDung = 0;
    int soCauSai = 0;
    int soCauChuaLam = 0;

    System.out.println("Đang tính kết quả...");
    for (int i = 0; i < dsCauHoi.size(); i++) {
      QuestionDTO question = dsCauHoi.get(i);
      if (userAnswers[i] == -1) {
        soCauChuaLam++;
      } else if (question.getCorrectAnswer() - 1 == userAnswers[i]) { // Điều chỉnh so sánh
        soCauDung++;
      } else {
        soCauSai++;
      }
    }

    System.out.println("Số câu đúng: " + soCauDung);
    System.out.println("Số câu sai: " + soCauSai);
    System.out.println("Số câu chưa làm: " + soCauChuaLam);

    String ketQua = soCauDung >= 21 ? "Đạt" : "Không đạt";
    System.out.println("Kết quả: " + ketQua);

    showKetQua(soCauDung, soCauSai, soCauChuaLam, ketQua);
  }


  private void showKetQua(int soCauDung, int soCauSai, int soCauChuaLam, String ketQua) {
    try {
      System.out.println("Đang tải view_ketqua.fxml...");
      FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/view_ketqua.fxml"));
      Parent root = loader.load();
      System.out.println("Tải thành công!");

      KetQuaController controller = loader.getController();
      System.out.println("Thiết lập dữ liệu...");
      controller.setResultData(
          lblHoTen.getText().replace("Họ tên: ", ""),
          lblNgaySinh.getText().replace("Ngày sinh: ", ""),
          lblSoBaoDanh.getText().replace("Số Báo Danh: ", ""),
          soCauDung, soCauSai, soCauChuaLam, ketQua
      );

      System.out.println("Mở cửa sổ kết quả...");
      Stage stage = new Stage();
      stage.setTitle("Kết Quả Bài Thi");
      stage.setScene(new Scene(root));
      stage.show();

      // Đóng cửa sổ hiện tại
      ((Stage) lblThoiGianConLai.getScene().getWindow()).close();
    } catch (Exception e) {
      System.out.println("Lỗi khi mở cửa sổ kết quả!");
      e.printStackTrace();
    }
  }


  private void registerAnswerButtonAction(Button button) {
    button.setOnAction(event -> {
      int answerIndex = -1;
      if (button == btnDapAn1) answerIndex = 0;
      else if (button == btnDapAn2) answerIndex = 1;
      else if (button == btnDapAn3) answerIndex = 2;
      else if (button == btnDapAn4) answerIndex = 3;

      if (currentActiveButton != null) {
        int questionIndex = java.util.Arrays.asList(questionButtons).indexOf(currentActiveButton);
        if (questionIndex != -1) {
          userAnswers[questionIndex] = answerIndex;
        }
      }

      if (currentAnswerButton != null && currentAnswerButton != button) {
        currentAnswerButton.setStyle("-fx-background-color: " + DEFAULT_COLOR + ";");
      }
      button.setStyle("-fx-background-color: " + ACTIVE_COLOR + ";");
      currentAnswerButton = button;
    });
  }

  public void setUserData(String loaiGPLX, String hoTen, String ngaySinh, String soCCCD, String diaChi, String soBaoDanh) {
    lblLoaiGPLX.setText("Loại GPLX: " + loaiGPLX);
    lblHoTen.setText("Họ tên: " + hoTen);
    lblNgaySinh.setText("Ngày sinh: " + ngaySinh);
    lblSoCCCD.setText("Số CCCD: " + soCCCD);
    lblDiaChi.setText("Địa chỉ: " + diaChi);
    lblSoBaoDanh.setText("Số Báo Danh: " + soBaoDanh);
  }

  // Phương thức để tải câu hỏi dựa trên chỉ số index
  private void loadCauHoi(int index) {
    try {
      if (dsCauHoi == null || index < 0 || index >= dsCauHoi.size()) {
        System.out.println("Không thể tải câu hỏi tại index: " + index);
        return;
      }

      QuestionDTO question = dsCauHoi.get(index);
      lbl_CauHoi.setText("Câu hỏi: " + question.getContent());
      lbl_CauHoi.setWrapText(true);

      // Kiểm tra và tải ảnh câu hỏi (nếu có)
      String imagePath = "/ntu/hieutm/appluyenthia1/images/" + question.getImagePath();
      Image image = null;

      try {
        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
      } catch (Exception e) {
        System.out.println("Không tìm thấy ảnh: " + imagePath);
      }

      if (image != null) {
        imgCauHoi.setImage(image);
      } else {
        imgCauHoi.setImage(null);
      }

      // Hiển thị đáp án
      String[] answers = question.getAnswers();
      if (answers != null && answers.length >= 4) {
        btnDapAn1.setText(answers[0]);
        btnDapAn2.setText(answers[1]);
        btnDapAn3.setText(answers[2]);
        btnDapAn4.setText(answers[3]);

        btnDapAn1.setWrapText(true);
        btnDapAn2.setWrapText(true);
        btnDapAn3.setWrapText(true);
        btnDapAn4.setWrapText(true);
      }

      // Khôi phục trạng thái đáp án
      int selectedAnswer = userAnswers[index];
      Button[] answerButtons = {btnDapAn1, btnDapAn2, btnDapAn3, btnDapAn4};
      for (int i = 0; i < answerButtons.length; i++) {
        if (i == selectedAnswer) {
          answerButtons[i].setStyle("-fx-background-color: " + ACTIVE_COLOR + ";");
          currentAnswerButton = answerButtons[i];
        } else {
          answerButtons[i].setStyle("-fx-background-color: " + DEFAULT_COLOR + ";");
        }
      }

      // Đổi màu nút câu hỏi được chọn
      if (currentActiveButton != null && currentActiveButton != questionButtons[index]) {
        currentActiveButton.setStyle("-fx-background-color: " + DEFAULT_COLOR + ";");
      }
      questionButtons[index].setStyle("-fx-background-color: " + ACTIVE_COLOR + ";");
      currentActiveButton = questionButtons[index];
    } catch (Exception e) {
      System.out.println("Lỗi khi tải câu hỏi tại index: " + index);
      e.printStackTrace();
    }
  }

  // Xử lý sự kiện khi nhấn nút "Kết Thúc"
  @FXML
  private void ketThucThi(ActionEvent event) {
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Xác Nhận");
    confirmAlert.setHeaderText("Bạn có chắc chắn muốn kết thúc bài thi?");
    confirmAlert.setContentText("Chọn \"Có\" để kết thúc hoặc \"Không\" để tiếp tục.");

    Optional<ButtonType> result = confirmAlert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      System.out.println("Người dùng đã chọn kết thúc bài thi.");
      timeline.stop();
      endBaiThi();
    } else {
      System.out.println("Người dùng đã chọn không kết thúc bài thi.");
    }
  }

}
