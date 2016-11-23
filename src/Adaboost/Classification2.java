package Adaboost;

import java.util.Arrays;

public class Classification2 {
	private static final int featureSize = 44;
	User trainer1;
	User trainer2;
	User tester;
	
	double _result;
	
	double[] trainer1F;
	double[] trainer2F;
	double[] testerF;
	double[] diffBase;

	public Classification2(User trainer1, User trainer2, User tester) {
		
		//for (int i = 0; i < featureSize; i++){
			trainer1F = trainer1.getFeature();
			trainer2F = trainer2.getFeature();
			testerF = tester.getFeature();
		//}
		diffBase = new double[featureSize];
		getBase();
		
		_result = getScore()/44;
		
//		boolean result = adaboost(tester, trainer1, trainer2);
		
		System.out.println(_result);
	}
	
	public void getBase(){
		for (int i = 0; i < featureSize; i++){
			diffBase[i] = Math.abs(trainer1F[i] - trainer2F[i]);
		}
	}
	
	public double getScore(){
		double[] scoreV = new double[featureSize];
		Arrays.fill(scoreV, 0);
		double score = 0;

		for (int i = 0; i < 44 ; i++){
			double base;
			double value1 = Math.abs( Math.abs(testerF[i]) - Math.abs(trainer1F[i]));
			double value2 = Math.abs( Math.abs(testerF[i]) - Math.abs(trainer2F[i]));
			double value = Math.min(value1,  value2);
			
			if (trainer1F[i] !=0 && trainer2F[i] !=0){
				base = diffBase[i] / Math.min(Math.abs(trainer1F[i]), Math.abs(trainer2F[i]));
			}else if (trainer1F[i] !=0 || trainer2F[i] !=0){
				base = diffBase[i] / Math.max(Math.abs(trainer1F[i]), Math.abs(trainer2F[i]));
			}else {
				base = 0;
			}
			
			if (base != 0){
				double percent = diffBase[i] / base;
				double percentR = value / base;
				
				if (percentR < percent*1.4){
					scoreV[i] = 1;
				}
			}
			
			if (Math.abs(value - diffBase[i])/diffBase[i] < 0.2 ){
				scoreV[i] = 1;
			}
			
			if( i < 5 && ((testerF[i] - trainer1F[i] == 0) || (testerF[i] - trainer2F[i] == 0))){
				scoreV[i] = 1;
			}			
		}
		
		for (int i = 0; i < featureSize ; i++){
//			if (i >= 17 && i <=24){
//				scoreV[i] *= 1.2;
//			}
			if ( i < 6) {
				scoreV[i] *= 0.8;
			}
			score += scoreV[i];
			System.out.println(i + "th: " + scoreV[i]);
		}
		
		
		
		return score;
	}
	
	public double getResult(){
		return _result;
	}
	
}
