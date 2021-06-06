package com.codenjoy.dojo.pseudo;

import com.codenjoy.dojo.services.printer.CharElement;

import java.util.List;
import java.util.function.Function;

public interface ElementReader {

    List<CharElement> values();

    Function<Character, CharElement> mapper();

    CharElement hero();
}