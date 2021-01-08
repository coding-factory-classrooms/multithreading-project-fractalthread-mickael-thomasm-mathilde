import org.example.utils.cache.LRUCache;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class LRUCacheTest {

    @Test
    public void get_all_elements() {
        String[] strings = {
            "Test",
            "Test1"
        };

        List<String> expected = new ArrayList<>();
        for (int i = strings.length - 1; i >= 0; i--) {
            expected.add(strings[i]);
        }

        LRUCache<String, String> cache = new LRUCache<>();
        for (int i = 0; i < strings.length; i++) {
            cache.add(String.valueOf(i), strings[i]);
        }

        Assert.assertEquals(expected, cache.getAll());
    }

    @Test
    public void add_element_success() {
        String text = "Test";
        List<String> expected = new ArrayList<>();
        expected.add(text);

        LRUCache<String, String> cache = new LRUCache<>();
        cache.add("1", text);

        Assert.assertEquals(expected, cache.getAll());
    }

    @Test(expected = InvalidParameterException.class)
    public void null_add_element_error() {
        LRUCache<String, String> cache = new LRUCache<>();
        cache.add("1", null);
    }

    @Test
    public void add_more_than_15_element_success() {
        String[] strings = new String[16];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = "Test" + i;
        }

        List<String> expected = new ArrayList<>();
        for (int i = strings.length - 1; i > 0; i--) {
            expected.add(strings[i]);
        }

        LRUCache<String, String> cache = new LRUCache<>();
        for (int i = 0; i < strings.length; i++) {
            cache.add(String.valueOf(i), strings[i]);
        }

        Assert.assertEquals(expected, cache.getAll());
    }

    @Test
    public void get_an_element_success() {
        String[] strings = {
                "Test",
                "Test1",
                "Test2",
                "Test3",
                "Test4",
                "Test5"
        };

        LRUCache<String, String> cache = new LRUCache<>();
        for (int i = 0; i < strings.length; i++) {
            cache.add(String.valueOf(i), strings[i]);
        }

        Assert.assertEquals(strings[strings.length - 1], cache.get("5"));
    }

    @Test
    public void get_an_inexisting_element_return_null() {
        LRUCache<String, String> cache = new LRUCache<>();

        Assert.assertNull(cache.get("0"));
    }

    @Test
    public void reset_cache_success() {
        String[] strings = {
            "Test"
        };

        LRUCache<String, String> cache = new LRUCache<>();
        cache.add("1", strings[0]);

        Assert.assertEquals(1, cache.size());

        cache.reset();

        Assert.assertEquals(0, cache.size());
    }

    @Test
    public void contains_success() {
        String[] strings = {
            "Test"
        };

        LRUCache<String, String> cache = new LRUCache<>();

        Assert.assertFalse(cache.contains("1"));
        cache.add("1", strings[0]);
        Assert.assertTrue(cache.contains("1"));
    }

    @Test
    public void return_correct_size() {
        String[] strings = {
            "Test",
            "Test1"
        };

        LRUCache<String, String> cache = new LRUCache<>();
        for (int i = 0; i < strings.length; i++) {
            cache.add(String.valueOf(i), strings[i]);
        }

        Assert.assertEquals(strings.length, cache.size());
    }

}
