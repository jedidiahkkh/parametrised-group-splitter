import java.util.*;

public class Grouping {

    final static int COUNT = 4;
    Set<String>[] groups;
    Map<Integer,Integer> allocs;

    public Grouping() {
        groups = new Set[COUNT];
        for (int i = 0; i < COUNT; i++) {
            groups[i] = new HashSet<>();
        }
        allocs = new HashMap<>();
    }

    public Grouping(Grouping g) {
        groups = new Set[COUNT];
        for (int i = 0; i < COUNT; i++) {
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
