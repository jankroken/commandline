package com.github.jankroken.commandline.domain.internal;

import com.github.jankroken.commandline.annotations.*;
import com.github.jankroken.commandline.util.Methods;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class OptionSpecificationFactory {


    public static OptionSpecification getOptionSpecification(Object spec, Method method) {
        var builder = new OptionSpecificationBuilder();
        builder.addMethod(method);
        builder.addConfiguration(spec);
        for (var annotation : method.getAnnotations()) {
            if (annotation instanceof Option) {
            } else if (annotation instanceof LongSwitch) {
                builder.addLongSwitch(((LongSwitch) annotation).value());
            } else if (annotation instanceof ShortSwitch) {
                builder.addShortSwitch(((ShortSwitch) annotation).value());
            } else if (annotation instanceof Toggle) {
                builder.addToggle(((Toggle) annotation).value());
            } else if (annotation instanceof SingleArgument) {
                builder.addSingleArgument();
            } else if (annotation instanceof AllAvailableArguments) {
                builder.addAllAvailableArguments();
            } else if (annotation instanceof ArgumentsUntilDelimiter) {
                builder.addUntilDelimiter(((ArgumentsUntilDelimiter) annotation).value());
            } else if (annotation instanceof SubConfiguration) {
                builder.addSubset(((SubConfiguration) annotation).value());
            } else if (annotation instanceof Required) {
                builder.addRequired();
            } else if (annotation instanceof Multiple) {
                builder.addOccurrences(Occurrences.MULTIPLE);
            } else if (annotation instanceof LooseArguments) {
                builder.addLooseArgs();
            } else {
                // todo
                System.out.println("Unhandled annotation: " + annotation);
            }
        }
        return builder.getOptionSpecification();

    }

    public static List<OptionSpecification> getOptionSpecifications(Object spec, Class<?> optionClass) {
        var methods = new Methods(optionClass).byAnnotation(Option.class);
        List<OptionSpecification> optionSpecifications = new ArrayList<>();
        for (var method : methods) {
            optionSpecifications.add(getOptionSpecification(spec, method));
        }
        return optionSpecifications;
    }
}
