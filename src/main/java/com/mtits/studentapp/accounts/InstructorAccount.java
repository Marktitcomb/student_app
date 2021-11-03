package com.mtits.studentapp.accounts;

import com.mtits.studentapp.database.Database;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Peter Sun
 */
public class InstructorAccount extends Account {

    private ArrayList<String> courses;
    private ArrayList<String> students;
    private int hwNumber;

    public InstructorAccount() {
    }

    public void setLogInInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getFirstName() {
        return name;
    }

    public String getLastName() {
        return surname;
    }

    /**
     * Get courses instructor is teaching
     * @return Courses instructor is teaching
     */
    public ArrayList<String> getCourses() {
        String sql = "select coursename from courses";
        try {
            pStmt = Database.createConnection().prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            courses = new ArrayList<String>();
            while (rs.next()) {
                sql = "select * from " + rs.getString(1) + " where instructorid = " + id;
                pStmt = Database.createConnection().prepareStatement(sql);
                ResultSet instructorInClassRS = pStmt.executeQuery();
                if (instructorInClassRS.next()) {
                    courses.add(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return courses;
    }

    /**
     * Get students from instructor's class teaching
     * @param course Course to look for
     * @return Students in course the instructor is teaching
     */
    public ArrayList<String> getStudents(String course) {
        String sql = "select studentid from " + course + "course";
        try {
            pStmt = Database.createConnection().prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            students = new ArrayList<String>();
            while (rs.next()) {
                sql = "select id, firstname, lastname from studentaccounts where id = " + rs.getString(1) ;
                pStmt = Database.createConnection().prepareStatement(sql);
                ResultSet studentInClass = pStmt.executeQuery();
                if (studentInClass.next()) {
                    students.add(studentInClass.getString(1) + " - " + studentInClass.getString(2) + " " + studentInClass.getString(3));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return students;
    }

    /**
     * Check if instructor is in database
     * @return True if instructor is in database
     */
    @Override
    public boolean isInDatabase() {
        boolean ret = false;

        String sql = "select id, lastname, firstname from InstructorAccounts where username = ? and password = ?";
        try {
            pStmt = Database.createConnection().prepareStatement(sql);
            pStmt.setString(1, this.userName);
            pStmt.setString(2, this.password);

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                initializeAccount(rs);
                ret = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * Initialize instructor fields
     * @param rs Result set from query
     */
    private void initializeAccount(ResultSet rs) {

        try {
            this.id = rs.getString(1);
            this.name = rs.getString(2);
            this.surname = rs.getString(3);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Assign homework from textbox to specified course
     * @param course Course to assign homework to
     * @param homeworkAssignment Homework assignment
     */
    public void assignHw(String course, String homeworkAssignment) {
        hwNumber = getHwNumberFromDb(course);
        setHwNumberInDB(course, ++hwNumber);
        appendHwToCourseTable(course);

        //Writer output = null;
        File file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\netBeanProjects\\\\studentApp\\src\\HomeworkAssignment\\" + course + "\\HW" + hwNumber + ".txt");
        try (Writer output = new BufferedWriter(new FileWriter(file))){
            //output = new BufferedWriter(new FileWriter(file));
            output.write(homeworkAssignment);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Append to course database homework assignment column
     * @param course Course to add homework to 
     */
    private void appendHwToCourseTable(String course) {
        String sql = "alter table " + course + " add Hw" + hwNumber + " int";
        try {
            pStmt = Database.createConnection().prepareStatement(sql);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get total amount of homework for course
     * @param course Course to find total amount of homework
     * @return Total amount of homework
     */
    public int getHwNumberFromDb(String course) {
        int ret = 0;
        String sql = "select numHw from courses where coursename = \'" + course + "\'";
        //String sql = "select numHw from courses where coursename = 'Bio'";
        try {
            pStmt = Database.createConnection().prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("numHw");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * Set total amount of homework for course
     * @param course Course to set amount of home for
     * @param numberOfHw Amount of homework assigned
     */
    private void setHwNumberInDB(String course, int numberOfHw) {
        String sql = "update courses set numHw = " + Integer.toString(numberOfHw) + " where coursename = \'" + course + "\'";
        try {
            pStmt = Database.createConnection().prepareStatement(sql);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Set student grade for specified homework assignment
     * @param student Student to look for
     * @param course Course teaching
     * @param hw Homework number
     * @param grade Grade for homework
     */
    public void setGradeInDB(String student, String course, String hw, String grade) {
        String sql = "update " + course + "course set " + hw + " = \'" + grade + "\' where studentid = \'" + student + "\'";
        try {
            pStmt = Database.createConnection().prepareStatement(sql);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
