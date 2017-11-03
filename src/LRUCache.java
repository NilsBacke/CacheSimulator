import java.util.*;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	private int _numMisses, _capacity;
	private Map<T, ListElement<T, U>> _hashmap; // weakest type
	private Deque<ListElement<T, U>> _linkedList; // weakest type
	private DataProvider<T, U> _dataProvider;

	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (DataProvider<T, U> provider, int capacity) {
		_numMisses = 0;
		_hashmap = new HashMap<>(capacity);
		_linkedList = new LinkedList<>();
		_dataProvider = provider;
		_capacity = capacity;
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
		ListElement element;
		// if cache hit
		if (_hashmap.containsKey(key)) {
			 element = _hashmap.get(key);
			_linkedList.remove(element);
			_linkedList.addFirst(element);
		} else {
			_numMisses++;
			final U value = _dataProvider.get(key);
			element = new ListElement<>(key, value);
			_hashmap.put(key, element);
			_linkedList.addFirst(element);
		}

		if (_linkedList.size() > _capacity) {
			final ListElement<T, U> lastElement = _linkedList.removeLast();
			_hashmap.remove(lastElement.getKey());
		}

		return (U) element.getValue();
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return _numMisses;
	}
}
