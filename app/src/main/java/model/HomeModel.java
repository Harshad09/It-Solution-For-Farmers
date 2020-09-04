package model;

import java.util.List;

public class HomeModel
{
    private String question;
    private List<String> answer;
    private List<String> user;
    private long upvotes;
    private String imageUrl;
    private List<String> upvoters;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public List<String> getUser() {
        return user;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }

    public long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(long upvotes) {
        this.upvotes = upvotes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getUpvoters() {
        return upvoters;
    }

    public void setUpvoters(List<String> upvoters) {
        this.upvoters = upvoters;
    }

    public HomeModel() {
    }
}

