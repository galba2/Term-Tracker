package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;

import Model.Assessment;
import Model.Course;
import Model.Note;
import Model.Term;

public class DatabaseAccess extends SQLiteOpenHelper {

    private final static String TAG = "DatabaseAccess";

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "database.db";

    private static DatabaseAccess mDatabaseAccess;

    public static DatabaseAccess getInstance(Context context) {
        if (mDatabaseAccess == null) {
            mDatabaseAccess = new DatabaseAccess(context);
        }
        return mDatabaseAccess;
    }

    private DatabaseAccess(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //~~~SETUP TABLES~~~

    //Setup term table
    private static final class TermTable{
        private static final String TABLE = "terms";
        private static final String COL_ID = "id";
        private static final String COL_TITLE = "title";
        private static final String COL_START = "start";
        private static final String COL_FINISH = "finish";
    }

    //Setup course table
    private static final class CourseTable {
        private static final String TABLE = "courses";
        private static final String COL_ID = "id";
        private static final String COL_TITLE = "title";
        private static final String COL_START = "start";
        private static final String COL_FINISH = "finish";
        private static final String COL_STATUS = "status";
        private static final String COL_NAME = "name";
        private static final String COL_PHONE = "phone";
        private static final String COL_EMAIL = "email";
        private static final String COL_TERM = "term";
    }

    //Setup assessment table
    private static final class AssessmentTable{
        private static final String TABLE = "assessments";
        private static final String COL_ID = "id";
        private static final String COL_TITLE = "title";
        private static final String COL_START = "start";
        private static final String COL_FINISH = "finish";
        private static final String COL_TYPE = "type";
        private static final String COL_COURSE = "course";
    }

    //Setup  note table
    private static final class NoteTable{
        private static final String TABLE = "note";
        private static final String COL_ID = "id";
        private static final String COL_COURSE = "course";
        private static final String COL_TEXT = "text";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //CREATE TABLES//

        // Create terms table
        db.execSQL("create table " + TermTable.TABLE + " (" +
                    TermTable.COL_ID + " integer primary key autoincrement, " +
                    TermTable.COL_TITLE + ", " +
                    TermTable.COL_START + ", " +
                    TermTable.COL_FINISH + ")");

        //Create course table with foreign key that cascade deletes
        db.execSQL("create table " + CourseTable.TABLE + " (" +
                    CourseTable.COL_ID + " integer primary key autoincrement, " +
                    CourseTable.COL_TITLE + ", " +
                    CourseTable.COL_START + ", " +
                    CourseTable.COL_FINISH + ", " +
                    CourseTable.COL_STATUS + ", " +
                    CourseTable.COL_NAME + ", " +
                    CourseTable.COL_PHONE + ", " +
                    CourseTable.COL_EMAIL + ", " +
                    CourseTable.COL_TERM + ", " +
                    "foreign key(" + CourseTable.COL_TERM + ") references " +
                    TermTable.TABLE + "(" + TermTable.COL_ID + ") on delete cascade)");

        // Create assessment table with foreign key that cascade deletes
        db.execSQL("create table " + AssessmentTable.TABLE + " (" +
                    AssessmentTable.COL_ID + " integer primary key autoincrement, " +
                    AssessmentTable.COL_TITLE + ", " +
                    AssessmentTable.COL_START + ", " +
                    AssessmentTable.COL_FINISH + ", " +
                    AssessmentTable.COL_TYPE + ", " +
                    AssessmentTable.COL_COURSE + ", " +
                    "foreign key(" + AssessmentTable.COL_COURSE + ") references " +
                    CourseTable.TABLE + "(" + CourseTable.COL_ID + ") on delete cascade)");

        // Create note table with foreign key that cascade deletes
        db.execSQL("create table " + NoteTable.TABLE + " (" +
                    NoteTable.COL_ID + " integer primary key autoincrement, " +
                    NoteTable.COL_COURSE + ", " +
                    NoteTable.COL_TEXT + ", " +
                    "foreign key(" + NoteTable.COL_COURSE + ") references " +
                    CourseTable.TABLE + "(" + CourseTable.COL_ID + ") on delete cascade)");


        //ADD SAMPLE DATA//

        // Add some terms
        String[] titles = {"Spring", "Fall", "Summer"};
        String[] starts = {"8/14/2022" , "8/24/2022", "8/34/2022"};
        String[] ends = {"8/15/2022" , "8/25/2022", "8/35/2022"};
        for(int i = 0; i <= 2; i++){
            Term term = new Term(titles[i],starts[i],ends[i]);
            ContentValues values = new ContentValues();
            values.put(TermTable.COL_TITLE, term.getTermTitle());
            values.put(TermTable.COL_START, term.getTermStart());
            values.put(TermTable.COL_FINISH, term.getTermEnd());
            db.insert(TermTable.TABLE,null,values);
        }
        // Add some courses
        String[] titlesC = {"Math", "English", "Art"};
        String[] startsC = {"9/14/2022" , "9/24/2022", "9/34/2022"};
        String[] endsC = {"9/15/2022" , "9/25/2022", "9/35/2022"};
        String[] status = {"In Progress" , "Plan to Take", "Completed"};
        String[] name = {"Monica" , "Ed", "Garfield"};
        String[] phone = {"555" , "555", "555"};
        String[] email = {"sup@gov.gov" , "sup2@gov.gov", "sup3@gov.gov"};
        int[] termC = {3 , 1, 2};
        for(int i = 0; i <= 2; i++){
            Course course = new Course(titlesC[i],startsC[i],endsC[i],status[i],name[i],phone[i],email[i],termC[i]);
            ContentValues values = new ContentValues();
            values.put(CourseTable.COL_TITLE, course.getCourseTitle());
            values.put(CourseTable.COL_START, course.getCourseStart());
            values.put(CourseTable.COL_FINISH, course.getCourseEnd());
            values.put(CourseTable.COL_STATUS, course.getCourseStatus());
            values.put(CourseTable.COL_NAME, course.getCourseName());
            values.put(CourseTable.COL_PHONE, course.getCoursePhone());
            values.put(CourseTable.COL_EMAIL, course.getCourseEmail());
            values.put(CourseTable.COL_TERM, course.getCourseTerm());
            db.insert(CourseTable.TABLE,null,values);
        }
        // Add some assessments
        String[] titlesA = {"QAM", "GTX", "JUP"};
        String[] startsA = {"1/14/2022" , "1/24/2022", "1/34/2022"};
        String[] endsA = {"1/15/2022" , "1/25/2022", "1/35/2022"};
        String[] type = {"Performance", "Objective", "Performance"};
        int[] course = {2, 3, 1};
        for(int i = 0; i <= 2; i++){
            Assessment assessment = new Assessment(titlesA[i],startsA[i],endsA[i],type[i],course[i]);
            ContentValues values = new ContentValues();
            values.put(AssessmentTable.COL_TITLE, assessment.getTitle());
            values.put(AssessmentTable.COL_START, assessment.getAssessmentStart());
            values.put(AssessmentTable.COL_FINISH, assessment.getAssessmentEnd());
            values.put(AssessmentTable.COL_TYPE, assessment.getAssessmentType());
            values.put(AssessmentTable.COL_COURSE, assessment.getAssessmentCourse());
            db.insert(AssessmentTable.TABLE,null,values);
        }

        // add some notes
        int[] noteCourse = {2, 3, 1};
        String[] noteText = {"NOTE~111" , "NOTE~222", "NOTE~333"};
        for(int i = 0; i <= 2; i++){
            Note note = new Note(noteCourse[i],noteText[i]);
            ContentValues values = new ContentValues();
            values.put(NoteTable.COL_COURSE, note.getNoteCourse());
            values.put(NoteTable.COL_TEXT, note.getNoteText());
            db.insert(NoteTable.TABLE,null,values);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TermTable.TABLE);
        db.execSQL("drop table if exists " + CourseTable.TABLE);
        db.execSQL("drop table if exists " + AssessmentTable.TABLE);
        db.execSQL("drop table if exists " + NoteTable.TABLE);
        onCreate(db);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                db.execSQL("pragma foreign_keys = on;");
            } else {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
    }


    //~~~TERM TABLES METHODS~~~

    public ArrayList<Term> getAllTerms(){

        ArrayList<Term> terms = new ArrayList<Term>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + TermTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Term term = new Term();
                term.setId(cursor.getInt(0));
                term.setTermTitle(cursor.getString(1));
                term.setTermStart(cursor.getString(2));
                term.setTermEnd(cursor.getString(3));
                terms.add(term);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return terms;
    }

    public boolean addTerm(Term term){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TermTable.COL_TITLE, term.getTermTitle());
        values.put(TermTable.COL_START, term.getTermStart());
        values.put(TermTable.COL_FINISH, term.getTermEnd());
        long id = db.insert(TermTable.TABLE, null, values);
        return id != -1;
    }

    public void updateTerm(Term term) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TermTable.COL_TITLE, term.getTermTitle());
        values.put(TermTable.COL_START, term.getTermStart());
        values.put(TermTable.COL_FINISH, term.getTermEnd());
        db.update(TermTable.TABLE, values,
                TermTable.COL_ID + " = " + term.getId(), null);
    }

    public void deleteTerm(int termID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TermTable.TABLE,
                TermTable.COL_ID + " = " + termID, null);
    }

    //~~~COURSE TABLES METHODS~~~

    public ArrayList<Course> getAllCourses(){

        ArrayList<Course> courses = new ArrayList<Course>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + CourseTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(cursor.getInt(0));
                course.setCourseTitle(cursor.getString(1));
                course.setCourseStart(cursor.getString(2));
                course.setCourseEnd(cursor.getString(3));
                course.setCourseStatus(cursor.getString(4));
                course.setCourseName(cursor.getString(5));
                course.setCoursePhone(cursor.getString(6));
                course.setCourseEmail(cursor.getString(7));
                course.setCourseTerm(cursor.getInt(8));
                courses.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return courses;
    }

    public boolean addCourse(Course course){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CourseTable.COL_TITLE, course.getCourseTitle());
        values.put(CourseTable.COL_START, course.getCourseStart());
        values.put(CourseTable.COL_FINISH, course.getCourseEnd());
        values.put(CourseTable.COL_STATUS, course.getCourseStatus());
        values.put(CourseTable.COL_NAME, course.getCourseName());
        values.put(CourseTable.COL_PHONE, course.getCoursePhone());
        values.put(CourseTable.COL_EMAIL, course.getCourseEmail());
        values.put(CourseTable.COL_TERM, course.getCourseTerm());
        long id = db.insert(CourseTable.TABLE, null, values);
        return id != -1;
    }

    public void updateCourse(Course course) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CourseTable.COL_TITLE, course.getCourseTitle());
        values.put(CourseTable.COL_START, course.getCourseStart());
        values.put(CourseTable.COL_FINISH, course.getCourseEnd());
        values.put(CourseTable.COL_STATUS, course.getCourseStatus());
        values.put(CourseTable.COL_NAME, course.getCourseName());
        values.put(CourseTable.COL_PHONE, course.getCoursePhone());
        values.put(CourseTable.COL_EMAIL, course.getCourseEmail());
        values.put(CourseTable.COL_TERM, course.getCourseTerm());
        db.update(CourseTable.TABLE, values,
                CourseTable.COL_ID + " = " + course.getId(), null);
    }

    public void deleteCourse(int courseID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CourseTable.TABLE,
                CourseTable.COL_ID + " = " + courseID, null);
    }

    //~~~ASSESSMENT TABLES METHODS~~~

    public ArrayList<Assessment> getAllAssessments(){

        ArrayList<Assessment> assessments = new ArrayList<Assessment>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + AssessmentTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Assessment assessment = new Assessment();
                assessment.setId(cursor.getInt(0));
                assessment.setTitle(cursor.getString(1));
                assessment.setAssessmentStart(cursor.getString(2));
                assessment.setAssessmentEnd(cursor.getString(3));
                assessment.setAssessmentType(cursor.getString(4));
                assessment.setAssessmentCourse(cursor.getInt(5));
                assessments.add(assessment);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return assessments;
    }

    public boolean addAssessment(Assessment assessment){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AssessmentTable.COL_TITLE, assessment.getTitle());
        values.put(AssessmentTable.COL_START, assessment.getAssessmentStart());
        values.put(AssessmentTable.COL_FINISH, assessment.getAssessmentEnd());
        values.put(AssessmentTable.COL_TYPE, assessment.getAssessmentType());
        values.put(AssessmentTable.COL_COURSE, assessment.getAssessmentCourse());
        long id = db.insert(AssessmentTable.TABLE, null, values);
        return id != -1;
    }

    public void updateAssessment(Assessment assessment) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AssessmentTable.COL_TITLE, assessment.getTitle());
        values.put(AssessmentTable.COL_START, assessment.getAssessmentStart());
        values.put(AssessmentTable.COL_FINISH, assessment.getAssessmentEnd());
        values.put(AssessmentTable.COL_TYPE, assessment.getAssessmentType());
        values.put(AssessmentTable.COL_COURSE, assessment.getAssessmentCourse());
        db.update(AssessmentTable.TABLE, values,
                AssessmentTable.COL_ID + " = " + assessment.getId(), null);
    }

    public void deleteAssessment(int assessmentID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(AssessmentTable.TABLE,
                AssessmentTable.COL_ID + " = " + assessmentID, null);
    }

    //~~~NOTE TABLES METHODS~~~

    public ArrayList<Note> getAllNotes(){

        ArrayList<Note> notes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + NoteTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setNoteCourse(cursor.getInt(1));
                note.setNoteText(cursor.getString(2));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public boolean addNote(Note note){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteTable.COL_COURSE, note.getNoteCourse());
        values.put(NoteTable.COL_TEXT, note.getNoteText());
        long id = db.insert(NoteTable.TABLE, null, values);
        return id != -1;
    }

    public void updateNote(Note note) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteTable.COL_COURSE, note.getNoteCourse());
        values.put(NoteTable.COL_TEXT, note.getNoteText());
        db.update(NoteTable.TABLE, values,
                NoteTable.COL_ID + " = " + note.getId(), null);
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(NoteTable.TABLE,
                NoteTable.COL_ID + " = " + note.getId(), null);
    }
}
