package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;

public class BSTCoPilot extends BinarySearch{
    // BST root node

    private List<FlightCrewMember> suitableCoPilots;

    // Constructor for BST =>initial empty tree
    public BSTCoPilot() {
        super();
        List<FlightCrewMember> suitableCoPilots = new ArrayList<>();
    }



    public void add(FlightCrewMember key) {
        if (key.getRole().equals(FlightCrewMember.Role.COPILOT)) {
            root = add_Recursive(root, key);
        }

    }



    public FlightCrewMember search(FlightCrewMember crewMember) {
        double from = crewMember.getWorkExperience() - 10;
        double to = crewMember.getWorkExperience() - 5;

        double from2 = crewMember.getWorkExperience() + 3;

        if (crewMember.getRole().equals(FlightCrewMember.Role.PILOT)) {
            Node root1 = searchByPilot(root, from, to);
            if (root1!= null)
                return root1.crewMember;
            else
                return null;
        } else {
            Node root2 = searchByFlightAttendant(root, from2);
            if (root2!= null)
                return root2.crewMember;
            else
                return null;
        }
//        if (root!= null)
//            return root.crewMember;
//        else
//            return null;
    }


    public Node searchByPilot(Node root, double from, double to)  {
        // rn tagastab esimese, kes sobib
        if (root==null || root.crewMember.getRole().equals(FlightCrewMember.Role.COPILOT)
                && root.crewMember.getWorkExperience() >= from && root.crewMember.getWorkExperience() <= to)
            //suitablePilots.add(root.crewMember);
            return root;
        if (root.crewMember.getWorkExperience() > to)
            return searchByPilot(root.left, from, to);
        return searchByPilot(root.right, from, to);
    }

    public Node searchByFlightAttendant(Node root, double from)  {
        // rn tagastab esimese, kes sobib
        if (root==null || root.crewMember.getRole().equals(FlightCrewMember.Role.COPILOT)
                && root.crewMember.getWorkExperience() >= from)
            //suitablePilots.add(root.crewMember);
            return root;
        if (root.crewMember.getWorkExperience() > from)
            return searchByFlightAttendant(root.left, from);
        return searchByFlightAttendant(root.right, from);
    }
}
