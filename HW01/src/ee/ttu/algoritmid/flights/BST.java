package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;

class BST {
    //node class that defines BST node
    class Node {
        FlightCrewMember crewMember;
        Node left, right;

        public Node(FlightCrewMember crewMember){
            this.crewMember = crewMember;
            left = right = null;
        }
    }
    // BST root node
    Node root;
    List<FlightCrewMember> waitingList = new ArrayList<>();

    // Constructor for BST =>initial empty tree
    BST(){
        root = null;
    }
    //delete a node from BST
    void delete(FlightCrewMember crewMember) {
        root = delete_Recursive(root, crewMember);
    }

    //recursive delete function
    Node delete_Recursive(Node root, FlightCrewMember key)  {
        //tree is empty
        if (root == null)  return root;

        //traverse the tree
        if (key.getWorkExperience() < root.crewMember.getWorkExperience())     //traverse left subtree
            root.left = delete_Recursive(root.left, key);
        else if (key.getWorkExperience() > root.crewMember.getWorkExperience())  //traverse right subtree
            root.right = delete_Recursive(root.right, key);
        else if (root.crewMember != key){
            FlightCrewMember.Role rootRole = root.crewMember.getRole();
            if (rootRole.equals(key.getRole())) {
                root.right = delete_Recursive(root.right, key);
            } else if (rootRole.equals(FlightCrewMember.Role.PILOT)) {
                root.left = delete_Recursive(root.left, key);
            } else if (rootRole.equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {
                root.right = delete_Recursive(root.right, key);
            } else if (rootRole.equals(FlightCrewMember.Role.COPILOT)) {
                if (key.getRole().equals(FlightCrewMember.Role.PILOT)) {
                    root.right = delete_Recursive(root.right, key);
                } else {
                    root.left = delete_Recursive(root.left, key);
                }
            }
        }
        else{
            // node contains only one child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // node has two children;
            //get inorder successor (min value in the right subtree)
            root.crewMember = minValue(root.right);

            // Delete the inorder successor
            root.right = delete_Recursive(root.right, root.crewMember);
        }
        return root;
    }

    FlightCrewMember minValue(Node root)  {
        //initially minval = root
        FlightCrewMember minval = root.crewMember;
        //find minval
        while (root.left != null)  {
            minval = root.left.crewMember;
            root = root.left;
        }
        return minval;
    }

    // insert a node in BST
    void add(FlightCrewMember key)  {
        root = insert_Recursive(root, key);
    }

    //recursive insert function
    Node insert_Recursive(Node root, FlightCrewMember key) {
        //tree is empty
        if (root == null) {
            root = new Node(key);
            return root;
        }
        //traverse the tree
        if (key.getWorkExperience() < root.crewMember.getWorkExperience())     //insert in the left subtree
            root.left = insert_Recursive(root.left, key);
        else if (key.getWorkExperience() > root.crewMember.getWorkExperience())    //insert in the right subtree
            root.right = insert_Recursive(root.right, key);
        // return pointer
        else {
            FlightCrewMember.Role rootRole = root.crewMember.getRole();
            if (rootRole.equals(key.getRole())) {
                root.right = insert_Recursive(root.right, key);
            } else if (rootRole.equals(FlightCrewMember.Role.PILOT)) {
                root.left = insert_Recursive(root.left, key);
            } else if (rootRole.equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {
                root.right = insert_Recursive(root.right, key);
            } else if (rootRole.equals(FlightCrewMember.Role.COPILOT)) {
                if (key.getRole().equals(FlightCrewMember.Role.PILOT)) {
                    root.right = insert_Recursive(root.right, key);
                } else {
                    root.left = insert_Recursive(root.left, key);
                }
            }
        }
        return root;
    }

    // method for inorder traversal of BST
    void inorder() {
        inorder_Recursive(root);
    }

    // recursively traverse the BST
    void inorder_Recursive(Node root) {
        if (root != null) {
            inorder_Recursive(root.left);
            waitingList.add(root.crewMember);
            System.out.print(root.crewMember.getRole() + "=" + root.crewMember.getWorkExperience() + " ");
            inorder_Recursive(root.right);
        }
    }

    boolean search(int key)  {
        root = search_Recursive(root, key);
        if (root!= null)
            return true;
        else
            return false;
    }

    //recursive insert function
    Node search_Recursive(Node root, int key)  {
        // Base Cases: root is null or key is present at root
        if (root==null || root.crewMember.getWorkExperience() ==key)
            return root;
        // val is greater than root's key
        if (root.crewMember.getWorkExperience() > key)
            return search_Recursive(root.left, key);
        // val is less than root's key
        return search_Recursive(root.right, key);
    }
}
class Main{
    public static void main(String[] args)  {
        //create a BST object
        BST bst = new BST();
        /* BST tree example
              45
           /     \
          10      90
         /  \    /
        7   12  50   */
        //insert data into BST
        FlightCrewMember member1 = new SomeTests.CrewMemberTemp("copilot", FlightCrewMember.Role.COPILOT, 45);
        FlightCrewMember member2 = new SomeTests.CrewMemberTemp("pilot", FlightCrewMember.Role.PILOT, 10);
        FlightCrewMember member3 = new SomeTests.CrewMemberTemp("pilot2", FlightCrewMember.Role.PILOT, 7);
        FlightCrewMember member4 = new SomeTests.CrewMemberTemp("assistant", FlightCrewMember.Role.FLIGHT_ATTENDANT, 12);
        FlightCrewMember member5 = new SomeTests.CrewMemberTemp("copilot", FlightCrewMember.Role.COPILOT, 90);
        FlightCrewMember member6 = new SomeTests.CrewMemberTemp("assistant", FlightCrewMember.Role.FLIGHT_ATTENDANT, 50);
        bst.add(member1);
        bst.add(member2);
        bst.add(member3);
        bst.add(member4);
        bst.add(member5);
        bst.add(member6);
        //print the BST
        System.out.println("The BST Created with input data(Left-root-right):");
        bst.inorder();

        //delete leaf node
        System.out.println("\nThe BST after Delete 12(leaf node):");
        bst.delete(member4);
        bst.inorder();
        //delete the node with one child
        System.out.println("\nThe BST after Delete 90 (node with 1 child):");
        bst.delete(member5);
        bst.inorder();

        FlightCrewMember member7 = new SomeTests.CrewMemberTemp("pilot2", FlightCrewMember.Role.FLIGHT_ATTENDANT, 45);
        System.out.println("\ninsert");
        bst.add(member7);
        bst.inorder();
        System.out.println("\nDELETE");
        bst.delete(member7);
        bst.inorder();

        //delete node with two children
        System.out.println("\nThe BST after Delete 45 (Node with two children):");
        bst.delete(member1);
        bst.inorder();
        //search a key in the BST
        boolean ret_val = bst.search (50);
        System.out.println("\nKey 50 found in BST:" + ret_val );
        ret_val = bst.search (12);
        System.out.println("\nKey 12 found in BST:" + ret_val );
    }
}