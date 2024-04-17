package midlab1;


public interface Stack<T> {
    public int size(); // returns the size of the stack
    public boolean isEmpty(); // checks if the stack is empty
    public T top() throws StackException;
    public T pop() throws StackException;
    public void push (T item) throws StackException;
    public T peek() throws StackException;
    public void clear();
    public int search(T item);
}

