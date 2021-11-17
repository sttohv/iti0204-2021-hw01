package ee.ttu.algoritmid.flights;

public class Node {
    //node class that defines BST node

    FlightCrewMember crewMember;
    Node left, right;

    public Node(FlightCrewMember crewMember){
        this.crewMember = crewMember;
        left = right = null;
    }

    public FlightCrewMember getCrewMember() {
        return crewMember;
    }

}
