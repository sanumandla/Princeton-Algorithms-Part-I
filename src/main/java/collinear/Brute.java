package main.java.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Brute {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Wrong number of arguments passed");
        }

        Brute brute = new Brute();

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read file
        String filename = args[0];
        In in = brute.readFile(filename);
        Point[] points = brute.copyToArray(in);
        brute.generateLineSegments(points);

        // display to screen all at once
        StdDraw.show(0);
    }

    private In readFile(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        In in = new In(filename);
        return in;
    }

    private Point[] copyToArray(In in) {
        if (in == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        int N = in.readInt();
//    StdOut.println("Number of lines: " + N);

        Point[] points = new Point[N];

        // Copy points to array
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
//      StdOut.println("(x, y) = (" + x + ", " + y + ")");
            Point p = new Point(x, y);
            points[i] = p;
        }

        // Sort the array according to ascending order of x and y axes
        Arrays.sort(points);

        return points;
    }

    private void generateLineSegments(Point[] points) {
        int N = points.length;

        for (int i = 0; i < N; i++) {
            Point p1 = points[i];
            for (int j = i + 1; j < N; j++) {
                Point p2 = points[j];
                for (int k = j + 1; k < N; k++) {
                    Point p3 = points[k];
                    for (int l = k + 1; l < N; l++) {
                        Point p4 = points[l];

                        double slope1 = p1.slopeTo(p2);
                        double slope2 = p1.slopeTo(p3);
                        double slope3 = p1.slopeTo(p4);

                        if ( (slope1 == slope2) && (slope2 == slope3) && (slope3 == slope1) ) {
                            StdOut.println(p1 + "->" + p2 + "->" + p3 + "->" + p4);
                            p1.drawTo(p4);
                        }
                    }
                }
            }
        }
    }

}