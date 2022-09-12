import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.partitioningBy;

public class Lift {
    private final int capacity;
    private final Building building;
    private final Set<Person> passengers = new HashSet<>();
    private int currentFloor;
    private Boolean directionUp;
    private int step = 1;

    public Lift(Building building, boolean isUp, int floor, int capacity, Set<Person> passengers) {
        this.building = building;
        currentFloor = floor;
        directionUp = isUp;
        this.passengers.addAll(passengers);
        this.capacity = capacity;
        printStatus(0);
    }

    public Set<Person> getPassengers() {
        return passengers;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void makeStep() {
        currentFloor = directionUp ? currentFloor + 1 : currentFloor - 1;
        Set<Person> toMoveOut = moveOut(passengers, currentFloor);
        Set<Person> toMoveIn = moveIn(building.getPeopleOnFloor(currentFloor), directionUp, capacity - passengers.size());
        passengers.addAll(toMoveIn);
        building.addPersons(currentFloor, toMoveOut);
        toMoveOut.forEach(p -> p.changeFloor(building.getFloors(), currentFloor));
        printStatus(toMoveOut.size());
    }

    private void printStatus(int movedOut) {
        System.out.printf("\n   *** Step %d ***\n", step++);
        for (int i = building.getFloors(); i > 0; i--) {
            String direction = "";
            String passengers = "";
            String out = "";
            if (i == currentFloor) {
                out = String.valueOf(movedOut);
                direction = directionUp ? "^" : "v";
                passengers = this.passengers.stream()
                        .map(Person::getNextFloor)
                        .map(String::valueOf)
                        .collect(Collectors.joining("\s"));
            }
            String waiting = building.getPeopleOnFloor(i).stream()
                    .map(Person::getNextFloor)
                    .map(String::valueOf)
                    .collect(Collectors.joining("\s"));
            System.out.printf("%2s|%-1s%-14s%-1s|%-10s\n", out, direction, passengers, direction, waiting);
        }
    }

    Set<Person> moveOut(Set<Person> passengers, int floor) {
        Set<Person> toMoveOut = passengers.stream()
                .filter(p -> p.getNextFloor() == floor)
                .collect(Collectors.toSet());
        passengers.removeAll(toMoveOut);
        if (passengers.isEmpty()) {
            directionUp = null;
        }
        return toMoveOut;
    }

    Set<Person> moveIn(Set<Person> peopleOnFloor, Boolean direction, int limit) {
        if (direction == null) {
            directionUp = peekPassengersDirection(peopleOnFloor);
        }
        Set<Person> toMoveIn;

        Predicate<Person> more = p -> p.getNextFloor() > currentFloor;
        Predicate<Person> less = p -> p.getNextFloor() < currentFloor;

        toMoveIn = peopleOnFloor.stream()
                .filter(directionUp ? more : less)
                .limit(limit)
                .collect(Collectors.toSet());
        peopleOnFloor.removeAll(toMoveIn);

        return toMoveIn;
    }

    private boolean peekPassengersDirection(Set<Person> people) {
        Map<Boolean, List<Person>> majority = people.stream()
                .collect(partitioningBy(p -> p.getNextFloor() > currentFloor));
        long peopleWhoNeedsUp = majority.get(TRUE).size();
        long peopleWhoNeedsDown = majority.get(FALSE).size();
        if (peopleWhoNeedsDown == peopleWhoNeedsUp || people.isEmpty()) {
            return currentFloor <= building.getFloors() / 2;
        }
        return peopleWhoNeedsUp > peopleWhoNeedsDown;
    }
}
