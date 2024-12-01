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

    // add an exception handler using @ExceptionHandler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){
        // create a StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMassage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add another exception handler ... to catch any exception (Catch all)
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc){
        // create a StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMassage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
