import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  
  private int length;
  private Node first;
  private Node last;
  
  private class Node {
    Item item;
    Node next;
    Node prev;
  }
  
   // construct an empty deque
  public Deque() {
  }
   
  // is the deque empty?
  public boolean isEmpty() {
    return length == 0;
  }
  
  // return the number of items on the deque  
  public int size() {
    return length;
  }
  
  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new java.lang.NullPointerException();
    }
    
    Node newNode = new Node();
    newNode.item = item;
    newNode.next = first;
    
    if (first == null) {
      first = newNode;
      last = newNode;
    } else {
      first.prev = newNode; 
    }
    
    first = newNode;
    
    ++length;
  }
  
  // add the item to the end
  public void addLast(Item item) {
    if (item == null) {
      throw new java.lang.NullPointerException();
    }
    
    Node newNode = new Node();
    newNode.item = item;
    newNode.prev = last;
    
    if (last == null) {
      first = newNode;
      last = newNode;
    } else {
      last.next = newNode;
    }
    
    last = newNode;
    
    ++length;
  }
  
  // remove and return the item from the front
  public Item removeFirst() {
    if (length == 0) {
      throw new java.util.NoSuchElementException();
    }
    
    Node node = first;
    first = first.next;
    
    if (first != null) {
      first.prev = null;
      node.next = null;
    } 
    
    if (--length == 0) {
      first = null;
      last = null;
    }
    
    return node.item;
  }
  
  // remove and return the item from the end
  public Item removeLast() {
    if (length == 0) {
      throw new java.util.NoSuchElementException();
    }
    
    Node node = last;
    last = last.prev;
    
    if (last != null) {
      last.next = null;
      node.prev = null;
    }
    
    if (--length == 0) {
      first = null;
      last = null;
    }
    
    return node.item;
  }
  
  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new ListIterator();
  }
  
  private class ListIterator implements Iterator<Item> {
    private Node start = first;
    
    public boolean hasNext() {
      return start != null;
    }
    
    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
    
    public Item next() {
      if (start == null) {
        throw new java.util.NoSuchElementException();
      }
      
      Node node = start;
      start = start.next;
      
      return node.item;
    }
  }
  
  private void display() {
    Iterator<Item> itr = iterator();
    while(itr.hasNext()) {
      StdOut.print(itr.next() + " ");
    }
    StdOut.println();
  }
  
  // unit testing
  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<Integer>();
    StdOut.println("Is deque empty: " + deque.isEmpty());
    
    deque.addFirst(20);
    deque.addFirst(10);
    deque.addLast(30);
    deque.addLast(40);
    StdOut.println("Is deque empty: " + deque.isEmpty());
    StdOut.println("Length of deque: " + deque.size());    
    
    deque.display();
    
    deque.removeFirst();
    deque.removeLast();
    StdOut.println("Is deque empty: " + deque.isEmpty());
    StdOut.println("Length of deque: " + deque.size());    
    
    deque.display();
    
    deque.addFirst(5);
    deque.addLast(45);
    
    StdOut.println("Is deque empty: " + deque.isEmpty());
    StdOut.println("Length of deque: " + deque.size());    
    
    deque.display();
    
    deque.removeFirst();
    deque.removeLast();
    deque.removeLast();
    deque.removeLast();    
    StdOut.println("Is deque empty: " + deque.isEmpty());
    StdOut.println("Length of deque: " + deque.size());    
    
    deque.display();
    
    deque.addLast(70);
    deque.addLast(80);
    deque.addLast(90);
    deque.addLast(100);
    
    StdOut.println("Is deque empty: " + deque.isEmpty());
    StdOut.println("Length of deque: " + deque.size());   
    
    deque.display();
    
    deque.addFirst(60);
    deque.display();
    
    deque.removeFirst();
    deque.removeFirst();
    deque.removeFirst();
    deque.removeFirst();
    deque.removeFirst();
    
    StdOut.println("Is deque empty: " + deque.isEmpty());
    StdOut.println("Length of deque: " + deque.size());   
    
    deque.display();
  }
}