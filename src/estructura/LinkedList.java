package estructura;

public class LinkedList<T> implements IList<T>{

    private Node<T> head, tail;
    private int size;

    public LinkedList (){
        this.head = null;
        this.tail = null;
        size = 0;
    }

    @Override
    public void add(T o) {
        Node<T> value = new Node<>(o);

        if (isEmpty()) {
            head = tail = value;
        }

        else {
            value.setPrevious(tail);
            tail.setNext(value);
            tail = value;
        }
        size++;
    }

    @Override
    public void add(T o, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        if (index == size) {
            add(o);
            return;
        }

        Node<T> node = new Node<>(o);
        if (index == 0) {
            node.setNext(head);
            head.setPrevious(node);
            head = node;
        } else {
            Node<T> cursor = head;
            for (int i = 0; i < index; i++) {
                cursor = cursor.getNext();
            }

            Node<T> previous = cursor.getPrevious();
            node.setPrevious(previous);
            node.setNext(cursor);
            previous.setNext(node);
            cursor.setPrevious(node);
        }
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        Node<T> node = getNode(index);
        Node<T> previous = node.getPrevious();
        Node<T> next = node.getNext();

        if (previous == null) {
            head = next;
        } else {
            previous.setNext(next);
        }

        if (next == null) {
            tail = previous;
        } else {
            next.setPrevious(previous);
        }

        node.setPrevious(null);
        node.setNext(null);
        size--;

        if (size == 0) {
            head = tail = null;
        }

        return node.getVal();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        return getNode(index).getVal();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        if (index < size / 2) {
            Node<T> cursor = head;
            for (int i = 0; i < index; i++) {
                cursor = cursor.getNext();
            }
            return cursor;
        }

        Node<T> cursor = tail;
        for (int i = size - 1; i > index; i--) {
            cursor = cursor.getPrevious();
        }
        return cursor;
    }
}
