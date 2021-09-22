package sr.unasat.Dijkstra;

import java.util.Scanner;

public class eDistPrice { // distanceprice and parent, items stored in ePath array
        public int distancePrice;            // price of distance from start to this vertex
        public int parentVert;               // current parent of this vertex

        public eDistPrice(int parentV, int distPrice) {
            distancePrice = distPrice;
            parentVert = parentV;
        }
    }

class Graph {
    private final int MAX_VERTS = 20;
    private final int INFINITE = 0;
    private Vertex vertexList[];
    private int adjMat[][];
    private int nVerts;
    private int nTree;
    private eDistPrice ePath[];
    private int currentVert;
    private int startToCurrent;

    public Graph() {
        vertexList = new Vertex[MAX_VERTS];    // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        nTree = 0;

        for (int j = 0; j < MAX_VERTS; j++)          // set adjacency
            for (int k = 0; k < MAX_VERTS; k++)      // matrix
                adjMat[j][k] = INFINITE ;            // to infinite
        ePath = new eDistPrice[MAX_VERTS];           // expensive paths

    }
    public void addVertex(String city){
        vertexList[nVerts++] = new Vertex(city);
    }

    public void addEdge(int start, int end, int weight){
        adjMat[start][end] = weight;     // (directed)
    }

    public int findIndexOfStart(String startPoint) {
        int item;
        for (item = 0; item < nVerts; item++) {
            if (vertexList[item].stad.equalsIgnoreCase(startPoint))
                return item;
        }
        return item;
    }
    public void findExpensivePaths(int start){     // find all expensive paths
        int startTree= start;
        vertexList[startTree].isInTree = true;
        nTree = 1;                                 // put it in tree


        // transfer row of distancePrice from adjMat to ePath
        for(int j=0; j<nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            ePath[j] = new eDistPrice(startTree, tempDist); //opslaan van je temp weight
        }
        // until all vertices are in the tree
        while(nTree < nVerts) {
            int indexMax = getMax();                    // get maximum from ePath
            int maxDist = ePath[indexMax].distancePrice;

            if(maxDist == INFINITE) {                   // if all infinite
                                                        // or in tree,
                System.out.println("There are unreachable vertices");
                break;                                  // ePath is complete
            } else {                                    // reset currentVert
                currentVert = indexMax;                 // to the closest vert
                startToCurrent = ePath[indexMax].distancePrice;
                // maximum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // put current vertex in tree
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_ePath_expensive();                 // update ePath[] array
        }                                             // end while(nTree<nVerts)

        displayExpensivePaths();
        nTree = 0;                                    // clear tree
        for (int j = 0; j < nVerts; j++)
            vertexList[j].isInTree = false;
    }
    //get max path in lijst
    public int getMax() {                           // get entry from ePath with maximum distance
        int maxDist= INFINITE;                      // assume maximum
        int indexMax = 0;

        for(int j=1; j<nVerts; j++){                // voor elke vertex, als het in de tree is en groter dan de vorige
            if( !vertexList[j].isInTree && ePath[j].distancePrice > maxDist ){
                maxDist = ePath[j].distancePrice;
                indexMax = j;                        // update maximum
            }
        }                                            // end for
        return indexMax;                             // return index of maximum
    }

    //for longest
    public void adjust_ePath_expensive() {            // adjust values in expensive-path array ePath
        int column = 1;                               // skip starting vertex
        while(column < nVerts)                        // go across columns
        {
            // if this column vertex already in tree, skip it
            if( vertexList[column].isInTree ) {
                column++;
                continue;
            }
            // calculate distance for one ePath entry
            // get edge from currentVert to column
            int currentToFringe = adjMat[currentVert][column];

            // add distance from start
            int startToFringe = startToCurrent + currentToFringe;

            // get distance of current ePath entry
            int ePathDist = ePath[column].distancePrice;

            // compare distance from start with ePath entry
            if(startToFringe > ePathDist && currentToFringe != 0){    // if longer, update the longest Path
                ePath[column].parentVert = currentVert;
                ePath[column].distancePrice = startToFringe;
            }
            column++;
        }
    }
    public void displayExpensivePaths() {
        for (int j = 0; j < nVerts; j++)                          // display contents of ePath[]
        {
            System.out.print(vertexList[j].stad + "= ");          // Paramaribo =
            if (ePath[j].distancePrice == INFINITE)
                System.out.print("not reachable");                         // inf

            else
                System.out.print(ePath[j].distancePrice + "$");          // 500

            String parent = vertexList[ePath[j].parentVert].stad;

            System.out.println(" (" + "through" + " " + parent + ") ");         // (Paramaribo)

        }
        System.out.println("");
    }

}
class EXPPathApp {
    public static void main(String[] args) {
        Graph theGraph = new Graph();
        theGraph.addVertex("Paramaribo");         // 0
        theGraph.addVertex("Port of Spain");      // 1
        theGraph.addVertex("Caracas");            // 2
        theGraph.addVertex("Oranjestad");         // 3
        theGraph.addVertex("Kingston");           // 4
        theGraph.addVertex("San Juan");           // 5
        theGraph.addVertex("Saint John's");       // 6
        theGraph.addVertex("Bridgetown");         // 7

        theGraph.addEdge(0, 1, 400);
        theGraph.addEdge(0, 7, 700);
        theGraph.addEdge(1, 2, 500);
        theGraph.addEdge(1, 7, 200);
        theGraph.addEdge(2, 3, 200);
        theGraph.addEdge(3, 4, 400);
        theGraph.addEdge(3, 5, 600);
        theGraph.addEdge(5, 4, 700);
        theGraph.addEdge(6, 5, 200);
        theGraph.addEdge(7, 6, 400);
        theGraph.addEdge(7, 3, 300);
        theGraph.addEdge(7, 2, 500);

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter current location");
        String item = scan.next();
        System.out.println("Price of the most expensive routes are:");

        theGraph.findIndexOfStart(item);
        int i = theGraph.findIndexOfStart(item);
        theGraph.findExpensivePaths(i);

        System.out.println();
        scan.close();
    }
}

