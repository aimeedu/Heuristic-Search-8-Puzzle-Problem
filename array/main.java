import java.util.*;

public class main{
    public static void main(String args[]){
        int dim = 3; // dimension
        // use 0 to represent the white tile. use 1D array
        // List<Integer> goal = Arrays.asList(1,2,3,8,0,4,7,6,5);
        // List<Integer> easy = Arrays.asList(1,3,4,8,6,2,7,0,5);
        // List<Integer> medium = Arrays.asList(2,8,1,0,4,3,7,6,5);
        // List<Integer> hard = Arrays.asList(2,8,1,4,6,3,0,7,5);

        int[] goal = new int[]{1,2,3,8,0,4,7,6,5};
        int[] easy = new int[]{1,3,4,8,6,2,7,0,5};
        int[] medium = new int[]{2,8,1,0,4,3,7,6,5};
        int[] hard = new int[]{2,8,1,4,6,3,0,7,5};

        // init a puzzle
        // Puzzle p1 = new Puzzle(dim, goal, medium);
        Puzzle p = new Puzzle(dim, goal, medium);
        // Node t = new Node(easy);
        // t.h_manhattan(goal, easy);
        // // 1. misplaced heuristic A*
        // p.solve_misplaced();
        // // 2. Manhattan heuristic A* -> f(n) = g(n) current steps + h(n) heuristic value
        p.solve_manhattan();
        // // 3. Iterative deepening A* with Manhattan heuristic.
        // p.solve_IDA_manhattan();
        // // 4. Depth-first Branch and Bound with Manhattan heuristic. 
        // p.solve_DFBB_manhattan();
    }
}
