package model;

public class AddAnswerName
{
    private String name;

    public AddAnswerName() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AddAnswerName{" +
                "name='" + name + '\'' +
                '}';
    }
}
