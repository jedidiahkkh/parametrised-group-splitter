import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static PriorityQueue<RankedGrouping> generated = new PriorityQueue<>(1, Comparator.comparing(r -> r.score));
  static int SIZE;
  static int grid[][];
  static ArrayList<String> names = new ArrayList<>();
  static Set<Integer> fixed = new HashSet<>();

  static class RankedGrouping {
    Grouping g;
    double score;
    int lowest = 0;
    int lowestCount = 0;

    public RankedGrouping(Grouping g, double score) {
      this.g = g;
      this.score = score;
    }

    public RankedGrouping(Grouping g, double score, int lowest, int lowestCount) {
      this.g = g;
      this.score = score;
      this.lowest = lowest;
      this.lowestCount = lowestCount;
    }

    @Override
    public String toString() {
      return "RankedGrouping{" +
          "g=" + g +
          ", score=" + score +
          ", lowest=" + lowest +
          ", lowest_count=" + lowestCount +
          '}';
    }
  }

  public static void main(String[] args) throws IOException {
    Set<Integer> skip = new HashSet<>();
    BufferedReader br = new BufferedReader(new FileReader("data"));
    String line;
    int lineCount = 0;
    while ((line = br.readLine()) != null && !line.matches("=*")) {
      Scanner s = new Scanner(line);
      String name = s.next();
      System.out.println(name);
      if (name.equals("x")) {
        skip.add(lineCount);
        System.out.println("removed:" + lineCount+":"+s.next());
      }
      lineCount++;
    }
    br.close();

    final int DATASIZE = lineCount;
    SIZE = DATASIZE - skip.size();

    grid = new int[SIZE][SIZE];
    br = new BufferedReader(new FileReader("data"));
    int gridI = 0;
    for (int i = 0; i < DATASIZE; i++) {
      int gridJ = 0;
      Scanner s = new Scanner(br.readLine());
      if (skip.contains(i)) {
        continue;
      }
      names.add(s.next());
      for (int j = 0; j < i; j++) {
        if (skip.contains(j)) {
          s.nextInt();
          continue;
        }
        grid[gridI][gridJ++] = s.nextInt();
      }
      gridI++;
    }
    Grouping g = new Grouping();
    if (br.readLine() != null) {
      for (int i = 0; ; i++) {
        line = br.readLine();
        if (line == null) {
          break;
        }
        Scanner s = new Scanner(line);
        while(s.hasNext()) {
          int fixedName = names.indexOf(s.next());
          System.out.println("add "+fixedName+":"+i);
          g.add(fixedName, i);
          fixed.add(fixedName);
        }
      }
      bestGroup(0, g);
    } else {
      g.add(0, 0);
      bestGroup(1, g);
    }


    for (int i = 0; i < 10; i++) {
      System.out.println(generated.poll());
    }
  }

  public static void bestGroup(int member, Grouping cur) {
    if (member >= SIZE) {
      int maxGrpSize = Arrays.stream(cur.groups).mapToInt(s -> s.size()).max().orElseThrow();
      int minGrpSize = Arrays.stream(cur.groups).mapToInt(s -> s.size()).min().orElseThrow();
      if (maxGrpSize - minGrpSize >= 2) {
        return;
      }
      double average = 0, squareDiff = 0;
      int updated[][] = new int[SIZE][SIZE];
      int lowest = Integer.MAX_VALUE;
      int lowestCount = 0;
      for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < i; j++) {
          updated[i][j] = grid[i][j];
          if (cur.allocs.get(i) == cur.allocs.get(j)) {
            updated[i][j]++;
          }
          average += updated[i][j];
          lowest = Math.min(lowest, updated[i][j]);
        }
      }
      average /= SIZE * (SIZE - 1) / 2;
      for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < i; j++) {
          squareDiff += Math.pow((double) updated[i][j] - average, 2);
          if (updated[i][j] == lowest) {
            lowestCount++;
          }
        }
      }
      generated.add(new RankedGrouping(cur, squareDiff, lowest, lowestCount));
      return;
    }

    if (fixed.contains(member)) {
      bestGroup(member + 1, cur);
    } else {
      for (int groupNo = 0; groupNo < Grouping.COUNT; groupNo++) {
        Grouping g = new Grouping(cur);
        g.add(member, groupNo);
        bestGroup(member + 1, g);
      }
    }
  }

}
