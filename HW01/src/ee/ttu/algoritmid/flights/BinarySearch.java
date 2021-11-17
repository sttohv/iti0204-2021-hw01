package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BinarySearch {
    public Node root;
    public List<Node> waitingList;


    public BinarySearch() {
        root = null;
        waitingList = new ArrayList<>();
        List<FlightCrewMember> suitablePilots = new ArrayList<>();
    }


    public List<Node> getWaitingList() {
        inorder();
        return waitingList;
    }
    public void remove(FlightCrewMember crewMember) {
        root = remove_Recursive(root, crewMember);

    }

    public Node remove_Recursive(Node root, FlightCrewMember key) {
        if (root == null)  return root;

        //traverse the tree
        if (key.getWorkExperience() < root.crewMember.getWorkExperience())     //traverse left subtree
            root.left = remove_Recursive(root.left, key);
        else if (key.getWorkExperience() > root.crewMember.getWorkExperience())  //traverse right subtree
            root.right = remove_Recursive(root.right, key);
        else  {
            // node contains only one child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // node has two children;
            //get inorder successor (min value in the right subtree)
            root.crewMember = minValue(root.right);

            // Delete the inorder successor
            root.right = remove_Recursive(root.right, root.crewMember);
        }
        return root;
    }

    public FlightCrewMember minValue(Node root) {
        //initially minval = root
        FlightCrewMember minval = root.crewMember;
        //find minval
        while (root.left != null)  {
            minval = root.left.crewMember;
            root = root.left;
        }
        return minval;
    }

//    public void add(FlightCrewMember key) {
//        root = add_Recursive(root, key);
//
//    }
//
    public Node add_Recursive(Node root, FlightCrewMember key) {
        //tree is empty
        if (root == null) {
            root = new Node(key);
            return root;
        }
        //traverse the tree
        if (key.getWorkExperience() < root.crewMember.getWorkExperience())     //insert in the left subtree
            root.left = add_Recursive(root.left, key);
        else if (key.getWorkExperience() > root.crewMember.getWorkExperience())    //insert in the right subtree
            root.right = add_Recursive(root.right, key);
        // return pointer
        return root;
    }

    public void inorder(){
        if(!waitingList.isEmpty()){
            waitingList = new ArrayList<>();
        }
        inorder_Recursive(root);
    }

    public void inorder_Recursive(Node root) {
        if (root != null) {
            inorder_Recursive(root.left);
            waitingList.add(root);
            //System.out.print(root.crewMember.getRole() + "=" + root.crewMember.getWorkExperience() + " ");
            inorder_Recursive(root.right);
        }
    }


}
