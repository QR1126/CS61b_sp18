package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    MinPQ<SearchNode> pq;
    List<WorldState> list;
    SearchNode init;
    SearchNode target;

    private class SearchNode {
        private WorldState worldState;
        private int dis;
        private SearchNode prev;

        public SearchNode(WorldState worldState, int dis, SearchNode prev) {
            this.worldState = worldState;
            this.dis = dis;
            this.prev = prev;
        }
    }

    /**Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists.
     * */
    public Solver(WorldState initial) {
        pq = new MinPQ<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                int a = o1.dis + o1.worldState.estimatedDistanceToGoal();
                int b = o2.dis + o2.worldState.estimatedDistanceToGoal();
                return a - b;
            }
        });
        list = new LinkedList<>();
        init = new SearchNode(initial, 0, null);
        pq.insert(init);
        list.add(init.worldState);

        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();
            if (node.worldState.isGoal()) {
                target = node;
                return;
            }
            for (WorldState neighbor : node.worldState.neighbors()) {
                SearchNode nNode = new SearchNode(neighbor, node.dis + 1, node);
                pq.insert(nNode);
                list.add(nNode.worldState);
            }
        }
    }

    /**Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState.
     * */
    public int moves() {
        return target.dis;
    }

    /**Returns a sequence of WorldStates from the initial WorldState
     to the solution.
     **/
    public Iterable<WorldState> solution() {
        return list;
    }
}
