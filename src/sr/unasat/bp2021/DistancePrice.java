package sr.unasat.bp2021;

public class DistancePrice {  // distanceprice and parent, items stored in Path array
    public int distancePrice;            // price of  distance from start to this vertex
    public int parentVert;               // current parent of this vertex

    public DistancePrice(int parentV, int distPrice) {
        distancePrice = distPrice;
        parentVert = parentV;
    }
}

