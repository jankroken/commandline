package com.github.jankroken.commandline.util;

import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.happy.SimpleConfiguration;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodsTest {

    @Test
    public void testGetMethodsByAnnotation() {
        var methods = new Methods(SimpleConfiguration.class).byAnnotation(Option.class);
        assertThat(methods.size()).isEqualTo(2);
    }

}
