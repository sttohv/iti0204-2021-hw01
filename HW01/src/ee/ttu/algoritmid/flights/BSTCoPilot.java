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

    }


    public Node searchByPilot(Node root, double from, double to)  {
        if (root==null || root.crewMember.getWorkExperience() - from == 0)
            return root;
        if (root.crewMember.getWorkExperience() > to)
            return searchByPilot(root.left, from, to);
        else if(root.crewMember.getWorkExperience() < from)
            return searchByPilot(root.right, from, to);
        else {
            Node right = searchByPilot(root.right, from, to);
            if (right != null) return right;
            return root;
        }
    }

    public Node searchByFlightAttendant(Node root, double from)  {
        if (root==null || root.crewMember.getWorkExperience() - from == 0)
            return root;
        if (root.crewMember.getWorkExperience() < from)
            return searchByFlightAttendant(root.right, from);
        else {
            Node left = searchByFlightAttendant(root.left, from);
            if (left != null) return left;
            return root;
        }
    }
}
