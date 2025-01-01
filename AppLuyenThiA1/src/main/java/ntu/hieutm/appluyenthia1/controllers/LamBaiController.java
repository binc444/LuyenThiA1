package ntu.hieutm.appluyenthia1.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ntu.hieutm.appluyenthia1.BLL.QuestionBLL;
import ntu.hieutm.appluyenthia1.models.dtos.QuestionDTO;

import java.util.List;
import java.util.Objects;

public class LamBaiController {

  // Khai báo các nút câu hỏi
  @FXML
  private Button btn_cau1, btn_cau2, btn_cau3, btn_cau4, btn_cau5, btn_cau6;
  @FXML
  private Button btn_cau7, btn_cau8, btn_cau9, btn_cau10, btn_cau11, btn_cau12;
  @FXML
  private Button btn_cau13, btn_cau14, btn_cau15, btn_cau16, btn_cau17, btn_cau18;
  @FXML
  private Button btn_cau19, btn_cau20, btn_cau21, btn_cau22, btn_cau23, btn_cau24, btn_cau25;

  // Khai báo các nút đáp án
  @FXML
  private Button btnDapAn1, btnDapAn2, btnDapAn3, btnDapAn4;

  // Khai báo các Label để hiển thị thông tin người dùng
  @FXML
  private Label lblLoaiGPLX, lblHoTen, lblNgaySinh, lblSoCCCD, lblDiaChi, lblSoBaoDanh;

  // Label và ImageView để hiển thị câu hỏi và ảnh câu hỏi
  @FXML
  private Label lbl_CauHoi;
  @FXML
  private ImageView imgCauHoi;

  // Màu sắc mặc định của các nút
  private static final String DEFAULT_COLOR = "#d3d3d3";
  private static final String ACTIVE_COLOR = "#4CAF50";

  // Biến lưu trữ nút hiện tại đang được nhấn
  private Button currentActiveButton = null;
  private Button currentAnswerButton = null;

  // Mảng lưu chỉ số đáp án người dùng đã chọn
  private int[] userAnswers;

  // Tạo đối tượng BLL để lấy câu hỏi từ tầng BLL
  private final QuestionBLL questionBLL = new QuestionBLL();
  private List<QuestionDTO> questions;
  private Button[] questionButtons;

  // Phương thức khởi tạo khi Controller được khởi tạo
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

      // Khởi tạo mảng đáp án
      userAnswers = new int[25];
      for (int i = 0; i < userAnswers.length; i++) {
        userAnswers[i] = -1; // -1 nghĩa là chưa chọn đáp án
      }

      // Đăng ký sự kiện cho tất cả các nút câu hỏi để gọi phương thức loadCauHoi
      for (int i = 0; i < questionButtons.length; i++) {
        int index = i;
        questionButtons[i].setOnAction(event -> loadCauHoi(index));
      }

      // Đăng ký sự kiện cho các nút đáp án
      registerAnswerButtonAction(btnDapAn1);
      registerAnswerButtonAction(btnDapAn2);
      registerAnswerButtonAction(btnDapAn3);
      registerAnswerButtonAction(btnDapAn4);

      // Lấy danh sách câu hỏi từ tầng BLL
      questions = questionBLL.layCauHoi();
      if (questions == null || questions.isEmpty()) {
        System.out.println("Danh sách câu hỏi rỗng!");
      } else {
        System.out.println("Tải " + questions.size() + " câu hỏi.");
        loadCauHoi(0); // Load câu hỏi đầu tiên
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Phương thức để tải câu hỏi dựa trên chỉ số index
  private void loadCauHoi(int index) {
    try {
      if (questions == null || index < 0 || index >= questions.size()) {
        System.out.println("Không thể tải câu hỏi tại index: " + index);
        return;
      }

      QuestionDTO question = questions.get(index);
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

  // Phương thức đăng ký sự kiện cho các nút đáp án
  private void registerAnswerButtonAction(Button button) {
    button.setOnAction(event -> {
      // Tìm chỉ số của nút đáp án
      int answerIndex = -1;
      if (button == btnDapAn1) answerIndex = 0;
      else if (button == btnDapAn2) answerIndex = 1;
      else if (button == btnDapAn3) answerIndex = 2;
      else if (button == btnDapAn4) answerIndex = 3;

      // Lưu đáp án cho câu hỏi hiện tại
      if (currentActiveButton != null) {
        int questionIndex = java.util.Arrays.asList(questionButtons).indexOf(currentActiveButton);
        if (questionIndex != -1) {
          userAnswers[questionIndex] = answerIndex;
        }
      }

      // Đổi màu nút đáp án
      if (currentAnswerButton != null && currentAnswerButton != button) {
        currentAnswerButton.setStyle("-fx-background-color: " + DEFAULT_COLOR + ";");
      }
      button.setStyle("-fx-background-color: " + ACTIVE_COLOR + ";");
      currentAnswerButton = button;
    });
  }

  // Phương thức để thiết lập thông tin người dùng
  public void setUserData(String loaiGPLX, String hoTen, String ngaySinh, String soCCCD, String diaChi, String soBaoDanh) {
    lblLoaiGPLX.setText("Loại GPLX: " + loaiGPLX);
    lblHoTen.setText("Họ tên: " + hoTen);
    lblNgaySinh.setText("Ngày sinh: " + ngaySinh);
    lblSoCCCD.setText("Số CCCD: " + soCCCD);
    lblDiaChi.setText("Địa chỉ: " + diaChi);
    lblSoBaoDanh.setText("Số Báo Danh: " + soBaoDanh);
  }
}
