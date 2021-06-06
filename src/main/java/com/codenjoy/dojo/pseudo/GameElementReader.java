package com.codenjoy.dojo.pseudo;

import com.codenjoy.dojo.services.printer.CharElements;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class GameElementReader implements ElementReader {

    private String hero;
    private CharElements[] values;

    public GameElementReader(String game, String hero) {
        Class<?> clazz = loadClass(game);
        this.values = getEnumValues(clazz);
        this.hero = hero;
    }

    private static <E extends Enum> E[] getEnumValues(Class<?> enumClass) {
        try {
            Field field = enumClass.getDeclaredField("$VALUES");
            System.out.println(field);
            System.out.println(Modifier.toString(field.getModifiers()));
            field.setAccessible(true);
            Object o = field.get(null);
            return (E[]) o;
        } catch (Exception e) {
            throw new RuntimeException("Cant get Element values", e);
        }
    }

    private Class<?> loadClass(String game) {
        try {
            return getClass()
                    .getClassLoader()
                    .loadClass(String.format("com.codenjoy.dojo.games.%s.Element", game));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    String.format("The '%s' game must have an Element class", game),
                    e);
        }
    }

    @Override
    public List<CharElements> values() {
        return Arrays.asList(values);
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