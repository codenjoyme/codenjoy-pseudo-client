package com.codenjoy.dojo.pseudo;

import com.codenjoy.dojo.services.printer.CharElement;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class GameElementReader implements ElementReader {

    private String hero;
    private CharElement[] values;

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
    public List<CharElement> values() {
        return Arrays.asList(values);
    }

    @Override
    public Function<Character, CharElement> mapper() {
        return ch -> valueOf(ch);
    }

    public CharElement valueOf(char ch) {
        for (CharElement el : values()) {
            if (el.ch() == ch) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element with char: " + ch);
    }

    public CharElement valueOf(String name) {
        for (CharElement el : values()) {
            if (el.name().equals(name)) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element with name: " + name);
    }

    @Override
    public CharElement hero() {
        return valueOf(hero);
    }
}