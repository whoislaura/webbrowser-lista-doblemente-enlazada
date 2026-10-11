package estructura;

public class Node<T> {
    protected T val;
    protected Node<T> next;
    protected Node<T> previous;

    public Node(T val) {
        this.val = val;
        this.next = null;
        this.previous = null;
    }

    public Node(T val, Node<T> next) {
        this.val = val;
        this.next = next;
        this.previous = null;
        if (next != null) {
            next.setPrevious(this);
        }
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }
}
