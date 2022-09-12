import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Building {
    private final Integer floors;
    private final Map<Integer, Set<Person>> peopleOnFloors;

    public Building(Integer floors) {
        this.floors = floors;
        peopleOnFloors = new HashMap<>();
        for (int i = 1; i <= floors; i++) {
            peopleOnFloors.put(i, new HashSet<>());
        }
    }

    public Integer getFloors() {
        return floors;
    }

    public Set<Person> getPeopleOnFloor(int floor) {
        return peopleOnFloors.get(floor);
    }

    public void addPersons(int floor, Set<Person> persons) {
        peopleOnFloors.get(floor).addAll(persons);
    }

}
