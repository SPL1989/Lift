import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    @RepeatedTest(5)
    void changeFloor() {
        Person person = new Person(2);
        assertThat(person.changeFloor(2, 1))
                .isNotEqualTo(1);
    }
}