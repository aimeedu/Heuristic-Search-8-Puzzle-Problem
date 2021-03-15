import java.util.*;

public class Node implements Comparable<Node>{
    int id;
    int g, h;
    int f;
    // List<Integer> goal;
    // List<Integer> board;
    int[] goal;
    int[] board;
    Node parent;

    // constructors
    public Node(int[] board,int g, int h, int f){ // for goal board.
        this.board = board; 
        this.g = g;
        this.h = h;
        this.f = f;
    }

    public Node(int[] board, int g){ // start board
        // this.id = id;
        this.g = g;
        this.board = board; 
        this.parent = null;
    }

    public Node(int[] board){
        this.board = board; 
    }

    // methods

    // calculate Misplaced heuristics
    public int h_misplaced(int[] goal, int[] board, int g){
        int h = 0;
        for(int i=0; i<board.length; i++){
            int tile = board[i];
            if(tile != 0 && tile != goal[i]){
                h++;
            }
        }
        this.h = h;
        this.f = h + g;
        System.out.println(h);
        return h;
    } 
    
    public int findIndex(int[] arr, int tile){
        int pos = 0;
        for (int i = 0; i<arr.length; i++){
            if (arr[i] == tile) pos = i;
        }
        return pos;
    }

    // calculate Manhattan heuristics
    public int h_manhattan(int[] goal, int[] board) {
        int dim =3;
        int h = 0;
        for(int i = 0; i<goal.length; i++){
            int tile = board[i];
            if(tile != 0){    
                int goal_index = findIndex(goal, tile);
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
        for (int i=0; i<this.board.length; i++){
            temp += this.board[i];
            if(i%3==2){
                temp += "\n";
            }else{
                temp += " ";
            }
        }
        return temp;
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Node n = (Node) obj;

        for(int i=0; i<this.board.length; i++){
            // System.out.println(this.board.get(i) + " " + n.board.get(i));
            if (this.board[i] != n.board[i]) return false;
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
