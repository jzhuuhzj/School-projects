package cs445.a5;

import java.util.*;

public class Test{
	public static void main (String args[]) {
		
		TernaryTree<String> aTree = new TernaryTree<>("A");
		TernaryTree<String> cTree = new TernaryTree<>("C");
		TernaryTree<String> eTree = new TernaryTree<>("E");
		TernaryTree<String> gTree = new TernaryTree<>("G");
		TernaryTree<String> emptyTree = new TernaryTree<>();
		TernaryTree<String> bTree = new TernaryTree<>("B",aTree,cTree,eTree);
		TernaryTree<String> fTree = new TernaryTree<>("F",eTree,gTree,bTree);
		TernaryTree<String> dTree = new TernaryTree<>("D",bTree,fTree,gTree);
		TernaryTree<String> hTree = new TernaryTree<>("B",emptyTree,cTree,eTree);

				
		System.out.println("root： " + hTree.getRootData());
		System.out.println("height： " + hTree.getHeight());
		System.out.println("total number of nodes： " + hTree.getNumberOfNodes());
		aTree.getLevelOrderIterator();
		aTree.getPreorderIterator();
		aTree.getPostorderIterator();
		//aTree.getInorderIterator();

		TernaryTree<Integer> node1 = new TernaryTree<>(1);
		TernaryTree<Integer> node2 = new TernaryTree<>(2);
		TernaryTree<Integer> node3 = new TernaryTree<>(3);
		TernaryTree<Integer> node4 = new TernaryTree<>(4);
		TernaryTree<Integer> node5 = new TernaryTree<>(5);
		TernaryTree<Integer> node6 = new TernaryTree<>(6);
		TernaryTree<Integer> node7 = new TernaryTree<>(7);
		TernaryTree<Integer> node8 = new TernaryTree<>(8);
		TernaryTree<Integer> node9 = new TernaryTree<>(9);
		TernaryTree<Integer> node10 = new TernaryTree<>(10);
		TernaryTree<Integer> node11 = new TernaryTree<>(11);
		TernaryTree<Integer> node12 = new TernaryTree<>(12);
		TernaryTree<Integer> emptyTree2 = new TernaryTree<>();
		TernaryTree<Integer> emptyTree3 = new TernaryTree<>();
		TernaryTree<Integer> emptyTree4 = new TernaryTree<>();

		


		
		TernaryTree<Integer> treeA = new TernaryTree<>(9, node12, emptyTree2, emptyTree2);
		TernaryTree<Integer> treeB = new TernaryTree<>(6, node1, emptyTree4, node3);
		TernaryTree<Integer> treeC = new TernaryTree<>(3, node5, node7, node8);
		TernaryTree<Integer> treeD = new TernaryTree<>(6, treeA, emptyTree3, treeB);		
		
		TernaryNode<Integer> n6 = new TernaryNode<>(6);
		TernaryNode<Integer> n1 = new TernaryNode<>(1);
		TernaryNode<Integer> n2 = new TernaryNode<>(2);
		TernaryNode<Integer> n3 = new TernaryNode<>(3);
		
		System.out.println(n6.getData());
		
		n6.setLeftChild(n1);
		n6.setMiddleChild(n2);
		n6.setRightChild(n3);
		
		System.out.println("height: " + n6.getHeight());
		System.out.println("number of nodes: " + n6.getNumberOfNodes());
		System.out.println(n6.getLeftChild().getData());
		System.out.println(n6.getMiddleChild().getData());
		System.out.println(n6.getRightChild().getData());
		
		System.out.println("D's root: " + treeD.getRootData());
		System.out.println("D's height: " + treeD.getHeight());
		System.out.println("D's number of nodes: " + treeD.getNumberOfNodes());
		
		Iterator pre = treeD.getPreorderIterator();
		Iterator post = treeD.getPostorderIterator();
		Iterator level = treeD.getLevelOrderIterator();
		
		System.out.print("postorder: ");

		while(post.hasNext()){
			System.out.print(post.next());
		}

		System.out.println();
		System.out.print("preorder: ");

		while(pre.hasNext()){
			
			System.out.print(pre.next());
		}

		System.out.println();
		System.out.print("levelorder: ");
		
		while(level.hasNext()){
			System.out.print(level.next());
		}
		System.out.println();
				
	}
}