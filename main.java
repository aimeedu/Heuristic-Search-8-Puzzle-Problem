
public class main{
    public static void main(String args[]){
        // use 0 to represent the empty tile.
        // 1D
        int[] g = {1,2,3,8,0,4,7,6,5};
        int index = 0;
        // 2D
        int [][] goal = new int[3][3];
        for (int i=0; i< goal.length; i++){
            for (int j=0; j<goal[0].length; j++){
                goal[i][j] = g[index];
                index++;
                // System.out.println(goal[i][j]);
            }
        }
    
    }
    

}
