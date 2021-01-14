import org.example.utils.threadpool.TaskQueue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TaskQueueTest {

    TaskQueue<String> queue;

    @Before
    public void before() {
        queue = new TaskQueue<>();
    }

    @Test
    public void add_element_success() {
        queue.add("asd");

        Assert.assertEquals("asd", queue.getFirst());
    }

    @Test(expected = NullPointerException.class)
    public void add_element_error_null_pointer() {
        queue.add(null);
    }

    @Test
    public void take_element_success() {
        queue.add("Test");
        queue.add("Test2");

        Assert.assertEquals("Test", queue.getFirst());
        queue.take();
        Assert.assertEquals("Test2", queue.getFirst());
    }
}
