import java.util.*;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	private int _numMisses, _capacity;
	private Map<T, Node<T, U>> _hashmap; // weakest type
	private DataProvider<T, U> _dataProvider;
	private Node _head, _tail;

	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (DataProvider<T, U> provider, int capacity) {
		_numMisses = 0;
		_hashmap = new HashMap<>(capacity);
		_dataProvider = provider;
		_capacity = capacity;
		_head = null;
		_tail = null;
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
		Node node;
		// if cache hit
		if (_hashmap.containsKey(key)) {
			node = _hashmap.get(key); // gets the requested node
			remove(node); // removes the requested node from the linked list
			addToFront(node); // adds the requested node to the front of the linked list
		} else { // if cache miss
			_numMisses++;
			final U value = _dataProvider.get(key); // get value from data provider
			node = new Node<>(key, value); // instantiate a new node with the requested key and returned value
			_hashmap.put(key, node); // adds the new node to the hashmap
			addToFront(node); // adds the new node to the front of the linked list
		}

		// if cache is full
		if (_hashmap.size() > _capacity) {
			final Node nodeBeingRemoved = _tail;
			remove(_tail); // remove the LRU node from the linked list
			_hashmap.remove(nodeBeingRemoved.getKey()); // remove that same node from the hashmap
		}

		return (U) node.getValue();
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return _numMisses;
	}

	/**
	 * Adds the given node to the front of the doubly linked list
	 * @param node The node that will be added to the front of the doubly linked list
	 */
	private void addToFront(Node node) {
		if (_head == null) {
			_head = node;
			_tail = node;
		} else {
			Node newSecondNode = _head;
			_head = node;
			newSecondNode._prev = node;
			_head._next = newSecondNode;
		}
	}

	/**
	 * Removes the given node from the doubly linked list
	 * @param node The node intended to be removed from the doubly linked list
	 */
	private void remove(Node node) {
		if (node == _head) {
			_head._next._prev = null;
			_head = _head._next;
		} else if (node == _tail) {
			_tail._prev._next = null;
			_tail._prev = _tail;
		} else {
			node._prev._next = node._next;
			node._next._prev = node._prev;
		}
	}

	/**
	 * A single element used in the implementation of the linked list and hashmap
	 * @param <T> The key data type
	 * @param <U> The value data type
	 */
	private class Node<T, U> {

		private T _key;
		private U _value;
		Node<T, U> _next, _prev;

		private Node(T key, U value) {
			this._key = key;
			this._value = value;
			this._prev = null;
			this._next = null;
		}

		private T getKey() {
			return _key;
		}

		private U getValue() {
			return _value;
		}
	}

}