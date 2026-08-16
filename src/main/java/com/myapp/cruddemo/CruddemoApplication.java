package com.myapp.cruddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

}


	/*
	@Bean
	public CommandLineRunner commandLineRuner(UserRepository appDAO){
		return runner ->{
			//createInstructor(appDAO);
			//findInstructor(appDAO);
			//deleteInstructor(appDAO);
			//findInstructorDetail(appDAO);
			//deleteInstructorDetail(appDAO);
			//createInstructorWithCourses(appDAO);
			//findInstructorWithCourses(appDAO);
			//findCoursesForInstructor(appDAO);
			//findInstructorWithCoursesJoinFetch(appDAO);
			//updateInstructor(appDAO);
			//updateCourse(appDAO);
			//deleteCourse(appDAO);

			//createCourseAndReviews(appDAO);
			//retrieveCourseAndReviews(appDAO);
			//deleteCourseAndReviews(appDAO);

			//createCourseAndStudents(appDAO);
			//findCourseAndStudents(appDAO);
			//findStudentAndCourses(appDAO);
			//addMoreCoursesForStudent(appDAO);
			deleteStudent(appDAO);
		};
	}
	private void createInstructor(UserRepository appDAO){
		//create the instructor
		Instructor tempInstructor = new Instructor("Ali", "Darby", "dardy@luv2code.com");
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.youtube.com", "Football");

		//associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail );
		System.out.println("saving instructor: "+ tempInstructor);
		appDAO.save (tempInstructor);
		}
	
	private void findInstructor(UserRepository appDAO){
		int theId = 1;
		System.out.println("Finding instructor id: "+ theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);
		System.out.println("Found instructor: "+ tempInstructor);
	
	}
	private void deleteInstructor(UserRepository appDAO){
		int theId = 1;
		System.out.println("Deleting instructor id: "+ theId);
		appDAO.deleteInstructorById(theId);
		System.out.println("Deleted instructor id: "+ theId);
	}

	private void findInstructorDetail(UserRepository appDAO){
		int theId = 2;
		System.out.println("Finding instructor detail id: "+ theId);
		InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);
		System.out.println("Found instructor detail: "+ tempInstructorDetail);
		System.out.println("The associated instructor: "+ tempInstructorDetail.getInstructor());
	
	}
	private void deleteInstructorDetail(UserRepository appDAO){
		int theId=3;
		System.out.println("Deleting instructor detail id: "+ theId);
		appDAO.deleteInstructorDetailById(theId);
	}
	public void createInstructorWithCourses(UserRepository appDAO){
				//create the instructor
		Instructor tempInstructor = new Instructor("susan", "public", "susan@luv2code.com");
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.youtube.com", "gaming");

		//associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail );

		Course tempCourse1 = new Course("Air Guitar");
		Course tempCourse2 = new Course("PinBall Masterclass");
		Course tempCourse3 = new Course("Java Programing");
	
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);
		tempInstructor.add(tempCourse3);
		System.out.println("saving instructor: "+ tempInstructor);
		System.out.println("the courses: "+ tempInstructor.getCourses());
		appDAO.save (tempInstructor);
	}

	public void findInstructorWithCourses(UserRepository appDAO){
		int theId = 1;
		Instructor tempInstructor = appDAO.findInstructorById(theId);
		System.out.println("tempinstructor: "+ tempInstructor);
		System.out.println("the courses: "+ tempInstructor.getCourses());

	}
	public void findCoursesForInstructor(UserRepository appDAO){
		int theId = 1;
		Instructor tempInstructor = appDAO.findInstructorById(theId);
		System.out.println("tempinstructor: "+ tempInstructor);
		// find courses for an instructor id 
		List<Course> courses = appDAO.findCoursesByInstructorId(theId);
	
		tempInstructor.setCourses(courses);
		System.out.println("the courses: "+ tempInstructor.getCourses());

	}

	public void findInstructorWithCoursesJoinFetch(UserRepository appDAO){

		int theId = 1;
		Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);
		System.out.println("tempISsnstructor: "+ tempInstructor);
		System.out.println("the courses: "+ tempInstructor.getCourses());
	}

	private void updateInstructor(UserRepository appDAO){
		int theId = 1;
		Instructor tempInstructor = appDAO.findInstructorById(theId);
		tempInstructor.setLastName("Chad");
		appDAO.update(tempInstructor);
		System.out.println("Updated instructor: "+ tempInstructor);
	}
	
	private void updateCourse(UserRepository appDAO){
		int theId = 10;
		Course tempCourse = appDAO.findCourseById(theId);
		tempCourse.setTitle("Updated Course Title");
		appDAO.update(tempCourse);
		System.out.println("Updated course: "+ tempCourse);
	}
	private void deleteCourse(UserRepository appDAO){
		int theId =10;
		appDAO.deleteCourseById(theId);
		System.out.println("Deleted course id: "+ theId);

	}

	private void createCourseAndReviews(UserRepository appDAO){
		Course tempCourse = new Course("How to do a backflip");
		tempCourse.addReview(new Review("Useful course!..."));
		tempCourse.addReview(new Review("Great course... love it!"));
		tempCourse.addReview(new Review("What a dump course!"));

		System.out.println("Saving the course: "+ tempCourse);
		System.out.println("the reviews: "+ tempCourse.getReviews());
		appDAO.save(tempCourse);

	}

	private void retrieveCourseAndReviews(UserRepository appDAO){
		int theId = 10;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);
		System.out.println("tempCourse: "+ tempCourse);
		System.out.println("the reviews: "+ tempCourse.getReviews());

	}
	private void deleteCourseAndReviews(UserRepository appDAO){
		int theId = 10;
		System.out.println("Deleting course id: "+ theId);
		appDAO.deleteCourseById(theId); //cascade.all will delete the reviews of the course with it 
	}
	private void createCourseAndStudents(UserRepository appDAO){

		//create a course
		Course tempCourse = new Course("Java programing");
		//create students
		Student tempStudent1 = new Student("Max","Godwen","max@gmail.com");
		Student tempStudent2 = new Student("Tom","Lee","tom@gmail.com");
		Student tempStudent3 = new Student("John","Div","john@gmail.com");
		//add students to the course
		tempCourse.addStudent(tempStudent1);
		tempCourse.addStudent(tempStudent2);
		tempCourse.addStudent(tempStudent3);
		//save the course and associated students
		System.out.println("saving course: "+ tempCourse + "associated students"+ tempCourse.getStudents());
		appDAO.save(tempCourse);
	}
	private void findCourseAndStudents(UserRepository appDAO){

		int theId = 10;
		Course tempCourse = appDAO.findCourseAndStudentsByCourseId(theId);
		System.out.println("Course:" + tempCourse + "\nStudents: "+ tempCourse.getStudents());
	}

	private void findStudentAndCourses(UserRepository appDAO){
		int theId=2;
		Student tempStudent = appDAO.findStudentAndCoursesByStudentId(theId);
		System.out.println("Stuudent: "+tempStudent);
		System.out.println("Courses: "+tempStudent.getCourses());

	}
	private void addMoreCoursesForStudent(UserRepository appDAO){
		int theId = 2;
		Student tempStudent = appDAO.findStudentAndCoursesByStudentId(theId);
		Course tempCourse1 =new Course("Rubik's Cube");
		Course tempCourse2 =new Course("Game Development");

		tempStudent.addCourse(tempCourse1);
		tempStudent.addCourse(tempCourse2);

		System.out.println("updating student: "+ tempStudent+ "\nassociated courses: "+ tempStudent.getCourses());

		appDAO.update(tempStudent);
	}

	private void deleteStudent(UserRepository appDAO){
		int theId = 1;
		System.out.println("Deleting student");
		appDAO.deleteStudentById(theId);
	}
}*/