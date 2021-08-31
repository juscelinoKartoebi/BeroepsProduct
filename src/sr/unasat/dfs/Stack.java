package sr.unasat.dfs;

    public class Stack {

        private final int SIZE = 20;
        private int[] stack;
        private int top;

        public Stack(){          // constructor
            stack = new int[SIZE];         // make array
            top = -1;
        }
        public void push(int j){            // put item on stack
         stack[++top] = j; }
        public int pop(){                // take item off stack

            return stack[top--];
        }

        public int peek()           // peek at top of stack

        { return stack[top];
        }
        public boolean isEmpty()     // true if nothing on stack-
        { return (top == -1);
        }
    }

    class Vertex {
    public String stad;                      // stad (e.g. "Paramaribo")
    public boolean wasVisited;

    public Vertex(String city ){            // constructor

        stad = city;
        wasVisited = false;
    }
}
        class Graph {

        private final int MAX_VERTS = 20;
        private Vertex vertexList[];      // list of vertices
        private int adjMat[][];           // adjacency matrix
        private int nVerts;               // current number of vertices
        private Stack theStack;

        public Graph(){         // constructor

            vertexList = new Vertex[MAX_VERTS];
            adjMat = new int[MAX_VERTS][MAX_VERTS];            // adjacency matrix
            nVerts = 0;
            for(int j=0; j<MAX_VERTS; j++)
                for(int k=0; k<MAX_VERTS; k++)            // set adjacency matrix to 0
                    adjMat[j][k] = 0;
            theStack = new Stack();
        } // end constructor

        public void addVertex(String city)
        {
            vertexList[nVerts++] = new Vertex(city);
        }

        public void addEdge(int start, int end)
        {
            adjMat[start][end] = 1;
         // adjMat[end][start] = 1;
        }

        public void displayVertex(int v)
        {
            System.out.print(vertexList[v].stad);
        }
        public void dfs()          // depth-first search
        {                         // begin at vertex 0
            vertexList[0].wasVisited = true;           // mark it
            displayVertex(0);                 // display it
            theStack.push(0);                  // push it
            while( !theStack.isEmpty() )           // until stack empty,

            {
                // get an unvisited vertex adjacent to stack top
                int v = getAdjUnvisitedVertex( theStack.peek() );
                if(v == -1)                    // if no such vertex,

                    theStack.pop();
                else                     // if it exists,
                {
                    vertexList[v].wasVisited = true;     // mark it
                    displayVertex(v);                           // display it

                    theStack.push(v);                      // push it
                }
            } // end while

            // stack is empty, so we’re done
            for(int j=0; j<nVerts; j++)                  // reset flags
                vertexList[j].wasVisited = false;
        } // end dfs

        // returns an unvisited vertex adj to v
        public int getAdjUnvisitedVertex(int v)
        {
            for(int j=0; j<nVerts; j++)
                if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
                    return j;
            return -1;
        }

    }



