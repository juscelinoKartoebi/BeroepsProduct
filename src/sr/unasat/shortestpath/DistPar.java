package sr.unasat.shortestpath;

class DistPar {                     // distance and parent
                                    // items stored in sPath array
    public int distancePr;            // distance from start to this vertex
    public int parentVert;          // current parent of this vertex

    public DistPar(int parentV, int distPr) {
        distancePr = distPr;
        parentVert = parentV;
    }
}

class Graph {
    private final int MAX_VERTS = 20;
    private final int INFINITY = 1000000;
    private Vertex vertexList[];            // list of vertices
    private int adjMat[][];                 // adjacency matrix
    private int nVerts;                     // current number of vertices
    private int nTree;                      // number of verts in tree
    private DistPar cPath[];                // array for shortest-path data
    private int currentVert;                // current vertex
    private int startToCurrent;             // distance to currentVert

    public Graph() {                           // constructor
        vertexList = new Vertex[MAX_VERTS];    // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        nTree = 0;

        for (int j = 0; j < MAX_VERTS; j++)         // set adjacency
            for (int k = 0; k < MAX_VERTS; k++)     // matrix
                adjMat[j][k] = INFINITY;            // to infinity
        cPath = new DistPar[MAX_VERTS];             // shortest paths

    }

    public void addVertex(String city){
        vertexList[nVerts++] = new Vertex(city);
    }

    public void addEdge(int start, int end, int weight){
        adjMat[start][end] = weight;     // (directed)
    }

    public void shortPath(int userStartTree) {     // find all shortest paths
        int startTree = userStartTree;
        // start at vertex 0
        vertexList[startTree].isInTree = true;
        nTree = 1;                                 // put it in tree

        // transfer row of distances from adjMat to sPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            cPath[j] = new DistPar(startTree, tempDist);
        }
        // until all vertices are in the tree
        while (nTree < nVerts) {
            int indexMin = getMin();                   // get minimum from sPath
            int minDist = cPath[indexMin].distancePr;
            if (minDist == INFINITY)                   // if all infinite
            {                                          // or in tree,
                System.out.println("There are unreachable vertices");
                break;                                 // sPath is complete
            } else {                                   // reset currentVert
                currentVert = indexMin;                // to closest vert
                startToCurrent = cPath[indexMin].distancePr;
                // minimum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // put current vertex in tree
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath_cheapest();                    // update sPath[] array
        }                                               // end while(nTree<nVerts)
        displayCheapestPaths();                                 // display sPath[] contents
        nTree = 0;                                      // clear tree
        for (int j = 0; j < nVerts; j++)
            vertexList[j].isInTree = false;
    }

    public int getMin() {                               // get entry from sPath with minimum distance
        int minDist = INFINITY;                         // assume minimum
        int indexMin = 0;

        for (int j = 1; j < nVerts; j++) {              // for each vertex, if it’s in tree and smaller than old one
            if (!vertexList[j].isInTree && cPath[j].distancePr < minDist) {
                minDist = cPath[j].distancePr;
                indexMin = j;                           // update minimum
            }
        }                                               // end for
        return indexMin;                                // return index of minimum
    }

    public void adjust_sPath_cheapest() {         // adjust values in shortest-path array sPath
        int column = 1;                           // skip starting vertex

        while (column < nVerts)                   // go across columns
        {
            // if this column’s vertex already in tree, skip it
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            // calculate distance for one sPath entry
            // get edge from currentVert to column
            int currentToFringe = adjMat[currentVert][column];

            // add distance from start
            int startToFringe = startToCurrent + currentToFringe;

            // get distance of current sPath entry
            int sPathDist = cPath[column].distancePr;

            // compare distance from start with sPath entry
            if (startToFringe < sPathDist)                         // if shorter,
            {
                // update sPath
                cPath[column].parentVert = currentVert;
                cPath[column].distancePr = startToFringe;
            }
            column++;
        }                                                    // end while(column < nVerts)
    }                                                        // end adjust_sPath()

    public void displayCheapestPaths() {
        for (int j = 0; j < nVerts; j++)                          // display contents of sPath[]
        {
            System.out.print(vertexList[j].stad + "=");           // B=
            if (cPath[j].distancePr == INFINITY)
                System.out.print("inf");                         // inf

            else
                System.out.print(cPath[j].distancePr);             // 50

            String parent = vertexList[cPath[j].parentVert].stad;
            System.out.println(" (" + parent + ") ");             // (A)

        }

        System.out.println("");
    }
}
