package Adaboost;

import java.awt.Point;
import java.util.ArrayList;

public class Points{
	public ArrayList< ArrayList<Point> > _signature;
	public int strokeNumber;
	public int _totalPointNumber;
	public int _longestStroke;
	int _longestStrokePoint;
	
	
	public Points(){
		_signature = new ArrayList<ArrayList<Point>>();
		strokeNumber = _signature.size();
		_totalPointNumber = 0;
		_longestStroke = 20;
		_longestStrokePoint = 0;
	}
	
	
	public void addStroke(ArrayList<Point> stroke){
		_signature.add(stroke);
		updatePoints();
	}
	
	public void updatePoints(){
		strokeNumber = _signature.size();
		for (int i = 0; i < strokeNumber ; i++){
			if (_longestStrokePoint <_signature.get(i).size() ){
				_longestStrokePoint = _signature.get(i).size();
				_longestStroke = i;
			}
			_totalPointNumber += _signature.get(i).size();
		}
	}
	
}
