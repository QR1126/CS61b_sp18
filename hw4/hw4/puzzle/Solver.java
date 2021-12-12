package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    private MinPQ<SearchNode> pq;
    //we can use the prev to travel the solution
    private List<WorldState> solution;
    /**Note that the parent SearchNode should not add to the pq again
     * But The key issue is that you shouldn’t consider a state to be “used” until it is dequeued.
     * In other words, if you DO attempt to do this,
     * you should only “mark” a WorldState when it is dequeued from the PQ,
     * not when it is enqueued!
     * */
//    Set<WorldState> vis;
    private SearchNode init;
    private SearchNode target;
    private Map<WorldState, Integer> memory;

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
        memory = new HashMap<>();
        pq = new MinPQ<>(new Comparator<SearchNode>() {
            private int getDis(SearchNode node) {
                if (!memory.containsKey(node)) {
                    memory.put(node.worldState, node.worldState.estimatedDistanceToGoal());
                }
                return memory.get(node.worldState);
            }

            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                int a = o1.dis + getDis(o1);
                int b = o2.dis + getDis(o2);
                return a - b;
            }
        });
        init = new SearchNode(initial, 0, null);
        pq.insert(init);

        while (!pq.isEmpty()) {
            SearchNode nowNode = pq.delMin();
            if (nowNode.worldState.isGoal()) {
                target = new SearchNode(nowNode.worldState, nowNode.dis, nowNode.prev);
                break;
            }
            for (WorldState neighbor : nowNode.worldState.neighbors()) {
                if (nowNode.prev != null && neighbor.equals(nowNode.prev.worldState)) continue;
                SearchNode nNode = new SearchNode(neighbor, nowNode.dis + 1, nowNode);
                pq.insert(nNode);
            }
        }

        solution = new ArrayList<>();
        Stack<WorldState> stk = new Stack<>();
        while (target != null) {
            stk.push(target.worldState);
            target = target.prev;
        }
        while (!stk.isEmpty()) {
            solution.add(stk.pop());
        }
    }

    /**Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState.
     * */
    public int moves() {
        return solution.size() - 1;
    }

    /**Returns a sequence of WorldStates from the initial WorldState
     to the solution.
     **/
    public Iterable<WorldState> solution() {
        return solution;
    }
}

