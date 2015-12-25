import java.util.Iterator;

public class Subset {
  public static void main(String[] args) {
    Subset subset = new Subset();
    
    if  ( (args.length != 1) || (args[0] == null || args[0].isEmpty()) ) {
      throw new IllegalArgumentException();
    } 
   
    int sampleSize = Integer.valueOf(args[0]);
//    StdOut.println("Sample size: " + sampleSize);
    
    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
    
    while (!StdIn.isEmpty()) {
      String input = StdIn.readString();
      randomizedQueue.enqueue(input);
    }
    
//    subset.display(randomizedQueue);  
    
    int i = 0;
    while(i < sampleSize) {
      StdOut.println(randomizedQueue.dequeue());
      i++;
    }
  }
  
  private void display(RandomizedQueue randomizedQueue) {
    Iterator<String> itr = randomizedQueue.iterator();
    while(itr.hasNext()) {
      StdOut.print(itr.next() + " ");
    }
    StdOut.println();
  }
}