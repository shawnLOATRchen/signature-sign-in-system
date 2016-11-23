package Adaboost;

public class Classification {
	
	public boolean isTrue = false;

	public Classification(User trainer1, User trainer2, User tester) {
		int J_FEATURE = 44;
		int T_ITERATION = 2;
		double H = 0;
		double[] y0 = {1, -1};
		double[] D = {0.5, 0.5};
		double[] y = new double[2];
		double[] testerFeatures = tester.getFeature();
		double[] trainerFeatures1 = trainer1.getFeature();
		double[] trainerFeatures2 = trainer2.getFeature();
		for (int i = 0; i < J_FEATURE * T_ITERATION; i++) {
			int j = i % J_FEATURE;
			double mu1 = testerFeatures[j];
			double mu2 = trainerFeatures1[j]; 
			
			y[0] = compareBetweenTwo(testerFeatures[j], mu1, mu2);
			y[1] = compareBetweenTwo(trainerFeatures1[j], mu1, mu2);
			
			double alpha = getAlpha(D, y, y0);
			D = updateD(D, alpha, y, y0);
			
			int h = compareBetweenTwo(trainerFeatures2[j], mu1, mu2);
			
			H = H + alpha * h;
//			System.out.println("h:" + h + ", alpha:" + alpha);
			System.out.println("H:" + H);
		}
		
		if (H >= 12 ){
			isTrue = false;
		}else {
			isTrue = true;
		}
		
		boolean result = adaboost(tester, trainer1, trainer2);
		System.out.println(result);
	}
	
	private boolean adaboost(User tester, User trainer1, User trainer2) {
		int J_FEATURE = 44;
		int T_ITERATION = 2;
		double H = 0;
		double[] y0 = {1, -1};
		double[] D = {0.5, 0.5};
		double[] y = new double[2];
		double[] testerFeatures = tester.getFeature();
		double[] trainerFeatures1 = trainer1.getFeature();
		double[] trainerFeatures2 = trainer2.getFeature();
		for (int i = 0; i < J_FEATURE * T_ITERATION; i++) {
			int j = i % J_FEATURE;
			double mu1 = testerFeatures[j];
			double mu2 = trainerFeatures1[j]; 
			
			y[0] = compareBetweenTwo(testerFeatures[j], mu1, mu2);
			y[1] = compareBetweenTwo(trainerFeatures1[j], mu1, mu2);
			
			double alpha = getAlpha(D, y, y0);
			D = updateD(D, alpha, y, y0);
			
			int h = compareBetweenTwo(trainerFeatures2[j], mu1, mu2);
			
			H = H + alpha * h;
//			System.out.println("h:" + h + ", alpha:" + alpha);
			System.out.println("H:" + H);
		}
		
		if (H >= 12 ){
			return false;
		}else {
			return true;
		}
	}
	
	private int compareBetweenTwo(double a, double mu1, double mu2) {
		if (Math.abs(a-mu1) <= Math.abs(a-mu2)){
			return 1;
		}else {
			return -1;
		}
	}
	
	private double getAlpha(double[] D, double[] y, double[] y0) {
		double e = 0;
		double alpha = 0;
		for (int i = 0; i < y.length; i++) {
			if (y[i] == y0[i]){
//				System.out.println(D[i]);
				e += D[i];
			}
		}
//		System.out.println("e: " + e);
		if (e == 0){
			alpha = 0.01;
		}else if(e == 1){
			alpha = 0.5 * Math.log(0.1);
		}else{
			alpha = 0.5 * Math.log((1-e)/e);
		}
		return alpha;
	}
	
	private double[] updateD(double[] D, double alpha, double[] y, double[] y0) {
		for (int i = 0; i < D.length; i++) {
			D[i] = D[i] * Math.exp(-alpha * y[i] * y0[i]);
		}
		double sumOfD = D[0] + D[1];
		for (int i = 0; i < D.length; i++) {
			D[i] = D[i] / sumOfD;
		}
		return D;
	}
}
