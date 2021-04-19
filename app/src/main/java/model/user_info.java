package model;

public class user_info {


private  String fname;
private  String  lname;
private String email;
private String password;
private String collegeid;
private Boolean isStudent;
private String classe;
public user_info(){}

    public user_info(String fname, String lname, String email, String password, String collegeid, Boolean isStudent, String classe) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.collegeid = collegeid;
        this.isStudent = isStudent;
        this.classe = classe;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public void setStudent(Boolean student) {
        isStudent = student;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public user_info(String fname, String lname, String email, String password, String collegeid, Boolean isStudent) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.collegeid = collegeid;
        this.isStudent = isStudent;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollegeid() {
        return collegeid;
    }

    public void setCollegeid(String collegeid) {
        this.collegeid = collegeid;
    }
}
