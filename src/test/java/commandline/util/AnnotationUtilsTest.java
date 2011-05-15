package commandline.util;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import commandline.annotations.Option;
import commandline.happy.SimpleConfiguration;

public class AnnotationUtilsTest {

	@Test
	public void testGetMethodsByAnnotation() {
		List<Method> methods = AnnotationUtils.getMethodsByAnnotation(SimpleConfiguration.class, Option.class);
		assertEquals(methods.size(),2);
	}

}
