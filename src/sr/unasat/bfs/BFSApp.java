package sr.unasat.bfs;

class BFSApp {
    public static void main(String[] args) {
        Graph bfsGraph = new Graph();
        bfsGraph.addVertex("Paramaribo, ");       // 0
        bfsGraph.addVertex("Port Of Spain, ");    // 1
        bfsGraph.addVertex("Caracas, ");          // 2
        bfsGraph.addVertex("Oranjestad, ");       // 3
        bfsGraph.addVertex("Kingston, ");         // 4
        bfsGraph.addVertex("San Juan, ");         // 5
        bfsGraph.addVertex("Saint John's, ");     // 6
        bfsGraph.addVertex("Bridgetown, ");       // 7


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

        System.out.print("Visits: ");
        bfsGraph.bfs();
        System.out.println();
    } // end main()
}
