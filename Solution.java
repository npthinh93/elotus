import java.util.*;
import java.util.stream.*;
import java.util.concurrent.ThreadLocalRandom;

class Solution {

  public static final String ZERO = "0";
  public static final String ONE = "1";

  public static Map<Integer, List<Integer>> graph = new HashMap<>();

  public static List<Integer> grayCode(int n) {
    if (n <= 0) return new ArrayList<>();
 
    List<String> arr = new ArrayList<>();
    
    arr.add("0");
    arr.add("1");
    
    int i, j;
    for (i = 2; i < (1<<n); i = i<<1)
    {
      for (j = i-1 ; j >= 0 ; j--)
          arr.add(arr.get(j));

      // appended 0 to the first half
      for (j = 0 ; j < i ; j++)
          arr.set(j, ZERO + arr.get(j));

      // appended 1 to the second half
      for (j = i ; j < 2*i ; j++)
          arr.set(j, ONE + arr.get(j));
    }
    return arr.stream()
    .map(s -> Integer.parseInt(s,2))
    .collect(Collectors.toList());
  }

  private static void calculateDistance(int root, int n, int[] answer, int[] subtreeSize) {
    for (Integer child : graph.get(root)) {
        if (answer[child] == 0) {
            answer[child] = (answer[root] - subtreeSize[child]) + (n - subtreeSize[child]);
            calculateDistance(child, n, answer, subtreeSize);
        }
    }
  }

  private static void calculateRoot(int root, int level, boolean[] visited, int[] subtreeSize, int[] distance) {
    visited[root] = true;
    var size = 1;
    for (Integer child : graph.get(root)) {
        if (!visited[child]) {
            calculateRoot(child, level + 1, visited, subtreeSize, distance);
            size += subtreeSize[child];
        }
    }
    distance[root] = level;
    subtreeSize[root] = size;
  }

  public static int findLength(int[] nums1, int[] nums2) {
    int m = nums1.length, n = nums2.length, ans = 0;
    int[][] dp = new int[2][n+1];

    for(int i = 1; i <= m; i++){
        for(int j = 1; j <= n; j++){
            if(nums1[i-1] == nums2[j-1]){
                dp[i%2][j] = 1 + dp[(i-1)%2][j-1];
                ans = Math.max(ans, dp[i%2][j]);
            }else{
                dp[i%2][j] = 0;
            }
        }
    }
    return ans;
  }

  public static int[] sumOfDistancesInTree(int n, int[][] edges) {
    var answer = new int[n];
    for (var i = 0; i < n; i++) graph.put(i, new LinkedList<>());
    for (var edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    var root = ThreadLocalRandom.current().nextInt(0, n); // any vertex can be a root
    var subtreeSize = new int[n];
    var distance = new int[n];
    var visited = new boolean[n];
    calculateRoot(root, 0, visited, subtreeSize, distance);
    answer[root] = Arrays.stream(distance).sum();
    calculateDistance(root, n, answer, subtreeSize);
    return answer;
  }
  
  public static void main(String[] args) {
    List<Integer> grayCodes = grayCode(2);
    grayCodes.forEach(System.out::println);
    System.out.println(Arrays.toString(sumOfDistancesInTree(6, new int[][]{{0, 1}, {0, 2}, {2, 3}, {2, 4}, {2, 5}})));
    System.out.println(findLength(new int[]{1,2,3,2,1}, new int[]{3,2,1,4,7}));
  }
}
