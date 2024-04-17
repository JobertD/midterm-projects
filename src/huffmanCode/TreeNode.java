package huffmanCode;

/**
 * TreeNode object contains a data field and two references to other TreeNode objects
 */
public class TreeNode<T> {
    private TreeNode<T> leftNode;
    private T data;
    private TreeNode<T> rightNode;
    public TreeNode(T nodeData) {
        data = nodeData;
        leftNode = null;
        rightNode = null;
    }


    /**
     * Code for the TreeNode class, this will be used to create a binary tree
     */
    public TreeNode() {
    }

    public void setLeft(TreeNode<T> left) {
        leftNode = left;
    }
    public void setRight(TreeNode<T> right) {
        rightNode = right;
    }
    public void setData(T d) {
        data = d;
    }
    public TreeNode<T> getLeftNode() {
        return leftNode;
    }
    public TreeNode<T> getRightNode() {
        return rightNode;
    }
    public T getData() {
        return data;
    }

    /**
     * Used to insert a new node into the tree.
     * @param insertValue
     */
    public void insert(T insertValue) {
        if (insertValue.toString().compareTo(data.toString()) < 0) {
            if (leftNode == null)
                leftNode = new TreeNode<T>(insertValue);
            else
                leftNode.insert(insertValue);
        } // end if
        else if (insertValue.toString().compareTo(data.toString()) > 0) {
            if (rightNode == null)
                rightNode = new TreeNode<T>(insertValue);
            else
                rightNode.insert(insertValue);
            }// end else if
        } // end insert
     // end TreeNode<T> class

    @Override
    public String toString() {
        return "TreeNode{" +
                "left=" + leftNode +
                ", Data=" + data +
                ", right=" + rightNode +
                '}';
    }
}