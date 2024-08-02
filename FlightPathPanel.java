import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class FlightPathPanel extends JPanel {
    private final List<List<Point>> flightPaths;
    private final List<Color> colors;
    private final int gridSize = 50;

    public FlightPathPanel(List<List<Point>> flightPaths, List<Color> colors) {
        this.flightPaths = flightPaths;
        this.colors = colors;
        resolveIntersections();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int plotWidth = getMaxCoordinateX() * gridSize;
        int plotHeight = getMaxCoordinateY() * gridSize;
        int offsetX = (width - plotWidth) / 2;
        int offsetY = (height - plotHeight) / 2;

        g2d.setStroke(new BasicStroke(2));

        for (int i = 0; i < flightPaths.size(); i++) {
            List<Point> path = flightPaths.get(i);
            g2d.setColor(colors.get(i));
            for (int j = 0; j < path.size() - 1; j++) {
                Point start = path.get(j);
                Point end = path.get(j + 1);
                g2d.drawLine(
                    start.x * gridSize + offsetX,
                    -start.y * gridSize + height - offsetY,
                    end.x * gridSize + offsetX,
                    -end.y * gridSize + height - offsetY
                );
            }
        }
    }

    private void resolveIntersections() {
        List<List<Point>> adjustedPaths = new ArrayList<>();
        for (List<Point> path : flightPaths) {
            List<Point> adjustedPath = new ArrayList<>(path);
            for (Point point : adjustedPath) {
                point.translate(2, 2);
            }
            adjustedPaths.add(adjustedPath);
        }
        flightPaths.clear();
        flightPaths.addAll(adjustedPaths);
    }

    private boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        return (orientation(p1, q1, p2) != orientation(p1, q1, q2)) &&
               (orientation(p2, q2, p1) != orientation(p2, q2, q1));
    }

    private int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        return (val == 0) ? 0 : (val > 0) ? 1 : 2;
    }

    private int getMaxCoordinateX() {
        return flightPaths.stream()
            .flatMap(List::stream)
            .mapToInt(p -> p.x)
            .max()
            .orElse(0);
    }

    private int getMaxCoordinateY() {
        return flightPaths.stream()
            .flatMap(List::stream)
            .mapToInt(p -> p.y)
            .max()
            .orElse(0);
    }

    public static void main(String[] args) {
        List<List<Point>> flightPaths = new ArrayList<>();
        flightPaths.add(List.of(new Point(1, 1), new Point(2, 2), new Point(3, 3)));
        flightPaths.add(List.of(new Point(1, 1), new Point(2, 4), new Point(3, 2)));
        flightPaths.add(List.of(new Point(1, 1), new Point(4, 2), new Point(3, 4)));

        List<Color> colors = List.of(Color.RED, Color.GREEN, Color.BLUE);

        JFrame frame = new JFrame("Flight Paths");
        FlightPathPanel panel = new FlightPathPanel(flightPaths, colors);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
