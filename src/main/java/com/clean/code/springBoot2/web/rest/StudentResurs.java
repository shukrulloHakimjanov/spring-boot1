package com.clean.code.springBoot2.web.rest;

import com.clean.code.springBoot2.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentResurs {
    @GetMapping("/student/all")
    public ResponseEntity getAll(){
        Student stu=new Student(1L,"Aziz", "Azizov","3-kurse");
        Student stu2=new Student(2L,"Fotima", "Valiyev","4-kurse");
        Student stu3=new Student(3L,"Vali", "Valiyev","2-kurse");

        List<Student>students=new ArrayList<>();
        students.add(stu);
        students.add(stu2);
        students.add(stu3);
        return  ResponseEntity.ok(students);
    }
    @GetMapping("/student/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        Student stu=new Student(id,"Aziz", "Azizov","3-kurse");
        return ResponseEntity.ok(stu);
    }
    @PostMapping("/student")
    public ResponseEntity create(@RequestBody Student student){
        return ResponseEntity.ok(student);

    }
    @GetMapping("/student")
    public ResponseEntity getOne(@RequestParam Long id ,
                                 @RequestParam String name,
                                 @RequestParam String last,
                                 @RequestParam String course){
        Student stu=new Student(id,name, last,course);
        return ResponseEntity.ok(stu);
    }


    @DeleteMapping ("/student/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return ResponseEntity.ok("information is delete");
    }
    @PutMapping("/student/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Student studentNew){
        Student stu=new Student(1L,"Aziz", "Azizov","3-kurse");
        stu.setId(id);
        stu.setName(studentNew.getName());
        stu.setLast(studentNew.getLast());
        return ResponseEntity.ok(stu);
    }
}
