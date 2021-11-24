package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author QR1126
 */

public class Percolation {

    private int n;
    private int m;
    private WeightedQuickUnionUF Open;
    private WeightedQuickUnionUF Merge;
    private WeightedQuickUnionUF virtual;
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, -1, 0, 1};

    public Percolation(int N) {  // create N-by-N grid, with all sites initially blocked
        if (N < 0) {
            throw new IllegalArgumentException();
        }
        this.n = N;
        this.m = 0;
        this.Open = new WeightedQuickUnionUF(N * N + 1);
        this.Merge = new WeightedQuickUnionUF(N * N);
        this.virtual = new WeightedQuickUnionUF(N * N + 2);
        // parent[N * N] open
    }

    public void open(int row, int col) {     // open the site (row, col) if it is not open already
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        int pos = xyTo1D(row, col);
        if (Open.find(pos) != Open.find(n * n)) {
            Open.union(pos, n * n);
            this.m++;
            if (row == 0) {
                virtual.union(pos, n * n);
                if (Open.connected(xyTo1D(row + 1, col), n * n)) {
                    virtual.union(xyTo1D(row + 1, col), n * n);
                }
            }
            if (row == n - 1) {
                virtual.union(pos, n * n + 1);
                if (Open.connected(xyTo1D(row - 1, col), n * n)) {
                    virtual.union(xyTo1D(row - 1, col), n * n + 1);
                }
            }
            for (int i = 0; i < 4; i++) {
                int xx = row + dx[i], yy = col + dy[i];
                if (xx < 0 || xx >= n || yy < 0 || yy >= n || Open.find(xyTo1D(xx, yy)) != Open.find(pos)) {
                    continue;
                }
                Merge.union(xyTo1D(xx, yy), pos);
                virtual.union(xyTo1D(xx, yy), pos);
            }
        }
    }

    public boolean isOpen(int row, int col) {   // is the site (row, col) open?
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        int pos = xyTo1D(row, col);
        return Open.connected(pos, n * n);
//        return Open.find(pos) == Open.find(n * n);
    }

    public boolean isFull(int row, int col) {   // is the site (row, col) full?
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        int pos = xyTo1D(row, col);
        return virtual.connected(pos, n * n) || virtual.connected(n * n, n * n + 1);
    }

    public int numberOfOpenSites() {      // number of open sites
        return m;
    }

    private int xyTo1D(int x, int y) {
        return  x * n + y;
    }

    public boolean percolates() {    // does the system percolate?
        return virtual.connected(n * n, n * n + 1);
    }

    public static void main(String[] args) {     // use for unit testing (not required)

    }

}
