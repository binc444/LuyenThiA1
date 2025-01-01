package ntu.hieutm.appluyenthia1.BLL;

import ntu.hieutm.appluyenthia1.DAL.QuestionDAL;
import ntu.hieutm.appluyenthia1.models.dtos.QuestionDTO;

import java.util.List;

public class QuestionBLL {
  // Khởi tạo đối tượng QuestionDAL để tương tác với tầng dữ liệu
  private final QuestionDAL questionDAL = new QuestionDAL();

  public List<QuestionDTO> layCauHoi() {
    return questionDAL.getAllQuestions();
  }
}
