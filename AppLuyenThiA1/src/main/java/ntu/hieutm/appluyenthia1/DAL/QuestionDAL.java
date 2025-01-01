package ntu.hieutm.appluyenthia1.DAL;

import ntu.hieutm.appluyenthia1.models.dtos.QuestionDTO;
import ntu.hieutm.appluyenthia1.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAL {
  //truy vấn cơ sở dữ liệu để lấy tất cả các câu hỏi và đáp án.
  // retrun danh sách các đối tượng QuestionDTO chứa thông tin câu hỏi và đáp án.
  public List<QuestionDTO> getAllQuestions() {
    // Tạo danh sách để lưu trữ các câu hỏi
    List<QuestionDTO> questions = new ArrayList<>();
    try (
        // Kết nối đến cơ sở dữ liệu
        Connection conn = DatabaseConnection.getConnnection("luyenthi_banglaixe", "root", "");
    ) {
      // SQL Query để lấy câu hỏi và các đáp án từ cơ sở dữ liệu
      String query = "SELECT c.id, c.noi_dung, c.duong_dan_anh, " +
          "GROUP_CONCAT(d.noi_dung_dap_an ORDER BY d.thu_tu_dap_an) AS answers, " +
          "c.dap_an_dung FROM cau_hoi c " +
          "JOIN dap_an d ON c.id = d.id_cau_hoi GROUP BY c.id";

      // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
      PreparedStatement statement = conn.prepareStatement(query);

      // Thực thi câu lệnh truy vấn và lấy kết quả
      ResultSet rs = statement.executeQuery();

      // Lặp qua kết quả của truy vấn và tạo các đối tượng QuestionDTO
      while (rs.next()) {
        int id = rs.getInt("id"); // Lấy ID câu hỏi
        String content = rs.getString("noi_dung"); // Lấy nội dung câu hỏi
        // Kiểm tra và lấy đường dẫn ảnh (nếu có, nếu không thì sử dụng ảnh mặc định)
        String imagePath = rs.getString("duong_dan_anh") != null ? rs.getString("duong_dan_anh") : "default.png";

        // Lấy các đáp án và phân tách thành mảng từ chuỗi
        String[] answers = rs.getString("answers").split(",");

        // Lấy đáp án đúng
        int correctAnswer = rs.getInt("dap_an_dung");

        // Thêm câu hỏi vào danh sách
        questions.add(new QuestionDTO(id, content, imagePath, answers, correctAnswer));
      }
    } catch (SQLException e) {
      // In ra lỗi nếu có vấn đề khi thực thi câu lệnh SQL
      e.printStackTrace();
    }

    // Trả về danh sách câu hỏi đã lấy từ cơ sở dữ liệu
    return questions;
  }
}
