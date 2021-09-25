package sr.unasat.bp2021;
import java.util.Scanner;

public class ApplicationStarter {
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

//      (Dijkstra)
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

//       (DFS & BFS)
//        theGraph.addEdge(0, 1);
//        theGraph.addEdge(0, 7);
//        theGraph.addEdge(1, 2);
//        theGraph.addEdge(1, 7);
//        theGraph.addEdge(2, 3);
//        theGraph.addEdge(3, 4);
//        theGraph.addEdge(3, 5);
//        theGraph.addEdge(5, 4);
//        theGraph.addEdge(4, 4);
//        theGraph.addEdge(7, 2);
//        theGraph.addEdge(7, 3);
//        theGraph.addEdge(7, 6);
//        theGraph.addEdge(6, 5);

//      (Expensive Route)
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter current location");
//        String item = scan.next();
//        System.out.println("Price of the most expensive routes are:");
//
//        theGraph.findIndexOfStart(item);
//        int i = theGraph.findIndexOfStart(item);
//        theGraph.findExpensivePaths(i);
//
//        System.out.println();
//        scan.close();

//       (Cheapest Route)
                Scanner scan = new Scanner(System.in);
        System.out.println("Enter current location");
        String city = scan.next();

        System.out.println("The cheapest routes are: ");
        theGraph.findIndexOfStart(city);
        int i = theGraph.findIndexOfStart(city);
        theGraph.findCheapestPaths(i);

        System.out.println("");
        scan.close();

//        (BFS)
//                System.out.println("Cities that are adjacent to each others are: ");
//        theGraph.bfs();
//        System.out.println();

//        (DFS)
//                System.out.println("The following destinations that exist are: ");
//        theGraph.dfs();
//        System.out.println();
    }
}
