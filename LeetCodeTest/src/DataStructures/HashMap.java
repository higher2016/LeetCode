package DataStructures;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.sun.org.apache.xalan.internal.utils.Objects;

public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {

	/**
	 * 默认初始容量是16,容量是哈希表中桶(Entry数组)的数量，初始容量只是哈希表在创建时的容量。
	 */
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

	static final int MAXIMUM_CAPACITY = 1 << 30;

	/**
	 * 默认的加载因子大小(默认值是0.75)<br/>
	 * 加载因子是哈希表在其容量自动增加之前可以达到多满的一种尺度. 当哈希表中的条目数超出了加载因子与当前容量的乘积时，通过调用 rehash
	 * 方法将容量翻倍。
	 */
	static final float DEFAULT_LOAD_FACTOR = 0.75f;

	static final Entry<?, ?>[] EMPTY_TABLE = {};

	/**
	 * Entry数组，哈希表，长度必须为2的幂,这个东西就是存放HashMap所有键值对的数组
	 */
	@SuppressWarnings("unchecked")
	transient Entry<K, V>[] table = (Entry<K, V>[]) EMPTY_TABLE;

	/**
	 * 已存元素的个数,注意size的大小跟table的长度是不一定一致的！！！这个是HashMap使用hash原理和链表来储存键值对造成的
	 */
	transient int size;

	/**
	 * 下次扩容的临界值（阈值），size>=threshold就会扩容(capacity * load factor).
	 */
	int threshold;

	/**
	 * HashMap对象的实际加载因子大小
	 */
	final float loadFactor;

	/**
	 * 该HashMap结构被修改过的次数（包括扩容修改其映射数(添加键值对)或内部结构）
	 */
	transient int modCount;

	/**
	 * 默认的 threshold
	 */
	static final int ALTERNATIVE_HASHING_THRESHOLD_DEFAULT = Integer.MAX_VALUE;

	private static class Holder {
		static final int ALTERNATIVE_HASHING_THRESHOLD;
		static {
			// 读取 Sun定义的threshold的值
			String altThreshold = java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("jdk.map.althashing.threshold"));
			int threshold;
			try {
				threshold = (null != altThreshold) ? Integer.parseInt(altThreshold) : ALTERNATIVE_HASHING_THRESHOLD_DEFAULT;

				// disable alternative hashing if -1
				if (threshold == -1) {
					threshold = Integer.MAX_VALUE;
				}

				if (threshold < 0) {
					throw new IllegalArgumentException("value must be positive integer.");
				}
			} catch (IllegalArgumentException failed) {
				throw new Error("Illegal value for 'jdk.map.althashing.threshold'", failed);
			}
			ALTERNATIVE_HASHING_THRESHOLD = threshold;// 虚拟机各自实现中，也会有对应的
														// threshold 设置
		}
	}

	transient int hashSeed = 0;

	/**
	 * 在HashMap所有构造函数都会调用该构造函数（HashMap的母构造函数）
	 * 
	 * @param initialCapacity
	 *            ：初始化数组长度
	 * @param loadFactor
	 *            ：加载因子
	 */
	public HashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);

		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;

		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

		this.loadFactor = loadFactor;
		threshold = initialCapacity;
		init();// 因为所有的构造函数都会调用该构造函数，所以直接在这里进行相关初始化
	}

	public HashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	public HashMap(Map<? extends K, ? extends V> m) {
		this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
		inflateTable(threshold);// 扩充map的容量

		putAllForCreate(m);// 将传入的map对象的所有元素放进到新建的HashMap对象中
	}

	private void putAllForCreate(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			putForCreate(e.getKey(), e.getValue());
	}

	/**
	 * 创建节点Entry<K,V>并加入到table数组
	 * 
	 * @param key
	 * @param value
	 */
	private void putForCreate(K key, V value) {
		int hash = null == key ? 0 : hash(key);// 计算key的hash值
		int i = indexFor(hash, table.length);// 计算根据key的hash值和table的长度计算key-value在数组中的位置

		// 此处迭代原因就是为了防止存在相同的key值，若发现两个hash值（key）相同时，HashMap的处理方式是用新value替换旧value，这里并没有处理key，这就解释了HashMap中没有两个相同的key。
		for (Entry<K, V> e = table[i]; e != null; e = e.next) { // 判断该条链上是否有hash值相同的(key相同)若存在相同，则直接覆盖value
			Object k;
			if (e.hash == hash/** 存放的地方相同 */
			&& ((k = e.key) == key || (key != null && key.equals(k)))/** key相同 */
			) {
				e.value = value;
				return;
			}
		}
		createEntry(hash, key, value, i);// 根据key的hash值，发现table指定位置上没有Entry<K,V>对象就直接加进去，加进去的Entry<K,V>对象的next引用指向自身
	}

	/**
	 * 直接新建一个Entry<K,V>对象，并插入到table[bucketIndex]的位置<br/>
	 * 将原来这个位置的对象的引用（为空就是null）放到新插入的Entry<K,V>对象的next节点中，新的Entry<K,V>直接就在table[
	 * bucketIndex]这个位置上
	 * 
	 * @param hash
	 *            ：key的HashMap计算的hash值
	 * @param key
	 * @param value
	 * @param bucketIndex
	 *            ：该Entry<K,V>对象在table数组中的位置
	 */
	void createEntry(int hash, K key, V value, int bucketIndex) {
		Entry<K, V> e = table[bucketIndex];// 如果原来这个位置上有对象（其实有没有都一样）就将他的引用放到新插入的Entry<K,V>对象的next节点中
		table[bucketIndex] = new Entry<K, V>(hash, key, value, e);//新建节点，并加到table的相应位置上
		size++;
	}

	/**
	 * 计算根据key的hash值和table的长度计算key-value在数组中的位置
	 * 
	 * @param hash
	 *            ：key的HashMap计算的hash值
	 * @param length
	 *            ：table的长度
	 * @return
	 */
	static int indexFor(int hash, int length) {
		// 这一段代码的作用非常重要：均匀分布table数据并且充分利用空间。
		// 一个萝卜一个坑，尽量使得数组table的每个位置上的元素数量只有一个，那么当我们用hash算法求得这个位置的时候，马上就可以知道对应位置的元素就是我们要的，而不用再去遍历链表。
		return hash & (length - 1);
	}

	/**
	 * 计算键的HashMap的hash码
	 * 
	 * @param k
	 * @return
	 */
	final int hash(Object k) {// 计算键的HashMap的hash码
		int h = hashSeed;
		if (0 != h && k instanceof String) {
			return sun.misc.Hashing.stringHash32((String) k);
		}
		h ^= k.hashCode();// HashMap的键所需要的hash值并不是直接Object.hashCode()值

		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	/**
	 * 该函数的作用是：如果输入的number是2的幂次方就放回number，否则就返回比这个数大且又最接近这个数的2的幂次方数（例如：输入1返回1，
	 * 输入2返回2，输入3返回4，输入19返回32）
	 * 
	 * @param number
	 * @return
	 */
	private static int roundUpPowerOf(int number) {
		int rounded = number >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : (rounded = Integer.highestOneBit(number)) != 0 ? (Integer.bitCount(number) > 1) ? rounded << 1 : rounded : 1;
		return rounded;
	}

	/**
	 * 扩容，将HashMap对象的容积扩大的方法(并不是将容积扩大两倍)
	 * 
	 * @param toSize
	 */
	@SuppressWarnings("unchecked")
	private void inflateTable(int toSize) {
		int capacity = roundUpPowerOf(toSize);

		threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
		table = new Entry[capacity];// map的容积扩大
		initHashSeedAsNeeded(capacity);
	}

	final boolean initHashSeedAsNeeded(int capacity) {
		boolean currentAltHashing = hashSeed != 0;
		boolean useAltHashing = sun.misc.VM.isBooted() && (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
		boolean switching = currentAltHashing ^ useAltHashing;
		if (switching) {
			hashSeed = useAltHashing ? sun.misc.Hashing.randomHashSeed(this) : 0;
		}
		return switching;
	}

	private void init() {
	}

	/**
	 * 返回该HashMap对象的key-value的对数（注意不一定等于table的长度）
	 */
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * <strong>根据key获取value</strong>
	 */
	public V get(Object key) {
		if (key == null)
			return getForNullKey();// 果然key为null是一个特殊的存在

		Entry<K, V> entry = getEntry(key);
		return null == entry ? null : entry.value;
	}

	/**
	 * 根据key获取table数组某个位置下链表中的某个Entry<K,V>的对象
	 * 
	 * @param key
	 * @return
	 */
	final Entry<K, V> getEntry(Object key) {
		if (size == 0)
			return null;

		int hash = (key == null) ? 0 : hash(key);
		for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {//
			Object k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))// key的HashMap的hash值相同，且equals(),才算是该键对应的值
				return e;
		}
		return null;
	}

	/**
	 * 获取key为null的value。<br/>
	 * 注意：当key==0的时候它也是储存在table[0]这个位置
	 * 
	 * @return
	 */
	private V getForNullKey() {
		if (size == 0)
			return null;
		for (Entry<K, V> e = table[0]; e != null; e = e.next) {
			if (e.key == null)// 注意，当key==0的时候它也是储存在table[0]这个位置
				return e.value;
		}
		return null;
	}

	/**
	 * 重点在返回值上面,如果是直接在HashMap中新增键值对而不是替换键值对，就会返回null。否则返回被替换掉的value的值<br/>
	 * the previous value associated with <tt>key</tt>, or <tt>null</tt> if
	 * there was no mapping for <tt>key</tt>. (A <tt>null</tt> return can also
	 * indicate that the map previously associated <tt>null</tt> with
	 * <tt>key</tt>.
	 */
	public V put(K key, V value) {
		if (table == EMPTY_TABLE)
			inflateTable(threshold);//扩容，将HashMap对象中的table的容量扩大
		if (key == null)
			return putForNullKey(value);
		int hash = hash(key);
		
		//根据key的hash值和table的长度计算这对key-value在数组中的位置，也就是通过hash值快速插入（这样就可以快速判断table中是否存在相同的key，而不是将table遍历一次）。
		//这样插入key-value直接找到table indexFor(hash, table.length)位置，然后判断链表中所有的Entry的key是否有重复的
		int i = indexFor(hash, table.length);
		
		//遍历table[i]中是否存在
		for (Entry<K, V> e = table[i]; e != null; e = e.next) {
			if (e.hash == hash && (e.key == key || key.equals(e.key))) {
				// table中存在key与新加进来的key的hash值相等（这并不代表两个key相等）且两者相等，则将该键值对的值替换为新的value（key不做任何变化）
				V oldValue = e.value;
				e.value = value;
				e.recordAccess(this);
				return oldValue;
			}
		}
		modCount++;//表示HashMap结构修改的次数
		addEntry(hash, key, value, i);
		return null;// 所以说如果是直接在HashMap中新增键值对而不是替换键值对，就会返回null
	}

	/**
	 * 对于key==null的情况，HashMap默认这对键值对储存在table[0]位置
	 * 
	 * @param value
	 * @return
	 */
	private V putForNullKey(V value) {
		for (Entry<K, V> e = table[0]; e != null; e = e.next) {
			if (e.key == null) {// 替换原有的value，并返回该value
				V oldValue = e.value;
				e.value = value;
				e.recordAccess(this);
				return oldValue;
			}
		}

		modCount++;
		addEntry(0, null, value, 0);
		return null;
	}

	/**
	 * 这个方法可能会将table的长度增长为原来的两倍
	 * 
	 * @param hash
	 * @param key
	 * @param value
	 * @param bucketIndex
	 *            ：该键值对储存在table数组的位置（下标）
	 */
	void addEntry(int hash, K key, V value, int bucketIndex) {
		if ((size >= threshold) && (null != table[bucketIndex])) {// 当map键值对数量达到阈值将会自动扩大table的容量
			resize(2 * table.length);
			hash = (null != key) ? hash(key) : 0;
			bucketIndex = indexFor(hash, table.length);
		}
		createEntry(hash, key, value, bucketIndex);
	}

	/**
	 * 将table的容量增大到新的容量（newCapacity）
	 * 
	 * @param newCapacity
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void resize(int newCapacity) {
		Entry[] oldTable = table;
		int oldCapacity = oldTable.length;
		if (oldCapacity == MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}

		Entry[] newTable = new Entry[newCapacity];
		transfer(newTable, initHashSeedAsNeeded(newCapacity));
		table = newTable;
		threshold = (int) Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);

	}

	/**
	 * 将原来table的所有Entry<K,V>搬到新的newTable中<br/>
	 * 
	 * @param newTable
	 * @param initHashSeedAsNeeded
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void transfer(Entry[] newTable, boolean rehash) {
		int newCapacity = newTable.length;
		for (Entry<K, V> e : table) {
			while (null != e) {// 这里如果是并发执行的话可能形成死循环，这个链表的复制很有意思，最终会把链表的顺序调转
				Entry<K, V> next = e.next;
				if (rehash)
					e.hash = (null == e.key ? 0 : hash(e.key));
				int i = indexFor(e.hash, newCapacity);
				e.next = newTable[i];// newTable一开始就是空的，所以newTable[i]一开始就是null
				newTable[i] = e;
				e = next;
			}
		}
	}

	/**
	 * 该键值对是否含有某个键的值
	 */
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		int numKeysToBeAdded = m.size();
		if (numKeysToBeAdded == 0)
			return;

		if (table == EMPTY_TABLE)
			inflateTable((int) Math.max(numKeysToBeAdded * loadFactor, threshold));

		if (numKeysToBeAdded > threshold) {
			int targetCapacity = (int) (numKeysToBeAdded / loadFactor + 1);
			if (targetCapacity > MAXIMUM_CAPACITY)
				targetCapacity = MAXIMUM_CAPACITY;
			int newCapacity = table.length;
			while (newCapacity < targetCapacity)
				newCapacity <<= 1;
			if (newCapacity > table.length)
				resize(newCapacity);
		}

		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			put(e.getKey(), e.getValue());
	}

	public V remove(Object key) {
		Entry<K, V> e = removeEntryForKey(key);
		return (e == null ? null : e.value);
	}

	/**
	 * removeEntryForKey:根据key删除键值对<br/>
	 * 值得认真研究一下里面的循环
	 * 
	 * @author JM 2017-3-28 下午10:47:48
	 * @param key
	 * @return Entry<K,V>
	 */
	private Entry<K, V> removeEntryForKey(Object key) {
		if (size == 0)
			return null;

		int hash = (key == null) ? 0 : hash(key);
		int i = indexFor(hash, table.length);
		Entry<K, V> prev = table[i];
		Entry<K, V> e = prev;

		while (e != null) {// 这个循环和transfer(Entry[] newTable, boolean
							// rehash)方法里面的那个循环都设计得相当巧妙
			Entry<K, V> next = e.next;
			Object k = e.key;
			if (e.hash == hash && (k == key || (key != null && key.equals(k)))) {
				modCount++;
				size--;
				if (prev == e)
					table[i] = next;
				else
					prev.next = next;
				e.recordRemoval(this);
				return e;
			}
			prev = e;
			e = next;
		}
		return e;
	}

	/**
	 * 其实跟removeEntryForKey差不多
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	final Entry<K, V> removeMapping(Object o) {
		if (size == 0 || !(o instanceof Map.Entry))
			return null;

		Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
		Object key = entry.getKey();

		// return
		// removeEntryForKey(key);,其实后面一大长传都可以这样写，并且把上面的if语句里面的"size==0 ||"去掉
		int hash = (key == null) ? 0 : hash(key);
		int i = indexFor(hash, table.length);
		Entry<K, V> prev = table[i];
		Entry<K, V> e = prev;

		while (e != null) {
			Entry<K, V> next = e.next;
			if (e.hash == hash && e.equals(entry)) {
				modCount++;
				size--;
				if (prev == e)
					table[i] = next;
				else
					prev.next = next;
				e.recordRemoval(this);
				return e;
			}
			prev = e;
			e = next;
		}
		return e;
	}

	/**
	 * 清空所有的键值对，但是不改变table的长度
	 */
	public void clear() {
		Arrays.fill(table, null);// 这个函数可以借鉴使用一下，意思是将数组table里面所有元素都替换为null
		modCount++;
		size = 0;
	}

	/**
	 * 查找map的所有值中是否存在某个值
	 */
	@SuppressWarnings("rawtypes")
	public boolean containsValue(Object value) {
		if (value == null)
			return containsNullValue();

		Entry[] tab = table;
		for (int i = 0; i < tab.length; i++)
			for (Entry e = tab[i]; e != null; e = e.next)
				if (value.equals(e.value))
					return true;
		return false;
	}

	@SuppressWarnings("rawtypes")
	private boolean containsNullValue() {
		Entry[] tab = table;
		for (int i = 0; i < tab.length; i++)
			for (Entry e = tab[i]; e != null; e = e.next)
				if (e.value == null)
					return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		HashMap<K, V> result = null;
		try {
			result = (HashMap<K, V>) super.clone();
		} catch (CloneNotSupportedException e) {
		}

		if (result.table != EMPTY_TABLE)
			result.inflateTable(Math.min((int) Math.min(size * Math.min(1 / loadFactor, 4.0f), HashMap.MAXIMUM_CAPACITY), table.length));
		result.entrySet = null;
		result.modCount = 0;
		result.size = 0;
		result.init();
		result.putAllForCreate(this);

		return result;
	}

	private transient Set<Map.Entry<K, V>> entrySet = null;

	private Set<K> keySet;

	private Collection<V> values;

	/**
	 * HashMap内自定义的Entry，HashMap的基本存储单元
	 * 
	 * @author 李嘉明
	 * @2017-3-28
	 * @param <K>
	 * @param <V>
	 */
	static class Entry<K, V> implements Map.Entry<K, V> {

		final K key;
		V value;
		
		/**
		 * 指向下一个Entry<K,V> 节点的引用，这个就是链表每个对象链接的关键。
		 */
		Entry<K, V> next;
		int hash;

		/**
		 * 
		 * @param hash
		 * @param key
		 * @param value
		 * @param next
		 *            ：指向下一个Entry<K,V> 节点的引用
		 */
		public Entry(int hash, K key, V value, Entry<K, V> next) {
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
		
		/**
		 * 重写equals()方法
		 * 判断Entry相等的条件是建和值均相等，不过和next（链表中下一个Entry）没什么关系
		 */
		public final boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			@SuppressWarnings("unchecked")
			Map.Entry<K, V> e = (Map.Entry<K, V>) o;
			Object k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {// 键相等
				Object v1 = getValue();
				Object v2 = e.getValue();
				if (v1 == v2 || v1 != null && v1.equals(v2))// 值相等
					return true;
			}
			return false;// 比较HashMap的Entry要满足键相等和值相等两个条件才能相等
		}

		/**
		 * 获取Entry的hash值
		 */
		public final int hashCode() {// Entry的hashCode是Entry的键和值的位运算得来的
			return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
		}

		public final String toString() {
			return getKey() + "=" + getValue();
		}

		/**
		 * This method is invoked whenever the value in an entry is overwritten
		 * by an invocation of put(k,v) for a key k that's already in the
		 * HashMap.暂时看不出有什么鸟用
		 */
		void recordAccess(HashMap<K, V> m) {
		}

		/**
		 * This method is invoked whenever the entry is removed from the table.
		 */
		void recordRemoval(HashMap<K, V> m) {
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// 注意这个并不是链表结构
	private abstract class HashIterator<E> implements Iterator<E> {
		Entry<K, V> next;
		int expectedModCount;
		int index;
		Entry<K, V> current;

		public HashIterator() {
			expectedModCount = modCount;
			if (size > 0) {
				Entry[] t = table;
				// 不太习惯这样的语法
				while (index < t.length && (next = t[index++]) == null)
					;// ！！！将table数组的第一个不为null的Entry添加到next。这样在迭代的时候，就能从第一个（nextEntry）开始迭代
			}
		}

		public final boolean hasNext() {
			return next != null;
		}

		/**
		 * 我之前一直在疑惑，为什么Iterator的next()函数可以，一个个按顺序且不重复地迭代读出来，看完这里之后我明白了<br/>
		 * 主要是next和current这两个成员变量的原因，每调用这个方法一次next的引用就会指向下一个对象。
		 * 
		 * @return
		 */
		final Entry<K, V> nextEntry() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			Entry<K, V> e = next;
			if (e == null)// 这里居然是抛异常。
				throw new NoSuchElementException();

			if ((next = e.next) == null) {// ！！！很关键的一行代码（如果table该位置上不止一个Entry（也就是这个位置是一个链表）,那么next引用的将会是链表中下一个对象）
				Entry[] t = table;
				// 最关键的一行代码，如果table该位置上只有一个Entry，那么next直接引用table[i++]
				while (index < t.length && (next = t[index++]) == null)
					;
			}
			current = e;
			return e;
		}

		public void remove() {
			if (current == null)
				throw new IllegalStateException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			Object k = current.key;
			current = null;
			HashMap.this.removeEntryForKey(k);
			expectedModCount = modCount;
		}
	}

	private final class ValueIterator extends HashIterator<V> {
		public V next() {
			return nextEntry().value;
		}
	}

	private final class KeyIterator extends HashIterator<K> {
		public K next() {
			return nextEntry().key;
		}
	}

	private final class EntryIterator extends HashIterator<Map.Entry<K, V>> {
		public Map.Entry<K, V> next() {
			return nextEntry();
		}
	}

	Iterator<K> newKeyIterator() {
		return new KeyIterator();
	}

	Iterator<V> newValueIterator() {
		return new ValueIterator();
	}

	Iterator<Map.Entry<K, V>> newEntryIterator() {
		return new EntryIterator();
	}

	public Set<K> keySet() {
		Set<K> ks = keySet;
		return (ks != null ? ks : (keySet = new KeySet()));
	}

	private final class KeySet extends AbstractSet<K> {
		public Iterator<K> iterator() {
			return newKeyIterator();
		}

		public int size() {
			return size;
		}

		public boolean contains(Object o) {
			return containsKey(o);
		}

		public boolean remove(Object o) {
			return HashMap.this.removeEntryForKey(o) != null;
		}

		public void clear() {
			HashMap.this.clear();
		}
	}

	public Collection<V> values() {
		Collection<V> vs = values;
		return (vs != null ? vs : (values = new Values()));
	}

	private final class Values extends AbstractCollection<V> {
		public Iterator<V> iterator() {
			return newValueIterator();
		}

		public int size() {
			return size;
		}

		public boolean contains(Object o) {
			return containsValue(o);
		}

		public void clear() {
			HashMap.this.clear();
		}
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return entrySet0();
	}

	private Set<Map.Entry<K, V>> entrySet0() {
		Set<Map.Entry<K, V>> es = entrySet;
		return es != null ? es : (entrySet = new EntrySet());
	}

	private final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public Iterator<java.util.Map.Entry<K, V>> iterator() {
			return newEntryIterator();
		}

		@SuppressWarnings("unchecked")
		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<K, V> e = (Map.Entry<K, V>) o;
			Entry<K, V> candiate = getEntry(e.getKey());
			return candiate != null && candiate.equals(e);
		}

		public boolean remove(Object o) {
			return removeMapping(o) != null;
		}

		public int size() {
			return size;
		}

		public void clear() {
			HashMap.this.clear();
		}
	}

	/**
	 * 将HashMap对象写入到流中（所有的键值对，threshold,loadfactor...）<br/>
	 * 即序列化
	 * 
	 * @param s
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		// Write out the threshold, loadfactor, and any hidden stuff
		s.defaultWriteObject();
		// Write out number of buckets(写入hash桶的数量)
		if (table == EMPTY_TABLE)
			s.writeInt(roundUpPowerOf(threshold));
		else
			s.writeInt(table.length);
		// 写入map键值对的数量
		s.writeInt(size);
		// Write out all keys and values(alternating)，交替地将所有的key-value写入流中
		if (size > 0) {
			for (Map.Entry<K, V> e : entrySet0()) {
				s.writeObject(e.getKey());
				s.writeObject(e.getValue());
			}
		}
	}

	private static final long serialVersionUID = 362498820763181265L;

	/**
	 * Reconstitute the {@code HashMap} instance from a stream (i.e.,
	 * deserialize it). 从流中重构读取HashMap（即反序列化，相当于根据流的信息新建一个HashMap对象）
	 */
	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		// Read in the threshold (ignored), loadfactor, and any hidden stuff
		s.defaultReadObject();
		if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
			throw new InvalidObjectException("Illegal load factor: " + loadFactor);
		}

		// 创建新的table用来装Entry
		table = (Entry<K, V>[]) EMPTY_TABLE;

		s.readInt();

		int mappings = s.readInt();
		if (mappings < 0)
			throw new InvalidObjectException("Illegal mappings count: " + mappings);

		// capacity chosen by number of mappings and desired load (if >= 0.25)
		int capacity = (int) Math.min(mappings * Math.min(1 / loadFactor, 4.0f),
				// we have limits...
				HashMap.MAXIMUM_CAPACITY);
		
		 // allocate the bucket array;
        if (mappings > 0) {
            inflateTable(capacity);
        } else {
            threshold = capacity;
        }

        init();  // Give subclass a chance to do its thing.
        
        for(int i = 0;i<mappings;i++){
        	//下面；两行代码顺序一定不能反
        	K key = (K) s.readObject();
        	V value = (V) s.readObject();
        	putForCreate(key, value);
        }
		
	}

	// These methods are used when serializing HashSets
	int capacity() {
		return table.length;
	}

	float loadFactor() {
		return loadFactor;
	}
}