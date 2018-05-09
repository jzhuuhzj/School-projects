package cs445.a5;

class TernaryNode<T> {
	private T data;
	private TernaryNode<T> leftChild;
	private TernaryNode<T> middleChild;
	private TernaryNode<T> rightChild;
	
	public TernaryNode(){
		this(null);
	}
	
	public TernaryNode(T dataPortion){
		this(dataPortion, null, null, null);
	}
	
	public TernaryNode(T dataPortion, TernaryNode<T> newLeftChild,
			TernaryNode<T> newMiddleChild, TernaryNode<T> newRightChild){
		data = dataPortion;
		leftChild = newLeftChild;
		middleChild = newMiddleChild;
		rightChild = newRightChild;
	}
	
	public T getData(){
		return data;
	}
	
	public void setData(T newData){
		data = newData;
	}
	
	public TernaryNode<T> getLeftChild(){
		return leftChild;
	}
	
	public void setLeftChild(TernaryNode<T> newLeftChild){
		leftChild = newLeftChild;
	}
	
	public boolean hasLeftChild(){
		return leftChild != null;
	}
	
	public TernaryNode<T> getMiddleChild(){
		return middleChild;
	}
	
	public void setMiddleChild(TernaryNode<T> newMiddleChild){
		middleChild = newMiddleChild;
	}
	
	public boolean hasMiddlechild(){
		return middleChild != null;
	}
	
	public TernaryNode<T> getRightChild(){
		return rightChild;
	}
	
	public void setRightChild(TernaryNode<T> newRightChild){
		rightChild = newRightChild;
	}
	
	public boolean hasRightchild(){
		return rightChild != null;
	}
	
	public boolean isLeaf() {
        return (leftChild == null) && (middleChild == null) && (rightChild == null);
    }
	
	public int getNumberOfNodes() {
        int leftNumber = 0;
        int middleNumber = 0;
        int rightNumber = 0;

        if (leftChild != null) {
            leftNumber = leftChild.getNumberOfNodes();
        }
        
        if (middleChild != null) {
            middleNumber = middleChild.getNumberOfNodes();
        }

        if (rightChild != null) {
            rightNumber = rightChild.getNumberOfNodes();
        }

        return 1 + leftNumber + middleNumber + rightNumber;
    }


    public int getHeight() {
        return getHeight(this); 
    }

    private int getHeight(TernaryNode<T> node) {
        int height = 0;

        if (node != null)
            height = 1 + Math.max(Math.max(getHeight(node.getLeftChild()),getHeight(node.getMiddleChild())),
                                  getHeight(node.getRightChild()));
        
        return height;
    }

    
    public TernaryNode<T> copy() {
    	TernaryNode<T> newRoot = new TernaryNode<>(data);

        if (leftChild != null) {
            newRoot.setLeftChild(leftChild.copy());
        }
        
        if (middleChild != null) {
            newRoot.setMiddleChild(middleChild.copy());
        }

        if (rightChild != null) {
            newRoot.setRightChild(rightChild.copy());
        }

        return newRoot;
    }
	
	
	
	
}