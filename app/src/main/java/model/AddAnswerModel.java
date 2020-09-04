package model;

public class AddAnswerModel
{
    private String question;


    public AddAnswerModel() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }





    @Override
    public String toString() {
        return "AddAnswerModel{" +
                "question='" + question + '\'' +
                '}';
    }
}
