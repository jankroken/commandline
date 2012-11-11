package commandline.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Methods {

    private Class annotatedClass;
    public Methods(Class annotatedClass) {
        this.annotatedClass = annotatedClass;
    }

	public List<Method> byAnnotation(Class<? extends Annotation> searchAnnotation) {
		List<Method> methods = new ArrayList<>();
	methodLoop:
		for (Method method: annotatedClass.getMethods()) {
			if (method.isAnnotationPresent(searchAnnotation)) {
				methods.add(method);
				continue methodLoop;
			}
		}
		return methods;
	}
}
