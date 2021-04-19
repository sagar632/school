package model;

public class college_notice {
    private String heading;
    private String content;
    private String image;
    private String date;
private String from;
    public college_notice() {
    }

    public college_notice(String heading, String content, String image, String date,String from) {
        this.heading = heading;
        this.content = content;
        this.image = image;
        this.date = date;
        this.from=from;
    }
    public college_notice(String heading, String content, String date,String from) {
        this.heading = heading;
        this.content = content;

        this.date = date;
        this.from=from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
