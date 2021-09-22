package sr.unasat.Dijkstra;

import java.util.Scanner;

class cDistpar {                     // distance and parent, items stored in cPath array
    public int distancePrice;        // distance from start to this vertex
    public int parentVert;           // current parent of this vertex

    public cDistpar(int parentV, int distPrice) {
        distancePrice = distPrice;
        parentVert = parentV;
    }
}

class cGraph {
    private final int MAX_CITIES = 20;
    private final int INFINITY = 1000000;
    private Vertex cityList[];            // list of vertices
    private int adjMat[][];               // adjacency matrix
    private int nCities;                  // current number of vertices
    private int nTree;                    // number of verts in tree
    private cDistpar cPath[];             // array for shortest-path data
    private int currentVert;              // current vertex
    private int startToCurrent;           // distance to currentVert

    public cGraph() {
        cityList = new Vertex[MAX_CITIES];    // adjacency matrix
        adjMat = new int[MAX_CITIES][MAX_CITIES];
        nCities = 0;
        nTree = 0;

        for (int j = 0; j < MAX_CITIES; j++)         // set adjacency matrix to infinity
            for (int k = 0; k < MAX_CITIES; k++)
                adjMat[j][k] = INFINITY;
        cPath = new cDistpar[MAX_CITIES];            // shortest paths

    }
    public void addVertex(String city) {
        cityList[nCities++] = new Vertex(city);
    }

    public void addEdge(int start, int end, int weight) {
        adjMat[start][end] = weight;     // (directed)
    }
    public int findIndexOfStart(String startPoint) {
        int item;
        for (item = 0; item < nCities; item++) {
            if (cityList[item].stad.equalsIgnoreCase(startPoint))
                return item;
        }
        return item;
    }
    public void findCheapestPaths(int start) {     // find all cheapest paths
        int startTree= start;
        // start at vertex 0
        cityList[startTree].isInTree = true;
        nTree = 1;                                 // put it in tree

        // transfer row of distances from adjMat to sPath
        for (int j = 0; j < nCities; j++) {
            int tempDist = adjMat[startTree][j];
            cPath[j] = new cDistpar(startTree, tempDist); //opslaan van je temp weight
        }
        // until all vertices are in the tree
        while (nTree < nCities) {
            int indexMin = getMin();                   // get minimum from cPath
            int minDist = cPath[indexMin].distancePrice;
            if (minDist == INFINITY)                   // if all infinite
            {                                          // or in tree,
                System.out.println("There are unreachable vertices");
                break;                                 // cPath is complete
            } else {                                   // reset currentVert
                currentVert = indexMin;                // to closest vert
                startToCurrent = cPath[indexMin].distancePrice;
                // minimum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // put current vertex in tree
            cityList[currentVert].isInTree = true;
            nTree++;
            adjust_cPath_cheapest();                    // update cPath[] array
        }                                               // end while(nTree<nVerts)
        displayCheapestPaths();                         // display cPath[] contents
        nTree = 0;                                      // clear tree
        for (int j = 0; j < nCities; j++)
            cityList[j].isInTree = false;
    }

    public int getMin() {                               // get entry from cPath with minimum distance
        int minDist = INFINITY;                         // assume minimum
        int indexMin = 0;

        for (int j = 1; j < nCities; j++) {              // for each vertex, if it’s in tree and smaller than old one
            if (!cityList[j].isInTree && cPath[j].distancePrice < minDist) {
                minDist = cPath[j].distancePrice;
                indexMin = j;                           // update minimum
            }
        }                                               // end for
        return indexMin;                                // return index of minimum
    }

    public void adjust_cPath_cheapest() {         // adjust values in cheapest-path array cPath
        int column = 1;                           // skip starting vertex

        while (column < nCities){                   // go across columns
            // if this column’s vertex already in tree, skip it
            if (cityList[column].isInTree) {
                column++;
                continue;
            }
            // calculate distance for one cPath entry
            // get edge from currentVert to column
            int currentToFringe = adjMat[currentVert][column];

            // add distance from start
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
        for (int j = 0; j < nCities; j++)                          // display contents of cPath[]
        {
            System.out.print(cityList[j].stad + "= ");             // Paramaribo =
            if (cPath[j].distancePrice == INFINITY)
                System.out.print("*not reachable*");               // inf

            else
                System.out.print(cPath[j].distancePrice + "$");    // 500

            String parent = cityList[cPath[j].parentVert].stad;
            System.out.println(" (" + "through" + " " + parent + ") ");     // (through Paramaribo)
        }
        System.out.println("");
    }
}
class CHEAPApp {
    public static void main(String[] args) {
        cGraph theGraph = new cGraph();
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

        System.out.println("The cheapest routes are: ");
        theGraph.findIndexOfStart(item);
        int i = theGraph.findIndexOfStart(item);
        theGraph.findCheapestPaths(i);

        System.out.println("");
        scan.close();
    }
}


