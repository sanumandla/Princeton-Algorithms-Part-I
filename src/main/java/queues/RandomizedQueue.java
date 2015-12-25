import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private static final int DEFAULT_THRESHOLD = 10;
  
  private static final int SCALE_UP_THRESHOLD = 75;
  private static final int SCALE_DOWN_THRESHOLD = 25;
  
  private int index;
  private Item[] array;
  
  public RandomizedQueue() {
    array = (Item[]) new Object[DEFAULT_THRESHOLD];
  }
   
  // is the queue empty?
  public boolean isEmpty() {
    return index == 0;
  }
  
  // return the number of items on the queue  
  public int size() {
    return index;
  }
  
  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new java.lang.NullPointerException();
    }
    
    float capacity = (float) size() / array.length;
    if (capacity * 100 >= SCALE_UP_THRESHOLD) {
      upSize();
    }
   
    array[index++] = item; 
  }
  
  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    
    float capacity = (float) size() / array.length;
    //StdOut.println("Capacity = " + capacity);
    if (capacity * 100 <= SCALE_DOWN_THRESHOLD) {
      downSize();
    }
    
    int random = StdRandom.uniform(index);
    Item item = array[random];
    array[random] = array[--index];
    array[index] = null;
    
    return item;
  }
  
  // return (but do not remove) a random item
  public Item sample() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    
    int random = StdRandom.uniform(index);
    
    return array[random];
  }
  
  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    Item[] newArray = (Item[]) new Object[size()];
    for (int i = 0; i < size(); i++) {
      newArray[i] = array[i];
    }
    
    return new ListIterator(newArray);
  }
  
  private class ListIterator implements Iterator<Item> {
    private int idx = 0;
    private Item[] arrayInternal;
    
    public ListIterator(Item[] itemArray) {
      this.arrayInternal = itemArray;
      StdRandom.shuffle(arrayInternal);
    }
    
    public boolean hasNext() {
      return validate() && arrayInternal[idx] != null;
    }
    
    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
    
    public Item next() {
      if (!validate()) {
        throw new java.util.NoSuchElementException();
      }
      
      return arrayInternal[idx++];
    }
    
    private boolean validate() {
      return arrayInternal.length > 0 && (idx >= 0 && idx < index);
    }
  }
  
  // Double the capacity of the array
  private void upSize() {
    Item[] newArray = (Item[]) new Object[array.length * 2];
    copyArray(newArray, array);
  }

  // Reduce the capacity of the array by half
  private void downSize() {
    Item[] newArray = (Item[]) new Object[array.length / 2];
    copyArray(newArray, array);
  }
  
  private void copyArray(Item[] newArray, Item[] oldArray) {
    for(int i = 0; i < index; i++) {
      newArray[i] = oldArray[i];
    }
    
    // Replace global array
    array = newArray;
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
    RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();
    StdOut.println("Is queue empty: " + randomQueue.isEmpty());
    
    randomQueue.enqueue(10);
    randomQueue.enqueue(20);
    randomQueue.enqueue(30);
    randomQueue.enqueue(40);
    StdOut.println("Added 4 items");
    randomQueue.display();  
    StdOut.println("Is queue empty: " + randomQueue.isEmpty());
    StdOut.println("Length of queue: " + randomQueue.size());    
    
    randomQueue.enqueue(50);
    randomQueue.enqueue(60);
    StdOut.println("Added 2 items");
    randomQueue.display();
    
    StdOut.println("Random sample = " + randomQueue.sample());
    StdOut.println("Random sample = " + randomQueue.sample());
    StdOut.println("Is queue empty: " + randomQueue.isEmpty());
    StdOut.println("Length of queue: " + randomQueue.size());    
    randomQueue.display();
    
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Dequeue = " + randomQueue.dequeue());  
    StdOut.println("Length of queue: " + randomQueue.size());    
    randomQueue.display();
    
    randomQueue.enqueue(70);
    randomQueue.enqueue(80);
    randomQueue.enqueue(90);
    StdOut.println("Added 3 items");
    StdOut.println("Length of queue: " + randomQueue.size());    
    randomQueue.display();
    
    StdOut.println("Random sample = " + randomQueue.sample());
    StdOut.println("Random sample = " + randomQueue.sample());
    StdOut.println("Random sample = " + randomQueue.sample());
    StdOut.println("Length of queue: " + randomQueue.size());    
    randomQueue.display();
    
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Dequeue = " + randomQueue.dequeue());  
    StdOut.println("Length of queue: " + randomQueue.size());    
    randomQueue.display();
    
    // Create 2 new iteratos
    Iterator<?> itr1 = randomQueue.iterator();
    Iterator<?> itr2 = randomQueue.iterator();
    
    StdOut.println("Iterator operations on first iterator");
    itr1.next();
    itr1.next();
    itr1.next();
    while(itr1.hasNext()) {
      StdOut.print(itr1.next() + " ");
    }
    StdOut.println();
    
    StdOut.println("Iterator operations on second iterator");
    itr2.next();
    while(itr2.hasNext()) {
      StdOut.print(itr2.next() + " ");
    }
    StdOut.println();
    
    StdOut.println("==== Validate array capacity ====");
    
    StdOut.println("==== Increase array size ====");
    randomQueue = new RandomizedQueue<Integer>();
    StdOut.println("Is queue empty: " + randomQueue.isEmpty());
    randomQueue.enqueue(10);
    randomQueue.enqueue(20);
    randomQueue.enqueue(30);
    randomQueue.enqueue(40);
    randomQueue.enqueue(50);
    randomQueue.enqueue(60);
    randomQueue.enqueue(70);
    StdOut.println("Added 7 items");
    randomQueue.display();  
    StdOut.println("Length of queue: " + randomQueue.size());  
   
    randomQueue.enqueue(80);
    randomQueue.enqueue(90);
    randomQueue.enqueue(100);
    randomQueue.enqueue(110);
    StdOut.println("Added 4 items");
    randomQueue.display();  
    StdOut.println("Length of queue: " + randomQueue.size());  
    
    StdOut.println("==== Decrease array size ====");
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Length of queue: " + randomQueue.size());  
    
    StdOut.println("Dequeue = " + randomQueue.dequeue());
    StdOut.println("Length of queue: " + randomQueue.size());  
    
    // Check if iterator.next() throws NoSuchElement and also on empty randomized queue
    Iterator<?> itr3 = randomQueue.iterator();
    while(itr3.hasNext()) {
      StdOut.print(itr3.next() + " ");
    }
    
    try {
      itr3.next();
    } catch (java.util.NoSuchElementException e) {
      StdOut.println();
      StdOut.println(e.toString());
    }
    
    StdOut.println("Empty queue");
    randomQueue.dequeue();
    randomQueue.dequeue();
    randomQueue.dequeue();
    randomQueue.dequeue();
    
    try {
      itr3.next();
    } catch (java.util.NoSuchElementException e) {
      StdOut.println(e.toString());
    }
  }  
  
}