/* NetId(s): djg17, ret87. Time spent: hh hours, mm minutes.

 * Name(s):
 * What I thought about this assignment:
 * 
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * An instance of DiseaseTree represents the spreading of a Disease among a Network of people.
 * In this model, each person can "catch" the disease from only a single person.
 * The root of the DiseaseTree is the person who first got the disease. From there,
 * each person in the DiseaseTree is the child of the person who gave them the disease.
 * For example, for the tree:
 * <p>
 *       A
 *      / \
 *     B   C
 *        / \
 *       D   E
 * <p>
 * Person A originally got the disease, B and C caught the disease from A,
 * and D and E caught the disease from C.
 * <p>
 * Important note: The name of each person in the disease tree is unique.
 *
 * @author Mshnik
 */
public class DiseaseTree {

    /** The String to be used as a separator in toString() */
    public static final String SEPARATOR = " - ";

    /**  The String that marks the start of children in toString() */
    public static final String START_CHILDREN_DELIMITER = "[";

    /** The String that divides children in toString()  */
    public static final String DELIMITER = ", ";

    /** The String that marks the end of children in toString() */
    public static final String END_CHILDREN_DELIMITER = "]";

    /** The String that is the space increment in toStringVerbose() */
    public static final String VERBOSE_SPACE_INCREMENT = "\t";

    /** The person at the root of this DiseaseTree.
     * This is the disease ancestor of everyone in this DiseaseTree: the
     * person who got sick first and indirectly caused everyone in it to get sick.
     * root is non-null.
     * All Person's in a DiseaseTree have different names. There are no duplicates
     */
    private Person root;

    /** The children of this DiseaseTree node.
     * Each element of children got the disease from the person at this node.
     * root is non-null but will be an empty set if this is a leaf.  */
    private Set<DiseaseTree> children;

    /** Constructor: a new DiseaseTree with root p and no children.
     * Throw an IllegalArgumentException if p is null. */
    public DiseaseTree(Person p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct DiseaseTree with null root");
        root= p;
        children= new HashSet<>();
    }

    /** Constructor: a new DiseaseTree that is a copy of tree p.
     * Tree p and its copy have no node in common (but nodes can share a Person).
     * Throw an IllegalArgumentException if p is null.  */
    public DiseaseTree(DiseaseTree p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct copy of null DiseaseTree");
        root= p.root;
        children= new HashSet<>();

        for (DiseaseTree dt : p.children) {
            children.add(new DiseaseTree(dt));
        }
    }

    /** Return the person that is at the root of this DiseaseTree. */
    public Person getRoot() {
        return root;
    }

    /** Return the number of direct children of this DiseaseTree. */
    public int numberOfChildren() {
        return children.size();
    }

    /** Return a COPY of the set of children of this DiseaseTree.  */
    public Set<DiseaseTree> copyOfChildren() {
        return new HashSet<>(children);
    }

    /** Return the DiseaseTree object in this tree whose root is  p
     * (null if p is not in this tree).
     * <p>
     * Example: Calling getTree(root) will return this. */
    public DiseaseTree getTree(Person p) {
        if (root == p) return this; //Base case - look here

        // Recursive case - ask children to look
        for (DiseaseTree dt : children) {
            DiseaseTree search= dt.getTree(p);
            if (search != null) return search;
        }

        return null; // p is not in the tree
    }

    /** Add c to this DiseaseTree as a child of p
     * and return the DiseaseTree whose root is the new child.
     * Throw an IllegalArgumentException if:<br>
     * -- p or c is null,<br>
     * -- c is already in this DiseaseTree, or<br>
     * -- p is not in this DiseaseTree
     * There is a simple, non-recursive implementation of this function */
    public DiseaseTree add(Person p, Person c) throws IllegalArgumentException {
        //TODO 1
        // Note: Do not test for p being in the DiseaseTree by calling contains
        // because that may end up doing extra work. This method requires you
        // to find the DiseaseTree whose root is p. Look in this class for an
        // already written method to do this. */
    	getTree(p).children.add(new DiseaseTree (c));
        
        return getTree(c);
    }

    /** Return the number of persons in this DiseaseTree.
     * Note: If this is a leaf, the size is 1 (just the root) */
    public int size() {
        //TODO 2
    	if(this.numberOfChildren()==0)
    		return 1;
    	else
    		return this.size_helper(1);
    }

    /** Recurse through this DiseaseTree to find the number of
     *  persons in this DiseaseTree. 
     */
    private int size_helper(int count)
    {
    	//check if we have reached a leaf
    	if(this.numberOfChildren()==0)
    		return count;
    	//otherwise recurse through all of the children and
    	//increment count for each child visited
    	else {
    		for (DiseaseTree dt : children) {
    			count=dt.size_helper(count)+1;
    		}
    	}
    	return count;
    }
    
    /*** Return the depth at which p occurs in this DiseaseTree, or -1
     * if p is not in the DiseaseTree.
     * Note: depthOf(root) is 0.
     * If p is a child of this DiseaseTree, then depth(p) is 1. etc. */
    public int depthOf(Person p) {
        //TODO 3
        // Note: Do NOT call function contains(p) to find out whether p is in
        // the tree. The recursive case consists of looking for the depth of p
        // in each child. If looking in one child finds the depth, just return
        // the answer, thus terminating execution of the method.
        // If checking each child recursively doesn't find that p is in the tree,
        // return -1 at the end of the method.
        if(p==root)
        	return 0;
        else
        {
        	//utilize helper function with initial search level set to 0
        	return this.depthOf_helper(0, p);
        }
    	
    }
    
    /** Recurses through the tree with a current level indicator (int level)
     *  Return level if Person p is found, otherwise return -1
     */
    
    private int depthOf_helper(int level,Person p)
    {	
    	int temp;
    	if(this.getRoot()==p)
    		return level;
    	
    	//recurse through children to find specified Person p
		for (DiseaseTree dt : children) {
			temp=dt.depthOf_helper(level+1, p);
			//temp will always be -1 unless Person p is found
			if(temp!=-1)
				return temp;
		}
    	
    	return -1;
    }

    /** Return true iff this DiseaseTree contains p. */
    public boolean contains(Person p) {
        /* Note: This DiseaseTree contains p iff the root of this DiseaseTree is
         * p or if one of p's children contains p. */
        if (root == p) return true;
        for (DiseaseTree dt : children) {
            if (dt.contains(p)) return true;
        }
        return false;
    }

    /** Return the maximum depth of this DiseaseTree, i.e. the longest path from
     * the root to a leaf. Example. If this DiseaseTree is a leaf, return 0. */
    public int maxDepth() {
        int maxDepth= 0;
        for (DiseaseTree dt : children) {
            maxDepth = Math.max(maxDepth, dt.maxDepth() + 1);
        }
        return maxDepth;
    }

    /** Return the width of this tree at depth d (i.e. the number of diseaseTrees
     * that occur at depth d, where the depth of the root is 0.
     * Throw an IllegalArgumentException if depth < 0.
     * Thus, for the following tree :
     * Depth level:
     * 0       A
     *        / \
     * 1     B   C
     *      /   / \
     * 2   D   E   F
     *          \
     * 3         G
     * <p>
     * A.widthAtDepth(0) = 1,  A.widthAtDepth(1) = 2,
     * A.widthAtDepth(2) = 3,  A.widthAtDepth(3) = 1,
     * A.widthAtDepth(4) = 0.
     * C.widthAtDepth(0) = 1,   C.widthAtDepth(1) = 2
     */
    public int widthAtDepth(int d) throws IllegalArgumentException {
        //TODO 4
        // Hint: Use this recursive definition. If d = 0, the answer is 1.
        // If d > 0, the answer is: sum of widths of the children at depth d-1.
    	Set<DiseaseTree> chillen= new HashSet<>();
    	
    	if(d==0)
    		return 1;
    	else
    		return this.findSetDepth(d, 0, chillen).size();
    }
    
    
    /** Returns a set of all children at specified depth d
     *  int n is the dynamic counter variable that gets incremented
     *  upon each recursive call. int d is the original specified depth 
     *  and does not change with a recursive call
     */
    private Set<DiseaseTree> findSetDepth(int d, int n, Set<DiseaseTree> chillen)
    {	
    	//stop searching if depth is reached
    	if(n>d-1)
    		return chillen;	
    	//add children of node at d-1 to chillen
    	else if(n==d-1){
    		for (DiseaseTree dt : children) {
    			chillen.add(new DiseaseTree(dt));
    		}
    	}
    	//recurse through to find proper depth
    	else if(n<d-1){
    		for (DiseaseTree dt : children) {
    			chillen=dt.findSetDepth(d, n+1, chillen);
    		}
    	}
    	return chillen;
    }

    /** Return the maximum width of all the widths in this tree, i.e. the
     * maximum value that could be returned from widthAtDepth for this tree. */
    public int maxWidth() {
        return maxWidthImplementationTwo(this);
    }

    /** Simple implementation of maxWith. Relies on widthAtDepth.
     * Takes time proportional to the square of the size of the t. */
    static int maxWidthImplementationOne(DiseaseTree t) {
        int width= 0;
        int depth= t.maxDepth();
        for (int i= 0; i <= depth; i++) {
            width= Math.max(width, t.widthAtDepth(i));
        }
        return width;
    }

    /** Better implementation of maxWidth. Caches results in an array.
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationTwo(DiseaseTree t) {
        // For each integer d, 0 <= d <= maximum depth of t, store in
        // widths[d] the number of nodes at depth d in t.
        // The calculation is done by calling recursive procedure addToWidths.
        int[] widths= new int[t.maxDepth() + 1];   // initially, contains 0's
        t.addToWidths(0, widths);

        int max= 0;
        for (int width : widths) {
            max= Math.max(max, width);
        }
        return max;
    }

    /** For each node of this DiseaseTree, which is at some depth d in this
     * DiseaseTree, add 1 to widths[depth + d]. */
    private void addToWidths(int depth, int[] widths) {
        widths[depth]++;        //the root of this DiseaseTree is at depth d = 0
        for (DiseaseTree dt : children) {
            dt.addToWidths(depth + 1, widths);
        }
    }

    /** Better implementation of maxWidth. Caches results in a HashMap.
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationThree(DiseaseTree t) {
        // For each possible depth d >= 0 in tree t, widthMap will contain the
        // entry (d, number of nodes at depth d in t). The calculation is
        // done using recursive procedure addToWidthMap.

        // For each integer d, 0 <= d <= maximum depth of t, add to
        // widthMap an entry <d, 0>.
        HashMap<Integer, Integer> widthMap= new HashMap<>();
        for (int d= 0; d <= t.maxDepth() + 1; d++) {
            widthMap.put(d, 0);
        }

        t.addToWidthMap(0, widthMap);

        int max= 0;
        for (Integer w : widthMap.values()) {
            max= Math.max(max, w);
        }
        return max;
    }

    /** For each node of this DiseaseTree, which is at some depth d in this DiseaseTree,
     * add 1 to the value part of entry <depth + d, ...> of widthMap. */
    private void addToWidthMap(int depth, HashMap<Integer, Integer> widthMap) {
        widthMap.put(depth, widthMap.get(depth) + 1);  //the root is at depth d = 0
        for (DiseaseTree dt : children) {
            dt.addToWidthMap(depth + 1, widthMap);
        }
    }

    /** Return the route the disease took to get from "here" (the root of this
     * DiseaseTree) to child c.
     * For example, for this tree:
     * <p>
     * Depth level:
     * 0        A
     *         / \
     * 1      B   C
     *       /   / \
     * 2    D   E   F
     *       \
     * 3      G
     * <p>
     * A.diseaseRouteTo(E) should return a list of [A,C,E].
     * A.diseaseRouteTo(A) should return [A].
     * A.diseaseRouteTo(X) should return null.
     * <p>
     * B.diseaseRouteTo(C) should return null.
     * B.diseaseRouteTo(D) should return [B,D] */
    public List<Person> diseaseRouteTo(Person c) {
        //TODO 5
        // Note: You have to return a List<Person>. But List is an
        // interface, so use something that implements it.
        // LinkedList<Person> is preferred to ArrayList<Person>, because
        // prepend (or its equivalent) may have to be used.
        // Base Case: The root of this DiseaseTree is c. Route is just [c].
    	
    	LinkedList<Person> ret= new LinkedList<Person>();
    	int depth= this.depthOf(c);
    	
    	if(depth==-1)
    		return null; //child not found in this subtree
    	
    	if(this.getRoot()==c){
    		ret.addLast(c);
    		return ret;
    	}
    	else{
    		ret=(LinkedList<Person>)this.diseaseRouteTo(this.getParent(c));
    		ret.addLast(c);
    	}

        return ret;
    }

    /** Return the immediate parent of c (null if c is not in this DiseaseTree).
     * <p>
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *     \
     * 3    G
     * <p>
     * A.getParent(E) returns C.
     * C.getParent(E) returns C.
     * A.getParent(B) returns A.
     * E.getParent(F) returns null.
     */
    public Person getParent(Person c) {
        // Base case
        for (DiseaseTree dt : children) {
            if (dt.root == c) return root;
        }

        // Recursive case - ask children to look
        for (DiseaseTree dt : children) {
            Person parent= dt.getParent(c);
            if (parent != null) return parent;
        }

        return null; //Not found
    }

    /** If either child1 or child2 is null or is not in this DiseaseTree, return null.
     * Otherwise, return the person at the root of the smallest subtree of this
     * DiseaseTree that contains child1 and child2.
     * <p>
     * Examples. For the following tree (which does not contain H):
     * <p>
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *     \
     * 3    G
     * <p>
     * A.sharedAncestorOf(B, A) is A
     * A.sharedAncestorOf(B, B) is B
     * A.sharedAncestorOf(B, C) is A
     * A.sharedAncestorOf(A, C) is A
     * A.sharedAncestorOf(E, F) is C
     * A.sharedAncestorOf(G, F) is A
     * B.sharedAncestorOf(B, E) is null
     * B.sharedAncestorOf(B, A) is null
     * B.sharedAncestorOf(D, F) is null
     * B.sharedAncestorOf(D, H) is null
     * A.sharedAncestorOf(null, C) is null
     */
    public Person sharedAncestorOf(Person child1, Person child2) {
        //TODO 6
        // HINT: if you are smart about this, you will realize that recursion
        // is not needed if you make use of an already written method
        
        return null;
    }

    /** Return a (single line) String representation of this DiseaseTree.
     * If this DiseaseTree has no children (it is a leaf), return the root's substring.
     * Otherwise, return
     * root's substring + SEPARATOR + START_CHILDREN_DELIMITER + each child's
     * toString, separated by DELIMITER, followed by END_CHILD_DELIMITER.
     * Make sure there is not an extra DELIMITER following the last child.
     * <p>
     * Finally, make sure to use the static final fields declared at the top of
     * DiseaseTree.java.
     * <p>
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1     B  C
     *      /  / \
     * 2   D  E   F
     *      \
     * 3     G
     * A.toString() should print:
     * (A) - HEALTHY - [(C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]], (B) - HEALTHY - [(D) - HEALTHY]]
     * <p>
     * C.toString() should print:
     * (C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]]
     */
    public String toString() {
        if (children.isEmpty()) return root.toString();
        String s= root.toString() + SEPARATOR + START_CHILDREN_DELIMITER;
        for (DiseaseTree dt : children) {
            s= s + dt.toString() + DELIMITER;
        }
        return s.substring(0, s.length() - 2) + END_CHILDREN_DELIMITER;
    }


    /** Return a verbose (multi-line) string representing this DiseaseTree. */
    public String toStringVerbose() {
        return toStringVerbose(0);
    }

    /** Return a verbose (multi-line) string representing this DiseaseTree.
     * Each person in the tree is on its own line, with indentation representing
     * what each person is a child of.
     * indent is the the amount of indentation to put before this line.
     * Should increase on recursive calls to children to create the above pattern.
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *     \
     * 3    G
     * <p>
     * A.toStringVerbose(0) should return:
     * (A) - HEALTHY
     * (C) - HEALTHY
     * (F) - HEALTHY
     * (E) - HEALTHY
     * (G) - HEALTHY
     * (B) - HEALTHY
     * (D) - HEALTHY
     * <p>
     * Make sure to use VERBOSE_SPACE_INCREMENT for indentation.
     */
    private String toStringVerbose(int indent) {
        String s= "";
        for (int i= 0; i < indent; i++) {
            s= s + VERBOSE_SPACE_INCREMENT;
        }
        s= s + root.toString();

        if (children.isEmpty()) return s;

        for (DiseaseTree dt : children) {
            s= s + "\n" + dt.toStringVerbose(indent + 1);
        }
        return s;
    }

    /** Return true iff this is equal to ob.
     * If ob is not a DiseaseTree, it cannot be equal to this DiseaseTree, return false.
     * Two DiseaseTrees are equal if they are the same object (==) or:
     * <br> - they have the same root Person object (==)
     * <br> - their children sets are equal, which requires:
     * <br> --- the two sets are of the same size
     * <br> --- for every DiseaseTree dt in one set, there is a DiseaseTree dt2
     * in the other set for which dt.equals(dt2) is true.
     * <p>
     * Otherwise the two DiseaseTrees are not equal.
     * Do not use any of the toString functions to write equals().
     * You can write a helper function --we found it useful-- but if you do,
     * put a precise specification on it.
     */
    public boolean equals(Object ob) {
        //TODO 7
        // Hint about checking whether each child of one tree equals SOME
        // other tree of the other tree's children.
        // First, you have to check them all until you find an equal one (or
        // return false if you don't.)
        // Second, you know that a child of one tree cannot equal more than one
        // child of another tree because the names of Person's are all unique;
        // there are no duplicates.
        
        return false;
    }

}
