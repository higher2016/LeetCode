package DataStructures;

import java.util.Arrays;

/**
 * BinaryHeap:二叉堆数据结构
 * 
 * @author JM
 * @date 2017-4-26 下午9:23:51
 * @since JDK 1.7
 */
public class BinaryHeap<AnyType extends Comparable<? super AnyType>> implements java.io.Serializable {

	/**
	 * BinaryHeap.java:
	 * 
	 * @author JM
	 * @date 2017-4-26 下午9:34:42
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private int currentSize;
	private transient Object[] array;
//	private static final int DEFAULT_CAPACITY = 10;
	private static final Object[] EMPTY_ELEMENTDATA = {};

	public BinaryHeap() {
		array = EMPTY_ELEMENTDATA;
	}

	public BinaryHeap(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		array = new Object[initialCapacity];
	}

	@SuppressWarnings("unchecked")
	public BinaryHeap(AnyType[] items) {
		currentSize = items.length;
		array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];
		int i = 1;
		for (AnyType item : items)
			array[i++] = item;
		bulidHeap();
	}

	/**
	 * 插入（上滤）
	 */
	@SuppressWarnings("unchecked")
	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);

		int hole = ++currentSize;
		for (; hole > 1 && x.compareTo((AnyType) array[hole / 2]) < 0; hole /= 2)
			array[hole] = array[hole / 2];
		array[hole] = x;
	}
	
	public AnyType deleteMin(){
		if(isEmpty())
			throw new RuntimeException("Binary is empty");
		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);
		return minItem;
	}

	@SuppressWarnings("unchecked")
	public AnyType findMin() {
		if(isEmpty())
			return null;
		return (AnyType) array[1];
	}

	private boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 扩张数组
	 */
	private void enlargeArray(int i) {
		array = Arrays.copyOf(array, i);
	}

	/**
	 * bulidHeap:该方法可以将二叉树构建为一个二叉堆（就是将无序树构建为有序二叉堆）
	 */
	private void bulidHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}

	/**
	 * Internal method to percolate down in the heap.（保证二叉堆的排列顺序：下滤）
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	@SuppressWarnings("unchecked")
	private void percolateDown(int hole) {
		int child;
		AnyType tmp = (AnyType) array[hole];
		for (; hole * 2 <= currentSize; hole = child) {
			child = hole * 2;
			if (child != currentSize && ((AnyType) array[child + 1]).compareTo((AnyType) array[child]) < 0)
				child++; // 找出节点的两个子节点那个是最小的，然后最小的子节点再跟父节点比较大小
			if (((AnyType) array[child]).compareTo(tmp) < 0)
				array[hole] = array[child]; // 父节点如果比子节点大就把他们位置替换
			else
				break;
		}
		array[hole] = tmp;
	}
}
