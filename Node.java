import java.util.*;

public class Node implements Comparable<Node>{
    int g, h;
    int f;
    List<Integer> goal;
    List<Integer> board;
    Node parent;

    // constructors
    public Node(List<Integer> board,int g, int h, int f){ // for goal board.
        this.board = board; 
        this.g = g;
        this.h = h;
        this.f = f;
    }

    public Node(List<Integer> board, int g){ // start board
        this.g = g;
        this.board = board; 
        this.parent = null;
    }

    public Node(List<Integer> board){
        this.board = board; 
    }

    // methods

    // calculate Misplaced heuristics
    public int h_misplaced(List<Integer> goal, List<Integer> board, int g){
        int h = 0;
        for(int i=0; i<board.size(); i++){
            int tile = board.get(i);
            if(tile != 0 && tile != goal.get(i)){
                h++;
            }
        }
        this.h = h;
        this.f = h + g;
        System.out.println(h);
        return h;
    } 
    
    // calculate Manhattan heuristics
    public int h_manhattan(List<Integer> goal, List<Integer> board) {
        int dim =3;
        int h = 0;
        for(int i = 0; i<board.size(); i++){
            int tile = board.get(i);
            if(tile != 0){    
                int goal_index = goal.indexOf(tile);
                int step = Math.abs(i%dim - goal_index%dim) +  Math.abs(i/dim - goal_index/dim);
                // System.out.println("tile is: " + tile + ", Step is: " + step);
                h += (step); 
            }
        }
        this.h = h;
        this.f = h + g;
        System.out.println("h = " + this.h +", g="+this.g+ ", f = " + this.f);
        return this.h;
    }

    @Override 
    public String toString(){
        String temp = "\n";
        for (int i=0; i<this.board.size(); i++){
            temp += this.board.get(i);
            if(i%3==2){
                temp += "\n";
            }else{
                temp += " ";
            }
        }
        return temp;
    }

    public boolean equals(Object obj) {
        Node n = (Node) obj;
        for(int i=0; i<this.board.size(); i++){
            // System.out.println(this.board.get(i) + " " + n.board.get(i));
            if (this.board.get(i) != n.board.get(i)) return false;
        }
        return true;
    }

    @Override
    public int compareTo(Node n) {
        if (this.equals(n)) {
            return 0;
        }
        else{
            if (this.f != n.f) {
                // System.out.println(this.f + this.board.toString()+" <- " +n.f + n.board.toString());
                return this.f-n.f;
            }
            else return 1;
        }
    }
}
