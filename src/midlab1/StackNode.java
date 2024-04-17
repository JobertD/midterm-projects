package midlab1;

import java.util.LinkedList;

public class StackNode<T> implements Stack<T>{
    private LinkedList<T> stack;

    public StackNode() {
        stack = new LinkedList<>();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.size() == 0;
    }

    @Override
    public T top() throws StackException {
        if (stack.size() == 0) throw new StackException("Stack underflow error");
        else return stack.peek();
    }

    @Override
    public T pop() throws StackException {
        if (stack.size() == 0) throw new StackException("Stack underflow error");
        else return stack.pop();
    }

    @Override
    public void push(T item) throws StackException {
        stack.push(item);
    }

    @Override
    public T peek() throws StackException {
        if (stack.size() == 0) throw new StackException("Stack underflow error");
        else return stack.peek();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public int search(T item) {
        return stack.indexOf(item);
    }

    public String toString() {
        return stack.toString();
    }

}
