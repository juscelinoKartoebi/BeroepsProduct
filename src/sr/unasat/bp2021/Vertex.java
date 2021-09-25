package sr.unasat.bp2021;

public class Vertex {
        public String stad;          // stad (e.g. Paramaribo)
        public boolean isInTree;
        public boolean wasVisited;

        public Vertex(String city) { // constructor
            stad = city;
            isInTree = false;
            wasVisited = false;
        }
    }


