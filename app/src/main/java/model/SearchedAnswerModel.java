package model;

import java.util.List;

public class SearchedAnswerModel
{
    private String answer;
    private String queId;
    private String userId;
    private long upvotes;
    private String imageUrl;
    private List<String> upvoters;

    public List<String> getUpvoters() {
        return upvoters;
    }

    public void setUpvoters(List<String> upvoters) {
        this.upvoters = upvoters;
    }

    public String getQuestionid() {
        return queId;
    }

    public void setQuestionid(String questionid) {
        this.queId = questionid;
    }

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userid) {
        this.userId = userid;
    }



    public SearchedAnswerModel() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImageurl() {
        return imageUrl;
    }

    public void setImageurl(String imageurl) {
        this.imageUrl = imageurl;
    }

    public long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(long upvotes) {
        this.upvotes = upvotes;
    }

}
