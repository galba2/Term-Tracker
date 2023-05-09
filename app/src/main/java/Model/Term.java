package Model;

import java.sql.Date;
import java.util.ArrayList;

public class Term {

    private int id;
    private String termTitle;
    private String termStart;
    private String termEnd;

    public Term(){

    }

    public Term(String termTitle, String termStart, String termEnd) {
        this.termTitle = termTitle;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    public String getDates(){
        return termStart + " - " + termEnd;
    }
}
