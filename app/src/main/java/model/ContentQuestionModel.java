package model;

public class ContentQuestionModel
{
    private String Question;

    public ContentQuestionModel(String question) {
        this.Question = question;
    }

    public ContentQuestionModel() {
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

    @Override
    public String toString() {
        return "ContentQuestionModel{" +
                "Question='" + Question + '\'' +
                '}';
    }
}
