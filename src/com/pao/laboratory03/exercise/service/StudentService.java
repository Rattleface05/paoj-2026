package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.exceptions.InvalidStudentException;
import com.pao.laboratory03.exercise.exceptions.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Subject;


import java.util.*;

/**
 * 6. service/StudentService.java — SERVICIU (Singleton)
 *    - Câmp: List<Student> students (ArrayList)
 *    - Singleton pattern (constructor privat, getInstance())
 *    - Metode:
 *      a) void addStudent(String name, int age)
 *         → creează Student și adaugă în listă
 *         → dacă există deja un student cu același nume, aruncă RuntimeException
 *      b) Student findByName(String name)
 *         → caută în listă, aruncă StudentNotFoundException dacă nu găsește
 *      c) void addGrade(String studentName, Subject subject, double grade)
 *         → găsește studentul (findByName) și adaugă nota
 *      d) void printAllStudents()
 *         → afișează toți studenții cu notele lor
 *      e) void printTopStudents()
 *         → sortează studenții descrescător după medie și afișează
 *      f) Map<Subject, Double> getAveragePerSubject()
 *         → calculează media pe fiecare materie (din toți studenții care au notă)
 *
 */

public class StudentService {
    private static StudentService single_instance = null;

    public static StudentService getInstance(){
        if (single_instance == null){
            return new StudentService();
        }
        return single_instance;
    }



    private List<Student> students;
    private StudentService() {
        this.students = new ArrayList<Student>();
    }

    //a)
    public void addStudent(String name, int age){
        if(students.contains(new Student(name,age))){
            throw new RuntimeException("Student already exists");
        }

        students.add(new Student(name,age));
    }

    //b)
    public Student findByName(String name){
        for (Student student : students){
            if (Objects.equals(student.getName(), name)){
                return student;
            }
        }
        throw new StudentNotFoundException("Nu exista acest student ");
    }

    //c)
    public void addGrade(String studentName, Subject subject, double grade){
        findByName(studentName).addGrade(subject,grade);
    }

    //d)
    public void printAllStudent(){
        System.out.println(students);
    }

    //e)
    public void printTopStudents(){
        List<Student> order = students;
        order.sort( (a, b) -> {return (int) (a.getAverage() - b.getAverage());});
        System.out.println(order);
    }

    //f)
    public Map<Subject, Double> getAveragePerSubject(){
        Map<Subject, Integer> times = new HashMap<Subject, Integer>();
        Map<Subject, Double> total = new HashMap<Subject, Double>();

        for (Student student: students){
            Map<Subject, Double> aux = student.getGrades();
            for (Subject subject : aux.keySet()){
                times.put(subject, times.get(subject) + 1);
                total.put(subject, total.get(subject) + aux.get(subject));
            }
        }

        total.replaceAll((s, v) -> total.get(s) / times.get(s));

        return total;
    }
}
