package com.mbsystems.bmksecuritydemo.controllers;

import com.mbsystems.bmksecuritydemo.domain.Student;
import com.mbsystems.bmksecuritydemo.services.StudentList;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.security.AccessController.getContext;

@RestController
        @RequestMapping( "management/api/v1/students")
public class ManagementStudentController {

    @GetMapping
    @PreAuthorize( "hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')" )
    public List<Student> getAllStudents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println( authentication.getPrincipal());

        String username = (String)authentication.getPrincipal();
        String nametwo = authentication.getName();
        System.out.println( username );
        System.out.println( nametwo );
        return StudentList.STUDENT_LIST;
    }

    @PostMapping
    @PreAuthorize( "hasAnyAuthority('student:write')" )
    public void registerNewStudent( @RequestBody Student student ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( student );
    }

    @DeleteMapping( path = "/{id}")
    @PreAuthorize( "hasAnyAuthority('student:write')" )
    public void deleteStudent( @PathVariable("id") Integer id ) {
        System.out.println("delete student: " + id);
    }

    @PutMapping( path = "/{id}")
    @PreAuthorize( "hasAnyAuthority('student:write')" )
    public void updateStudent( @PathVariable("id") Integer id, @RequestBody Student student ) {
        System.out.println( String.format( "%s  %s", id, student ));
    }
}
