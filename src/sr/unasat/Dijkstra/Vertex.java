package sr.unasat.Dijkstra;

 class Vertex {
        public String stad;          // label (e.g. ‘A’)
        public boolean isInTree;

        public Vertex(String city) {         // constructor
            stad = city;
            isInTree = false;
        }
    }


