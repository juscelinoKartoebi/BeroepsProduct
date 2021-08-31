package sr.unasat.longestpath;


import sr.unasat.shortestpath.Vertex;

public class Max {
    // distance and parent
        // items stored in sPath array
        public int distancePrice;            // price of distance from start to this vertex
        public int parentVert;          // current parent of this vertex

        public Max(int parentV, int distPrice) {
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
        private Max expPath[];
        private int currentVert;
        private int startToCurrent;

        public Graph() {                           // constructor
            vertexList = new Vertex[MAX_VERTS];    // adjacency matrix
            adjMat = new int[MAX_VERTS][MAX_VERTS];
            nVerts = 0;
            nTree = 0;

            for (int j = 0; j < MAX_VERTS; j++)          // set adjacency
                for (int k = 0; k < MAX_VERTS; k++)      // matrix
                    adjMat[j][k] = INFINITE ;            // to infinity
            expPath = new Max[MAX_VERTS];             // shortest paths

        }
        public void addVertex(String city) {
            vertexList[nVerts++] = new Vertex(city);
        }

        public void addEdge(int start, int end, int weight) {
            adjMat[start][end] = weight;     // (directed)
        }

        public void longestPath(int userStartTree){     // find all longest paths
            int startTree=userStartTree;
            vertexList[startTree].isInTree = true;
            nTree = 1;                                 // put it in tree


            // transfer row of distances from adjMat to sPath
            for(int j=0; j<nVerts; j++) {
                int tempDist = adjMat[startTree][j];
                expPath[j] = new Max(startTree, tempDist);
            }
            // until all vertices are in the tree
            while(nTree < nVerts) {
                int indexMax = getMax();                  // get minimum from sPath
                int maxDist = expPath[indexMax].distancePrice;
                if(maxDist == INFINITE)                   // if all infinite
                {                                         // or in tree,
                    System.out.println("There are unreachable vertices");
                    break;                                // sPath is complete
                }
                else

                {                                            // reset currentVert
                    currentVert = indexMax;                  // to the closest vert
                    startToCurrent = expPath[indexMax].distancePrice;
                    // minimum distance from startTree is
                    // to currentVert, and is startToCurrent
                }
                // put current vertex in tree
                vertexList[currentVert].isInTree = true;
                nTree++;
                adjust_sPath_longest();                 // update sPath[] array
            }                                           // end while(nTree<nVerts)

            displayLongestPaths();
            nTree = 0;                                  // clear tree
            for (int j = 0; j < nVerts; j++)
                vertexList[j].isInTree = false;
        }

        //get max path in lijst
        public int getMax() {                           // get entry from sPath with minimum distance
            int maxDist= INFINITE;                      // assume minimum
            int indexMax = 0;

            for(int j=1; j<nVerts; j++){                // for each vertex, if its in tree and smaller than old one
                if( !vertexList[j].isInTree && expPath[j].distancePrice > maxDist ){
                    maxDist = expPath[j].distancePrice;
                    indexMax = j;                        // update minimum
                }
            }                                            // end for
            return indexMax;                             // return index of minimum
        }

        //for longest
        public void adjust_sPath_longest() {              // adjust values in shortest-path array sPath
            int column = 1;                               // skip starting vertex
            while(column < nVerts)                        // go across columns
            {
                // if this column vertex already in tree, skip it
                if( vertexList[column].isInTree )
                {
                    column++;
                    continue;
                }
                // calculate distance for one sPath entry
                // get edge from currentVert to column
                int currentToFringe = adjMat[currentVert][column];

                // add distance from start
                int startToFringe = startToCurrent + currentToFringe;

                // get distance of current sPath entry
                int sPathDist = expPath[column].distancePrice;

                // compare distance from start with sPath entry
                if(startToFringe > sPathDist && currentToFringe != 0)    // if longer
                {
                    // update the longest Path
                    expPath[column].parentVert = currentVert;
                    expPath[column].distancePrice = startToFringe;
                }
                column++;
            }
        }
        public void displayLongestPaths() {
            for (int j = 0; j < nVerts; j++)                          // display contents of sPath[]
            {
                System.out.print(vertexList[j].stad + "= ");           // B=
                if (expPath[j].distancePrice == INFINITE)
                    System.out.print("inf");                         // inf

                else
                    System.out.print(expPath[j].distancePrice);             // 50

                String parent = vertexList[expPath[j].parentVert].stad;
//                String way = vertexList[lPath[j].distance].stad;

                System.out.println(" (" + parent + ") ");             // (A)
            }
            System.out.println("");
        }
    }

