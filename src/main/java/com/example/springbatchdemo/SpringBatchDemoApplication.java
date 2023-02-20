package com.example.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchDemoApplication {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-batch.xml");

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("CSVtoXML");

		System.out.println("Starting the batch job...");

		try{
			// Execute the Job
			JobExecution execution = jobLauncher.run(job, new JobParameters());

			System.out.println("\n***********************************\n");
			System.out.printf("Job Status : %s%n",execution.getStatus());
			System.out.println("Job completed");

		} catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
				 JobParametersInvalidException | JobRestartException e) {

			e.printStackTrace();

			System.out.println("Job failed");
		}
	}

}
