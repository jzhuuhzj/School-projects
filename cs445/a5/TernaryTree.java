package cs445.a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cs445.StackAndQueuePackage.*; 

public class TernaryTree<E> implements TernaryTreeInterface<E>{
	private TernaryNode<E> root;
	
	public TernaryTree(){
		root = null;
	}
	
	public TernaryTree(E rootData){
		root = new TernaryNode<>(rootData);
	}
	
	public TernaryTree(E rootData, TernaryTree<E> leftTree, TernaryTree<E> middleTree,
			TernaryTree<E> rightTree){
		privateSetTree(rootData, leftTree, middleTree, rightTree);
	}
	
	public E getRootData() {
		if (isEmpty()) {
            throw new EmptyTreeException();
        } else {
            return root.getData();
        }
	}
	
	public boolean isEmpty() {
		return root == null;
	}

	public int getHeight() {
		int height = 0;
        if (!isEmpty()) {
            height = root.getHeight();
        }
        return height;
	}

	public int getNumberOfNodes() {
		int numberOfNodes = 0;
        if (!isEmpty()) {
            numberOfNodes = root.getNumberOfNodes();
        }
        return numberOfNodes;
	}

	public Iterator<E> getLevelOrderIterator() {
		return new LevelOrderIterator();
	}
	
	public Iterator<E> getPreorderIterator() {
		return new PreorderIterator();
	}

	public Iterator<E> getPostorderIterator() {
		return new PostorderIterator();
	}
	
	/**TernaryTree does not support InorderIterator because InorderIterator
	 * depends on the leftTree to tell how to process the iteration. But in 
	 * TernaryTree it is likely to have a middle child which is not in the 
	 * consideration of InorderIterator.
	 */
	public Iterator<E> getInorderIterator() {
		throw new UnsupportedOperationException();
	}
	
	public void clear() {
		root = null;	
	}

	public void setTree(E rootData) {
		root = new TernaryNode<>(rootData);		
	}

	public void setTree(E rootData, TernaryTreeInterface<E> leftTree, TernaryTreeInterface<E> middleTree,
			TernaryTreeInterface<E> rightTree) {
		privateSetTree(rootData, (TernaryTree<E>)leftTree, (TernaryTree<E>)middleTree,
                (TernaryTree<E>)rightTree);		
	}
	
	private void privateSetTree(E rootData, TernaryTree<E> leftTree, TernaryTree<E> middleTree,
            TernaryTree<E> rightTree) {
		root = new TernaryNode<>(rootData);

		if ((leftTree != null) && !leftTree.isEmpty()) {
			root.setLeftChild(leftTree.root);
		}

		if ((middleTree != null) && !middleTree.isEmpty()) {
			if ((middleTree != leftTree) || (middleTree != rightTree)) { 
				root.setMiddleChild(middleTree.root);
			} else {
				root.setMiddleChild(middleTree.root.copy());
			}
		}
		
		if ((rightTree != null) && !rightTree.isEmpty()) {
			if ((rightTree != middleTree) || (rightTree != leftTree)) {
				root.setRightChild(rightTree.root);
			} else {
				root.setRightChild(rightTree.root.copy());
			}
		}

		if ((leftTree != null) && (leftTree != this)) {
			leftTree.clear();
		}
		
		if ((middleTree != null) && (middleTree != this)) {
			middleTree.clear();
		}

		if ((rightTree != null) && (rightTree != this)) {
			rightTree.clear();
		}
	}
	
	private class LevelOrderIterator implements Iterator<E> {
        private QueueInterface<TernaryNode<E>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            if (root != null) {
                nodeQueue.enqueue(root);
            }
        }

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        }

        public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.dequeue();
                TernaryNode<E> leftChild = nextNode.getLeftChild();
                TernaryNode<E> middleChild = nextNode.getMiddleChild();
                TernaryNode<E> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.enqueue(leftChild);
                }
                
                if (middleChild != null) {
                    nodeQueue.enqueue(middleChild);
                }

                if (rightChild != null) {
                    nodeQueue.enqueue(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
	
	private class PreorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;

        public PreorderIterator() {
            nodeStack = new LinkedStack<>();
            if (root != null) {
                nodeStack.push(root);
            }
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<E> leftChild = nextNode.getLeftChild();
                TernaryNode<E> middleChild = nextNode.getMiddleChild();
                TernaryNode<E> rightChild = nextNode.getRightChild();

                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }
                
                if (middleChild != null) {
                    nodeStack.push(middleChild);
                }

                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class PostorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;
        private TernaryNode<E> currentNode;

        public PostorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }
        public E next() {
            boolean foundNext = false;
            TernaryNode<E> leftChild, middleChild, rightChild, nextNode = null;

            // Find leftmost leaf
            while (currentNode != null) {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild(); 
                middleChild = currentNode.getMiddleChild();	
                rightChild = currentNode.getRightChild();
                
                
                if (leftChild == null) {
                    
                    if (middleChild == null){
                		currentNode = currentNode.getRightChild(); 
                	} 
                	else{
                		currentNode = currentNode.getMiddleChild();  
                	}

             	} 

                else
                {	
                	currentNode = leftChild;
                }
            }

            // Stack is not empty either because we just pushed a node, or
            // it wasn't empty to begin with since hasNext() is true.
            // But Iterator specifies an exception for next() in case
            // hasNext() is false.

            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                // nextNode != null since stack was not empty before pop

                TernaryNode<E> parent = null;
                if (!nodeStack.isEmpty()) {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild()) {
                        currentNode = parent.getMiddleChild();
                    } 
                    else if (nextNode == parent.getMiddleChild()){
                        currentNode = parent.getRightChild();
                    }
                    else{
                    	currentNode = null;
                    }
                } else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

		
}

   
