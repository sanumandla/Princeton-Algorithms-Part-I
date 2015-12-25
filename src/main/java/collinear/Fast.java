package main.java.collinear;

/**
 * Created by sridhar.anumandla on 8/13/15.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Fast {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Wrong number of arguments passed");
        }

        Fast fast = new Fast();

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read file
        String filename = args[0];
        In in = fast.readFile(filename);
        Point[] points = fast.copyToArray(in);
        fast.generateLineSegments(points);

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
        Point[] points = new Point[N];

        // Copy points to array
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
        }

        return points;
    }

    private Point[] copyArray(Point[] points) {
        Point[] newArray = new Point[points.length];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = points[i];
        }

        return newArray;
    }

    private void generateLineSegments(Point[] points) {
        int N = points.length;

        Arrays.sort(points);
        Point[] pointsBySlope = copyArray(points);

        for (int i = 0; i < N; i++) {
            Point origin = points[i];
            Arrays.sort(pointsBySlope, origin.SLOPE_ORDER);

            int count = 1;
            boolean first = true;
            double globalSlope = 0.0;

            for (int k = 0; k < N - 1; k++) {
                // Check if adjacent points have equal slopes
                if (origin.slopeTo(pointsBySlope[k]) == origin.slopeTo(pointsBySlope[k + 1])) {
                    // Check if origin is greater than the rest of the points
                    if(origin.compareTo(pointsBySlope[k]) == 1 && origin.compareTo(pointsBySlope[k + 1]) == 1) {
                        if (first) {
                            first = false;
                            globalSlope = origin.slopeTo(pointsBySlope[k]);
                        }

                        // If the point is last or if equal slopes don't exist anymore
                        if (k + 1 == N - 1 || (origin.slopeTo(pointsBySlope[k + 1]) != origin.slopeTo(pointsBySlope[k + 2]))) {
                            count++;
                        }

                        count++;
                    }
                    // Check if the last point and origin are equal
                    else if (origin.compareTo(pointsBySlope[k]) == 1 && origin.compareTo(pointsBySlope[k + 1]) == 0) {
                        count++;
                    }
                    // Check if origin is less than the points
                    else {
                        count = 1;
                    }

                }

                // If count is >= 4 then print the line segment
                if (k + 1 == N - 1 || origin.slopeTo(pointsBySlope[k]) != origin.slopeTo(pointsBySlope[k + 1])) {
                    if (count >= 4) {
                        Point end = origin;

                        first = true;
                        Point start = null;
                        for (int k1 = 0; k1 < N; k1++) {
                            if (origin.slopeTo(pointsBySlope[k1]) == globalSlope) {
                                if (first) {
                                    start = pointsBySlope[k1];
                                    first = false;
                                }

                                if (origin.compareTo(pointsBySlope[k1]) != 0) {
                                    StdOut.print(pointsBySlope[k1] + " -> ");
                                }
                            }
                        }
                        StdOut.print(end);
                        StdOut.println();

                        // Draw the line segment
                        if (start != null) {
                            start.drawTo(end);
                        }
                    }

                    // reset variables
                    count = 1;
                    first = true;
                }
            }
        }
    }
}