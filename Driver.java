import java.util.Arrays;
import java.io.File;

public class Driver {
	public static void main(String [] args) {
		// Testing Polynomial constructors, .add, .evaluate and .hasRoot
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1_coef = {6, 5};
		int [] c1_exp = {0, 3};
		Polynomial p1 = new Polynomial(c1_coef, c1_exp);
		double [] c2_coef = {-2,-9};
		int [] c2_exp = {1, 4};
		Polynomial p2 = new Polynomial(c2_coef, c2_exp);
		Polynomial s1 = p1.add(p2);
		System.out.println("s1(0.1) = " + s1.evaluate(0.1));
		if(s1.hasRoot(1))
			System.out.println("1 is a root of s1");
		else
			System.out.println("1 is not a root of s1");
		
		
		// testing Polynomial.multiple, .add
		
		double [] c3_coef = {2,-7,1,-5,3};
		int [] c3_exp = {2,0,1,3,4};
		Polynomial p3 = new Polynomial(c3_coef, c3_exp);
		double [] c4_coef = {9,-1,5,-2};
		int [] c4_exp = {0,1,3,5};
		Polynomial p4 = new Polynomial(c4_coef, c4_exp);
		
		Polynomial s2 = p3.add(p4);
		System.out.println("(coefs) sum of p3 and p4 is: " + Arrays.toString(s2.coef));
		System.out.println("(exp) sum of p3 and p4 is: " + Arrays.toString(s2.exp));
		
		Polynomial product = p3.multiply(p4);
		System.out.println("(coefs) product of p3 and p4 is: " + Arrays.toString(product.coef));
		System.out.println("(exp) product of p3 and p4 is: " + Arrays.toString(product.exp));
		
		
		// Testing constructor using File
		//String str = new String("5-3x2+7x8");
		// "(?=[+-])" check if for any given position, does + or - follows it, if yes then split around
		// System.out.println(Arrays.toString(str.split("(?=[+-])")));
		
		File f = new File("C:/Users/thuan/Downloads/lab2poly/src/lab2poly/polysample.txt/");
		Polynomial p5 = new Polynomial(f);
		System.out.println("(coefs) poly read from file: " + Arrays.toString(p5.coef));
		System.out.println("(exp) poly read from file: " + Arrays.toString(p5.exp));
		
		
		// testing .saveToFile
		String pathPolyToSave = "C:/Users/thuan/Downloads/lab2poly/src/lab2poly/polypaste.txt/";
		product.saveToFile(pathPolyToSave);
		
	}
}