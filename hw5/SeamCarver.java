import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private boolean isVertical = true;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.height = picture.height();
        this.width = picture.width();
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width()     {
        return width;
    }

    // height of current picture
    public int height()   {
        return height;
    }

    //energy of pixel at column x and row y
    public double energy(int x, int y)  {
//        int left = x == 0 ? width - 1 : x - 1;
//        int right = x == width - 1 ? 0 : x + 1;
//        int up = y == 0 ? height - 1 : y - 1;
//        int down = y == height - 1 ? 0 : y + 1;
//        double Rx, Gx, Bx, Ry, Gy, By, deltaX, deltaY;
//        Rx = Math.abs(picture.get(left, y).getRed() - picture.get(right, y).getRed());
//        Gx = Math.abs(picture.get(left, y).getGreen() - picture.get(right, y).getGreen());
//        Bx = Math.abs(picture.get(left, y).getBlue() - picture.get(right, y).getBlue());
//        Ry = Math.abs(picture.get(x, up).getRed() - picture.get(x, down).getRed());
//        Gy = Math.abs(picture.get(x, up).getGreen() - picture.get(x, down).getGreen());
//        By = Math.abs(picture.get(x, up).getBlue() - picture.get(x, down).getBlue());
//        deltaX = Math.pow(Rx, 2) + Math.pow(Gx, 2) + Math.pow(Bx, 2);
//        deltaY = Math.pow(Ry, 2) + Math.pow(Gy, 2) + Math.pow(By, 2);
//        return deltaX + deltaY;
        Color up, down, left, right;
        if (isVertical) {
            up = y > 0 ? picture.get(x, y - 1) : picture.get(x, height - 1);
            down = y < height - 1 ? picture.get(x, y + 1) : picture.get(x, 0);
            left = x > 0 ? picture.get(x - 1, y) : picture.get(width - 1, y);
            right = x < width - 1 ? picture.get(x + 1, y) : picture.get(0, y);
        } else {
            up = x > 0 ? picture.get(x - 1, y) : picture.get(height - 1, y);
            down = x < height - 1 ? picture.get(x + 1, y) : picture.get(0, y);
            left = y > 0 ? picture.get(x, y - 1) : picture.get(x, width - 1);
            right = y < width - 1 ? picture.get(x, y + 1) : picture.get(x, 0);
        }

        int rx = left.getRed() - right.getRed();
        int gx = left.getGreen() - right.getGreen();
        int bx = left.getBlue() - right.getBlue();
        int ry = up.getRed() - down.getRed();
        int gy = up.getGreen() - down.getGreen();
        int by = up.getBlue() - down.getBlue();

        return rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by;
    }


    //sequence of indices for vertical seam
    public int[] findVerticalSeam()  {
        int[] res = new int[height];
        int[][] path = new int[width][height];
        double[][] e = new double[width][height];
        double[][] M = new double[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                e[col][row] = isVertical ? energy(col, row) : energy(row, col);
                if (row == 0) {
                    M[col][row] = e[col][row];
                }
            }
        }
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int offset = 0;
                if (j == 0) {
                    M[j][i] = Math.min(M[j][i-1], M[j+1][i-1]) + e[j][i];
                    if (M[j][i-1] < M[j+1][i-1]) {
                        offset = 0;
                    } else {
                        offset = 1;
                    }
                }
                else if (j == width - 1) {
                    M[j][i] = Math.min(M[j][i-1], M[j-1][i-1]) + e[j][i];
                    if (M[j][i-1] < M[j-1][i-1]) {
                        offset = 0;
                    } else {
                        offset = -1;
                    }
                }
                else {
                    M[j][i] = Math.min(M[j][i-1], M[j+1][i-1]);
                    M[j][i] = Math.min(M[j][i], M[j-1][i-1]) + e[j][i];
                    offset = find(M[j-1][i-1], M[j][i-1], M[j+1][i-1]);
                }
                path[j][i] = offset;
            }
        }
        double minEnergy = Double.MAX_VALUE;
        int col = 0;
        for (int i = 0; i < width; i++) {
            if (M[i][height-1] < minEnergy) col = i;
            minEnergy = Math.min(minEnergy, M[i][height-1]);
        }
        List<Integer> travel = new ArrayList<>();
        travel.add(col);
        for (int i = 0; i < height - 1; i++) {
            int curCol = travel.get(i);
            int upCol = curCol + path[curCol][height-i-1];
            travel.add(upCol);
        }
        Collections.reverse(travel);
        for (int i = 0; i < travel.size(); i++) {
            res[i] = travel.get(i);
        }
        return res;
    }

    private static int find(double a, double b, double c) {
        List<Double> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        Collections.sort(list);
        double res = list.get(0);
        if (res == a) return -1;
        else if (res == b) return 0;
        else return 1;
    }


//    public int[] findVerticalSeam() {
//        int[][] path = new int[width][height];
//        double[][] cost = new double[width][height];
//        for (int i = 0; i < width; i++) {
//            double e = isVertical ? energy(i, 0) : energy(0, i);
//            cost[i][0] = e;
//            path[i][height - 1] = i;
//        }
//
//        for (int j = 1; j < height; j++) {
//            for (int i = 0; i < width; i++) {
//                double e = isVertical ? energy(i, j) : energy(j, i);
//                cost[i][j] = e + getMinCost(i, j, path, cost);
//            }
//        }
//
//        int[] res = new int[height];
//        double min = Double.MAX_VALUE;
//        int minPos = 0;
//        for (int i = 0; i < width; i++) {
//            if (cost[i][height - 1] < min) {
//                min = cost[i][height - 1];
//                minPos = i;
//            }
//        }
//
//        for (int j = height - 1; j >= 0; j--) {
//            res[j] = path[minPos][j];
//            minPos = res[j];
//        }
//        return res;
//    }
//
//    private double getMinCost(int i, int j, int[][] path, double[][] cost) {
//        double[] v = new double[3];
//        v[1] = cost[i][j - 1];
//        if (i > 0) {
//            v[0] = cost[i - 1][j - 1];
//        } else {
//            v[0] = Double.MAX_VALUE;
//        }
//        if (i < width - 1) {
//            v[2] = cost[i + 1][j - 1];
//        } else {
//            v[2] = Double.MAX_VALUE;
//        }
//        double res = Double.MAX_VALUE;
//        int pos = 0;
//        for (int m = 0; m < 3; m++) {
//            if (v[m] < res) {
//                res = v[m];
//                pos = m;
//            }
//        }
//        path[i][j - 1] = pos + i - 1;
//        return res;
//    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        isVertical = false;
        swap();
        int[] res = findVerticalSeam();
        swap();
        isVertical = true;
        return res;
    }

    private void swap() {
        int temp = width;
        width = height;
        height = temp;
    }



    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width || !isValidSeam(seam)) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height || !isValidSeam(seam)) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeVerticalSeam(picture, seam);
    }

    private boolean isValidSeam(int[] seam) {
        for (int i = 0, j = 1; j < seam.length; i++, j++) {
            if (Math.abs(seam[i] - seam[j]) > 1) {
                return false;
            }
        }
        return true;
    }
}
