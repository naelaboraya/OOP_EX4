package GUI;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import Game.Agent;
import Game.Arena;
import Game.Pokemon;
import GraphPack.*;

public class gui extends JFrame {

	public static final double EPS =0.000005;
	private int R = 2;
	private Arena frame;
	double[] x_range = new double[2];
	double[] y_range = new double[2];


	public static int Level;
	public static int Time ;
	public static int Moves;
	public static int Score;
	public gui(String a) {
		super(a);
	}

	public void renew(Arena A) {
		this.frame = A;
		refresh();}

	private void refresh() {
		window_fill();
	}

	public void paintComponents(Graphics G) {

		int width = this.getWidth();
		int height = this.getHeight();

		BufferedImage image_rescale = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		
		Graphics2D g= image_rescale.createGraphics();

		g.dispose();

		refresh();
		background_pic(G);
		draw_all_graph(G);
		draw_poks(G);
		agent_draw(G);
		score_draw(G);
	}

	/**
	 * This function is for the back ground
	 * @param g
	 */
	
	private void background_pic(Graphics g) {
		Image pic = Toolkit.getDefaultToolkit().getImage("src/GUI/pics/back2.jpg");
		g.drawImage(pic,0,0,this.getWidth()+350,this.getHeight()+350,null);
	}
	
	
	
	
	
	
	public void paint(Graphics g){
		this.repaint();
		int width = this.getWidth();
		int height = this.getHeight();

		Image image_container;
		Graphics buffer_graphics;
		image_container = createImage(width, height);
		buffer_graphics=image_container.getGraphics();
		paintComponents(buffer_graphics);
		g.drawImage(image_container, 0, 0, this);

	}
	
	
	
	

	
	
	

	/**
	 * This functions job is to draw the game players (pokemons) and give them images
	 * @param G
	 */
	private void draw_poks(Graphics G) {
		List<Game.Pokemon> fs = frame.get_pokemons();
		if (fs != null) {
			Iterator<Pokemon> itr = fs.iterator();

			while (itr.hasNext()) {
				Game.Pokemon P = itr.next();
				geo_loc pos = P.getLocation();
				if (pos != null) {

					double ansx = x_range(pos.x(), x_range[0], x_range[1] );
					double ansy = y_range(pos.y(), y_range[0], y_range[1] );
					if(P.getType() < 0){
						Image img = Toolkit.getDefaultToolkit().getImage("src/GUI/pics/pikatchu.png");
						G.drawImage(img,(int) ansx-5,(int) ansy-5, 30, 30 ,null);
					}else {
						Image img = Toolkit.getDefaultToolkit().getImage("src/GUI/pics/enemy2.png");
						G.drawImage(img,(int) ansx-5,(int) ansy-5, 30, 30 ,null);
					}


				}
			}
		}
	}
	
	
	
	

	/**
	 *	This function job is to draw the nodes
	 * @param node
	 * @param i
	 * @param G
	 */
	private void node_draw(NodeData node, int i, Graphics G) {
		GeoLocation pos = node.getLocation();



		double x = x_range(pos.x(), x_range[0], x_range[1] );
		double y = y_range(pos.y(), y_range[0], y_range[1] );

		G.setColor(Color.BLACK);
		G.fillOval((int) (x) + 4, (int) (y) + 4, 11, 11);

		((Graphics2D) G).setStroke(new BasicStroke(4));
		String key = String.valueOf(node.getKey());
		G.drawString(key, (int) (x) - R, (int) (y));
	}

	
	

	/**
	 * This function job is to draw the edges
	 * @param Edge
	 * @param G
	 */
	private void edge_draw(EdgeData Edge, Graphics G) {
		G.setFont(G.getFont().deriveFont(14.0F));
		double x1 = frame.get_graph().getNode(Edge.getSrc()).getLocation().x();
		double y1 = frame.get_graph().getNode(Edge.getSrc()).getLocation().y();

		double x2 = frame.get_graph().getNode(Edge.getDest()).getLocation().x();
		double y2 = frame.get_graph().getNode(Edge.getDest()).getLocation().y();

		x1 = (int) x_range(x1, x_range[0], x_range[1]);
		y1 = (int) y_range(y1, y_range[0], y_range[1]);

		x2 = (int) x_range(x2, x_range[0], x_range[1]);
		y2 = (int) y_range(y2, y_range[0], y_range[1]);

		G.setColor(Color.BLUE);
		((Graphics2D) G).setStroke(new BasicStroke(3));
		arrow_draw(G, (int) (x1) + 10, (int) (y1) + 10, (int)x2 + 10,(int) y2 + 10);

	}

	
	
	

	/**
	 * This function job is to drwaw agents
	 * @param G
	 */
	private void agent_draw(Graphics G) {
		List<Agent> _agents = frame.get_agents();
		G.setColor(Color.black);
		int temp = 0;

		while (_agents != null && temp < _agents.size()) {
			GeoLocation pos = _agents.get(temp).get_pos();
			temp++;


			if (pos != null) {
				double x = x_range(pos.x(), x_range[0], x_range[1] );
				double y = y_range(pos.y(), y_range[0], y_range[1] );
				Image pic = Toolkit.getDefaultToolkit().getImage("src/GUI/pics/ball.jpg");
				G.drawImage(pic,(int) x,(int) y, 25, 25 ,null);
			}
		}
	}

	
	

	/**
	 * The scoreboard
	 * @param g
	 */
	private void score_draw(Graphics g){
		String rank = "Level: "+Level;
		String Timer = "Timer: "+Time;
		String Grade = "Score: "+Score;
		String moves = "Moves: "+Moves;
		g.setColor(Color.ORANGE);


		g.drawString(rank,30,50);
		g.drawString(Timer,120,50);
		g.drawString(Grade,210,50);
		g.drawString(moves,300,50);
	}


	private double x_range(double k, double min, double max) {

		double d=this.getWidth() - 150;
		double var=(k - min) * d;
		double var2=max - min;

		return ((var / var2) + 14);

	}


	private double y_range(double y, double min, double max) {
		double var = this.getHeight() - 200;
		double var2 = y - min;
		double var3 =max - min;
		return (((var2 * var) / var3) + 30);
	}


	private void window_fill() {
		HashMap<Integer, Double> X = new HashMap<>();
		HashMap<Integer, Double> Y = new HashMap<>();
		if(frame!=null) {
			Iterator<NodeData> it = frame.get_graph().nodeIter();
			while (it.hasNext()) {
				NodeData temp = it.next();
				GeoLocation point = temp.getLocation();
				X.put(temp.getKey(), point.x());
				Y.put(temp.getKey(), point.y());
			}
		}
		double min_x = Collections.min(X.values());
		double max_x = Collections.max(X.values());
		double min_y = Collections.min(Y.values());
		double max_y = Collections.max(Y.values());



		x_range[0] = min_x;
		y_range[0] = min_y;
		x_range[1] = max_x;
		y_range[1] = max_y;

	}
	
	
	
	private void arrow_draw(Graphics g, int x1, int y1, int x2, int y2) {
		int size = 10;
		Graphics2D G = (Graphics2D) g.create();
		double dx = x2 - x1;
		double dy = y2 - y1;
		double  v1=dx * dx;
		double  v2=dy * dy;
		int length = (int) Math.sqrt(v1+v2);
		double angle = Math.atan2(dy, dx);




		AffineTransform trans = AffineTransform.getTranslateInstance(x1, y1);
		AffineTransform trans_angle=AffineTransform.getRotateInstance(angle);
		trans.concatenate(trans_angle);

		G.transform(trans);
		

		G.setStroke(new BasicStroke(3));

		length = length - (int) (EPS * length);
		G.drawLine(0, 0, length + (int) (EPS * length), 0);


		G.fillPolygon(new int[]{length, length - size, length - size, length}, new int[]{0, -size, size , 0}, 4);

	}


	private void draw_all_graph(Graphics G) {
		DirectedWeightedGraph g = frame.get_graph();

		Iterator<NodeData> iter= g.nodeIter();

		while (iter.hasNext()) {
			NodeData n = iter.next();
			G.setColor(Color.blue);
			node_draw(n, 1, G);
			Iterator<EdgeData> itr = g.edgeIter(n.getKey());
			while (itr.hasNext()) {
				EdgeData e = itr.next();
				G.setColor(Color.gray);
				edge_draw(e, G);
			}
		}
	}

}
