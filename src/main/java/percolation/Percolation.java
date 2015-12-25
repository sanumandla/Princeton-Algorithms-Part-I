/**
 * Created by sridhar.anumandla on 4/20/15.
 */
public class Percolation {

    private int rowLength;
    private int gridSize; 

    private int virtualTop, virtualBottom;
    
    private boolean[][] grid;
    
    private static int numberOfOpenSites = 0;
    private WeightedQuickUnionUF weightedQUF;
    private WeightedQuickUnionUF backwashWeightedQUF;
     
    // create N-by-N grid, with all sites blocked
    public Percolation(int rowLength) {
        if (rowLength <= 0) {
            throw new IllegalArgumentException("Number of elements should be greater than 0");
        }

        this.rowLength = rowLength;
        this.gridSize = rowLength * rowLength;
        this.grid = new boolean[rowLength + 1][rowLength + 1];  // this is a 1 indexed array (by default the array is set to false)
        
        // Initialize Weighted QUF algorithm
        weightedQUF = new WeightedQuickUnionUF(gridSize + 2);   // The 2 additional places are used to store the virtualTop and virtualBottom nodes
        backwashWeightedQUF = new WeightedQuickUnionUF(gridSize + 1); // To handle backwash
        
        virtualTop = gridSize;          
        virtualBottom = gridSize + 1;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validate(i, j);
        
        boolean retVal = openNode(i, j);  // Center
        if (!retVal) {
          return;
        }
        
        int p = xyTo1D(i-1, j-1);  // the -1 is just because WQUF algorithm uses 0 based indexing whereas we use 1 based indexing
          
        // Connect neighbours
        if ((i-1 >= 1) && isOpen(i-1, j)) {
          int q = xyTo1D(i-2, j-1);
          connectNodes(p, q);
        } 
        
        if ((i+1 <= rowLength) && isOpen(i + 1, j)) {
          int q1 = xyTo1D(i, j-1);
          connectNodes(p, q1);
        }
          
        if ((j-1 >= 1) && isOpen(i, j-1)) {
          int q2 = xyTo1D(i-1, j-2);
          connectNodes(p, q2);    
        }
          
        if ((j+1 <= rowLength) && isOpen(i, j + 1)) {
          int q3 = xyTo1D(i-1, j);
          connectNodes(p, q3);
        }   
        
        // Check if the top/bottom rows are open and if so connect
        // to their respective virtualTop and virtualBottom nodes
        connectToVirtualNodes(i, j);
    }
 
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
      validate(i, j);
      return grid[i][j];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
       validate(i, j);
       int node = xyTo1D(i-1, j-1);  
       return backwashWeightedQUF.connected(virtualTop, node);
    }

    // does the system percolate?
    public boolean percolates() {
      return weightedQUF.connected(virtualTop, virtualBottom);      
    }
    
    private boolean openNode(int i, int j) {
      if ( (i >= 1 && i <= rowLength) && (j >= 1 && j <= rowLength)) {
        if (!isOpen(i, j)) {
          grid[i][j] = true;  
          ++numberOfOpenSites;
          return true;
        }
      }
      
      return false;
    } 
    
    // Connect the nodes using Weighted UF algorithm
    private void connectNodes(int i, int j) {
      if ( (i < 1 && i > rowLength) && (j < 1 && j > rowLength)) {
        return;
      }
           
      if (!weightedQUF.connected(i, j)) {
        weightedQUF.union(i, j);    
      } 

      if (!backwashWeightedQUF.connected(i, j)) {
        backwashWeightedQUF.union(i, j);    
      } 
    }
    
     // Check if the node is in top row or bottom row and connect to 
    // virtualTop or virtualBottom accordingly, otherwise ignore. 
    // Special case is when there is only 1x1 grid then it connects to both virtualTop
    // and virtualBottom
    private void connectToVirtualNodes(int i, int j) {
      int p = xyTo1D(i-1, j-1);
      
      if (p >= 0 && p <= rowLength-1) {
        weightedQUF.union(virtualTop, p);
        backwashWeightedQUF.union(virtualTop, p); // To handle backwash just connect node to top
      }  
      
      if (p >= (gridSize - rowLength) && p <= gridSize-1) {
        weightedQUF.union(virtualBottom, p);
      }
    }
 
    private int xyTo1D(int i, int j) {
      i = i < 0 ? 0 : i;
      i = i > (rowLength - 1) ? (rowLength - 1) : i;
      j = j < 0 ? 0 : j;
      j = j > (rowLength - 1) ? (rowLength - 1) : j;  
        
      int retVal = (i * rowLength) + j;
      
      return retVal;
    }

    private void validate(int row, int col) {
        if ( (row < 1 || row > rowLength) || (col < 1 || col > rowLength)) {
            throw new IndexOutOfBoundsException("Index should be between 1 and " + rowLength + " inclusive");
        }
    }
    
    private WeightedQuickUnionUF getWeightedQuickUnionUFInstance() {
      return weightedQUF; 
    }
    
    private void display2DArray() {
        for(int i = 1; i <= rowLength; i++) {
           for(int j = 1; j <= rowLength; j++) {
                System.out.print(" " + grid[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
      int rowLength = 20;
      
      // Set all sites as blocked
      Percolation percolation = new Percolation(rowLength);
      
/*
      // Check for special case of bashwash
      percolation.open(4, 1);
      percolation.open(3, 1);
      percolation.open(2, 1);
      percolation.open(1, 1);
      percolation.open(1, 4);
      percolation.open(2, 4);
      percolation.open(4, 4);
      percolation.open(3, 4);
      StdOut.println(percolation.isFull(4, 4));
      System.exit(1); */


/*      
      // Test xyTo1D
      int i1 = StdRandom.uniform(rowLength) + 1;
      int j1 = StdRandom.uniform(rowLength) + 1;
      
      int retVal = percolation.xyTo1D(i1-1, j1-1);
      StdOut.println("1D index for (" + i1 + ", " + j1 + ") = " + retVal);
      
      // Test isOpen
      boolean status = percolation.isOpen(i1, j1);
      StdOut.println("Is node open for (" + i1 + ", " + j1 + ") = " + status);
      
      System.exit(0);
*/

/*     
      // Test open
      percolation.open(3, 4);
      percolation.display2DArray();
      
      StdOut.println(weightedQUF.count() + " components");
      System.exit(0);
*/  
 
/*
      // Test open again 
      percolation.open(1, 1);
      percolation.display2DArray();
      percolation.open(1, 2);
      percolation.display2DArray();
      StdOut.println(weightedQUF.connected(0, 1));
      System.exit(0);
*/
    
/*
      percolation.open(3, 4);   
      boolean result1 = percolation.isFull(3, 4);
      StdOut.println("Result ==> " + result1);
      percolation.open(1, 1); 
      boolean result2 = percolation.isFull(1, 1);
      StdOut.println("Result ==> " + result2);
      System.exit(0);
*/
      
      int counter = 0;
      while (percolation.getWeightedQuickUnionUFInstance().count() > 1) {
        int i = StdRandom.uniform(rowLength) + 1;
        int j = StdRandom.uniform(rowLength) + 1;
       
        StdOut.println("Randomly selected (i, j) = (" + i + ", " + j + ")");
       
        // Open site if not opened and connect to opened neighbours
        percolation.open(i, j);
      
        if (percolation.percolates()) {
          StdOut.println("System percolates");
          break;
        } 
        
        StdOut.println("Count = " + percolation.getWeightedQuickUnionUFInstance().count());
      }
      
//      percolation.display2DArray();
      
      StdOut.println("Number of open sites = " + numberOfOpenSites);
      StdOut.println("Percolation threshold = " + (float) numberOfOpenSites / percolation.gridSize); 
    }
}    