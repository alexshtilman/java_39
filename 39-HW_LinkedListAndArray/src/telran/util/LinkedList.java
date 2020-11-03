package telran.util;

public class LinkedList<T> implements IndexedList<T> {
    private static class Node<T> {
        public T obj;
        public Node<T> next;
        public Node<T> prev;

        public Node(T obj) {
            this.obj = obj;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private Node<T> getNode(int index) {

        return (index < size / 2) ? getFromLeft(index) : getFromRight(index);
    }

    private Node<T> getFromRight(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private Node<T> getFromLeft(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void removeNode(Node<T> node) {
        Node<T> nodeNext = node.next;
        Node<T> nodePrev = node.prev;
        nodePrev.next = nodeNext;
        nodeNext.prev = nodePrev;
    }

    private void removeTail() {
        if (this.size() == 1) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
    }

    private void removeHead() {
        if (this.size() == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
    }

    private void addMiddle(int index, T obj) {
        Node<T> newItem = new Node<>(obj);
        Node<T> nodeCurrent = getNode(index);

        newItem.prev = nodeCurrent.prev;
        newItem.next = nodeCurrent;
        nodeCurrent.prev.next = newItem;
        nodeCurrent.prev = newItem;
    }

    private void setHead(T obj) {
        Node<T> newHead = new Node<>(obj);
        newHead.next = head;
        newHead.prev = null;
        head = newHead;

    }

    @Override
    public void add(T obj) {
        Node<T> node = new Node<>(obj);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> nodeIndex = getNode(index);
        return nodeIndex.obj;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(int index, T obj) {
        if (index < 0 || index >= size)
            return false;
        if (index == 0)
            setHead(obj);
        else {
            addMiddle(index, obj);
        }
        size++;
        return true;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size)
            return null;
        Node<T> node = getNode(index);
        T res = node.obj;
        if (index == 0)
            removeHead();
        else if (index == size - 1)
            removeTail();
        else
            removeNode(node);
        size--;
        return res;
    }

    @Override
    public int indexOf(Object pattern) {
        Node<T> first = head;
        for (int i = 0; i < size; i++) {
            if (first.obj.equals(pattern)) {
                return i;
            }
            first = first.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object pattern) {
        Node<T> last = tail;
        for (int i = size - 1; i >= 0; i--) {
            if (last.obj.equals(pattern)) {
                return i;
            }
            last = last.prev;
        }
        return -1;
    }

    @Override
    public void reverse() {
        if (head != tail) {
            Node<T> lastElement = tail;
            Node<T> firstElement = head;
            for (int i = 0; i < size / 2; i++) {
                T tempObj = firstElement.obj;
                firstElement.obj = lastElement.obj;
                lastElement.obj = tempObj;
                firstElement = firstElement.next;
                lastElement = lastElement.prev;
            }
        }
    }

    @Override
    public T remove(Object pattern) {
        Node<T> first = head;
        for (int i = 0; i < size; i++) {
            if (first.obj.equals(pattern)) {
                return this.remove(i);
            }
            first = first.next;
        }
        return null;
    }

    @Override
    public boolean removeAll(IndexedList<T> patterns) {
        int currentSize = this.size();
        for (Node<T> current = head; current != null; current = current.next) {
            if (patterns.contains(current.obj)) {
                this.remove(current.obj);
            }
        }
        return currentSize != this.size();
    }

    @Override
    public boolean contains(T pattern) {
        return this.indexOf(pattern) == -1 ? false : true;
    }

    @Override
    public boolean retainAll(IndexedList<T> patterns) {
        int currentSize = this.size();
        for (Node<T> current = head; current != null; current = current.next) {
            if (!patterns.contains(current.obj)) {
                this.remove(current.obj);
            }
        }
        return currentSize != this.size();
    }
}

