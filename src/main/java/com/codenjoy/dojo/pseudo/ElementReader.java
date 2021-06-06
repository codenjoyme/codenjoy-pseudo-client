package com.codenjoy.dojo.pseudo;

import com.codenjoy.dojo.services.printer.CharElements;

import java.util.List;
import java.util.function.Function;

public interface ElementReader {
    List<CharElements> values();
    Function<Character, CharElements> mapper();
    CharElements hero();
}