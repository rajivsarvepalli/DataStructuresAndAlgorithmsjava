package Testing;
import java.util.*;

import trees.*;

public class TestingTreesPackage {
	//avl and binary should be working
	//redblack deletion not working for 60, 40, 50, 30, 20, 70, 80,35 delelte 30
	public static void main(String[] args) {
		int numElements = 10;
		RedBlackTree<Integer, Integer> treeR = new RedBlackTree<Integer,Integer>();
		AvlTree<Integer, Integer> treeA = new AvlTree<Integer,Integer>();
		BinarySearchTree<Integer, Integer> treeB = new BinarySearchTree<Integer,Integer>();
		PrettyTreePrint<Integer, Integer, Tree<Integer,Integer>> printOut = new PrettyTreePrint<Integer, Integer, Tree<Integer,Integer>>();

		ArrayList<Integer> toAdd = new ArrayList<Integer>();
		for(int i=0; i<numElements; i++) {
			toAdd.add(i);
		}
		treeR.insert(toAdd, toAdd);
		treeA.insert(toAdd, toAdd);
		treeB.insert(toAdd, toAdd);
		printOut.printTree(treeR, false);
		printOut.printTree(treeA, false);
		//printOut.printTree(treeB, false);
		System.out.println(treeR.height());
		System.out.println(treeA.height());
		System.out.println(treeB.height());

	}
	

}
