package sr.unasat.bfs;

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
        public void insert(int item){                      // put item at rear of queue
            if(rear == SIZE-1)
                rear = -1;
            queArray[++rear] = item;
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
    class Vertex {
        public String stad;                      // stad (e.g. "Paramaribo")
        public boolean wasVisited;

        public Vertex(String city ){             // constructor
            stad = city;
            wasVisited = false;
        }
    }
    class Graph {
        private final int MAX_VERTS = 20;
        private Vertex vertexList[];                   // list of vertices
        private int adjMat[][];                        // adjacency matrix
        private int nVerts;                            // current number of vertices
        private Queue theQueue;

        public Graph() {                                  // constructor
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

        public void addEdge(int start, int end){
            adjMat[start][end] = 1;
          // adjMat[end][start] = 1;
        }

        public void displayVertex(int v){
            System.out.print(vertexList[v].stad);
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
        public int getAdjUnvisitedVertex(int v)
        {
            for(int j=0; j<nVerts; j++)
                if(adjMat[v][j]==1 && !vertexList[j].wasVisited)
                    return j;
            return -1;
        }                                                  // end getAdjUnvisitedVertex()
    }                                                      // end class Graph

