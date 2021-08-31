package sr.unasat.shortestpath;

public class Vertex {
    public String stad;          // label (e.g. ‘A’)
    public boolean isInTree;

    public Vertex(String city) {         // constructor
        stad = city;
        isInTree = false;
    }

} // end class Vertex
