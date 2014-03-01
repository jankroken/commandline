package com.github.jankroken.commandline.util;

import com.zerolegacy.commandline.annotations.Option;
import com.github.jankroken.commandline.happy.SimpleConfiguration;
import com.zerolegacy.commandline.util.Methods;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MethodsTest {

    @Test
    public void testGetMethodsByAnnotation() {
        List<Method> methods = new Methods(SimpleConfiguration.class).byAnnotation(Option.class);
        assertEquals(methods.size(), 2);
    }

}
