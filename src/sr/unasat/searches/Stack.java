package sr.unasat.searches;

    public class Stack {
        private final int SIZE = 20;
        private int[] stack;
        private int top;

        public Stack(){
            stack = new int[SIZE];          // make array
            top = -1;                       // wijst dat je nog geen items erin heb gezet
        }
        public void push(int data){            // put item on stack
         stack[++top] = data;
        }

        public int pop(){                  // take item off stack
            return stack[top--];
        }

        public int peek(){                // peek at top of stack
        return stack[top];
        }
        public boolean isEmpty(){         // true if nothing on stack-
         return (top == -1);
        }
    }
        class Graph {
        private final int MAX_VERTS = 20;
        private Vertex vertexList[];      // list of vertices
        private int adjMat[][];           // adjacency matrix
        private int nVerts;               // current number of vertices
        private Stack theStack;

        public Graph(){
            vertexList = new Vertex[MAX_VERTS];
            adjMat = new int[MAX_VERTS][MAX_VERTS];            // adjacency matrix
            nVerts = 0;
            for(int j=0; j<MAX_VERTS; j++)
                for(int k=0; k<MAX_VERTS; k++)            // set adjacency matrix to 0
                    adjMat[j][k] = 0;
            theStack = new Stack();
        }

        public void addVertex(String city){            //voegt vertices toe aan je vertexList array
            vertexList[nVerts++] = new Vertex(city);
        }

        public void addEdge(int start, int end){    //voegt edges toe en geeft aan waar het begint een waar het eindigt
            adjMat[start][end] = 1; //(directed)
         // adjMat[end][start] = 1;
        }
        public void displayVertex(int v){                  //hier word de bezochte vertices displayed
            System.out.print(vertexList[v].stad + "- ");
        }

        public void dfs(){        // depth-first search
                                  // begin at vertex 0
            vertexList[0].wasVisited = true;     // mark it
            displayVertex(0);                 // display it
            theStack.push(0);                 // push it
            while( !theStack.isEmpty() ) {        // gaat loopen until stack empty,

                // get an unvisited vertex adjacent to stack top
                int v = getAdjUnvisitedVertex( theStack.peek() );
                if(v == -1)                    // if no such vertex,
                    theStack.pop();
                else{
                    // if it exists,
                    vertexList[v].wasVisited = true;     // mark it
                    displayVertex(v);                    // display it

                    theStack.push(v);                    // push it
                }
            }
            // stack is empty, so we’re done
            for(int j=0; j<nVerts; j++)                  // reset flags
                vertexList[j].wasVisited = false;
        }
            // returns an unvisited vertex adj to v
            public int getAdjUnvisitedVertex(int v){     //zoekt j neighbours van v
                for(int j=0; j<nVerts; j++)
                    if(adjMat[v][j]==1 && vertexList[j].wasVisited==false){
                        return j;
                    }
                return -1;
            }
    }

class DFSApp
{
    public static void main(String[] args) {
        Graph theGraph = new Graph();
        theGraph.addVertex("Paramaribo ");       // 0
        theGraph.addVertex("Port Of Spain ");    // 1
        theGraph.addVertex("Caracas ");          // 2
        theGraph.addVertex("Oranjestad ");       // 3
        theGraph.addVertex("Kingston ");         // 4
        theGraph.addVertex("San Juan ");         // 5
        theGraph.addVertex("Saint John's ");     // 6
        theGraph.addVertex("Bridgetown ");       // 7


        theGraph.addEdge(0,1);
        theGraph.addEdge(0,7);
        theGraph.addEdge(1,2);
        theGraph.addEdge(1,7);
        theGraph.addEdge(2,3);
        theGraph.addEdge(3,4);
        theGraph.addEdge(3,5);
        theGraph.addEdge(5,4);


        theGraph.addEdge(7,2);
        theGraph.addEdge(7,3);
        theGraph.addEdge(7,6);
        theGraph.addEdge(6,5);

        System.out.println("The following destinations that exist are: ");
        theGraph.dfs();
        System.out.println();
    }
}







