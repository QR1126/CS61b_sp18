package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.nio.file.OpenOption;
import java.util.Optional;


/**
 * @author QR1126
 */

public class Percolation {

    private int n;
    private int numOpenSites;
    private int topSite;
    private int bottomSite;
    private boolean[][] grid;
    private WeightedQuickUnionUF Open;
    private WeightedQuickUnionUF OpenExcludeBottom;
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, -1, 0, 1};

    public Percolation(int N) {  // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = N;
        this.numOpenSites = 0;
        this.topSite = N * N;
        this.bottomSite = N * N + 1;
        this.Open = new WeightedQuickUnionUF(N * N + 2);
        this.OpenExcludeBottom = new WeightedQuickUnionUF(N * N + 1);
        this.grid = new boolean[N][N];
    }

    public void open(int row, int col) {     // open the site (row, col) if it is not open already
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            numOpenSites++;
        }
        if (row == 0) {
            Open.union(xyTo1D(row, col), topSite);
            OpenExcludeBottom.union(xyTo1D(row, col), topSite);
        }
        if (row == n - 1) {
            Open.union(xyTo1D(row, col), bottomSite);
        }
        for (int i = 0; i < 4; i++) {
            int xx = row + dx[i], yy =col + dy[i];
            if (xx < 0 || xx >= n || yy < 0 || yy >= n) {
                continue;
            }
            int pos = xyTo1D(xx, yy);
            if (isOpen(xx, yy)) {
                Open.union(pos, xyTo1D(row, col));
                OpenExcludeBottom.union(pos, xyTo1D(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {   // is the site (row, col) open?
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {   // is the site (row, col) full?
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        return OpenExcludeBottom.connected(xyTo1D(row, col), topSite);
    }

    public int numberOfOpenSites() {      // number of open sites
        return this.numOpenSites;
    }

    private int xyTo1D(int x, int y) {
        return  x * n + y;
    }

    public boolean percolates() {    // does the system percolate?
        return Open.connected(topSite, bottomSite);
    }

    public static void main(String[] args) {     // use for unit testing (not required)

    }

}
