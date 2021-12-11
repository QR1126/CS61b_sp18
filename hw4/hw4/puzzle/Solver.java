package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    MinPQ<SearchNode> pq;
    //we can use the prev to travel the solution
    List<WorldState> res;
    /**Note that the parent SearchNode should not add to the pq again
     * But The key issue is that you shouldn’t consider a state to be “used” until it is dequeued.
     * In other words, if you DO attempt to do this,
     * you should only “mark” a WorldState when it is dequeued from the PQ,
     * not when it is enqueued!
     * */
//    Set<WorldState> vis;
    SearchNode init;
    SearchNode target;
    Map<WorldState, Integer> memory;

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
        init = new SearchNode(initial, 0, null);
        pq.insert(init);

        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();
            SearchNode prevNode = node.prev;
            if (node.worldState.isGoal()) {
                target = node;
                return;
            }
            for (WorldState neighbor : node.worldState.neighbors()) {
                if (prevNode != null && neighbor.equals(prevNode.worldState)) continue;
                SearchNode nNode = new SearchNode(neighbor, node.dis + 1, node);
                pq.insert(nNode);
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
        res = new ArrayList<>();
        while (target != null) {
            res.add(target.worldState);
            target = target.prev;
        }
        Collections.reverse(res);
        return res;
    }
}
