import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        Random rnd = new Random();
        int floors = rnd.nextInt(21 - 5) + 5;
        Building building = new Building(floors);

        for(int i = 1; i <= floors; i++) {
            int peopleOnFloor = rnd.nextInt(11);
            Set<Person> people = new HashSet<>();
            for (int j = 0; j < peopleOnFloor; j++) {
                Person person = new Person(i);
                person.changeFloor(floors, i);
                people.add(person);
            }
            building.addPersons(i, people);
        }

        Set<Person> firstPassengers = new HashSet<>(building.getPeopleOnFloor(1));
        building.getPeopleOnFloor(1).removeAll(firstPassengers);

        Lift lift = new Lift(building, true, 1, 5, firstPassengers);

        int steps = 0;

        do {
            lift.makeStep();
            steps++;
        } while (steps < 20);
    }
}
