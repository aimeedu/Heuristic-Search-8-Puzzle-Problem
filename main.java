import java.util.*;

public class main{
    public static void main(String args[]){
        int d = 3; // dimension
        // use 0 to represent the empty tile. use 1D array
        List<Integer> goal = Arrays.asList(1,2,3,8,0,4,7,6,5);
        List<Integer> hard = Arrays.asList(2,8,1,4,6,3,0,7,5);
        
        // init a puzzle
        puzzle p = new puzzle(d, goal, hard);
        
        // 1. misplaced heuristic A*
        p.solve_misplaced();
        // 2. Manhattan heuristic A* -> f(n) = g(n) current steps + h(n) heuristic value
        p.solve_manhattan();
        // 3. Iterative deepening A* with Manhattan heuristic.
        p.solve_IDA_manhattan();
        // 4. Depth-first Branch and Bound with Manhattan heuristic. 
        p.solve_DFBB_manhattan();
    }
}

class puzzle{
    // calculate Misplaced heuristics
    // h_misplaced(goal, hard);
    // calculate Manhattan heuristics
    // h_manhattan(d, goal, hard);
    int d, h_misplaced, h_manhattan;
    
    List<Integer> goal;
    List<Integer> board;

    List<Integer> open = new ArrayList<>();
    List<Integer> closed = new ArrayList<>();

    // constructor
    public puzzle(int d, List<Integer> goal, List<Integer> board){
        this.d = d;
        this.goal = goal;
        this.board = board;
    }

    // in class methods
    public void solve_misplaced(){

    }

    public void solve_manhattan(){

    }

    public void solve_IDA_manhattan(){

    }

    public void solve_DFBB_manhattan(){

    }

    // calculate Misplaced heuristics
    public int h_misplaced(List<Integer> goal, List<Integer> board){
        int h = 0;
        for(int i=0; i<goal.size(); i++){
            int tile = goal.get(i);
            if(tile != 0 && tile != board.get(i)){
                h++;
            }
        }
        return h;
    } 
    // calculate Manhattan heuristics
    public int h_manhattan(int d, List<Integer> goal, List<Integer> board) {
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
