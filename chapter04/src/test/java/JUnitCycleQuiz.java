import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

public class JUnitCycleQuiz {


    @BeforeEach
    public void hello(){
        System.out.println("Hello!");
    }

    @AfterAll
    public static void bye(){
        System.out.println("Bye!");
    }
}
