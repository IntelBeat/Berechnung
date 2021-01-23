import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Graph extends JPanel {
    private static final int MAX_SCORE = 300;
    private static final int PREF_W = 800;
    private static final int PREF_H = 650;
    private static final int BORDER_GAP = 30;
    private static final Color GRAPH_COLOR = Color.green;
    private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final int GRAPH_POINT_WIDTH = 7;
    private static final int X_HATCH_CNT = 10;
    private static final int Y_HATCH_CNT = 10;



    private List<Integer> scores;

    private Point min_values;
    private Point max_values;


    public Graph(List<Integer> scores) {
        this.scores = scores;
    }

    public Graph(ArrayList<Point> values) {
        Point min = new Point(0, 0);
        Point max = new Point(0, 0);

        for(Point p: values){
            if(p.x < min.x) min.x = p.x;
            if(p.x > max.x) max.x = p.x;
            if(p.y < min.y) min.y = p.y;
            if(p.y > max.y) max.y = p.y;
        }

        min_values = min;
        max_values = max;

    }

    public Graph(double min_x, double max_x, double min_y, double max_y) {

        min_values = new Point(min_x, min_y);
        max_values = new Point(max_x, max_y);

        createGraph(new Point(min_x, min_y), new Point(max_x, max_y));

    }

    private void createGraph(Point min, Point max) {

    }

    private void drawAcis(Graphics2D g2d){

        int x_offset = (int) ((getWidth()-2*BORDER_GAP) * min_values.x / (min_values.x - max_values.x));
        int y_offset = (int) ((getHeight()-2*BORDER_GAP) * max_values.y / (max_values.y - min_values.y));

        System.out.println("x: " + x_offset);
        System.out.println("y: " + y_offset);

        // create x and y axes
        g2d.drawLine(BORDER_GAP, BORDER_GAP + y_offset, getWidth() - BORDER_GAP, BORDER_GAP + y_offset);
        g2d.drawLine(BORDER_GAP + x_offset, getHeight() - BORDER_GAP, BORDER_GAP + x_offset, BORDER_GAP);

        // create hatch marks for x axis.
        for (int i = 0; i <= X_HATCH_CNT; i++) {
            int x0 = i * (getWidth() - BORDER_GAP * 2) / X_HATCH_CNT + BORDER_GAP;
            int x1 = x0;
            int y0 = BORDER_GAP;
            int y1 = BORDER_GAP + GRAPH_POINT_WIDTH;
            g2d.drawLine(x0, y0 + y_offset, x1, y1 + y_offset);
        }

        // and for y axis
        for (int i = 0; i <= Y_HATCH_CNT; i++) {
            int x0 = BORDER_GAP;
            int x1 = BORDER_GAP - GRAPH_POINT_WIDTH;
            int y0 = getHeight() - ((i * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
            int y1 = y0;
            g2d.drawLine(x0 + x_offset, y0, x1 + x_offset, y1);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        drawAcis(g2d);
    }


    protected void paintComponentA(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (scores.size() - 1);
        double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE - 1);

        List<Point> graphPoints = new ArrayList<Point>();
        for (int i = 0; i < scores.size(); i++) {
            int x1 = (int) (i * xScale + BORDER_GAP);
            int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale + BORDER_GAP);
            graphPoints.add(new Point(x1, y1));
        }

        // create x and y axes
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

        // create hatch marks for y axis.
        for (int i = 0; i < Y_HATCH_CNT; i++) {
            int x0 = BORDER_GAP;
            int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
            int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
            int y1 = y0;
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < scores.size() - 1; i++) {
            int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
            int x1 = x0;
            int y0 = getHeight() - BORDER_GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            g2.drawLine(x0, y0, x1, y1);
        }

        Stroke oldStroke = g2.getStroke();
        g2.setColor(GRAPH_COLOR);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = (int) graphPoints.get(i).x;
            int y1 = (int) graphPoints.get(i).y;
            int x2 = (int) graphPoints.get(i + 1).x;
            int y2 = (int) graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(GRAPH_POINT_COLOR);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = (int) graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
            int y = (int) graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    public static void drawGraph(List<Integer> scores) {
        Graph mainPanel = new Graph(scores);

        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main (String[] args){
        Graph g = new Graph(-10, 10, -10, 10);

        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(g);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public class Point {
        public double x;
        public double y;

        public Point(double x, double y){
            this.x = x;
            this.y = y;
        }
    }

}