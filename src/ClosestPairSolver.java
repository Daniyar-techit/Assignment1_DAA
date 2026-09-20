import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class ClosestPairSolver {
    private int maxRecursionDepth = 0;
    private long comparisonCount = 0;

    public static class Pair {
        public final Point p1;
        public final Point p2;
        public final double distance;

        public Pair(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = p1.distanceTo(p2);
        }
    }

    public Pair findClosestPair(List<Point> points) {
        maxRecursionDepth = 0;
        comparisonCount = 0;

        Point[] ptsX = points.toArray(new Point[0]);
        Point[] ptsY = points.toArray(new Point[0]);

        Arrays.sort(ptsX, (a, b) -> Double.compare(a.getX(), b.getX()));
        Arrays.sort(ptsY, (a, b) -> Double.compare(a.getY(), b.getY()));

        return closestPairRec(ptsX, ptsY, 0, ptsX.length - 1, 0);
    }

    private Pair closestPairRec(Point[] ptsX, Point[] ptsY, int low, int high, int depth) {
        if (depth > maxRecursionDepth) maxRecursionDepth = depth;

        if (high - low <= 3) {
            return bruteForce(ptsX, low, high);
        }

        int mid = low + (high - low) / 2;
        Point midPoint = ptsX[mid];

        List<Point> leftYList = new ArrayList<>();
        List<Point> rightYList = new ArrayList<>();
        for (Point p : ptsY) {
            comparisonCount++;
            if (p.getX() <= midPoint.getX() && leftYList.size() < (mid - low + 1)) {
                leftYList.add(p);
            } else {
                rightYList.add(p);
            }
        }

        Point[] leftY = leftYList.toArray(new Point[0]);
        Point[] rightY = rightYList.toArray(new Point[0]);

        Pair leftPair = closestPairRec(ptsX, leftY, low, mid, depth + 1);
        Pair rightPair = closestPairRec(ptsX, rightY, mid + 1, high, depth + 1);

        Pair minPair = leftPair.distance < rightPair.distance ? leftPair : rightPair;
        double d = minPair.distance;

        List<Point> stripList = new ArrayList<>();
        for (Point p : ptsY) {
            comparisonCount++;
            if (Math.abs(p.getX() - midPoint.getX()) < d) {
                stripList.add(p);
            }
        }

        for (int i = 0; i < stripList.size(); i++) {
            for (int j = i + 1; j < stripList.size() && (stripList.get(j).getY() - stripList.get(i).getY()) < d; j++) {
                comparisonCount++;
                Point p1 = stripList.get(i);
                Point p2 = stripList.get(j);
                double dist = p1.distanceTo(p2);
                if (dist < d) {
                    d = dist;
                    minPair = new Pair(p1, p2);
                }
            }
        }

        return minPair;
    }

    private Pair bruteForce(Point[] pts, int low, int high) {
        Pair minPair = null;
        double minDistance = Double.MAX_VALUE;
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                comparisonCount++;
                double dist = pts[i].distanceTo(pts[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                    minPair = new Pair(pts[i], pts[j]);
                }
            }
        }
        return minPair;
    }

    public int getMaxRecursionDepth() { return maxRecursionDepth; }
    public long getComparisonCount() { return comparisonCount; }
}