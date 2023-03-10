package main.object;
//40
public class Day {
    int mouth;
    int day;
    String content="";
    public Day(int mouth, int day, String content) {
        this.mouth = mouth;
        this.day = day;
        this.content = content;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
