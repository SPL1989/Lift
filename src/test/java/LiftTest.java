import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LiftTest {
    Building building = new Building(10);

    @Test
    void liftDoNotCarryMoreThanCapacity() {
        Set<Person> people = new HashSet<>() {{
            add(new Person(3));
            add(new Person(3));
            add(new Person(3));
        }};

        building.addPersons(2, people);

        Lift lift = new Lift(building, true, 1, 5,
                Set.of(
                        new Person(5),
                        new Person(5),
                        new Person(5)
                ));

        lift.makeStep();

        assertThat(lift.getPassengers().size()).isEqualTo(5);
        assertThat(building.getPeopleOnFloor(2).size()).isEqualTo(1);
    }

    @Test
    void liftChangeDirectionWhenRichLastFloor() {
        Set<Person> people = new HashSet<>() {{
            add(new Person(10));
            add(new Person(10));
            add(new Person(10));
        }};
        Lift lift = new Lift(building, true, 9, 5,  people);

        lift.makeStep();

        assertThat(lift.getCurrentFloor()).isEqualTo(10);
        assertThat(lift.getPassengers()).isEmpty();
        assertThat(building.getPeopleOnFloor(10).size()).isEqualTo(3);

        lift.makeStep();

        assertThat(lift.getCurrentFloor()).isEqualTo(9);
    }

    @Test
    void liftChangeDirectionWhenRichFirstFloor() {
        Set<Person> people = new HashSet<>() {{
            add(new Person(1));
            add(new Person(1));
            add(new Person(1));
        }};
        Lift lift = new Lift(building, false, 2, 5, people);

        lift.makeStep();

        assertThat(lift.getCurrentFloor()).isEqualTo(1);
        assertThat(lift.getPassengers()).isEmpty();
        assertThat(building.getPeopleOnFloor(1).size()).isEqualTo(3);

        lift.makeStep();

        assertThat(lift.getCurrentFloor()).isEqualTo(2);
    }

    @Test
    void liftMoveToMaxPassengersFloor() {
        Lift lift = new Lift(building, true, 1, 5,
                Set.of(
                        new Person(8)
                ));
        int steps = 0;
        do {
            lift.makeStep();
            steps++;
        } while (steps < 7);
        assertThat(lift.getCurrentFloor()).isEqualTo(8);
        assertThat(lift.getPassengers()).isEmpty();
        assertThat(building.getPeopleOnFloor(8).size()).isEqualTo(1);
    }

    @Test
    void liftChooseDirectionOfMajority() {
        Set<Person> passengers = new HashSet<>() {{
            add(new Person(5));
            add(new Person(5));
            add(new Person(5));
        }};
        Set<Person> peopleOnFlor = new HashSet<>() {{
            add(new Person(7));
            add(new Person(4));
            add(new Person(1));
            add(new Person(1));
            add(new Person(1));
        }};
        building.addPersons(5, peopleOnFlor);
        Lift lift = new Lift(building, true, 4, 5, passengers);

        lift.makeStep();

        assertThat(lift.getCurrentFloor()).isEqualTo(5);
        assertThat(lift.getPassengers().size()).isEqualTo(4);
        assertThat(building.getPeopleOnFloor(5).size()).isEqualTo(4);
    }

    @Test
    void generalTest() {
        Set<Person> firstFloor = new HashSet<>() {{
            add(new Person(2));
            add(new Person(4));
            add(new Person(6));
        }};
        Set<Person> secondFloor = new HashSet<>() {{
            add(new Person(7));
            add(new Person(4));
            add(new Person(1));
            add(new Person(1));
            add(new Person(1));
        }};
        building.addPersons(2, secondFloor);
        Set<Person> thirdFloor = new HashSet<>() {{
            add(new Person(7));
            add(new Person(7));
            add(new Person(7));
            add(new Person(7));
            add(new Person(1));
            add(new Person(1));
            add(new Person(1));
        }};
        building.addPersons(3, thirdFloor);

        //fourth - sixth floors are empty

        Set<Person> seventhFloor = new HashSet<>() {{
            add(new Person(10));
            add(new Person(10));
            add(new Person(10));
            add(new Person(1));
            add(new Person(1));
            add(new Person(1));
            add(new Person(1));
            add(new Person(1));
            add(new Person(1));
        }};
        building.addPersons(7, seventhFloor);

        Lift lift = new Lift(building, true, 1, 5, firstFloor);

        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(2);
        assertThat(lift.getPassengers().size()).isEqualTo(4);
        assertThat(building.getPeopleOnFloor(2).size()).isEqualTo(4);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(3);
        assertThat(lift.getPassengers().size()).isEqualTo(5);
        assertThat(building.getPeopleOnFloor(3).size()).isEqualTo(6);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(4);
        assertThat(lift.getPassengers().size()).isEqualTo(3);
        assertThat(building.getPeopleOnFloor(4).size()).isEqualTo(2);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(5);
        assertThat(lift.getPassengers().size()).isEqualTo(3);
        assertThat(building.getPeopleOnFloor(5).size()).isEqualTo(0);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(6);
        assertThat(lift.getPassengers().size()).isEqualTo(2);
        assertThat(building.getPeopleOnFloor(6).size()).isEqualTo(1);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(7);
        assertThat(lift.getPassengers().size()).isEqualTo(5);
        assertThat(building.getPeopleOnFloor(7).size()).isEqualTo(6);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(6);
        assertThat(lift.getPassengers().size()).isEqualTo(5);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(5);
        assertThat(lift.getPassengers().size()).isEqualTo(5);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(4);
        assertThat(lift.getPassengers().size()).isEqualTo(5);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(3);
        assertThat(lift.getPassengers().size()).isEqualTo(5);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(2);
        assertThat(lift.getPassengers().size()).isEqualTo(5);
        lift.makeStep();
        assertThat(lift.getCurrentFloor()).isEqualTo(1);
        assertThat(lift.getPassengers().size()).isEqualTo(0);
    }
}