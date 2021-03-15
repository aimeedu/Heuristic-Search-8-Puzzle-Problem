import java.util.*;

public class Puzzle{
    // calculate Misplaced heuristics
    // h_misplaced(goal, hard);
    // calculate Manhattan heuristics
    // h_manhattan(dim, goal, hard);
    int dim, h_misplaced, h_manhattan;
    List<Integer> goal;
    List<Integer> start;
    Node goal_node;
    Node start_node;
    // Map<List<Integer>, Node> open_map, close_map; // <board, f_value>
    // PriorityQueue<Node> open, close;
    TreeSet<Node> open, close;

    // constructor
    public Puzzle(int dim, List<Integer> goal_arr, List<Integer> start_arr){
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
        open.add(start_node);
        close = new TreeSet<>();

        int g = 0;
        while(g<8){//!end
            System.out.println("\n"+g+"------------------------");
            // first() Returns the first (lowest) element currently in this set. not remove yet.
            Node cur = open.first(); // get the best node in the open list. least f(n). 
            System.out.println("\nBoard with least f = "+cur.f+cur.toString());
            open.remove(cur);
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
                List<List<Integer>> neighbors = getNeighbors(cur.board);
                for (List<Integer> n : neighbors){
                    Node node = new Node(n);
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
                    // }

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

