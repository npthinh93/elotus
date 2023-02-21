import java.util.*;
import java.util.stream.*;

class Solution {

  public static final String ZERO = "0";
  public static final String ONE = "1";

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
  
  public static void main(String[] args) {
    List<Integer> grayCodes = grayCode(2);
  }
}
