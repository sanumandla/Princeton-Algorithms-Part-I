package main.java.collinear;

/*************************************************************************
 * Name: Sridhar
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point p1, Point p2) {
            double p1Slope = Point.this.slopeTo(p1);
            double p2Slope = Point.this.slopeTo(p2);

            if ( (p1Slope < p2Slope)) {
                return -1;
            } else if (p1Slope > p2Slope) {
                return 1;
            } else {
                return p1.compareTo(p2);
            }
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
//      validate(that);

        if (this.x == that.x && this.y == that.y) {
            // It's important to ignore NEGATIVE_INFINITY because Arrays.sort() strictly considers it as less than POSITIVE_INFINITY
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            return 0.0;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            return (double) (that.y - this.y) / (that.x - this.x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
//      validate(that);

        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return 1;
        } else {
            if (this.x < that.x) {
                return -1;
            } else if (this.x > that.x) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private void validate(Point that) {
        if (that == null) {
            throw new IllegalArgumentException();
        }
    }

    // unit test
    public static void main(String[] args) {
        Point point1 = new Point(3, 4);

        // Test compareTo
        StdOut.println(point1.compareTo(new Point(5, 5)));
        StdOut.println(point1.compareTo(new Point(5, 4)));
        StdOut.println(point1.compareTo(new Point(3, 4)));
        StdOut.println(point1.compareTo(new Point(3, 2)));
        StdOut.println(point1.compareTo(new Point(2, 2)));

        // Test slopeTo
        StdOut.println(point1.slopeTo(new Point(3, 4)));
        StdOut.println(point1.slopeTo(new Point(5, 4)));
        StdOut.println(point1.slopeTo(new Point(2, 4)));
        StdOut.println(point1.slopeTo(new Point(3, 2)));
        StdOut.println(point1.slopeTo(new Point(3, 5)));
        StdOut.println(point1.slopeTo(new Point(5, 5)));
        StdOut.println(point1.slopeTo(new Point(7, 9)));
        StdOut.println(point1.slopeTo(new Point(5, 3)));

        // Test SLOPE_ORDER comparator
        Point[] points = new Point[5];
        Point point1a = new Point(1,1);
        Point point2a = new Point(1,2);
        Point point3a = new Point(2,3);
        Point point4a = new Point(4,5);
        Point point5a = new Point(5,5);
        points[0] = point1a;
        points[1] = point2a;
        points[2] = point3a;
        points[3] = point4a;
        points[4] = point5a;

        for (int i = 0; i < points.length; i++) {
            StdOut.print("(" + points[i].x + "," + points[i].y + ")" + " ");
        }
        StdOut.println();

        Arrays.sort(points, point1.SLOPE_ORDER);

        for (int i = 0; i < points.length; i++) {
            StdOut.print("(" + points[i].x + "," + points[i].y + ")" + " ");
        }
        StdOut.println();
    }
}