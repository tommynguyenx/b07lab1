public class Polynomial {
	double [] coef;
	
	public Polynomial() {
		this.coef = new double[1];
	}

	public Polynomial(double [] coef) {
		this.coef = new double[coef.length];
		for (int i = 0; i < coef.length; ++i) {
			this.coef[i] = coef[i];
		}
	}

	public Polynomial add(Polynomial p) {
		double [] x = new double[Math.max(p.coef.length, coef.length)];
		
		for (int i = 0; i < x.length; ++i) {
			if (i < Math.min(p.coef.length, coef.length))
				x[i] = p.coef[i] + coef [i];
			else if (p.coef.length > coef.length)
				x[i] = p.coef[i];
			else
				x[i] = coef[i];
		}

		Polynomial q = new Polynomial(x);
		return q;
	}


	public double evaluate(double x) {
		double val = 0;
		for (int i = 0; i < coef.length; ++i) 
			val += coef[i]*(Math.pow(x, i));

		return val;
	}


	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}

}
	