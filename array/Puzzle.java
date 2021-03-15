import java.util.*;

public class Puzzle{
    // calculate Misplaced heuristics
    // h_misplaced(goal, hard);
    // calculate Manhattan heuristics
    // h_manhattan(dim, goal, hard);
    int dim, h_misplaced, h_manhattan;
    int[] goal;
    int[] start;
    Node goal_node;
    Node start_node;
    // Map<List<Integer>, Node> open_map, close_map; // <board, f_value>
    // PriorityQueue<Node> open, close;
    TreeSet<Node> open, close;

    // constructor
    public Puzzle(int dim, int[] goal_arr, int[] start_arr){
        this.dim = dim;
        this.goal = goal_arr;
        this.start = start_arr;
        this.goal_node = new Node(goal_arr, 0, 0, 0);
        this.start_node = new Node(start_arr, 0);
        this.start_node.h_manhattan(goal_arr, start_arr);
    }

    // in class methods
    public void solve_misplaced(){

    }
    
    public List<int[]> getNeighbors(int[] board){

        int white_tile_index = 0;
        for (int j = 0; j<board.length; j++){
            if (board[j] == 0) white_tile_index = j;
        }

        List<int[]> neighbors = new ArrayList<>();

        int[] right = new int[9];
        int[] done = new int[9];
        int[] left = new int[9];
        int[] up = new int[9];
        
        System.out.println("white tile index: " + white_tile_index);
        // System.out.println("board size: " + board.size());
        for (int i = 0; i<board.length; i++){
            // right
            if (white_tile_index % 3 != 2){
                if(i == white_tile_index){
                    right[i] = board[i+1];
                }else if(i == white_tile_index+1){
                    right[i] = 0;
                    // System.out.println("right: " + i);
                }else{
                    right[i] = board[i];
                }  
            }
            // done
            if ((white_tile_index+3) < board.length){
                if(i == white_tile_index){
                    done[i] = board[i+3];
                }else if(i == white_tile_index+3){
                    done[i] = 0;
                    // System.out.println("done: "+ i);
                }else{
                    done[i] = board[i];
                }
            }
            // left
            if (white_tile_index % 3 != 0){
                
                if(i == (white_tile_index-1)){
                    left[i] = 0;
                    // System.out.println("left: "+ i);
                }else if(i == white_tile_index){
                    left[i] = board[white_tile_index-1];
                }else{
                    left[i] = board[i];
                }
            }
            // up
            if ((white_tile_index-3) >= 0){
              
                if(i == (white_tile_index-3)){
                    up[i] = 0;
                    // System.out.println("up: "+ i);
                }else if(i == white_tile_index){
                    up[i] = board[i-3];
                }else{
                    up[i] = board[i];
                }
            }
        }
        if(!allZero(right)) neighbors.add(right);
        if(!allZero(done)) neighbors.add(done);
        if(!allZero(left)) neighbors.add(left);
        if(!allZero(up)) neighbors.add(up);
        return neighbors;
    }

    public boolean allZero(int[] a){
        int count = 0;
        for (int i = 0; i<2; i++){
            if (a[i] == 0) count ++;
        }
        return count==2;
    }

    public void solve_manhattan(){
        boolean end = false;
        // {board1, board2, ....} the board is represent by a list of 9 integers.
        open = new TreeSet<>();
        open.add(start_node);
        close = new TreeSet<>();

        int g = 0;
        while(g<8){//!end
            System.out.println("\n"+g+"------------------------");
            // first() Returns the first (lowest) element currently in this set. not remove yet.
            Node cur = open.first(); // get the best node in the open list. least f(n). 
            System.out.println("\nBoard with least f = "+cur.f+cur.toString());
            System.out.println("Remove: ");
            open.remove(cur);
            System.out.println("Add: ");
            close.add(cur); // expand

            System.out.println("open list: "+ open);
            System.out.println("close list: "+ close);

            if (cur.equals(goal_node)){
                end = true;
                System.out.println("Found path with " + g + " Steps!");
                return; // we want to return sequence of Node.
            }else{
                g++; // take one more step to check neighbors.
                
                // build all neighbor's board after move the white tile, and check one by one.
                List<int[]> neighbors = getNeighbors(cur.board);
                for (int[] n : neighbors){
                    Node node = new Node(n);
                    if (cur.parent==null || !node.equals(cur.parent) ){

                        System.out.println("\nNeighbors: "+node.toString());
                        if (close.contains(node)){
                            Node temp = close.floor(node);
                            System.out.println("close temp.g = "+ temp.g + " do nothing!");
                            if (g<temp.g){
                                System.out.println("close contains node! current g is lower.");
                                close.remove(node);
                                node.g = g;
                                node.parent = cur;
                                node.h_manhattan(goal, n);
                                open.add(node);
                            }

                        }else if(open.contains(node)){
                            Node temp = open.floor(node);
                            System.out.println("open temp.g = "+ temp.g + " do nothing!");
                            if(g<temp.g){
                                System.out.println("open contains node! current g is lower.");
                                node.g = g;
                                node.parent = cur;
                            }

                        }else{
                            
                            node.g = g;
                            node.parent = cur;
                            node.h_manhattan(goal, n); // calculate h and f.
                            // System.out.print("h="+temp+ ", g="+node.g+", f=" +node.f +"\n");
                            open.add(node);
                            System.out.println("3. Not in both set!");
                        }
                    }

                }
                // System.out.println("open list: "+ open);
                // System.out.println("close list: "+ close);
            }
            // g++;
        }
     
    }

    // public void solve_manhattan(){
    //     boolean end = false;
    //     // {board1, board2, ....} the board is represent by a list of 9 integers.
    //     open = new TreeSet<>();
    //     close = new TreeSet<>();
    //     open.add(start_node);

    //     int g = 0;
    //     while(g<6){//!end
    //         System.out.println("\ng = "+g+" ------------------------");
    //         // first() Returns the first (lowest) element currently in this set. not remove yet.
    //         Node cur = open.first(); // get the best node in the open list. least f(n). 
    //         System.out.println("\nBoard with least f = "+cur.f+cur.toString());
    //         if (cur.equals(goal_node)){
    //             end = true;
    //             System.out.println("Found path with " + g + " Steps!");
    //             return; // we want to return sequence of Node.
    //         }else{
    //             g++; // take one more step to check neighbors.
    //             close.add(cur); // expand
    //             // build all neighbor's board after move the white tile, and check one by one.
    //             List<List<Integer>> neighbors = getNeighbors(cur.board);
    //             int i = 0;
    //             for (List<Integer> n : neighbors){
    //                 i++;
    //                 Node node = new Node(n);
    //                 // if (g >=2 && !node.equals(cur.parent)){
    //                     System.out.println("\n"+i+"th-----------\nNeighbors: "+node.toString());
    //                     if (close.contains(node)){
    //                         Node retrive = close.floor(node);
    //                         if(g<retrive.g){
    //                             System.out.println("1.close contains node!");
    //                             close.remove(retrive);   
    //                             // update g value and parent.
    //                             retrive.g = g;
    //                             retrive.parent = cur;
    //                             // add to open list.
    //                             open.add(retrive);
    //                         }

    //                     }else if(open.contains(node)){
    //                         Node retrive = open.floor(node);
    //                         if(g<retrive.g){
    //                             System.out.println("2.open contains node!");
    //                             open.remove(retrive);
    //                             // update g value and parent.
    //                             retrive.g = g;
    //                             retrive.parent = cur;
    //                             // add to open list.
    //                             open.add(retrive);
    //                         }
    //                     }else if (!open.contains(node) && !close.contains(node)){
    //                         System.out.println("3.Neither have the node!");
    //                         node.g = g;
    //                         node.h_manhattan(goal, n); // calculate h and f.
    //                         // System.out.print("h="+temp+ ", g="+node.g+", f=" +node.f +"\n");  
    //                         // System.out.println("\n---------------"); 
    //                         open.add(node);
    //                     }else{
    //                         System.out.println("do nothing!");
    //                         continue;
    //                     }
    //                 // }

    //             }
    //             System.out.println("open list: "+ open);
    //             System.out.println("close list: "+ close);
    //         }
    //         // g++;
    //     }
     
    // }



    // public void solve_manhattan_2(){
    //     boolean end = false;
    //     // {board1, board2, ....} the board is represent by a list of 9 integers.
    //     open_map = new HashMap<>();
    //     close_map = new HashMap<>();     
    //     open = new PriorityQueue<>((a,b)-> (open_map.get(b)==null) ? 0 : open_map.get(b).f - open_map.get(a).f);
    //     close = new PriorityQueue<>((a,b)-> (close_map.get(b)==null) ? 0 : close_map.get(b).f - close_map.get(a).f);

    //     open_map.putIfAbsent(start, start_node);
    //     open.offer(start_node);
        
    //     int g = 0;
    //     while(g<6){//!end
    //         System.out.println("\ng = "+g+" ------------------------");
    //         // first() Returns the first (lowest) element currently in this set. not remove yet.
    //         Node cur = open.poll();
    //         open_map.remove(cur.board);
    //         // Node cur = open.first(); // get the best node in the open list. least f(n). 
    //         System.out.println("\nBoard with least f = "+cur.f+cur.toString());
    //         if (cur.equals(goal_node)){
    //             end = true;
    //             System.out.println("Found path with " + g + " Steps!");
    //             return; // we want to return sequence of Node.
    //         }else{
    //             g++; // take one more step to check neighbors.
    //             close.offer(cur); // expand
    //             close_map.put(cur.board, cur);
    //             // build all neighbor's board after move the white tile, and check one by one.
    //             List<List<Integer>> neighbors = getNeighbors(cur.board);
    //             int i = 0;
    //             for (List<Integer> n : neighbors){
    //                 i++;
    //                 Node node = new Node(n);
    //                 // if (g >=2 && !node.equals(cur.parent)){
    //                     System.out.println("\n"+i+"th-----------\nNeighbors: "+node.toString());
    //                     if (close_map.containsKey(n)){
    //                         if(g<cur.g){
    //                             System.out.println("1.close contains node!");
    //                             close.remove(node);
    //                             Node temp = close_map.remove(n);
    //                             // update g value and parent.
    //                             temp.g = g;
    //                             temp.parent = cur;
    //                             // add to open list.
    //                             open.offer(temp);
    //                             open_map.put(n, temp);
    //                         }

    //                     }else if(open_map.containsKey(n)){
    //                         if(g<cur.g){
    //                             System.out.println("2.open contains node!");
    //                             open.remove(node);
    //                             Node temp = open_map.remove(n);
    //                             // update g value and parent.
    //                             temp.g = g;
    //                             temp.parent = cur;
    //                             // add to open list.
    //                             open.offer(temp);
    //                             open_map.put(n, temp);
    //                         }


    //                     }else if (!open_map.containsKey(n) && !close_map.containsKey(n)){
    //                         System.out.println("3.Neither have the node!");
    //                         node.g = g;
    //                         node.h_manhattan(goal, n); // calculate h and f.
    //                         // System.out.print("h="+temp+ ", g="+node.g+", f=" +node.f +"\n");  
    //                         // System.out.println("\n---------------"); 
    //                         open_map.put(n, node);
    //                         open.offer(node);
    //                     }else{
    //                         System.out.println("do nothing!");
    //                         continue;
    //                     }
    //                 // }

    //             }
    //             System.out.println("open list: "+ open);
    //             System.out.println("close list: "+ close);
    //         }
    //         // g++;
    //     }
     
    // }
    public void solve_IDA_manhattan(){

    }

    public void solve_DFBB_manhattan(){

    }
}

