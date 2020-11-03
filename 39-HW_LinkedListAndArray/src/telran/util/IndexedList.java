package telran.util;

public interface IndexedList <T> {
	
void add(T obj);
boolean add (int index, T obj);
T remove (int index);
T get (int index);
int indexOf (Object pattern);
int lastIndexOf(Object pattern);
int size();
void reverse();
/**
 * removes first object equals pattern
 * @param pattern
 * @return reference to a removed object or null
 */
T remove (Object pattern);
/**
 * removes all object equaled patterns
 * @param patterns
 * @return true at least one object has been removed otherwise false
 */
boolean removeAll(IndexedList <T> patterns);
/**
 * checks if object like pattern exists
 * @param pattern
 * @return true if exists otherwise false
 */
boolean contains(T pattern);
/**
 * removes all object not equaled from given 	patterns
 * @param patterns
 * @return true at least one object has been removed otherwise false
 */
boolean retainAll(IndexedList <T> patterns);
}
