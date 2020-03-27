package com.mbsystems.bmksecuritydemo.controllers;

import com.mbsystems.bmksecuritydemo.domain.Student;
import com.mbsystems.bmksecuritydemo.services.StudentList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "api/v1/students")
public class StudentController {

    @GetMapping( path = "/{id}" )
    public Student getStudent( @PathVariable( "id") Integer id ) {
        return StudentList.STUDENT_LIST.stream()
                .filter( student -> student.getId().equals( id ) )
                .findFirst()
                .orElseThrow( () -> new IllegalStateException( "Student ID: " + id + " does not exist." ) );
    }
}
