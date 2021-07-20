package com.fatma.eval.repository;

import org.springframework.data.repository.CrudRepository;

import com.fatma.eval.entities.Student;

public interface StudentRepository  extends CrudRepository<Student, Long>{

	Student getStudentByEmail(String email);

}
