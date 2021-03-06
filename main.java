import java.util.*;

public class main{
    public static void main(String args[]){
        // use 0 to represent the empty tile.
        // 1D
        int d = 3; // dimension
        List<Integer> goal = Arrays.asList(1,2,3,8,0,4,7,6,5);
        List<Integer> hard = Arrays.asList(2,8,1,4,6,3,0,7,5);

        // calculate Misplaced heuristics
        h_misplaced(goal, hard);
        // calculate Manhattan heuristics
        h_manhattan(d, goal, hard);

        // 1. misplaced heuristic A*
        // 2. Manhattan heuristic A* -> f(n) = g(n) current steps + h(n) heuristic value
        // 3. Iterative deepening A* with Manhattan heuristic.
        // 4. Depth-first Branch and Bound with Manhattan heuristic.
        List<Integer> open = new ArrayList<>();
        List<Integer> closed = new ArrayList<>();

    }

    // build misplaced heuristics
    public static int h_misplaced(List<Integer> goal, List<Integer> board){
        int h = 0;
        for(int i=0; i<goal.size(); i++){
            int tile = goal.get(i);
            if(tile != 0 && tile != board.get(i)){
                h++;
            }
        }
        return h;
    } 
    // build Manhattan heuristics
    public static int h_manhattan(int d, List<Integer> goal, List<Integer> board) {
        int h = 0;
        for(int i = 0; i<goal.size(); i++){
            int tile = goal.get(i);
            if(tile != 0){
                int board_index = board.indexOf(tile);
                int step = Math.abs(i%d - board_index%d) +  Math.abs(i/d - board_index/d);
                h += (step); 
            }
        }
        return h;
    }

}
