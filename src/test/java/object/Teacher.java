package object;

import java.util.Arrays;
import java.util.Objects;

public class Teacher {
    private String name, gender;
    private int age, workExperience;
    private String[] subjects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(int workExperience) {
        this.workExperience = workExperience;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return age == teacher.age && workExperience == teacher.workExperience && Objects.equals(name, teacher.name) && Objects.equals(gender, teacher.gender) && Arrays.equals(subjects, teacher.subjects);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, gender, age, workExperience);
        result = 31 * result + Arrays.hashCode(subjects);
        return result;
    }
}
