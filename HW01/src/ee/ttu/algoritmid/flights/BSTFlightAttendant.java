package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;

public class BSTFlightAttendant extends BinarySearch{
    // BST root node

    private List<FlightCrewMember> suitableFlightAttendants;

    BSTFlightAttendant(){
        super();
        List<FlightCrewMember> suitableFlightAttendants = new ArrayList<>();


    }

    public void add(FlightCrewMember key) {
        if (key.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {
            root = add_Recursive(root, key);
        }

    }

    public FlightCrewMember search(FlightCrewMember coPilot) {
        double to = coPilot.getWorkExperience() - 3;

        Node root1 = search_Recursive(root, to);
        if (root1!= null)
            return root1.crewMember;
        else
            return null;
    }

    public Node search_Recursive(Node root, double to) {
        // rn tagastab esimese, kes sobib
        if (root==null || root.crewMember.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)
                && root.crewMember.getWorkExperience() <= to)
            //suitablePilots.add(root.crewMember);
            return root;

        if (root.crewMember.getWorkExperience() > to)
            return search_Recursive(root.left, to);

        return search_Recursive(root.right, to);
    }
}
