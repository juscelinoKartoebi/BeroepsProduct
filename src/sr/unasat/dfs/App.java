package sr.unasat.dfs;

class App
{
    public static void main(String[] args)
    {
        Graph theGraph = new Graph();
        theGraph.addVertex("Paramaribo, ");       // 0
        theGraph.addVertex("Port Of Spain, ");    // 1
        theGraph.addVertex("Caracas, ");          // 2
        theGraph.addVertex("Oranjestad, ");       // 3
        theGraph.addVertex("Kingston, ");         // 4
        theGraph.addVertex("San Juan, ");         // 5
        theGraph.addVertex("Saint John's, ");     // 6
        theGraph.addVertex("Bridgetown, ");       // 7


        theGraph.addEdge(0,1);
        theGraph.addEdge(0,7);
        theGraph.addEdge(1,2);
        theGraph.addEdge(1,7);
        theGraph.addEdge(2,3);
        theGraph.addEdge(3,4);
        theGraph.addEdge(3,5);
        theGraph.addEdge(5,4);


        theGraph.addEdge(7,2);
        theGraph.addEdge(7,3);
        theGraph.addEdge(7,6);
        theGraph.addEdge(6,5);

        System.out.print("De bestaande bestemmingen zijn: ");
        theGraph.dfs();
        System.out.println();
    }
}


