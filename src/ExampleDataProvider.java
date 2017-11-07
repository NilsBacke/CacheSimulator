import java.util.ArrayList;

public class ExampleDataProvider<T, U> implements DataProvider<T, U>{

    private ArrayList<ListElement<T, U>> _list;
    private int _numFetches;

    // keys and values must be the same lengths
    public ExampleDataProvider(ArrayList<T> keys, ArrayList<U> values) {
        _numFetches = 0;
        _list = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            _list.add(new ListElement<>(keys.get(i), values.get(i)));
        }
    }

    public U get(T key) {
        _numFetches++;
        for (ListElement ele : _list) {
            if (ele.getKey().equals(key)) {
                return (U) ele.getValue();
            }
        }
        return null;
    }

    public int getNumFetches() {
        return _numFetches;
    }

    private static class ListElement<T, U> {

        private T key;
        private U value;

        public ListElement(T key, U value) {
            this.key = key;
            this.value = value;
        }

        public T getKey() {
            return key;
        }

        public U getValue() {
            return value;
        }
    }
}
