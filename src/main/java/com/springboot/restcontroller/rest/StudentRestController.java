package com.springboot.restcontroller.rest;

import com.springboot.restcontroller.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    // Define @PostConstruct to load the student data .... only once
    @PostConstruct
    public void loadData(){
        theStudents = new ArrayList<>();

        theStudents.add(new Student("Chayan","Maiti"));
        theStudents.add(new Student("Surajit","Dutta"));
        theStudents.add(new Student("Romit","Maji"));
    }

    // Define endpoints for "/students"  - return a list of students
    @GetMapping("/students")
    public List<Student> getStudents(){

        return theStudents;
    }

    // define endpoints or "/students/{studentId}" - return student at index...
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){
        // just index into the list ... keep it simple for now
        // Check the studentId again list size
        if((studentId>=theStudents.size()) || (studentId<0)){
            throw new StudentNotFoundException("Student id not found - "+ studentId);
        }
        return theStudents.get(studentId);
    }

}
