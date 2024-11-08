import java.util.*;

public class Grouping {

    static int noOfGroups = 4;

    Set<String>[] groups;
    Map<Integer,Integer> allocs;

    public static void setNoOfGroups(int noOfGroups) {
        Grouping.noOfGroups = noOfGroups;
    }

    public Grouping() {
        groups = new Set[noOfGroups];
        for (int i = 0; i < noOfGroups; i++) {
            groups[i] = new HashSet<>();
        }
        allocs = new HashMap<>();
    }

    public Grouping(Grouping g) {
        groups = new Set[noOfGroups];
        for (int i = 0; i < noOfGroups; i++) {
            groups[i] = new HashSet<>(g.groups[i]);
        }
        allocs = new HashMap<>(g.allocs);
    }

    public void add(int member, int group) {
        groups[group].add(Main.names.get(member));
        allocs.put(member,group);
    }

    @Override
    public String toString() {
        return "Grouping{" +
                "groups=" + Arrays.toString(groups) +
                '}';
    }
}
