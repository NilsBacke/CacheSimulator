import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	@Test
	public void leastRecentlyUsedIsCorrect () {

		final ArrayList<Integer> listOfNum = generateNumbers(0, 10);
		final ArrayList<String> listOfStrings = generateStrings('a', 'j');


		ExampleDataProvider<Integer,String> provider = new ExampleDataProvider<>(listOfNum, listOfStrings);
		Cache<Integer,String> cache = new LRUCache<>(provider, 5);

		System.out.println(cache.getNumMisses());
		System.out.println(cache.get(0));
		System.out.println(cache.getNumMisses());
		System.out.println(provider.getNumFetches());
		System.out.println(cache.get(0));
		System.out.println(cache.getNumMisses());
		System.out.println(provider.getNumFetches());
	}

	private ArrayList<Integer> generateNumbers(int start, int end) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = start; i < end; i++) {
			list.add(i);
		}
		return list;
	}

	private ArrayList<String> generateStrings(char start, char end) {
		ArrayList<String> list = new ArrayList<>();
		for (char c = start; c <= end; c++) {
			list.add(Character.toString(c));
		}
		return list;
	}
}
