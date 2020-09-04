package model;

public class EducationModel {

    private String name;
    private String link;
    private String eligibility;
    private String overview;
    private String documentRequired;



    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDocumentRequired() {
        return documentRequired;
    }

    public void setDocumentRequired(String documentRequired) {
        this.documentRequired = documentRequired;
    }


    public EducationModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}