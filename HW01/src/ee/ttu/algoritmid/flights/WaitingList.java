package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;

public class WaitingList {

    private List<FlightCrewMember> waitingList = new ArrayList<>();
    static class Node {
        FlightCrewMember crewMember;
        Node left, right;

        Node(FlightCrewMember crewMember) {
            this.crewMember = crewMember;
            left = null;
            right = null;
        }
    }

    public void add(Node node, FlightCrewMember crewMember) {
        if (crewMember.getWorkExperience() < node.crewMember.getWorkExperience()) {
            doSomethingLeft(node, crewMember);
        } else if (crewMember.getWorkExperience() > node.crewMember.getWorkExperience()) {
            doSomethingRight(node, crewMember);
        } else {
            if (node.crewMember.getRole().equals(FlightCrewMember.Role.PILOT)) {
                doSomethingLeft(node, crewMember);
            } else if (node.crewMember.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {
                doSomethingRight(node, crewMember);
            } else if (node.crewMember.getRole().equals(FlightCrewMember.Role.COPILOT)) {
                if (crewMember.getRole().equals(FlightCrewMember.Role.PILOT)) {
                    doSomethingRight(node, crewMember);
                } else {
                    doSomethingLeft(node, crewMember);
                }
            }
        }
    }

    public List<FlightCrewMember> traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            waitingList.add(node.crewMember);
            System.out.print(" " + node.crewMember.getName());
            traverseInOrder(node.right);
        }
        return waitingList;
    }

    public void doSomethingRight(Node node, FlightCrewMember crewMember) {
        if (node.right != null) {
            add(node.right, crewMember);
        } else {
            System.out.println("  Inserted " + crewMember.getName() + " to right of "
                    + node.crewMember.getName());
            node.right = new Node(crewMember);
        }
    }

    public void doSomethingLeft(Node node, FlightCrewMember crewMember) {
        if (node.left != null) {
            add(node.left, crewMember);
        } else {
            System.out.println(" Inserted " + crewMember.getName() + " to left of " + node.crewMember.getName());
            node.left = new Node(crewMember);
        }
    }

//    public static void main(String args[])
//    {
//        WaitingList waitingList = new WaitingList();
//        Node root = new Node();
//        System.out.println("Binary Tree Example");
//        System.out.println("Building tree with root value " + root.value);
//        waitingList.insert(root, 2);
//        waitingList.insert(root, 4);
//        waitingList.insert(root, 8);
//        waitingList.insert(root, 6);
//        waitingList.insert(root, 7);
//        waitingList.insert(root, 3);
//        waitingList.insert(root, 9);
//        System.out.println("Traversing tree in order");
//        waitingList.traverseLevelOrder();
//
//    }

}
