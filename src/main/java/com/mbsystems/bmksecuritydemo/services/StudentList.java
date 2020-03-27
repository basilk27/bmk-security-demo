package com.mbsystems.bmksecuritydemo.services;

import com.mbsystems.bmksecuritydemo.domain.Student;

import java.util.Arrays;
import java.util.List;

public interface StudentList {

    List<Student> STUDENT_LIST = Arrays.asList(
            Student.builder().id( 1 ).name( "James Bond" ).build(),
            Student.builder().id( 2 ).name( "Maria Jones" ).build(),
            Student.builder().id( 3 ).name( "Anna Smith" ).build()
    );
}
