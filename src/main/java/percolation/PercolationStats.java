/**
 * Created by sridhar.anumandla on 04/30/15.
 */
public class PercolationStats {
  
  private int rowLength;
  private int numberOfTrials;
  
  private double[] thresholdArray;
  
  // Perform T independent experiments on an N-by-N grid
  public PercolationStats(int N, int T) {
    if (N <= 0 || T <= 0) {
      throw new java.lang.IllegalArgumentException();
    } 
    
    this.rowLength = N;
    this.numberOfTrials = T;
    
    thresholdArray = new double[numberOfTrials]; 
    populateThreshold();
  }
  
  // Sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(thresholdArray);
  }
  
  // Sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(thresholdArray);
  }
  
  // Low  endpoint of 95% confidence interval
  public double confidenceLo() {
    double mean = mean();
    double avgStdDev = averageStdDev();
    return (mean - avgStdDev);
  }
  
  // High endpoint of 95% confidence interval
  public double confidenceHi() {
    double mean = mean();
    double avgStdDev = averageStdDev();
    return (mean + avgStdDev);
  }
  
  private double averageStdDev() {
    return (1.96 * stddev()) / Math.sqrt(numberOfTrials);
  }
  
  // Find percolation threshold for each run and populate the array
  private void populateThreshold() {
    for (int i = 0; i < numberOfTrials; i++) {
      double threshold = singleExperiment(rowLength);
      thresholdArray[i] = threshold;
    }
  }
  
  // Find percolation threshold for a single run
  private double singleExperiment(int rowLength) {
      Percolation percolation = new Percolation(rowLength);
  
      int numberOfOpenSites = 0;
      int gridSize = rowLength * rowLength;
      
      while(!percolation.percolates()) {
        int i = StdRandom.uniform(1, rowLength + 1); 
        int j = StdRandom.uniform(1, rowLength + 1);
        
        // Open site if not opened and connect to opened neighbours
        if (!percolation.isOpen(i, j)) {       
          percolation.open(i, j);
          ++numberOfOpenSites;
        }
      }
      
      return (double) numberOfOpenSites / gridSize;
  }

  // Test client
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      throw new Exception("Invalid number of arguments. Please provide 2 arguments N and T");
    }
    
    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);
    
    PercolationStats percolationStats = new PercolationStats(N, T);
//    double threshold = percolationStats.singleExperiment(N);
//    StdOut.println(threshold);
    StdOut.println("Mean = " + percolationStats.mean());
    StdOut.println("Standard Deviation = " + percolationStats.stddev());
    StdOut.println("Low Confidence Interval = " + percolationStats.confidenceLo());
    StdOut.println("High Confidence Interval = " + percolationStats.confidenceHi());
  }
  
}