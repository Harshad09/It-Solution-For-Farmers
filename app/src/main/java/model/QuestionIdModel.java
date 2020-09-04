package model;

public class QuestionIdModel {

    public String questionId ;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public QuestionIdModel() {
    }

    @Override
    public String toString() {
        return "QuestionIdModel{" +
                "questionId='" + questionId + '\'' +
                '}';
    }
}
