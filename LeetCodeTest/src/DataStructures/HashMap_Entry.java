package DataStructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.org.apache.xalan.internal.utils.Objects;

/**
 * HashMap_Entry:HashMap中实现的Entry也是HashMap的基本单元
 * @author	 JM
 * @date 	 2017-3-27 下午10:14:48
 * @since	 JDK 1.7
 */
public class HashMap_Entry<K,V> implements Map.Entry<K,V>{
	
	final K key;
	V value;
	Entry<K,V> next;
	int hash;
	
	
	public HashMap_Entry( int hash,K key, V value, Entry<K, V> next) {
		super();
		this.key = key;
		this.value = value;
		this.next = next;
		this.hash = hash;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V newValue) {
		V oldValue = value;
		value = newValue;
		return oldValue;
	}
	
	public final boolean equals(Object o){
		if(!(o instanceof Map.Entry))
			return false;
		@SuppressWarnings("unchecked")
		Map.Entry<K,V> e = (Map.Entry<K,V>)o;
		Object k1 = getKey();
		Object k2 = e.getKey();
		if(k1 == k2 || (k1 != null && k1.equals(k2))){//键相等
			Object v1 = getValue();
			Object v2 = e.getValue();
			if(v1 == v2 || v1 != null && v1.equals(v2))//值相等
				return true;
		}
		return false;//比较HashMap的Entry要满足键相等和值相等两个条件才能相等
	}
	
	public final int hashCode(){//Entry的hashCode是Entry的键和值的位运算得来的
		return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
	}
	
    public final String toString() {
        return getKey() + "=" + getValue();
    }

    /**
     * This method is invoked whenever the value in an entry is
     * overwritten by an invocation of put(k,v) for a key k that's already
     * in the HashMap.
     */
    void recordAccess(HashMap<K,V> m) {
    }

    /**
     * This method is invoked whenever the entry is
     * removed from the table.
     */
    void recordRemoval(HashMap<K,V> m) {
    }

}
