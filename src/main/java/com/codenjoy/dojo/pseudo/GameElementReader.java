package com.codenjoy.dojo.pseudo;

import com.codenjoy.dojo.games.bomberman.Element;
import com.codenjoy.dojo.services.printer.CharElements;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class GameElementReader implements ElementReader {

    private String hero;

    public GameElementReader(String game, String hero) {
        this.hero = hero;
    }

    @Override
    public List<CharElements> values() {
        return Arrays.asList(Element.values());
    }

    @Override
    public Function<Character, CharElements> mapper() {
        return ch -> valueOf(ch);
    }

    public CharElements valueOf(char ch) {
        for (CharElements el : values()) {
            if (el.ch() == ch) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element with char: " + ch);
    }

    public CharElements valueOf(String name) {
        for (CharElements el : values()) {
            if (el.name().equals(name)) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element with name: " + name);
    }

    @Override
    public CharElements hero() {
        return valueOf(hero);
    }
}
