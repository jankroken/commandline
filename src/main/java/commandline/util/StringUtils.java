package commandline.util;

import java.util.Arrays;

public class StringUtils {
	public static String printableList(Iterable<Object> collection) {
		if (collection == null) return null;
		boolean first = true;
		StringBuffer sb = new StringBuffer('[');
		for (Object o: collection) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			sb.append(o);
		}
		sb.append(']');
		return sb.toString();
	}
	
	public static String printableList(Object[] array) {
		return printableList(Arrays.asList(array));
	}
}
