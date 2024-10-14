package model;

import java.util.List;

public class Student {
    private String studentName;
    private Integer age;
    private List<String> subjects;
    private boolean studentIsActive;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public boolean isStudentIsActive() {
        return studentIsActive;
    }

    public void setStudentIsActive(boolean studentIsActive) {
        this.studentIsActive = studentIsActive;
    }
}
