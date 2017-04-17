import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by DhananJay on 24/1/17.
 */
public class NewString {
	public static void main(String[] args) {
		/*
		 * Student student=new Student("Jay"); Class[] params=new Class[1];
		 * params[0]=String.class; Class cl=student.getClass(); try { Method
		 * method=cl.getDeclaredMethod("setName",params);
		 * method.invoke(student); } catch (NoSuchMethodException |
		 * IllegalAccessException | InvocationTargetException e) {
		 * e.printStackTrace(); }
		 */

		/*
		 * foo(student); System.out.println(student.getName());
		 */
		//newFunction();
		int a=0,b=0;
		int n=10;

	}

	private static void newFunction() {
		List<String> list = Arrays.asList("jay1", "kay12", "ray12354", "sayd", "ma");

		// final Function<String,Predicate<String>> startWith=(String letter)->
		// name ->name.startsWith(letter);
		// System.out.println(list.stream().mapToInt(String::length).max());
		String nameFound = list.stream().reduce("kay12", (name1, name2) -> name1.length() > name2.length() ? name1 : name2);
		System.out.println(String.format("%s is longest name", nameFound));
		System.out.println(String.join("/", list));
		/*
		 * Integer sum = 0; for (String name : list) sum = sum + name.length();
		 * System.out.println("Sum is:" + sum);
		 */

		final Function<String, Predicate<String>> startWith2 = letter -> name -> name.startsWith(letter);
		Optional<String> findFirst = list.stream().filter(startWith2.apply("j")).findFirst();
		findFirst.ifPresent(name -> System.out.println(name.toUpperCase()));
		System.out.println(findFirst.orElse("Shooooo....Go away"));
		Function<String, Function<String, String>> fun = letter -> name -> name.concat(letter);
		String filteredList = list.stream().map(fun.apply("T")).collect(Collectors.joining(" "));

	}

	public static void foo(Student student) {
		student = new Student("max");
	}
}

interface jay<T> {
	T rock();
}