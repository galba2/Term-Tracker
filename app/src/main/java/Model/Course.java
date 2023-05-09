package Model;

import java.sql.Date;
import java.util.ArrayList;

public class Course {

    private int id;
    private String courseTitle;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseName;
    private String coursePhone;
    private String courseEmail;
    private int courseTerm;

    public Course(){

    }

    public Course(String courseTitle, String courseStart, String courseEnd, String courseStatus,
                  String courseName, String coursePhone, String courseEmail, int courseTerm) {

        this.courseTitle = courseTitle;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseName = courseName;
        this.coursePhone = coursePhone;
        this.courseEmail = courseEmail;
        this.courseTerm = courseTerm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePhone() {
        return coursePhone;
    }

    public void setCoursePhone(String coursePhone) {
        this.coursePhone = coursePhone;
    }

    public String getCourseEmail() {
        return courseEmail;
    }

    public void setCourseEmail(String courseEmail) {
        this.courseEmail = courseEmail;
    }

    public int getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(int courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getDates(){
        return courseStart + " - " + courseEnd;
    }
}
