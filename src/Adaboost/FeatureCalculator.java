package Adaboost;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FeatureCalculator {
	// define some instance variable
	public static final int FEATURESIZE = 44;
	private Points userSignNew;
	private double[] _featureList;
	private double[] _totalX;
	private double[] _totalY;
	private int totalPointNumber;
	private int longestStrokeNumber;
	private int longestStartPnt;
	private int longestEndPnt;
	ArrayList<Integer> cuspPntPos;

	// constructor
	public FeatureCalculator(Points signature){
		userSignNew = signature;
		_featureList = new double[FEATURESIZE];
		totalPointNumber = userSignNew._totalPointNumber;
		longestStrokeNumber = signature._longestStroke;
		longestEndPnt = totalPointNumber-1;
		
		if(totalPointNumber == 0){
			System.out.println("The point set seems wrong, no points found");
			return;
		}
		_totalX = new double[totalPointNumber];
		_totalY = new double[totalPointNumber];
		Arrays.fill(_totalX, 0);
		Arrays.fill(_totalY, 0);



		// probably add some checking statement here to check if the input is valid;



		// this will calculate the feature for current stroke;
		calculateFeature();
		outputFeature();

	}


	public void calculateFeature(){
		// do some preprocessing stuff

		// make some variables for calculating;
		double[] diffX = new double[totalPointNumber];
		double[] diffY = new double[totalPointNumber];
		double[] angle = new double[totalPointNumber];
		double[] tempFeature = new double[FEATURESIZE];		// below variable will hold the temperary feature;
		double[] diffAngle = new double[totalPointNumber];
		int pointNumberInd = 0;
		double whRatio = 0;
		double width= 0;
		double height = 0;
		double firstLastDist = 0;
		double totalLength = 0;
		double sumX = 0;
		double sumY = 0;
		double xBar = 0;
		double yBar = 0;
		double xxBar = 0;
//		double yyBar = 0;
		double xyBar= 0;
		boolean inLongest = false;

		Arrays.fill(tempFeature, 0); // put all the temp value to 0;
		Arrays.fill(diffX, 0);
		Arrays.fill(diffY, 0);
		Arrays.fill(angle, 0);
		
		// store the global min/max value of x and y
		double globalSmallX = 800;
		double globalSmallY = 400;
		double globalLargeX = 0;
		double globalLargeY = 0;
		
		// store the global postion of max/ min x y
		int maxXLocation = 0;
		int maxYLocation = 0;
		int minXLocation = 0;
		int minYLocation = 0;
		
		// number in each area;
		int[] pntQty = new int[8];
		Arrays.fill(pntQty, 0);
		
		// angle in each area;
		int[] angleQty = new int[8];
		Arrays.fill(angleQty, 0);
		
		int totalCuspQty = 0;
		int longCuspQty = 0;
		cuspPntPos = new ArrayList<Integer>();
		
		double [] DX = new double[totalPointNumber];
		double [] DY = new double[totalPointNumber];
		
		
		// iron through the whole x and y;
		int ttlPntQty = 0;
		for (int i = 0; i < userSignNew.strokeNumber ; i++){
			for (int j = 0; j < userSignNew._signature.get(i).size() ; j++){
				DX[ttlPntQty] = userSignNew._signature.get(i).get(j).x;
				DY[ttlPntQty] = userSignNew._signature.get(i).get(j).y;
				ttlPntQty ++;
			}
			
		}
		
		for (int i = 0; i< DX.length ; i++){
			if( i<2  ){
				double avgX = (DX[0] + DX[1])/2;
				_totalX[i] = avgX;
				double avgY = (DY[0]+ DY[1]) / 2;
				_totalY[i] = avgY;
			} else if(totalPointNumber - i <= 2){
				double avgX = (DX[DX.length - 1] + DX[DX.length - 2])/2;
				double avgY = (DY[DY.length - 1] + DY[DY.length - 2])/2;
				_totalX[i] = avgX;
				_totalY[i] = avgY;
			} else {
				double avgX = (DX[i-2] + DX[i-1] + DX[i] + DX[i+1] + DX[i+2])/5;
				double avgY = (DY[i-2] + DY[i-1] + DY[i] + DY[i+1] + DY[i+2])/5;
				_totalX[i] = avgX;
				_totalY[i] = avgY;
			}
		}
		






		//// LOOP THROUGH EACH STROKE;
		for (int i = 0; i < userSignNew.strokeNumber; i++){
			
			if (longestStrokeNumber == i && inLongest == false){
				longestStartPnt = pointNumberInd;
				inLongest = true;
			}
			
			if (inLongest == true && longestStrokeNumber != i && pointNumberInd < longestEndPnt){
				longestEndPnt = pointNumberInd;
				inLongest = false;
			}

			// this help to get the stroke length feature,                      FEATURE 1;
			double thisStrokeLength = 0;
			double largestX = 0;
			double largestY = 0;
			double smallestX = 800;
			double smallestY = 400;

			
			
			
			//// LOOP THROUGH EACH POINT IN THIS STROKE;
			for (int j = 0; j < userSignNew._signature.get(i).size(); j++){
				double thisX;
				double thisY;
				// write the point to the total array to access in future;
//				thisX = userSignNew._signature.get(i).get(j).x;
				thisX = _totalX[pointNumberInd]; // = thisX; 
//				thisY = 400 - userSignNew._signature.get(i).get(j).y;
				thisY = _totalY[pointNumberInd]; // = thisY;
				
				// update the largest and smallest X and Y value;
				largestX = Math.max(thisX, largestX);
				largestY = Math.max(thisY, largestY);
				smallestX = Math.min(thisX, smallestX);
				smallestY = Math.min(thisY, smallestY);
				
				sumX += thisX;
				sumY += thisY;

				// 1 calculate the distance between each points;
				double diffx, diffy;
				if (j == 0){
					diffx = 0;
					diffy = 0; //(userSignNew._signature.get(i).get(j).y) - userSignNew._signature.get(i).get(j-1).y;
					angle[pointNumberInd] = 0;
					diffAngle[pointNumberInd]  = 0;
				} else {
					diffx = _totalX[pointNumberInd] - _totalX[pointNumberInd-1];
					diffy = _totalY[pointNumberInd] - _totalY[pointNumberInd-1];
					double thisAngle = Math.atan2(diffy, diffx)/Math.PI * 180;
					if (thisAngle < 0){
						thisAngle = thisAngle + 360;
					}
					angle[pointNumberInd] = thisAngle;
					double thisDiffAngle;
					if (j == 2){
						thisDiffAngle = 0;
					} else {
						thisDiffAngle = thisAngle- angle[pointNumberInd-1];
					}
					if (thisDiffAngle > 180 || thisDiffAngle < -180){
						if (thisDiffAngle > 0){
							thisDiffAngle -= 180;
						} else {
							thisDiffAngle += 180;
						}
					}
					
					thisDiffAngle = Math.abs(thisDiffAngle);
					diffAngle[pointNumberInd] = thisDiffAngle;
				}

				// 2 calculate the total length of this stroke;
				double tempLenght = Math.sqrt(diffx*diffx + diffy*diffy);
				thisStrokeLength += tempLenght;

				// update the diffX vector and diffY vector
				diffX[pointNumberInd] = diffx;
				diffY[pointNumberInd] = diffy;
				
				// increment the pointNumberInd by 1;
				pointNumberInd += 1;

			}////////////////////////////////////////////
			// end LOOP THROUGH EACH POINTS IN THIS STROKE

			// get the length of the signature;
			totalLength += thisStrokeLength;
			xBar = (double)sumX / (double)totalPointNumber;
			yBar = (double)sumY / (double)totalPointNumber;
			
			if (largestX > globalLargeX){
				globalLargeX = largestX;
				maxXLocation = i;
			}
			if (largestY > globalLargeY){
				globalLargeY = largestY;
				maxYLocation = i;
			}
			if (smallestX < globalSmallX){
				globalSmallX = smallestX;
				minXLocation = i;
			}
			if (smallestY < globalSmallY){
				globalSmallY = smallestY;
				minYLocation = i;
			}

		}
		// END LOOP THROUGH EACH STROKE
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		width = (double)globalLargeX - (double)globalSmallX;
		height = (double)globalLargeY - (double)globalSmallY;
		whRatio = (double)width/(double)height;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		double midY = height/2 + (double)globalSmallY;
		double quartX = width/4 + (double)globalSmallX;
		double midX = width/2 + (double)globalSmallX;
		double quartX2 = (double)quartX + (double)midX - (double)globalSmallX;
		
		firstLastDist = Math.sqrt(((double)_totalX[0] - (double)_totalX[_totalX.length-1])*((double)_totalX[0] - (double)_totalX[_totalX.length-1])
				+ ((double)_totalY[0] - (double)_totalY[_totalX.length-1])*((double)_totalY[0] - (double)_totalY[_totalX.length-1]));
		
		startEndPos( midY, quartX, midX, quartX2);
		
		// second for loop
		for (int j = 0; j < totalPointNumber; j++){
			// get local x and y for easier calculation;
			double thisX = (double)_totalX[j];
			double thisY = (double)_totalY[j];
			
			xxBar += (thisX - xBar)*(thisX - xBar);
//			yyBar += (thisY - yBar) * (thisY - yBar);
			xyBar += (thisX - xBar)*(thisY - yBar);
			
			double thisAngle = angle[j];
			
			// get the histogram data for location;
			if(thisY >= midY){
				if (thisX < quartX){
					pntQty[0] +=1;
				} else if(thisX < midX){
					pntQty[1] += 1;
				} else if(thisX < quartX2){
					pntQty[2] +=1;
				} else {
					pntQty[3] +=1;
				}
			}else {
				if (thisX < quartX){
					pntQty[4] +=1;
				} else if(thisX < midX){
					pntQty[5] += 1;
				} else if(thisX < quartX2){
					pntQty[6] +=1;
				} else {
					pntQty[7] +=1;
				}
			}
			
			// get the histogram data for angles;
			if (thisAngle < 45){
				angleQty[0] +=1;
			}else if(thisAngle < 90){
				angleQty[1] +=1;
			}else if(thisAngle < 135){
				angleQty[2] +=1;
			}else if(thisAngle < 180){
				angleQty[3] +=1;
			}else if(thisAngle < 225){
				angleQty[4] +=1;
			}else if(thisAngle < 270){
				angleQty[5] +=1;
			}else if(thisAngle < 315){
				angleQty[6] +=1;
			}else{
				angleQty[7] +=1;
			}
			
			if (j > 10 && Math.abs(diffAngle[j]) > 60){
				totalCuspQty +=1;
				cuspPntPos.add(j);
			}
			
			
			
		}/////////////////////////////////////second for loop ////////////////////////////

		
		double regressionK = xyBar / xxBar;
		
		int xStart = 0;
		int xEnd = 0;
		int yStart = 0;
		int yEnd = 0;
		if (_totalX[1] - _totalX[0] < 0){
			xStart = -1;
		}else {
			xStart = 1;
		}
		
		if (_totalX[totalPointNumber-1] - _totalX[totalPointNumber-2] < 0){
			xEnd = -1;
		}else {
			xEnd = 1;
		}
		
		if (_totalY[1] - _totalY[0] < 0){
			yStart = -1;
		}else {
			yStart = 1;
		}
		
		if (_totalY[totalPointNumber-1] - _totalY[totalPointNumber-2] < 0){
			yEnd = -1;
		}else {
			yEnd = 1;
		}
		
		
		// cal a serial of functions to calculate the feature;
		// write the features into feature list;
		featureEditor(1, userSignNew._signature.size());  // stroke number    <- feature 1
		featureEditor(2, maxXLocation);
		featureEditor(3, minXLocation);
		featureEditor(4, maxYLocation);
		featureEditor(5, minYLocation);
		featureEditor(6, totalCuspQty);
		// 
		featureEditor(8, whRatio);
		// 
		featureEditor(10, pntQty[0]/(double)totalPointNumber);
		featureEditor(11, pntQty[1]/(double)totalPointNumber);
		featureEditor(12, pntQty[2]/(double)totalPointNumber);
		featureEditor(13, pntQty[3]/(double)totalPointNumber);
		featureEditor(14, pntQty[4]/(double)totalPointNumber);
		featureEditor(15, pntQty[5]/(double)totalPointNumber);
		featureEditor(16, pntQty[6]/(double)totalPointNumber);
		featureEditor(17, pntQty[7]/(double)totalPointNumber);
		
		// angle data
		featureEditor(18, angleQty[0]/(double)totalPointNumber);
		featureEditor(19, angleQty[1]/(double)totalPointNumber);
		featureEditor(20, angleQty[2]/(double)totalPointNumber);
		featureEditor(21, angleQty[3]/(double)totalPointNumber);
		featureEditor(22, angleQty[4]/(double)totalPointNumber);
		featureEditor(23, angleQty[5]/(double)totalPointNumber);
		featureEditor(24, angleQty[6]/(double)totalPointNumber);
		featureEditor(25, angleQty[7]/(double)totalPointNumber);
		
		featureEditor(26, firstLastDist/width);
		featureEditor(27, totalLength/width);
		featureEditor(28, regressionK);

		// get the longest features;
		longestFeatures( longestStartPnt,  longestEndPnt, width, height);
		
		featureEditor(37, (double)longestStartPnt / (double)totalPointNumber);
		featureEditor(38, ((double)totalPointNumber - (double)longestEndPnt)/(double)totalPointNumber);
		featureEditor(39, ((double)longestEndPnt - (double)longestStartPnt)/(double)totalPointNumber);
		if ((totalPointNumber - longestEndPnt) == 0){
			featureEditor(40, 1);
		}else {
			featureEditor(40, (double)longestEndPnt / ((double)totalPointNumber - (double)longestEndPnt));
		}
		
		featureEditor(41, xStart);
		featureEditor(42, xEnd);
		featureEditor(43, yStart);
		featureEditor(44, yEnd);
		

	}

	
	
	
	
	
	
	// this is the only function which can modify the _featureList;
	public void featureEditor(int featureInd, double featureVal){
		_featureList[featureInd-1] = featureVal;
	}

	// this will use to return the feature from the stroke;
	public double[] getFeature(){
		return _featureList;
	}
	
	public void outputFeature(){
		for (int i = 0; i < _featureList.length ; i++){
			System.out.println("Feature #"+ i+ "="+ _featureList[i]);
		}
	}
	
	public void startEndPos(double midY, double quartX, double midX, double quartX2){
		double startX = _totalX[0];
		double startY = _totalY[0];
		double endX = _totalX[_totalX.length-1];
		double endY = _totalY[_totalY.length-1];
		
		int startPos;
		int endPos;
		
		
		if(startY >= midY){
			if (startX < quartX){
				startPos = 0;
			} else if(startX < midX){
				startPos = 1;
			} else if(startX < quartX2){
				startPos = 2;
			} else {
				startPos = 3;
			}
		}else {
			if (startX < quartX){
				startPos = 4;
			} else if(startX < midX){
				startPos = 5;
			} else if(startX < quartX2){
				startPos = 6;
			} else {
				startPos = 7;
			}
		}
		
		if(endY >= midY){
			if (endX < quartX){
				endPos = 0;
			} else if(endX < midX){
				endPos = 1;
			} else if(endX < quartX2){
				endPos = 2;
			} else {
				endPos = 3;
			}
		}else {
			if (endX < quartX){
				endPos = 4;
			} else if(endX < midX){
				endPos = 5;
			} else if(endX < quartX2){
				endPos = 6;
			} else {
				endPos = 7;
			}
		}
		
		featureEditor(35, startPos);
		featureEditor(36, endPos);
	}
	
	
	
	public void longestFeatures(int longestStartPnt, int longestEndPnt, double globalW, double globalH){
		double longestRatio = (double)(longestEndPnt - longestStartPnt) / (double)totalPointNumber;
//		System.out.println(longestEndPnt + " " + longestStartPnt + " " + totalPointNumber);
		int cross = 0;
		
//		int[] diffX = new int[longestEndPnt - longestStartPnt-1];
//		int[] diffY = new int[longestEndPnt - longestStartPnt-1];
		
		double minX = 800;
		double maxX = 0;
		double minY = 400;
		double maxY = 0;
		
		int longestTotalY = 0;
		
		double area = 0;
		
		double x0 = _totalX[longestStartPnt];
		double y0 = _totalY[longestStartPnt];
		
		for (int i = longestStartPnt; i< longestEndPnt ; i++){
			double x1 = _totalX[i];
			double x2 = _totalX[i+1];
			double y1 = _totalY[i];
			double y2 = _totalY[i+1];
			
//			if (i == longestStartPnt){
//				diffX[0] = 0;
//				diffY[0] = 0;
//			}else {
//				diffX[i - longestStartPnt] = x1 - _totalX[i - 1];
//				diffY[i - longestStartPnt] = y1 - _totalY[i - 1];
//			}
			
			double dx1 = x1 - x0;
			double dx2 = x2 - x0;
			double dy1 = y1 - y0;
			double dy2 = y2 - y0;
			
			double dot = dx1 * dx2 + dy1 * dy2;
			double det = Math.sqrt(dx1*dx1 + dy1 * dy1)* Math.sqrt(dx2*dx2 + dy2*dy2);
			double thisArea = Math.sqrt(dx1*dx1 + dy1 * dy1)* Math.sqrt(dx2*dx2 + dy2*dy2) * Math.sin(Math.acos(dot/det));
			area += thisArea;
			
			
			longestTotalY += y1;
			minX = Math.min(minX, x1);
			minY = Math.min(minY, y1);
			maxX = Math.max(maxX, x1);
			maxY = Math.max(maxY, y1);
			
			double disBase = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
			
			int j = i;
			
			while (j < longestEndPnt){
				double px = _totalX[j];
				double py = _totalY[j];
				
				double dis1 = Math.sqrt((px - x1)*(px - x1) + (py - y1)*(py - y1));
				double dis2 = Math.sqrt((px - x2)*(px - x2) + (py - y2)*(py - y2));
				
				double disMin = Math.min(dis1, dis2);
				
				if(disMin < disBase){
					Iterator<Integer> it = cuspPntPos.iterator();
					while (it.hasNext()){
						int thisPnt = it.next();
						if (Math.abs(thisPnt - j) < 20){
							break;
						}
					}
					cross += 1;
				}
				j++;
			}
		}
		
//		for (int i = 0 ; i < diffX.length ; i++){
//			
//		}
		
		double width = maxX - minX;
		double height = maxY - minY;
		
		double ratio = width / height;
		double ratio2 = width / globalW;
		double ratio3 = height / globalH;
		
		double avgY = longestTotalY/ (longestEndPnt - longestStartPnt);
		double ratio4 = (avgY - minY) / (height);
		
		featureEditor(7, longestRatio);
		featureEditor(34 , cross);
		featureEditor(29, ratio);
		featureEditor(30, ratio2);
		featureEditor(31, ratio3);
		featureEditor(32, ratio4);
		featureEditor(33, area);
	}
}


/*
 * FEATURE LIST:
 * Feature 1:
 * 			Stroke Number;
 * Feature 2:
 * 			point number;


 */