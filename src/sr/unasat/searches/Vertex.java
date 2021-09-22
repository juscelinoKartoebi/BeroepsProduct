package sr.unasat.searches;

class Vertex {
    public String stad;                      // stad (e.g. "Paramaribo")
    public boolean wasVisited;

    public Vertex(String city) {             // constructor
        stad = city;
        wasVisited = false;
    }
}
