package model;

public class studentattendance {
    private String name,rollno,stid,classe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public studentattendance(String name, String rollno, String stid, String classe) {
        this.name = name;
        this.rollno = rollno;
        this.stid = stid;
        this.classe = classe;
    }
}
