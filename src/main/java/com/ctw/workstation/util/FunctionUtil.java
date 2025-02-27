package com.ctw.workstation.util;

import java.util.Optional;
import java.util.function.Function;

public class FunctionUtil {
    public static final Function<Optional<?>, String> OPTIONAL_TO_STRING = o -> o.isPresent() ? o.get().toString() : "NULL";
}
