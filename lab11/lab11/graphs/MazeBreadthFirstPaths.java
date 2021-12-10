package lab11.graphs;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int s;
    private int t;
    private boolean targetFound = false;
    private Queue<Integer> q;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        q = new LinkedList<Integer>();
        q.offer(s);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        while (!q.isEmpty()) {
            Integer v = q.poll();
            marked[v] = true;
            announce();
            if (v == t) {
                targetFound = true;
                return;
            }
            for (int x : maze.adj(v)) {
                if (!marked[x]) {
                    q.offer(x);
                    distTo[x] = distTo[v] + 1;
                    edgeTo[x] = v;
                    announce();
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

