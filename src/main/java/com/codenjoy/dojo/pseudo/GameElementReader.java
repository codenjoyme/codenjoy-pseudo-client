package com.codenjoy.dojo.pseudo;

import com.codenjoy.dojo.games.bomberman.Element;
import com.codenjoy.dojo.services.printer.CharElements;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class GameElementReader implements ElementReader {

    public GameElementReader(String game) {
        // TODO
    }

    @Override
    public List<CharElements> values() {
        return Arrays.asList(Element.values());
    }

    @Override
    public Function<Character, CharElements> mapper() {
        return ch -> Element.valueOf(ch);
    }

    @Override
    public Element hero() {
        return Element.BOMBERMAN;
    }
}
