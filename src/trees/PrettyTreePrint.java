package trees;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/**
 * credit to michal.kreuzman from https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 * base of this printer for BST is from him
 * Main use is for testing 
 *
 * @param <Key>
 * @param <Value>
 * @param <BTree>
 */
//printing horizontal and retuning as String not supported yet

public class PrettyTreePrint<Key extends Comparable<Key>, Value, BTree  extends Tree<Key, Value>>{
	public String stringTree(BTree tree, boolean horizontal){
		return stringTree("",tree.getRoot());
	}
	private  String stringTree(String s, TreeNode<Key, Value> curNode){
		return "";
	}
	public void printTree(BTree tree, boolean horizontal){
		if(!horizontal)
			this.printNode(tree.getRoot());
	}
	public void printNode(TreeNode<Key, Value> root) {
        int maxLevel = this.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private void printNodeInternal(List<TreeNode<Key, Value>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || this.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        this.printWhitespaces(firstSpaces);

        List<TreeNode<Key,Value>> newNodes = new ArrayList<TreeNode<Key, Value>>();
        for (TreeNode<Key, Value> node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            this.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                this.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    this.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print(" /");
                else
                    this.printWhitespaces(1);

                this.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    this.printWhitespaces(1);

                this.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private int maxLevel(TreeNode<Key, Value> node) {
        if (node == null)
            return 0;

        return Math.max(this.maxLevel(node.left), this.maxLevel(node.right)) + 1;
    }

    private <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
}

