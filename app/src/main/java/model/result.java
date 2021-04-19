package model;

import java.io.Serializable;

public class result implements Serializable {
    private String subname;
    private String fm;
    private String pm;
    private String om;
    private String remarks;
    private String grade;


    public result(String subname, String fm, String pm, String om, String remarks, String grade) {
        this.subname = subname;
        this.fm = fm;
        this.pm = pm;
        this.om = om;
        this.remarks = remarks;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getOm() {
        return om;
    }

    public void setOm(String om) {
        this.om = om;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
