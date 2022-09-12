import java.util.Random;

public class Person {
    private int nextFloor;

    public Person(int nextFloor) {
        this.nextFloor = nextFloor;
    }

    public int changeFloor(int maxFloor, int currentFloor) {
        do {
            nextFloor = new Random().nextInt(maxFloor + 1);
        } while (nextFloor == currentFloor || nextFloor == 0);
        return nextFloor;
    }

    public int getNextFloor() {
        return nextFloor;
    }
}
