package DataStructures.Sort;

/**
 * Sort: 收列相关的排序算法
 * 
 * @author JM
 * @date 2017-4-28 下午11:02:54
 * @since JDK 1.7
 */
public class Sort {
	
	/**
	 * Simple insert sort.
	 */
	public static <AnyType extends Comparable<? super AnyType>> void insertSort(AnyType[] a) {
		int j;
		for (int p = 1; p < a.length; p++) {
			AnyType tmp = a[p];
			for (j = p; tmp.compareTo(a[j - 1]) < 0; j--)
				a[j] = a[j - 1];
			a[j] = tmp;
		}
	}
}
