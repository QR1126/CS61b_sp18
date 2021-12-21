import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.*;
import java.util.List;

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
        int left = x == 0 ? width - 1 : x - 1;
        int right = x == width - 1 ? 0 : x + 1;
        int up = y == 0 ? height - 1 : y - 1;
        int down = y == height - 1 ? 0 : y + 1;
        double Rx, Gx, Bx, Ry, Gy, By, deltaX, deltaY;
        Rx = Math.abs(picture.get(left, y).getRed() - picture.get(right, y).getRed());
        Gx = Math.abs(picture.get(left, y).getGreen() - picture.get(right, y).getGreen());
        Bx = Math.abs(picture.get(left, y).getBlue() - picture.get(right, y).getBlue());
        Ry = Math.abs(picture.get(x, up).getRed() - picture.get(x, down).getRed());
        Gy = Math.abs(picture.get(x, up).getGreen() - picture.get(x, down).getGreen());
        By = Math.abs(picture.get(x, up).getBlue() - picture.get(x, down).getBlue());
        deltaX = Math.pow(Rx, 2) + Math.pow(Gx, 2) + Math.pow(Bx, 2);
        deltaY = Math.pow(Ry, 2) + Math.pow(Gy, 2) + Math.pow(By, 2);
        return deltaX + deltaY;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam()  {
        int[] res = new int[height];
        int[][] path = new int[width][height];
        double[][] e = new double[width][height];
        double[][] M = new double[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                e[col][row] = energy(col, row);
                if (row == 0) {
                    M[col][row] = e[col][row];
                }
            }
        }
        for (int r = 1; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (c == 0) {
                    M[c][r] = Math.min(M[c][r-1], M[c+1][r-1]) + e[c][r];
                    if (M[c][r-1] < M[c+1][r-1]) {
                        path[c][r] = 0;
                    } else {
                        path[c][r] = 1;
                    }
                }
                else if (c == width - 1) {
                    M[c][r] = Math.min(M[c][r-1], M[c-1][r-1]) + e[c][r];
                    if (M[c][r-1] < M[c-1][r-1]) {
                        path[c][r] = 0;
                    } else {
                        path[c][r] = -1;
                    }
                }
                else {
                    M[c][r] = Math.min(M[c-1][r-1], M[c][r-1]);
                    if (M[c-1][r] < M[c][r-1]) {
                        path[c][r] = -1;
                    } else {
                        path[c][r] = 0;
                    }
                    if (M[c+1][r-1] < M[c][r]) {
                        path[c][r] = 1;
                    }
                    M[c][r] = Math.min(M[c][r], M[c+1][r-1]) + e[c][r];
                }
            }
        }

        double ans = Double.MAX_VALUE;
        int pos = 0;
        for (int c = 0; c < width; c++) {
            if (M[c][height-1] < ans) {
                ans = M[c][height-1];
                pos = c;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int r = height - 1; r >= 0; r--) {
            list.add(pos);
            pos+= path[pos][r];
        }
        Collections.reverse(list);
        for (int i = 0; i < height; i++) {
            res[i] = list.get(i);
        }
        return res;
    }



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
