import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


// main class to query information from a given .csv file of student data
public class StudentInformation
{

	List<StudentData> studentData;
	
	public StudentInformation(String filename)
	{
		ReadFromFile read = null;
		try {
			read = new ReadFromFile(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		studentData = read.sd;
			
	} 

	public static void main(String[] args) throws FileNotFoundException
	{	
		Scanner scan = new Scanner(System.in);
		String filename = null;
		
		System.out.println("Enter 1 to choose default file (SampleData.csv)!");
		System.out.println("Enter 2 to choose custom .csv file, file must be in current folder!");
		System.out.print("Enter: ");
		int n = 0;
		try
		{
			n = scan.nextInt();
		}
		catch (InputMismatchException e)
		{
			System.out.println("Wrong input! Exited!");
			System.exit(1);
		}
		
		switch (n)
		{
			case 1:
				filename = "SampleData.csv";
				break;
			case 2:
				System.out.print("Please enter your custom filename: ");
				if (scan.hasNext())
				{
					filename = scan.next();
				}
				break;
			default:
				System.out.println("Wrong input! Exited!");
				System.exit(1);
		}
		scan.close();
		
		
		StudentInformation studentInfo = new StudentInformation(filename);
		
		System.out.println();
		studentInfo.getClassSectionsTaught();
		System.out.println();
		studentInfo.getClassTakenByEachStudent();
		System.out.println();
		studentInfo.getNumberOfStudentTakingEachClass();
		System.out.println();
		studentInfo.ProfessorsWithAtLeast2ClassesWith2OrMoreOfTheSameStudents();
		System.out.println();
		studentInfo.listOfStuDentTakeMoreThanOneClass();
		System.out.println();
		studentInfo.listOfProfessorTeachMoreThanOneClass();
	}

	// Class Sections Taught
	public void getClassSectionsTaught()
	{
		System.out.println("Class Sections Taught:");
		for (int i = 0; i < studentData.size(); i++)
		{
			System.out.println("\t" + studentData.get(i).course + ", " + studentData.get(i).professor);
		}
	}

	// Classes Taken By Each Student
	public void getClassTakenByEachStudent()
	{
		System.out.println("Class Taken By Each Student:");
		Map<Object, List<StudentData>> studentByID = studentData.stream().collect(Collectors.groupingBy(StudentData::getID));

		studentByID.forEach((k,v)->System.out.println("\t" + k + ":  "+ 
				((List<StudentData>)v).stream().map(m->m.getCourse()).collect(Collectors.joining(","))));
		/*
		List<Integer> list = studentData.stream()
								.map(StudentData::getID)
								.collect(Collectors.toList());
		studentData.stream()
		  .collect(Collectors.groupingBy(m -> m.studentID, Collectors.counting()))
		  .forEach((id,count)->System.out.println(id+"\t"+count));
		 */	
	}

	//Professors with at least 2 classes with 2 or more of the same students
	public void ProfessorsWithAtLeast2ClassesWith2OrMoreOfTheSameStudents()
	{
		System.out.println("Professors with at least 2 classes with 2 or more of the same students:");
		Map<String, Map<Integer, List<String>>> professorBy = studentData.stream()
				.collect(Collectors.groupingBy(StudentData::getProfessor, 
						Collectors.groupingBy(StudentData::getID, Collectors.mapping(StudentData::getCourse, Collectors.toList()))));

		for (Object key : professorBy.keySet()) 
		{
			Set<Integer> k = professorBy.get(key).keySet();
			boolean check = true;
			for (int i : k)
			{
				for (int j : k)
				{
					List<String> common = new ArrayList<String>(professorBy.get(key).get(i));
					common.retainAll(professorBy.get(key).get(j));
					if ((common.size() > 1) && (i != j))
					{
						check = false;
						System.out.print("\t" + key + ": ");
						for (int p = 0; p < common.size(); p++)
						{
							System.out.print(common.get(p) + ", ");
						}
						System.out.print(i + ", " + j);
					}
				}
				if (check == false) break;
			}
		}
		System.out.println();
	}

	//How many students are registered for each class?
	public void getNumberOfStudentTakingEachClass()
	{
		System.out.println("How many students are registered for each class: ");

		Map<String, List<Integer>> studentByCourse = studentData.stream()
				.collect(Collectors.groupingBy(StudentData::getCourse, 
						Collectors.mapping(StudentData::getID, Collectors.toList())));

		for (Object key : studentByCourse.keySet()) {
			System.out.println("\t" + key + ":" + studentByCourse.get(key).size());
		}

	}

	//How many students take more than one class?
	public void listOfStuDentTakeMoreThanOneClass()
	{
		System.out.println("How many students take more than one class: ");
		Map<Integer, List<String>> studentBy = studentData.stream()
				.collect(Collectors.groupingBy(StudentData::getID, 
						Collectors.mapping(StudentData::getCourse, Collectors.toList())));

		int count = 0;

		for (Object key : studentBy.keySet()) {
			if (studentBy.get(key).size() > 1)
			{
				count++;
			}
		}
		System.out.print("\t" + count + ": ");

		for (Object key : studentBy.keySet()) {
			if (studentBy.get(key).size() > 1)
			{	
				count--;
				System.out.print(key);
				if (count > 0)
					System.out.print(", ");
			}
		}
		System.out.println();
	}

	// How many professors teach more than one class?
	public void listOfProfessorTeachMoreThanOneClass()
	{
		System.out.println("How many professors teach more than one class: ");
		Map<String, List<String>> studentBy = studentData.stream()
				.collect(Collectors.groupingBy(StudentData::getProfessor, 
						Collectors.mapping(StudentData::getCourse, Collectors.toList())));

		int count = 0;

		for (Object key : studentBy.keySet()) {
			if (studentBy.get(key).size() > 1)
			{
				count++;
			}
		}
		System.out.print("\t" + count + ": ");

		for (Object key : studentBy.keySet()) {
			if (studentBy.get(key).size() > 1)
			{	
				count--;
				System.out.print(key);
				if (count > 0)
					System.out.print(", ");
			}
		}
		System.out.println();
	}

	// for fun
	public void getProfessorTeachingEachStudent()
	{
		System.out.println("Professor Teaching Each Student: ");
		Map<Integer, List<String>> studentBy = studentData.stream()
				.collect(Collectors.groupingBy(StudentData::getID, 
						Collectors.mapping(StudentData::getProfessor, Collectors.toList())));

		for (Object key : studentBy.keySet()) {
			System.out.println("\t" + key + ":" + studentBy.get(key));
		}

	}

	// for fun
	public void getClassTaughtByEachProfessor()
	{
		System.out.println("Class Taught By Each Professor: ");
		Map<Object, List<StudentData>> studentByProfessor = studentData.stream().collect(Collectors.groupingBy(StudentData::getProfessor));

		studentByProfessor.forEach((k,v)->System.out.println("\t" + k + ":  "+ 
				((List<StudentData>)v).stream().map(m->m.getCourse()).collect(Collectors.joining(","))));
	}

	// inner class to hold student data
	private class StudentData
	{
		//private Course course;
		private String course;
		private String professor;
		private int studentID;

		StudentData(String course, String professor, int studentID)
		{
			this.course = course;
			this.professor = professor;
			this.studentID = studentID;
		}

		public String getCourse() {
			return course;
		}

		public int getID() {
			return studentID;
		}

		public String getProfessor() {
			return professor;
		}

	}

	// Parse a line of data into a student object
	public StudentData ParseData(String line)
	{
		String[] tokens = line.split(","); 
		if (tokens.length > 3)
		{
			System.out.println("Data is not in the given format. Please check your .csv file!");
			System.exit(1);
		}
		String course = tokens[0].trim();
		String professor = tokens[1].trim();
		int studentID = 0;
		try 
		{
			studentID = Integer.parseInt(tokens[2].trim());

	    }
	    catch( NumberFormatException e ) 
		{
	    	//e.printStackTrace();
	    	System.out.println("Student ID should be integer. Please check your .csv file!");
	    	System.exit(1);
	    	
	    }
		
		
		return new StudentData(course, professor, studentID);
	}

	// Read the .csv file, parse lines into a student object list
	private class ReadFromFile 
	{
		List<StudentData> sd;

		public ReadFromFile(String filename) throws FileNotFoundException
		{
			List<StudentData> st = new ArrayList<StudentData>();
			String line;
			StudentData data;
			File file = new File(filename);
			Scanner sc;	    
			// check whether file exists or not
			if(file.exists())
			{     
				sc = new Scanner(file);

				// read file line-by-line, parse into words and put into array list of strings
				while (sc.hasNextLine())				
				{
					line = sc.nextLine();
					line = line.trim(); // remove leading and trailing whitespace
					if (!line.equals("")) // don't write out blank lines
					{
						data = ParseData(line);
						st.add(data); 
					}
				}
				sc.close();
			}
			else
			{
				System.out.println("File not found! Exited!");
				System.exit(1);
			}

			this.sd = st;
		} 
	}

}
