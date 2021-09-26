package sr.unasat.bp2021;

class Graph {
    private final int MAX_VERTS = 20;
    private Vertex vertexList[];                  // list of vertices
    private int adjMat[][];                        // adjacency matrix
    private int nVerts;                            // current number of vertices
    private Queue theQueue;
    private Stack theStack;

    private final int INFINITY = 1000000;
    private int nTree;                    // current number of vertices
    private DistancePrice cPath[];        // array for shortest-path data
    private int currentVert;              // current vertex
    private int startToCurrent;           // distance to currentVert

    private final int INFINITE = 0;
    private DistancePrice ePath[];


    public Graph() {                                      // constructor
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        nTree = 0;

        for (int j = 0; j < MAX_VERTS; j++)                // set adjacency
            for (int k = 0; k < MAX_VERTS; k++)            // matrix to 0
                adjMat[j][k] = 0;

        for (int l = 0; l < MAX_VERTS; l++)                // set adjacency
            for (int m = 0; m< MAX_VERTS; m++)            // matrix to 0
        adjMat[l][m] = INFINITY ;

//        for (int n = 0; n < MAX_VERTS; n++)                // set adjacency
//            for (int p = 0; p< MAX_VERTS; p++)            // matrix to 0
//                adjMat[n][p] = INFINITE  ;

        theQueue = new Queue();
        theStack = new Stack();
        cPath = new DistancePrice[MAX_VERTS];
        ePath = new DistancePrice[MAX_VERTS];
    }

    public void addVertex(String city) {
        vertexList[nVerts++] = new Vertex(city);
    }

    public void addEdge(int start, int end) { //voegt edges toe en geeft aan waar het begint en waar het eindigt
        adjMat[start][end] = 1;
        // adjMat[end][start] = 1;
    }

    public void displayVertex(int v) {
        System.out.print(vertexList[v].stad + " - ");
    }

    public void bfs() {                               // Breadth first search
        vertexList[0].wasVisited = true;              // mark it
        displayVertex(0);                          // display it
        theQueue.insert(0);                      // insert at tail
        int v2;

        while (!theQueue.isEmpty()) {                  // until queue empty,
            int v1 = theQueue.remove();                // remove vertex at head
            // until it has no unvisited neighbors
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1) {
                vertexList[v2].wasVisited = true;           // mark it
                displayVertex(v2);                          // display it
                theQueue.insert(v2);                        // insert it
            }                                               // end while
        }                                                   // end while(queue not empty)
    }
    // returns an unvisited vertex adj to v
    public int getAdjUnvisitedVertex(int v) {   //word gebruik om neighbours op te zoeken
        for (int j = 0; j < nVerts; j++)
            if (adjMat[v][j] == 1 && !vertexList[j].wasVisited)
                return j;
        return -1;
    }
//---------------------------------------------------------------------------------------------------------------------

    public void dfs() {        // depth-first search
        vertexList[0].wasVisited = true;     // mark it
        displayVertex(0);                 // display it
        theStack.push(0);                 // push it
        while (!theStack.isEmpty()) {        // gaat loopen until stack empty,
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1)                    // if no such vertex,
                theStack.pop();
            else {
                // if it exists,
                vertexList[v].wasVisited = true;     // mark it
                displayVertex(v);                    // display it
                theStack.push(v);                    // push it
            }
        }
        // stack is empty, so we’re done
        for (int j = 0; j < nVerts; j++)                  // reset flags
            vertexList[j].wasVisited = false;
    }

//----------------------------------------------------------------------------------------------------------

    public void addEdge(int start, int end, int weight) {
        adjMat[start][end] = weight;
    }
    public int findIndexOfStart(String startPoint) { //method om de startingpoint aan tegeven dmv een String
        int city;
        for (city = 0; city < nVerts; city++) {
            if (vertexList[city].stad.equalsIgnoreCase(startPoint))
                return city;
        }
        return city;
    }

    public void findCheapestPaths(int start) {     // zoekt nr de cheapest paths
        int startTree = start;
        // start at vertex 0
        vertexList[startTree].isInTree = true;
        nTree = 1;                                 // put it in tree

        // copy de distancePrice van adjM naar cPath
        for (int j = 0; j < nVerts; j++) {
            int tempDistPrice = adjMat[startTree][j];
            cPath[j] = new DistancePrice(startTree, tempDistPrice); //opslaan van je temp weight
        }
        while (nTree < nVerts) {
            int indexMin = getMin();                   // je haalt de minimum van cPath
            int minDist = cPath[indexMin].distancePrice; //van de minimum kijken we dan wat de distPrice is

            if (minDist == INFINITY) {                   // als distance inf is, dan zijn we klaar
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

    public int getMin() {                               // get city from cPath with minimum distance
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

        while (column < nVerts) {                   // je gaat door alle verts
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }//als het niet in die tree is
            // calculate distance for one cPath entry
            //neem edge van currentV naar die column
            int currentToFringe = adjMat[currentVert][column];

            // De twee weights pakken en optellen
            int startToFringe = startToCurrent + currentToFringe;

            // get distancePrice of current sPath entry
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
        for (int j = 0; j < nVerts; j++) {                         // display contents of cPath[]

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


    //--------------------------------------------------------------------------------------------------------------}
//    public void findExpensivePaths(int start) {     // find all expensive paths
//        int startTree = start;
//        vertexList[startTree].isInTree = true;
//        nTree = 1;                                 // put it in tree
//
//        // transfer row of distancePrice from adjMat to ePath
//        for (int j = 0; j < nVerts; j++) {
//            int tempDist = adjMat[startTree][j];
//            ePath[j] = new DistancePrice(startTree, tempDist); //opslaan van je temp weight
//        }
//        // until all vertices are in the tree
//        while (nTree < nVerts) {
//            int indexMax = getMax();                    // get maximum from ePath
//            int maxDist = ePath[indexMax].distancePrice;
//
//            if (maxDist == INFINITE) {                   // if all infinite
//                // or in tree,
//                System.out.println("(There are unreachable vertices!)");
//                break;                                  // ePath is complete
//            } else {                                    // reset currentVert
//                currentVert = indexMax;                 // to the closest vert
//                startToCurrent = ePath[indexMax].distancePrice;
//                // maximum distance from startTree is
//                // to currentVert, and is startToCurrent
//            }
//            // put current vertex in tree
//            vertexList[currentVert].isInTree = true;
//            nTree++;
//            adjust_ePath();                 // update ePath[] array
//        }                                             // end while(nTree<nVerts)
//
//        displayExpensivePaths();
//        nTree = 0;                                    // clear tree
//        for (int j = 0; j < nVerts; j++)
//            vertexList[j].isInTree = false;
//    }
//
//    //get max path in lijst
//    public int getMax() {                           // get entry from ePath with maximum distance
//        int maxDist = INFINITE;                      // assume maximum
//        int indexMax = 0;
//
//        for (int j = 1; j < nVerts; j++) {                // voor elke vertex, als het niet in de tree is en groter dan de vorige
//            if (!vertexList[j].isInTree && ePath[j].distancePrice > maxDist) {
//                maxDist = ePath[j].distancePrice;
//                indexMax = j;                        // update maximum
//            }
//        }                                            // end for
//        return indexMax;                             // return index of maximum
//    }
//
//    //for longest
//    public void adjust_ePath() {            // adjust values in expensive-path array ePath
//        int column = 1;                               // skip starting vertex
//        while (column < nVerts)                        // go across columns
//        {
//            // if this column vertex already in tree, skip it
//            if (vertexList[column].isInTree) {
//                column++;
//                continue;
//            }
//            // calculate distance for one ePath entry
//            // get edge from currentVert to column
//            int currentToFringe = adjMat[currentVert][column];
//
//            // add distance from start
//            int startToFringe = startToCurrent + currentToFringe;
//
//            // get distance of current ePath entry
//            int ePathDist = ePath[column].distancePrice;
//
//            // compare distance from start with ePath entry
//            if (startToFringe > ePathDist && currentToFringe != 0) {    // if longer, update the longest Path
//                ePath[column].parentVert = currentVert;
//                ePath[column].distancePrice = startToFringe;
//            }
//            column++;
//        }
//    }
//
//    public void displayExpensivePaths() {
//        for (int j = 0; j < nVerts; j++)                          // display contents of ePath[]
//        {
//            System.out.print(vertexList[j].stad + "= ");          // Paramaribo =
//            if (ePath[j].distancePrice == INFINITE)
//                System.out.print("not reachable");                         // inf
//
//            else
//                System.out.print(ePath[j].distancePrice + "$");          // 500
//
//            String parent = vertexList[ePath[j].parentVert].stad;
//
//            System.out.println(" (" + "through" + " " + parent + ") ");         // (Paramaribo)
//
//        }
//        System.out.println("");
//    }
}
