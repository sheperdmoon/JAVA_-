package main.object;
//90
public class Task {
    int id;
    String name;
    String begin;
    String end;
    String im;
    String al;
    String content;
    String reflect;
    public Task(int id, String name, String begin, String end, String im, String al, String content) {
        this.id = id;
        this.name = name;
        this.begin = begin;
        this.end = end;
        this.im = im;
        this.al = al;
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public String getIm() {
        return im;
    }

    public String getAl() {
        return al;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReflect() {
        return reflect;
    }

    public void setReflect(String reflect) {
        this.reflect = reflect;
    }
}

