package sr.unasat.searches;

public class Queue {
        private final int SIZE = 20;
        private final int[] queArray;
        private int front;
        private int rear;

        public Queue(){
            queArray = new int[SIZE];
            front = 0;
            rear = -1;
        }
        public void insert(int data){                      // put item at rear of queue
            if(rear == SIZE-1)
                rear = -1;
            queArray[++rear] = data;
        }

        public int remove(){                               // take item from front of queue
            int temp = queArray[front++];
            if(front == SIZE)
                front = 0;
            return temp;
        }
        public boolean isEmpty() {                          // true if queue is empty
            return ( rear+1==front || (front+SIZE-1==rear) );
        }
    }

class BGraph {
        private final int MAX_VERTS = 20;
        private Vertex vertexList[];                   // list of vertices
        private int adjMat[][];                        // adjacency matrix
        private int nVerts;                            // current number of vertices
        private Queue theQueue;

        public BGraph() {                                  // constructor
            vertexList = new Vertex[MAX_VERTS];
                                                          // adjacency matrix
            adjMat = new int[MAX_VERTS][MAX_VERTS];
            nVerts = 0;
            for(int j=0; j<MAX_VERTS; j++)                // set adjacency
                for(int k=0; k<MAX_VERTS; k++)            // matrix to 0
                    adjMat[j][k] = 0;
            theQueue = new Queue();
        }

        public void addVertex(String city){
            vertexList[nVerts++] = new Vertex(city);
        }

        public void addEdge(int start, int end){ //voegt edges toe en geeft aan waar het begint een waar het eindigt
            adjMat[start][end] = 1;
          // adjMat[end][start] = 1;
        }

        public void displayVertex(int v){
            System.out.print(vertexList[v].stad + " - ");
        }

        public void bfs(){                                // begin at vertex 0
            vertexList[0].wasVisited = true;              // mark it
            displayVertex(0);                          // display it
            theQueue.insert(0);                      // insert at tail
            int v2;

            while( !theQueue.isEmpty() ){                  // until queue empty,
                int v1 = theQueue.remove();                // remove vertex at head

                                                           // until it has no unvisited neighbors
                while( (v2=getAdjUnvisitedVertex(v1)) != -1 ){
                    vertexList[v2].wasVisited = true;           // mark it
                    displayVertex(v2);                          // display it
                    theQueue.insert(v2);                        // insert it
                }                                               // end while
            }                                                   // end while(queue not empty)
                                                                // queue is empty, so we’re done

            for(int j=0; j<nVerts; j++) // reset flags
                vertexList[j].wasVisited = false;
        }
        // returns an unvisited vertex adj to v
        public int getAdjUnvisitedVertex(int v){   //word gebruik om neighbours op te zoeken
            for(int j=0; j<nVerts; j++)
                if(adjMat[v][j]==1 && !vertexList[j].wasVisited)
                    return j;
            return -1;
        }
    }

class BFSApp {
    public static void main(String[] args) {
        BGraph bfsGraph = new BGraph();
        bfsGraph.addVertex("Paramaribo");       // 0
        bfsGraph.addVertex("Port Of Spain");    // 1
        bfsGraph.addVertex("Caracas");          // 2
        bfsGraph.addVertex("Oranjestad");       // 3
        bfsGraph.addVertex("Kingston");         // 4
        bfsGraph.addVertex("San Juan");         // 5
        bfsGraph.addVertex("Saint John's");     // 6
        bfsGraph.addVertex("Bridgetown");       // 7


        bfsGraph.addEdge(0, 1);
        bfsGraph.addEdge(0, 7);
        bfsGraph.addEdge(1, 2);
        bfsGraph.addEdge(1, 7);
        bfsGraph.addEdge(2, 3);
        bfsGraph.addEdge(3, 4);
        bfsGraph.addEdge(3, 5);
        bfsGraph.addEdge(5, 4);
        bfsGraph.addEdge(4, 4);

        bfsGraph.addEdge(7, 2);
        bfsGraph.addEdge(7, 3);
        bfsGraph.addEdge(7, 6);
        bfsGraph.addEdge(6, 5);

        System.out.println("Cities that are adjacent to each others are: ");
        bfsGraph.bfs();
        System.out.println();
    }
}

