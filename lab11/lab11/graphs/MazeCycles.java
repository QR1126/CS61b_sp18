package lab11.graphs;

import java.util.HashMap;
import java.util.Map;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze m;
    private boolean isFound = false;
    private int[] nxtTo;

    public MazeCycles(Maze m) {
        super(m);
        this.m = m;
        nxtTo = new int[this.m.N() * this.m.N()];
    }

    @Override
    public void solve() {
        dfs(0);
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (marked[w] && nxtTo[v] != w) {
                announce();
                isFound = true;
            }
        }
        if (isFound) {
            return;
        }
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                nxtTo[w] = v;
                dfs(w);
            }
        }
    }

}

