package hw4.puzzle;


import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;
import java.util.Map;

public class Board implements WorldState{

/*  Board(tiles): Constructs a board from an N-by-N array of tiles where
    tiles[i][j] = tile at row i, column j
    tileAt(i, j): Returns value of tile at row i, column j (or 0 if blank)
    size():       Returns the board size N
    neighbors():  Returns the neighbors of the current board
    hamming():    Hamming estimate described below
    manhattan():  Manhattan estimate described below
    estimatedDistanceToGoal(): Estimated distance to goal. This method should
    simply return the results of manhattan() when submitted to Gradescope.
    equals(y):    Returns true if this board's tile values are the same
    position as y's
    toString():   Returns the string representation of the board. This
    method is provided in the skeleton*/

    private int[][] init;
    private int[][] goal;
    private Map<Integer, int[]> map;
    private int N;
    private final static int BLANK = 0;

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> q = new Queue<>();
        int n = size();
        int xBlank = -1;
        int yBlank = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tileAt(i, j) == BLANK) {
                    xBlank = i;
                    yBlank = j;
                }
            }
        }
        int[][] neighbor = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                neighbor[i][j] = tileAt(i, j);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Math.abs(xBlank - i) + Math.abs(yBlank - j) - 1 == 0) {
                    neighbor[xBlank][yBlank] = neighbor[i][j];
                    neighbor[i][j] = BLANK;
                    Board ng = new Board(neighbor);
                    q.enqueue(ng);
                    neighbor[i][j] = neighbor[xBlank][yBlank];
                    neighbor[xBlank][yBlank] = BLANK;
                }
            }
        }
        return q;
    }

    public Board(int[][] tiles) {
        N = tiles.length;
        init = new int[N][N];
        goal = new int[N][N];
        map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                init[i][j] = tiles[i][j];
            }
        }
        int cnt = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goal[i][j] = cnt;
                map.put(cnt, new int[]{i, j});
                cnt++;
            }
        }
        goal[N-1][N-1] = 0;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > N - 1 || j < 0 || j > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        return init[i][j];
    }

    public int size() {
        return N;
    }

    public int hamming() {
        int dis = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (init[i][j] == BLANK) {
                    continue;
                }
                if (init[i][j] != xyTo1D(i, j)) {
                    dis++;
                }
            }
        }
        return dis;
    }

    public int manhattan() {
        int dis = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (init[i][j] == BLANK) continue;
                if (init[i][j] != goal[i][j]) {
                    int val = init[i][j];
                    int[] pos = map.get(val);
                    dis+=Math.abs(i - pos[0]) + Math.abs(j - pos[1]);
                }
            }
        }
        return dis;
    }

//    public int manhattan() {
//        int dis = 0;
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if (init[i][j] == BLANK) continue;
//                if (init[i][j] != goal[i][j]) {
//                    dis+= getDis(init[i][j], i, j);
//                }
//            }
//        }
//        return dis;
//    }

    private int xyTo1D(int i, int j) {
        return i * N + j + 1;
    }

    private int[] intToXY(int val) {
        return new int[]{ (val - 1)/N, (val - 1) % N };
    }

    private int getDis(int val, int i, int j) {
        int res = 0;
        int[] pos = intToXY(val);
        res+=Math.abs(i - pos[0]);
        res+=Math.abs(j - pos[1]);
        return res;
    }

    @Override
    public boolean equals(Object y) {
        if (y != null) {
           if (this == y) {
               return true;
           }
           if (y instanceof Board) {
               Board yy = (Board) y;
               int n = yy.N;
               if (n != this.N) {
                   return false;
               }
               for (int i = 0; i < n; i++) {
                   for(int j = 0; j < n; j++) {
                       if (yy.init[i][j] != this.init[i][j]) {
                           return false;
                       }
                   }
               }
               return true;
           }
        }
        return false;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}


