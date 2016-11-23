package Adaboost;




public class User {
	private static final int featureSize = 44;
	private String _userName;
	private String _cardNumber;
	private double[] _featureList;
	private double[] _weight;
	
	public User(){
		_userName = "John Doe";
		_cardNumber = "10000000"; //12345678 style;
		_featureList = new double[featureSize];
		_weight = new double[featureSize];
		/*
		for(int i = 0; i<featureSize; i++){
			_featureList[i] = 0.0;
			_weight[i] = 1;
		}
		*/
	}
	
	public User(String name, String cardNumber){
		this();
		_userName = name;
		_cardNumber = cardNumber;
	}
	
	// setter and getter for the use name
	public String getName(){
		return _userName;
	}
	
	public void setName(String name){
		_userName = name;
	}
	
	// setter and getter for the card number	
	public void setCardNumber(String newCardNumber){
		_cardNumber = newCardNumber;
	}
	
	public String getCardNumber(){
		return _cardNumber;
	}
	
	//setter and getter for the feature list	
	public void setNewFeature(double[] newFeatureList){
		_featureList = newFeatureList;
	}
	
	public double[] getFeature(){
		return _featureList;
	}
	
	// setter and getter for the weight of each feature	
	public void setWeight(double[] newWeight){
		// this method may not work, try to use 2 for loops to do the assignment;
		if (_weight.length != newWeight.length){
			System.out.println("Check the version of this program, feature weight size dose not match!");
			return;
		}
		_weight = newWeight;
	}
	
	public double[] getWeight(){
		return _weight;
	}
	
	

}
