import java.util.*;

public class Puzzle{
    // calculate Misplaced heuristics
    // h_misplaced(goal, hard);
    // calculate Manhattan heuristics
    // h_manhattan(dim, goal, hard);
    int dim, h_misplaced, h_manhattan;
    Node goal;
    Node start;
    TreeSet<Node> open, close; // <board, f_value>

    // constructor
    public Puzzle(int dim, List<Integer> goal_arr, List<Integer> start_arr){
        this.dim = dim;
        this.goal = new Node(goal_arr, 0, 0, 0);
        this.start = new Node(start_arr, 0);
        this.start.h_manhattan(goal_arr, start_arr);
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
        int white_tile_index = board.indexOf(0);
        System.out.println("white tile index: " + white_tile_index);
        // System.out.println("board size: " + board.size());
        for (int i = 0; i<board.size(); i++){
            // right
            if (white_tile_index % 3 != 2){
                // System.out.println("right!");
                if(i == white_tile_index){
                    right.add(board.get(i+1));
                }else if(i == white_tile_index+1){
                    right.add(0);
                    // System.out.println("right: " + i);
                }else{
                    right.add(board.get(i));
                }  
            }
            // done
            if ((white_tile_index+3) < board.size()){
  
                if(i == white_tile_index){
                    done.add(board.get(i+3));
                }else if(i == white_tile_index+3){
                    done.add(0);
                    // System.out.println("done: "+ i);
                }else{
                    done.add(board.get(i));
                }
            }
            // left
            if (white_tile_index % 3 != 0){
                // System.out.println("left!");
                if(i == (white_tile_index-1)){
                    left.add(0);
                    // System.out.println("left: "+ i);
                }else if(i == white_tile_index){
                    left.add(board.get(white_tile_index-1));
                }else{
                    left.add(board.get(i));
                }
            }
            // up
            if ((white_tile_index-3) >= 0){
                // System.out.println("up!");
                if(i == (white_tile_index-3)){
                    up.add(0);
                    // System.out.println("up: "+ i);
                }else if(i == white_tile_index){
                    up.add(board.get(i-3));
                }else{
                    up.add(board.get(i));
                }
            }
        }
        if(!right.isEmpty()) neighbors.add(right);
        if(!done.isEmpty()) neighbors.add(done);
        if(!left.isEmpty()) neighbors.add(left);
        if(!up.isEmpty()) neighbors.add(up);
        return neighbors;
    }

    public void solve_manhattan(){
        boolean end = false;
        // {board1, board2, ....} the board is represent by a list of 9 integers.
        open = new TreeSet<>();
        open.add(start);
        close = new TreeSet<>();
        int g = 0;
        while(g<15){//!end
            System.out.println("\n"+g+"------------------------");
            // first() Returns the first (lowest) element currently in this set. not remove yet.
            Node cur = open.first(); // get the best node in the open list. least f(n). 
            System.out.println("\nBoard with least f = "+cur.f+cur.toString());
            if (cur.equals(goal)){
                end = true;
                System.out.println("Found path with " + g + " Steps!");
                return; // we want to return sequence of Node.
            }else{
                g++; // take one more step to check neighbors.
                open.remove(cur);
                close.add(cur); // expand
                // build all neighbor's board after move the white tile, and check one by one.
                List<List<Integer>> neighbors = getNeighbors(cur.board);
                for (List<Integer> n : neighbors){
                    Node node = new Node(n);
                    if (!node.equals(cur)){
                        System.out.println("\nNeighbors: "+node.toString());
                        if (close.contains(node) && g<cur.g){
                            System.out.println("close contains node!");
                            close.remove(node);
                            open.add(node);
                            node.g = g;
                            node.parent = cur;
                        }else if(open.contains(node) && g<cur.g){
                            System.out.println("open contains node!");
                            node.g = g;
                            node.parent = cur;
                        }else{
                            node.g = g;
                            int temp = node.h_manhattan(goal.board, n); // calculate h and f.
                            System.out.print("h="+temp+ ", g="+node.g+", f=" +node.f +"\n");
                            open.add(node);
                        }
                    }

                }
            }
            // g++;
        }
     
    }
    public void solve_IDA_manhattan(){

    }

    public void solve_DFBB_manhattan(){

    }
}

