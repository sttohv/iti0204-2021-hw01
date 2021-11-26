package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;

public class BSTPilot extends BinarySearch{

    // BST root node

    private List<FlightCrewMember> suitablePilots;

    // Constructor for BST =>initial empty tree
    public BSTPilot() {
        super();
        List<FlightCrewMember> suitablePilots = new ArrayList<>();
    }


    public void add(FlightCrewMember key) {
        if (key.getRole().equals(FlightCrewMember.Role.PILOT)) {
            root = add_Recursive(root, key);
        }

    }



    public FlightCrewMember search(FlightCrewMember coPilot) {
        double from = coPilot.getWorkExperience() + 5;
        double to = coPilot.getWorkExperience() + 10;

        Node root1 = search_Recursive(root, from, to);
        if (root1!= null)
            return root1.crewMember;
        else
            return null;
    }


    public Node search_Recursive(Node root, double from, double to)  {
        if (root==null || root.crewMember.getWorkExperience() - from == 0)
            return root;
        if (root.crewMember.getWorkExperience() > to)
            return search_Recursive(root.left, from, to);
        else if (root.crewMember.getWorkExperience() < from)
            return search_Recursive(root.right, from, to);
        else {
            Node left = search_Recursive(root.left, from, to);
            if (left != null) return left;
            return root;
        }
    }
}
