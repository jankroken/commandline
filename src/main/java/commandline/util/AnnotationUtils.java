package commandline.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtils {

	public static List<Method> getMethodsByAnnotation(Class<? extends Object> clazz, 
													  Class<? extends Annotation> searchAnnotation) {
		List<Method> methods = new ArrayList<Method>();
	methodLoop:
		for (Method method: clazz.getMethods()) {
			if (method.isAnnotationPresent(searchAnnotation)) {
				methods.add(method);
				continue methodLoop;
			}
		}
		return methods;
	}
}
