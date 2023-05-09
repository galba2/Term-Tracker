package Model;

public class Assessment {

    private int id;
    private String title;
    private String assessmentStart;
    private String assessmentEnd;
    private String assessmentType;
    private int assessmentCourse;

    public Assessment(){
    }

    public Assessment(String title, String assessmentStart, String assessmentEnd, String assessmentType, int assessmentCourse) {
        this.title = title;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.assessmentType = assessmentType;
        this.assessmentCourse = assessmentCourse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(String assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public String getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(String assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getAssessmentCourse() {
        return assessmentCourse;
    }

    public void setAssessmentCourse(int assessmentCourse) {
        this.assessmentCourse = assessmentCourse;
    }

    public String getDates(){
        return assessmentStart + " - " + assessmentEnd;
    }
}
