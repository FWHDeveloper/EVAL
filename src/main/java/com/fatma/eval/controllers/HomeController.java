package com.fatma.eval.controllers;

import java.util.ArrayList;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fatma.eval.entities.Student;
import com.fatma.eval.repository.StudentRepository;


@Controller
public class HomeController {

	private final StudentRepository studentRepo;

	@Autowired
	public HomeController(StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
	}

	@GetMapping("view")
	public String listStudents(Authentication auth, Model model) {
		String name = auth.getName();
		ArrayList<String> roles = new ArrayList<String>();
		for (GrantedAuthority ga: auth.getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		String htmlPage = "teacher/viewAsTeacher.html";
		for (String i: roles)
		{
			if(i.equalsIgnoreCase("TEACHER"))
			{
		model.addAttribute("students", studentRepo.findAll());
			}
			else if(i.equalsIgnoreCase("STUDENT"))
			{
				model.addAttribute("student", studentRepo.getStudentByEmail(name));
				htmlPage = "student/viewAsStudent.html";
			}
		}
			return htmlPage;
	}

	@GetMapping("add")
	public String showAddStudentForm(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "teacher/addStudent";
	}
	@PostMapping("add")
    public String addStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "teacher/addStudent";
        }
        student.calcAverageGrade();
        studentRepo.save(student);
        return "redirect:view";
    }
	@GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {
        Student student = studentRepo.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
        studentRepo.delete(student);
        model.addAttribute("students", studentRepo.findAll());
        return "teacher/viewAsTeacher";
    }
	  @GetMapping("update/{id}")
	    public String showStudentFormToUpdate(@PathVariable("id") long id, Model model) {
	    	Student student = studentRepo.findById(id)
	            .orElseThrow(()->new IllegalArgumentException("Invalid student Id:" + id));
	    	
	    	 model.addAttribute("student", student);
	         return "teacher/updateStudent";
	    }
	    @PostMapping("update/{id}")
	    public String updateStudent(@PathVariable("id") long id, @Valid Student student, BindingResult result,
	        Model model) {
	    	System.out.println("ID = " +student.getId());
	        if (result.hasErrors()) {
	            student.setId(id);
	            return "teacher/updateStudent";
	        }
	        student.calcAverageGrade();
	        studentRepo.save(student);
	        model.addAttribute("students", studentRepo.findAll());
	        return "teacher/viewAsTeacher";
	    }
}
