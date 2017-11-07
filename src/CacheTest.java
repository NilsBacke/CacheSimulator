import static org.junit.Assert.*; 
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	ArrayList<Integer> listOfNum;
	ArrayList<String> listOfStrings;
	ExampleDataProvider<Integer,String> provider;
	Cache<Integer,String> cache;
	
	@Before
	public void initialize() {

		listOfNum = generateNumbers(0, 10);
		listOfStrings = generateStrings('a', 'j');


		provider = new ExampleDataProvider<>(listOfNum, listOfStrings);
		cache = new LRUCache<>(provider, 5);

		fillCache();
//		System.out.println(cache.getNumMisses());
//		System.out.println(cache.get(0));
//		System.out.println(cache.getNumMisses());
//		System.out.println(provider.getNumFetches());
//		System.out.println(cache.get(0));
//		System.out.println(provider.getNumFetches());
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
	
	private void fillCache(){
		for(int i=0;i<5;i++){
			cache.get(i);
		}
	}
	
	@Test
	public void fillRestOfCacheAndTestNumMisses(){
		assertEquals(cache.getNumMisses(), 5);
	}
	
	@Test
	public void callInCache(){
		cache.get(0);
		assertEquals(cache.getNumMisses(), 5);
	}
	
	@Test
	public void callOutCache(){
		cache.get(5);
		assertEquals(cache.getNumMisses(), 6);
	}
	
	@Test
	public void callRemovedObject(){
		cache.get(5);
		assertEquals(cache.getNumMisses(), 6);
		cache.get(0);
		assertEquals(cache.getNumMisses(), 7);
	}
	
	
}
