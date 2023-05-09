package Model;

public class Note {

    private int id;
    private int noteCourse;
    private String noteText;

    public Note(){

    }

    public Note(int noteCourse, String noteText) {

        this.noteCourse = noteCourse;
        this.noteText = noteText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoteCourse() {
        return noteCourse;
    }

    public void setNoteCourse(int noteCourse) {
        this.noteCourse = noteCourse;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
