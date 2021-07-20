package com.fatma.eval.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Student {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String studentId;
	private String email;
	private double math;
	private int math_coef;
	
	private double algorithms;
	private int algo_coef;
	
	private Double sport;
	private int spt_coef;
	
	private Double english;
	private int eng_coef;
	
	private double german;
	private int ger_coef;
	
	private double averageGrade;
	
	/** Calculate the average grade of a student.
	 */
	public void calcAverageGrade()
	{
		averageGrade = ( math*math_coef
						+ algorithms*algo_coef
				        + sport*spt_coef
				        + english*eng_coef+
				          german*ger_coef)/20;
	}
}
