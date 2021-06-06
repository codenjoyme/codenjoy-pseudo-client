package com.codenjoy.dojo.pseudo;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.services.printer.CharElement;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class GameElementReader implements ElementReader {

    private List<String> heroElements;
    private CharElement[] values;

    public GameElementReader(String game, List<String> heroElements) {
        Class<?> clazz = loadClass(game);
        this.values = getEnumValues(clazz);
        this.heroElements = heroElements;
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
    public CharElement[] heroElements() {
        return heroElements.stream()
                .map(name -> valueOf(name))
                .collect(toList())
                .toArray(CharElement[]::new);
    }
}
