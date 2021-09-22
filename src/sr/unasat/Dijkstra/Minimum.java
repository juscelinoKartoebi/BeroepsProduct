package sr.unasat.Dijkstra;

import java.util.Scanner;

class CDistpar {                     // distancePr and parent, items stored in cPath array
    public int distancePrice;        // distancePrice from start to this vertex
    public int parentVert;           // current parent of this vertex

    public CDistpar(int parentV, int distPrice) {
        distancePrice = distPrice;
        parentVert = parentV;
    }
}

class CGraph {
    private final int MAX_VERTS = 20;
    private final int INFINITY = 1000000;
    private Vertex vertexList[];            // list of vertices
    private int adjMat[][];               // adjacency matrix
    private int nVerts;                  // current number of vertices
    private int nTree;                    // number of verts in tree
    private CDistpar cPath[];             // array for shortest-path data
    private int currentVert;              // current vertex
    private int startToCurrent;           // distance to currentVert

    public CGraph() {
        vertexList = new Vertex[MAX_VERTS];    // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        nTree = 0;

        for (int j = 0; j < MAX_VERTS; j++)         // set adjacency matrix to infinity
            for (int k = 0; k < MAX_VERTS; k++)
                adjMat[j][k] = INFINITY;
        cPath = new CDistpar[MAX_VERTS];            // shortest paths

    }
    public void addVertex(String city) { //voegt vertices toe aan je vertexList array
        vertexList[nVerts++] = new Vertex(city);
    }

    public void addEdge(int start, int end, int weight) { //voegt edges toe en geeft aan waar het begint en waar het eindigt met de weight
        adjMat[start][end] = weight;     // (directed)
    }
    public int findIndexOfStart(String startPoint){ //method om de startingpoint aan tegeven dmv een String
        int city;
        for (city = 0; city < nVerts; city++) {
            if (vertexList[city].stad.equalsIgnoreCase(startPoint))
                return city;
        }
        return city;
    }
    public void findCheapestPaths(int start) {     // zoekt nr de cheapest paths
        int startTree= start;
        // start at vertex 0
        vertexList[startTree].isInTree = true;
        nTree = 1;                                 // put it in tree

        // copy de distancePrice van adjM naar cPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            cPath[j] = new CDistpar(startTree, tempDist); //opslaan van je temp weight
        }
        while (nTree < nVerts) {
            int indexMin = getMin();                   // je haalt de minimum van cPath
            int minDist = cPath[indexMin].distancePrice; //van de minimum kijken we dan wat de distPrice is
            if (minDist == INFINITY){                   // als distance inf is, dan zijn we klaar
                System.out.println("note!!(There are unreachable vertices!)");
                break;                                 // cPath is compleet
            } else {  // als er wel een waarde is, reset currentVert naar closest vert
                currentVert = indexMin;
                startToCurrent = cPath[indexMin].distancePrice;
                // minimum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // Hier geeft t aan dat het gevisited is
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_cPath();                    // update cPath[] array
        }                                               // end while(nTree<nVerts)
        displayCheapestPaths();                         // display cPath[] contents
        nTree = 0;                                      // clear tree
        for (int j = 0; j < nVerts; j++)
            vertexList[j].isInTree = false;
    }

    public int getMin() {                               // get entry from cPath with minimum distance
        int minDist = INFINITY;
        int indexMin = 0;

        for (int j = 1; j < nVerts; j++) {
            if (!vertexList[j].isInTree && cPath[j].distancePrice < minDist) {//voor elke vertex, als het niet in de tree is en kleiner is dan de vorige
                minDist = cPath[j].distancePrice;
                indexMin = j;                           // update minimum
            }
        }                                               // end for
        return indexMin;                                // return index of minimum
    }

    public void adjust_cPath() {         // adjust values in cPath,
        int column = 1;                  // skip starting vertex

        while (column < nVerts){                   // je gaat door alle verts
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }//als het niet in die tree is
            // calculate distance for one cPath entry
            //neem edge van currentV naar die column
            int currentToFringe = adjMat[currentVert][column];

            // De twee weights pakken en optellen
            int startToFringe = startToCurrent + currentToFringe;

            // get distance of current sPath entry
            int sPathDist = cPath[column].distancePrice;

            // compare distance from start with sPath entry
            if (startToFringe < sPathDist)                         // if shorter,
            {
                // update sPath
                cPath[column].parentVert = currentVert;
                cPath[column].distancePrice = startToFringe;
            }
            column++;
        }                                                    // end while(column < nVerts)
    }                                                        // end adjust_cPath()

    public void displayCheapestPaths() {
        for (int j = 0; j < nVerts; j++)                          // display contents of cPath[]
        {
            System.out.print(vertexList[j].stad + "= ");             // Paramaribo =
            if (cPath[j].distancePrice == INFINITY)
                System.out.print("*not reachable*");               // inf

            else
                System.out.print(cPath[j].distancePrice + "$");    // 500

            String parent = vertexList[cPath[j].parentVert].stad;
            System.out.println(" (" + "through" + " " + parent + ") ");     // (through Paramaribo)
        }
        System.out.println("");
    }
}
class CHEAPApp {
    public static void main(String[] args) {
        CGraph theGraph = new CGraph();
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
        String city = scan.next();

        System.out.println("The cheapest routes are: ");
        theGraph.findIndexOfStart(city);
        int i = theGraph.findIndexOfStart(city);
        theGraph.findCheapestPaths(i);

        System.out.println("");
        scan.close();
    }
}


