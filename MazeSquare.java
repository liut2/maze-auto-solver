public class MazeSquare {
	private String type;
        // declare boolean values leftWall , bottomWall, rignt wall and top wall.
        private boolean leftWall;
        private boolean bottomWall;
        private boolean visited;
        private boolean topWall;
        private boolean rightWall;
        private int[] position = new int[2];
        // initialize rightWall , topWall , leftWall and bottomWall to false
        public MazeSquare(){
                visited = leftWall = bottomWall = rightWall = topWall = false;

        }
        //set the maze square to have the rightwall.
        public void setRightWall(){
                this.rightWall = true;
        }
        //set the maze square to have the top wall.
        public void setTopWall(){
            this.topWall = true;
        }
        // return to see if the maze square has the topwall.
        public boolean hasTopWall(){
            return topWall;
        }
        //return to see if the maze square has the rightwall.
        public boolean hasRightWall(){
            return rightWall;
        }
        //return to see if the maze square has the leftwall.
        public boolean hasLeftWall(){
                return leftWall;
        }
        //return to see if the maze square has the bottomwall.
        public boolean hasBottomWall(){
                return bottomWall;
        }
        // set the maze square to be visited.
        public void setVisited(){
                this.visited = true;
        }
        //return to see if the maze square has been visited.
        public boolean ifVisited(){
                return this.visited;
        }
        //stotr the position information of each maze square.
        public void setPosition(int i, int j){
            
            this.position[0] = j;
            this.position[1] = i;
        }
        // get the position of each maze square.
        public int[] getPosition(){
            return this.position;
        }
        // if the given input type has a left wall or a bottom wall, set the respective wall to true
        public void setType(String type){
        	this.type = type;
                if(this.type.equals("L")){
                        leftWall = bottomWall = true;
                }else if(this.type.equals("|")){
                        leftWall = true;
                }else if(this.type.equals("_")){
                        bottomWall = true;
                }

        }
        
}
