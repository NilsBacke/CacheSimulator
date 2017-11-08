import static org.junit.Assert.*; 
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	private ArrayList<Integer> listOfNum;
	private ArrayList<String> listOfStrings;
	private ExampleDataProvider<Integer,String> provider;
	private Cache<Integer,String> cache;
	
	@Before
	public void initialize() {

		listOfNum = generateNumbers(0, 10);
		listOfStrings = generateStrings('a', 'j');

		provider = new ExampleDataProvider<>(listOfNum, listOfStrings);
		cache = new LRUCache<>(provider, 5);

		assertEquals(cache.getNumMisses(), 0); // numMisses is initialized as 0

		cache = fillCache(cache);
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
	
	private Cache fillCache(Cache cache1){
		for(int i=0;i<5;i++){
			cache1.get(i);
		}
		return cache1;
	}
	@Test
	public void fillCacheAndTestNumMisses(){
		assertEquals(cache.getNumMisses(), 5);
		assertEquals(provider.getNumFetches(), 5);
	}
	
	@Test
	public void callInCache(){
		cache.get(0);
		assertEquals(cache.getNumMisses(), 5);
		assertEquals(provider.getNumFetches(), 5);
	}
	
	@Test
	public void callOutCache(){
		cache.get(5);
		assertEquals(cache.getNumMisses(), 6);
		assertEquals(provider.getNumFetches(), 6);
	}
	
	@Test
	public void callRemovedObject(){
		cache.get(5);
		assertEquals(cache.getNumMisses(), 6);
		assertEquals(provider.getNumFetches(), 6);
		cache.get(0);
		assertEquals(cache.getNumMisses(), 7);
		assertEquals(provider.getNumFetches(), 7);
	}

	@Test
	public void fillCacheThenCallLRUObject() {
		ExampleDataProvider<Integer, String> testProvider = new ExampleDataProvider<>(listOfNum, listOfStrings);
		Cache<Integer, String> testCache = new LRUCache<>(testProvider, 5);
		assertEquals(testCache.getNumMisses(), 0);

		// filling the new cache
		testCache = fillCache(testCache);

		assertEquals(testCache.get(6), "g"); // from data provider
		assertEquals(testCache.getNumMisses(), 6);
		assertEquals(testProvider.getNumFetches(), 6);
		
		assertEquals(testCache.get(6), "g"); // from cache
		assertEquals(testCache.getNumMisses(), 6);
		assertEquals(testProvider.getNumFetches(), 6);
	}
	
	
}
