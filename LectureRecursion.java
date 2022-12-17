/**
 * IPR | Graphics - Exercise : Recursion
 * 
 * This is the handout notebook for the IPR Graphics Exercise.
 * You **do not** need to edit or hand in this file!
 *
 * @Contact: If you have any questions, please contact the tutors or the
 *           instructor (David Schedl, email: david.schedl@fh-hagenberg.at)
 * 
 */

// import List
import java.util.List;
import java.util.ArrayList;

public class LectureRecursion {

	// a multiplication function
	public static int multiply(int a, int b) {
		return a * b;
	}

	// overloading the multiply function. This is perfectly fine!
	public static int multiply(int a, int b, int c) {
		return a * b * c;
	}

	// The 'add' function that takes 3 arguments
	// first function takes two integers to sum and one title
	public static void add(int a, int b, String title) {
		int p = a + b;
		System.out.println("The addition of " + a + " and " + b + " is " + p + ", also called " + title + "!");
	}

	// second function takes three integers to sum and no title
	public static void add(int a, int b, int c) {
		int p = a + b + c;
		System.out.println("The addition of " + a + " and " + b + " and " + c + " is " + p + ".");
	}

	// recursive count down that just prints the numbers
	public static void countDown(int number) {
		System.out.println(number);
		if (number > 0) {
			countDown(number - 1);
		}
	}

	// recursive count up that just prints the numbers
	public static void countUp(int number) {
		if (number > 0) {
			countUp(number - 1);
		}
		System.out.println(number);
	}

	// Advanced recursive count down that creates a list of numbers
	// In Java we need the List datatype to store the numbers (more about that next
	// semester!).
	public static List<Integer> countDownList(int number) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(number);
		if (number > 0) {
			list.addAll(countDownList(number - 1));
		}
		return list;
	}

	public static void main(String[] args) {

		/*
		 * Method overloading (# parameters)
		 */
		{
			System.out.println(multiply(2, 3, 4));
			System.out.println(multiply(2, 3));

			// System.out.println(multiply(2.3, 3.4, 4.5)); // this does not work!
		}

		/*
		 * Method overloading (varying types)
		 */
		{
			add(2, 3, 4);
			add(2, 3, "sum");
		}

		/*
		 * Recursion
		 */
		{
			countDown(10);
			// System.out.println(countDownList(10));
			countUp(10);
		}

	}

}
