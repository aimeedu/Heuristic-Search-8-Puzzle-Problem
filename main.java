import java.util.*;

public class main{
    public static void main(String args[]){
        int dim = 3; // dimension
        // use 0 to represent the empty tile. use 1D array
        List<Integer> goal = Arrays.asList(1,2,3,8,0,4,7,6,5);
        List<Integer> hard = Arrays.asList(2,8,1,4,6,3,0,7,5);
        
        // init a puzzle
        Puzzle p = new Puzzle(dim, goal, hard);
        
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

class Puzzle{
    // calculate Misplaced heuristics
    // h_misplaced(goal, hard);
    // calculate Manhattan heuristics
    // h_manhattan(dim, goal, hard);
    int dim, h_misplaced, h_manhattan;
    Node goal;
    Node start;
    PriorityQueue<Node> open, close;

    // constructor
    public Puzzle(int dim, List<Integer> goal_arr, List<Integer> start_arr){
        this.dim = dim;
        this.goal = new Node(this.dim, goal_arr, 0, 0, 0);
        this.start = new Node(this.dim, 0, start_arr);
    }

    // in class methods
    public void solve_misplaced(){

    }
    
    public List<List<Integer>> getNeighbors(List<Integer> board){
        List<List<Integer>> neighbors = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> done = new ArrayList<>();
        List<Integer> left = new ArrayList<>();
        List<Integer> up = new ArrayList<>();
        int empty = board.indexOf(0);
        for (int i = 0; i<board.size(); i++){
            // right
            if (empty % 3 != 2){
                if(i == empty){
                    right.add(board.get(i+1));
                }else if(i == empty+1){
                    right.add(0);
                }else{
                    right.add(board.get(i));
                }  
            }
            // done
            if (empty+3 < board.size()){
                if(i == empty){
                    done.add(board.get(i+3));
                }else if(i == empty+3){
                    done.add(0);
                }else{
                    done.add(board.get(i));
                }
            }
            // left
            if (empty % 3 != 0){
                if(i == (empty-1)){
                    left.add(0);
                }else if(i == empty){
                    left.add(board.get(empty-1));
                }else{
                    left.add(board.get(i));
                }
            }
            // up
            if (empty-3 >= 0){
                if(i == (empty-3)){
                    up.add(0);
                }else if(i == empty){
                    done.add(board.get(i-3));
                }else{
                    done.add(board.get(i));
                }
            }
        }
        neighbors.add(right);
        neighbors.add(done);
        neighbors.add(left);
        neighbors.add(up);
        return neighbors;
    }

    public void solve_manhattan(){
        boolean end = false;
        // {board1, board2, ....} the board is represent by a list of 9 integers.
        open = new PriorityQueue<>((a,b) -> a.f - b.f);
        open.add(start);
        close = new PriorityQueue<>();
        int g = 0;
        while(!end){
            Node cur = open.poll(); // get the best node in the open list. least f(n).
            if (cur.board == goal.board){
                end = true;
                return;
            }else{
                close.add(cur); // expand
                // build all neighbor's board after move the white tile, and check one by one.
                List<List<Integer>> neighbors = getNeighbors(cur.board);
                for (List<Integer> n : neighbors){
                    if (close.contains(n) && g<close.get(n).g){

                    }else if(open.contains(n) && g<open.get(n).g){

                    }else{
                        Node next = new Node(dim,g,n); // set g.
                        next.h_manhattan(dim, goal.board, n); // calculate h and f.
                        open.add(next);
                    }
                }
            }
            g++;
        }
     
    }
    public void solve_IDA_manhattan(){

    }

    public void solve_DFBB_manhattan(){

    }
}

class Node{
    int dim;
    int g, h, f;
    List<Integer> goal;
    List<Integer> board;
    Node parent;

    // constructors
    public Node(int dim, List<Integer> board,int g, int h, int f){ // for goal board.
        this.dim = dim;
        this.board = board; 
        this.g = g;
        this.h = h;
        this.f = f;
    }

    public Node(int dim, int g, List<Integer> board){
        this.dim = dim;
        this.g = g;
        this.board = board; 
        // this.h = this.h_manhattan(goal, board, g);
        // this.f = h+g;
    }

    // methods

    // calculate Misplaced heuristics
    public int h_misplaced(List<Integer> goal, List<Integer> board, int g){
        int h = 0;
        for(int i=0; i<goal.size(); i++){
            int tile = goal.get(i);
            if(tile != 0 && tile != board.get(i)){
                h++;
            }
        }
        this.h = h;
        this.f = h + g;
        return h;
    } 
    
    // calculate Manhattan heuristics
    public int h_manhattan(int dim, List<Integer> goal, List<Integer> board) {
        int h = 0;
        for(int i = 0; i<goal.size(); i++){
            int tile = goal.get(i);
            if(tile != 0){
                int board_index = board.indexOf(tile);
                int step = Math.abs(i%dim - board_index%dim) +  Math.abs(i/dim - board_index/dim);
                h += (step); 
            }
        }
        this.h = h;
        this.f = h + g;
        return h;
    }
}