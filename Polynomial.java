import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Polynomial {
	double [] coef;
	int [] exp;
	
	public Polynomial() {
		this.coef = new double[0];
		this.exp = new int[0];
	}
	
	public Polynomial(double [] coef, int [] exp) {
		int len = coef.length;

		this.coef = new double[len];
		this.exp = new int[len];
		for (int i = 0; i < len; ++i) {
			this.coef[i] = coef[i];
			this.exp[i] = exp[i];
		}
	}
	
	public Polynomial(File f) {
		try (Scanner x = new Scanner(f)) {
			ArrayList<Double> x_coef_arrl = new ArrayList<Double>(0);
			ArrayList<Integer> x_exp_arrl = new ArrayList<Integer>(0);
			
			String input = new String(x.nextLine());
			x.close();
			for (String term : input.split("(?=[+-])")) {
				String [] components = term.split("x");
				x_coef_arrl.add(Double.parseDouble(components[0]));
				if (term.contains("x") && components.length == 2) {
					x_exp_arrl.add(Integer.parseInt(components[1]));
				} else if (term.contains("x")) {
					x_exp_arrl.add(1);
				} else {
					x_exp_arrl.add(0);
				}
			}
			
			int len = x_coef_arrl.size();
			this.coef = new double[len];
			this.exp = new int[len];
			if (len > 0) {
				for (int i = 0; i < len; ++i) {
					this.coef[i] = x_coef_arrl.get(i);
					this.exp[i] = x_exp_arrl.get(i);
				}
			}
		} catch (FileNotFoundException e) {
			// (have try-catch here so that Eclipse don't flag)
		}
		
	}

	public HashMap<Integer, Double> makeHash() {
		HashMap<Integer, Double> polyHash = new HashMap<Integer, Double>();

		for (int i = 0; i < this.coef.length; ++i) {
			polyHash.put(this.exp[i], this.coef[i]);
		}
		return polyHash;
	}
	
	public static Polynomial convertHashToPoly(HashMap<Integer, Double> xHash) {
		ArrayList<Double> x_coef_arrl = new ArrayList<Double>(0);
		ArrayList<Integer> x_exp_arrl = new ArrayList<Integer>(0);
		
		for (int key : xHash.keySet()) {
			if (xHash.get(key) != 0) {
				x_exp_arrl.add(key);
				x_coef_arrl.add(xHash.get(key));
			}
		}
		
		int len = x_exp_arrl.size();
		Polynomial x;
		
		if (len == 0) {
			x = new Polynomial();
		} else {
			double[] new_coef = new double[len];
			int[] new_exp = new int[len];
			for (int i = 0; i < len; ++i) {
				new_coef[i] = x_coef_arrl.get(i);
				new_exp[i] = x_exp_arrl.get(i);
			}
			x = new Polynomial(new_coef, new_exp);
		}
		return x;
	}

	public Polynomial add(Polynomial p) {
		HashMap<Integer, Double> pHash = p.makeHash();
		HashMap<Integer, Double> objHash = this.makeHash();
		HashMap<Integer, Double> xHash = new HashMap<Integer, Double>();
		
		for (int exp : objHash.keySet()) {
			if (pHash.containsKey(exp)) {
				xHash.put(exp, objHash.get(exp)+pHash.get(exp));
			} else {
				xHash.put(exp, objHash.get(exp));
			}
		}
		for (int exp : pHash.keySet()) {
			if (!xHash.containsKey(exp)) {
				xHash.put(exp, pHash.get(exp));
			}
		}
		return convertHashToPoly(xHash);
	}


	public Polynomial multiply(Polynomial p) {
		HashMap<Integer, Double> objHash = this.makeHash();
		HashMap<Integer, Double> pHash = p.makeHash();
		HashMap<Integer, Double> xHash = new HashMap<Integer, Double>();
		
		for (int exp1 : objHash.keySet()) {
			for (int exp2 : pHash.keySet()) {
				int new_exp = exp1 + exp2;
				if (!xHash.containsKey(new_exp)) {
					xHash.put(new_exp, objHash.get(exp1)*pHash.get(exp2));
				} else {
					xHash.replace(new_exp, xHash.get(new_exp) + objHash.get(exp1)*pHash.get(exp2));
				}
			}
		}
		return convertHashToPoly(xHash);
	}
	

	public double evaluate(double x) {
		double val = 0;
		for (int i = 0; i < this.coef.length; ++i) 
			val += this.coef[i]*(Math.pow(x, this.exp[i]));

		return val;
	}


	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
	
	public void saveToFile(String path) {
		// assume given file is empty
		try (FileWriter output = new FileWriter(new File(path))) {
			int len = this.coef.length;
			String polyStr = new String();
			
			for (int i = 0; i < len; ++i) {
				String toWrite = new String();

				if (this.coef[i] != 1)
					toWrite += String.valueOf(this.coef[i]);
				if (i != 0 && this.coef[i] > 0)
					toWrite = "+" + toWrite;
				if (this.exp[i] > 0) {
					toWrite += "x";
					if (this.exp[i] > 1)
						toWrite += String.valueOf(this.exp[i]);
				}
				polyStr += toWrite;
			}
			
			output.write(polyStr);
			output.close();
		} catch (IOException e) {
			// (try-catch for Eclipse to not flag) assume valid inputs
		}
	}
}