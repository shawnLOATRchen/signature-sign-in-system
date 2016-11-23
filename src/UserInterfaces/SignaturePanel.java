package UserInterfaces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JPanel;
import Adaboost.Points;

public class SignaturePanel extends JPanel {
	
	boolean isDraw = false;
	boolean isStart = false;
	public boolean doClean = false;
	public Points points = new Points();
	ArrayList<Point> stroke;
	
	public SignaturePanel() {
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				super.mousePressed(e);
				isDraw = true;
				isStart = true;
				doClean = false;
				stroke = new ArrayList<Point>();
			}
			
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				super.mouseReleased(e);
				isDraw = false;
				points.addStroke(stroke);
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(java.awt.event.MouseEvent e) {
				super.mouseDragged(e);
				if (isDraw){
					Point p = e.getPoint();
					if(p.x >0 && p.x <800 && p.y > 0 && p.y <400){
						stroke.add(p);
//						System.out.println("x:" + p.x + ", y:" + p.y);
					}
				}
				repaint();
			}
		});
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (doClean) {
			g.drawLine(0, 0, 0, 0);
			return;
		} else if (isStart && !doClean) {
			Graphics2D g2D = (Graphics2D) g;
			g2D.setColor(Color.BLACK);
			g2D.setStroke(new BasicStroke(3.5f));
			for (int s=0; s<points.strokeNumber; s++) {
				ArrayList<Point> singleStroke = points._signature.get(s);
				for (int i = 1; i < singleStroke.size(); i++) {
					//							System.out.println("x:" + singleStroke.get(i).x + ", y:" + singleStroke.get(i).y);
					g2D.drawLine(singleStroke.get(i-1).x, singleStroke.get(i-1).y, singleStroke.get(i).x, singleStroke.get(i).y);
				}
			}
			for (int i = 1; i < this.stroke.size(); i++) {
				g2D.drawLine(this.stroke.get(i-1).x, this.stroke.get(i-1).y, this.stroke.get(i).x, this.stroke.get(i).y);
			}
		} 
	}
	
	public void cleanPanel() {
		doClean = true;
		repaint();
	}
	
	
	
}
