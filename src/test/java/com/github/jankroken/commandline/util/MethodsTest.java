package com.github.jankroken.commandline.util;

import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.happy.SimpleConfiguration;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodsTest {

    @Test
    public void testGetMethodsByAnnotation() {
        List<Method> methods = new Methods(SimpleConfiguration.class).byAnnotation(Option.class);
        assertThat(methods.size()).isEqualTo(2);
    }

}
