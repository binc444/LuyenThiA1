package ntu.hieutm.appluyenthia1.models.dtos;

public class QuestionDTO {
  private int id;
  private String content;
  private String imagePath;
  private String[] answers;
  private int correctAnswer;

  public QuestionDTO(int id, String content, String imagePath, String[] answers, int correctAnswer) {
    this.id = id;
    this.content = content;
    this.imagePath = imagePath;
    this.answers = answers;
    this.correctAnswer = correctAnswer;
  }

  public int getId() { return id; }
  public String getContent() { return content; }
  public String getImagePath() { return imagePath; }
  public String[] getAnswers() { return answers; }
  public int getCorrectAnswer() { return correctAnswer; }
}
