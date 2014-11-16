/*This program was written by Tao Liu to read text file and automatically draw and solve a maze.
* Maza.java
*/ 

import java.util.*;
import java.io.File;
public class Maze {
    private ArrayList<ArrayList<String>> listOftypeList = new ArrayList<ArrayList<String>>();
    private ArrayList<String> typeList = new ArrayList<String>();
    private int[] size = new int[2];

    private int[] StartPosition = new int[2];
    private int[] EndPosition = new int[2];
    private ArrayList<ArrayList<MazeSquare>> list_list_MazeSquare = new ArrayList<ArrayList<MazeSquare>>();
    private ArrayList<MazeSquare> list_MazeSquare;
    private Stack<MazeSquare> stack_of_MazeSquare = new Stack<MazeSquare>();
   
    
    

    public void load(String fileName) {
        // load text file from command line argument containing maze configuration
        String filePath = fileName;
        File file = new File(filePath);
        Scanner scanner = null;

        try{
            scanner = new Scanner(file);
        // catch error if user imputs a bad file or file path
        }catch(Exception e){
            System.err.println("Can't open"+filePath);
            System.exit(1);
        }
        // load the first three line of the file and store data in three variables
        for(int i=0; i<3; i++){
            // store the size of the maze in setSize
            if(i==0){
                String line = scanner.nextLine();
                String[] parts= line.split(" ");
                setSize(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
            // store the maze starting position in setStartPosition
            }if(i==1){
                String line = scanner.nextLine();
                String[] parts= line.split(" ");
                setStartPosition(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
            // store the maze ending position in setEndPosition
            }if(i==2){
                String line = scanner.nextLine();
                String[] parts= line.split(" ");
                setEndPosition(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
            }
        }

        // store all of the types from the imput file into the two dimmensional ArrayList
        for(int i=3; i<3 + getSize()[1]; i++){
            this.typeList = new ArrayList<String>();
            String line = scanner.nextLine();
            for(int j=0; j<getSize()[0];j++){
                String type =line.substring(j,j+1);
                this.typeList.add(type);  
            }
             this.listOftypeList.add(this.typeList);
            
        }
        
    }
    // create mazeSquare objects and load them into the two dimmensional ArrayList list_list_MazeSquare
    public void loadMazeSquare(){
        int i,j;
        for(i=0; i<listOftypeList.size();i++){
            this.list_MazeSquare = new ArrayList<MazeSquare>();
            for(j=0; j<typeList.size();j++){
                MazeSquare mazeSquare = new MazeSquare();
                mazeSquare.setPosition(i,j);
                mazeSquare.setType(listOftypeList.get(i).get(j));
                //if the maze square is at the top of the whole maze. then it also has the top wall.
                if (i == 0){
                    mazeSquare.setTopWall();
                    //if the maze square is at the rightmost of the whole maze, then it also has the rightwall.
                }if ( j == typeList.size()-1 ){
                    mazeSquare.setRightWall();
                }

                this.list_MazeSquare.add(mazeSquare);
                
            }
            this.list_list_MazeSquare.add(this.list_MazeSquare);
        }
        for (i=0; i<list_list_MazeSquare.size();i++){
            for (j=0;j<list_list_MazeSquare.get(0).size();j++){
                if ((j != 0) &&  (list_list_MazeSquare.get(i).get(j).hasLeftWall() == true)){
                    list_list_MazeSquare.get(i).get(j-1).setRightWall();
                }if ((i != list_list_MazeSquare.size()-1) &&  (list_list_MazeSquare.get(i).get(j).hasBottomWall() == true)){
                    list_list_MazeSquare.get(i+1).get(j).setTopWall();
                }
            }
        }

    }

    // get the two dimensional Arraylist listOftypeList
    public ArrayList<ArrayList<String>> getList(){
        return this.listOftypeList;
    }
    // set the end position of the maze
    public void setEndPosition(int col, int row){
        this.EndPosition[0] = col;
        this.EndPosition[1] = row;
    }
    // get the end position of the maze
    public int[] getEndPosition(){
        return EndPosition;
    }
    // set the starting position of the maze
    public void setStartPosition(int col, int row){
        this.StartPosition[0] = col;
        this.StartPosition[1] = row;
    }
    // get the starting position of the maze
    public int[] getStartPosition(){
        return this.StartPosition;
    }
    // set the size of the maze
    public void setSize(int col, int row){
        
        this.size[0] = col;
        this.size[1] = row;
        
    }
    // get the size of the maze
    public int[] getSize(){
        return this.size;
    }
    
    // print the solution to the maze.
     public void print(){
        int i,j;
        //print the top wall automatically.
        System.out.print("+");
        for(j=0; j<list_MazeSquare.size();j++){
            System.out.print("-----+");
        }
       
       // print out the maze line by line.use the for loop to print out the three lines with the same "|"partternwith the right wall 
        //automatically being printed out.and
        //check if the sign"S" or "F" is in the maze square:if so, print out the sign.and then print out the bottom wall.
        //the process above is actually in a larger loop and so the process will be repeated for a few times.
        //Also, because I am going to print out the solution to the maze, So every time if the mazesquare is one of the blocks of the path solution, 
        //it will print out the "|  *  " instead of "|     ".
        System.out.println();
        for(i=0; i<list_list_MazeSquare.size();i++){
            for(int k=0; k<3; k++){
                for(j=0; j<list_MazeSquare.size();j++){
                    if(list_list_MazeSquare.get(i).get(j).hasLeftWall()){
                        
                        if ((i==getStartPosition()[1]) && (j==getStartPosition()[0]) && (k==1)){
                            System.out.print("|  S  ");
                        }

                        else if((i==getEndPosition()[1]) && (j==getEndPosition()[0]) && (k==1)){
                            System.out.print("|  F  ");
                        }else if ((k==1)&& checkPath(j,i,getSolution()).equals("true")) {
                            System.out.print("|  *  ");
                        }
                        else{
                            System.out.print("|     ");
                        }
                    }
                    
                    if(list_list_MazeSquare.get(i).get(j).hasLeftWall()==false){
                        
                        if ((i==getStartPosition()[1]) && (j==getStartPosition()[0]) && (k==1)){
                            System.out.print("   S  ");
                        }
                        else if((i==getEndPosition()[1]) && (j==getEndPosition()[0]) && (k==1)){
                            System.out.print("   F  ");
                        }else if ((k==1)&& checkPath(j,i,getSolution()).equals("true")) {
                            System.out.print("|  *  ");
                        }
                        else{
                            System.out.print("      ");
                        }
                        
                    }if(j== list_MazeSquare.size()-1){
                        System.out.print("|");
                    }
                    
                }
                System.out.println();
            }for(j=0; j<list_MazeSquare.size();j++){
                if(list_list_MazeSquare.get(i).get(j).hasBottomWall()){
                    System.out.print("+-----");
                }else{
                    System.out.print("+     ");
                }if(j== list_MazeSquare.size()-1){
                    System.out.print("+");
                }
            }
            System.out.println();
        }   
        
    }
    // this function is going to calculate the solution to the maze square, using a kind of data structure called stack.
    public Stack<MazeSquare> getSolution(){
        
        
        
        
        
        
        // Push the start square onto the stack, and mark the start square as visited.
        MazeSquare startingMazeSquare = stack_of_MazeSquare.push(list_list_MazeSquare.get(getStartPosition()[1]).get(getStartPosition()[0]));
        MazeSquare finishingMazeSquare = list_list_MazeSquare.get(getEndPosition()[1]).get(getEndPosition()[0]);
        list_list_MazeSquare.get(getStartPosition()[1]).get(getStartPosition()[0]).setVisited();
        MazeSquare T = null;
        
        MazeSquare pushedSquare = null;
        MazeSquare popedSquare = null;
        // If the stack is empty, you're done and the maze is unsolvable.
        while (!stack_of_MazeSquare.empty()){
            T = stack_of_MazeSquare.peek();
            // Let T be the top item on the stack. If T is equal to the finish square, you're done and the stack contains a solution to the maze.
            if ( T.equals(finishingMazeSquare)){
                return stack_of_MazeSquare;
            }else{
                // If all squares adjacent to T (i.e. the squares up, down, right, or left from T--no diagonal adjacency) are either blocked from T by a wall or are marked visited already, pop T off the stack and go back to 
                //to the condition of the while loop. if not all the walls from the four sides block the way, then check whether the adjacent area where the way is not blocked is visited or not
                if(!(T.hasLeftWall() && T.hasBottomWall()&& T.hasRightWall() && T.hasTopWall())){
                    //we check the square on the top of T and see if it's blocked or visited .if not, we set this new square as visited and push it to the stack.
                    if ((T.getPosition()[1] != 0) && (!T.hasTopWall()) && (list_list_MazeSquare.get(T.getPosition()[1]-1).get(T.getPosition()[0]).ifVisited() == false)){
                        list_list_MazeSquare.get(T.getPosition()[1]-1).get(T.getPosition()[0]).setVisited();
                        pushedSquare =  stack_of_MazeSquare.push(list_list_MazeSquare.get(T.getPosition()[1]-1).get(T.getPosition()[0]));
                        
                    }else{
                        //then if top area is not available, we did the same check to square to the bottom of T.if not visited, we set this new square as visited and push it to the stack.
                        if ((T.getPosition()[1] != list_list_MazeSquare.size()-1) && (!T.hasBottomWall()) && (list_list_MazeSquare.get(T.getPosition()[1]+1).get(T.getPosition()[0]).ifVisited() == false)){
                            list_list_MazeSquare.get(T.getPosition()[1]+1).get(T.getPosition()[0]).setVisited();
                            pushedSquare =  stack_of_MazeSquare.push(list_list_MazeSquare.get(T.getPosition()[1]+1).get(T.getPosition()[0]));
                            
                        }else{
                            //then if bottom area is not available , we did the same check to square to the left of the T. if not visited, we set this new square as visited and push it to the stack.
                            if((T.getPosition()[0] != 0) && (!T.hasLeftWall()) && (list_list_MazeSquare.get(T.getPosition()[1]).get(T.getPosition()[0]-1).ifVisited() == false)){
                                list_list_MazeSquare.get(T.getPosition()[1]).get(T.getPosition()[0]-1).setVisited();
                                pushedSquare =  stack_of_MazeSquare.push(list_list_MazeSquare.get(T.getPosition()[1]).get(T.getPosition()[0]-1));
                            }else{
                                //then if left area is not available , we did the same check to square to the right of the T. if not visited, we set this new square as visited and push it to the stack.
                                if((T.getPosition()[0] != (list_list_MazeSquare.get(0).size())-1) && (!T.hasRightWall()) && (list_list_MazeSquare.get(T.getPosition()[1]).get(T.getPosition()[0]+1).ifVisited() == false)){
                                    list_list_MazeSquare.get(T.getPosition()[1]).get(T.getPosition()[0]+1).setVisited();
                                    pushedSquare = stack_of_MazeSquare.push(list_list_MazeSquare.get(T.getPosition()[1]).get(T.getPosition()[0]+1));
                                }else{
                                    //if all the four sides' areas are visited, we pop the T off the stack and start from the last stack object again.
                                    popedSquare =  stack_of_MazeSquare.pop();
                                }
                            }
                        }
                    }
                }
                
            }
        }
        return stack_of_MazeSquare;

    }
    // we use this function to check whether the given maze square is one of the maze squares that constitute the solution.and return the value after the check.
    public String checkPath(int row, int col, Stack<MazeSquare> ii){
        int ROW = row;
        int COL = col;
        String truth = null;
        Stack<MazeSquare> iii = ii;
        int ss = iii.size();
        for(int i =0; i< ss ;i++){
            MazeSquare mmm = iii.get(i);
            
            if (ROW == mmm.getPosition()[0] && COL == mmm.getPosition()[1]){
                // System.out.println("true");
                truth = "true";
                break;
            }else{
                truth = "false";
            }
        }return truth;
    }
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Maze mazeFile [--showsolution]");
            System.exit(1);
        }
        try{
            String args1 = args[1];
            if (args1.equals("showsolution")){
            Maze maze = new Maze();
            maze.load(args[0]);
            maze.loadMazeSquare();
        
            maze.print();
            }
        }catch(Exception e){
            System.out.println("please check your args");
        }

    }  
}
