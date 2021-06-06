package com.codenjoy.dojo.bomberman.client.simple;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2020 Codenjoy
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

import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.printer.CharElements;

import java.util.function.Function;

public class RuleBoard extends AbstractBoard<CharElements> {

    public static final char ANY_CHAR = '?';

    private final Function<Character, CharElements> elements;
    private final CharElements hero;

    public RuleBoard(Function<Character, CharElements> elements, CharElements hero) {
        this.elements = elements;
        this.hero = hero;
    }

    @Override
    public CharElements valueOf(char ch) {
        try {
            return elements.apply(ch);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return boardAsString();
    }
    
    // TODO refactor me
    public boolean isNearHero(Pattern pattern) {
        Point meAtMap = this.getFirst(hero);

        RuleBoard part = this.clone(pattern.pattern());
        Point meAtPart = part.getFirst(hero);

        Point corner = meAtMap.relative(meAtPart);

        for (int dx = 0; dx < part.size; dx++) {
            for (int dy = 0; dy < part.size; dy++) {
                CharElements real = this.getAt(corner.getX() + dx, corner.getY() + dy);
                Character mask = part.field(dx, dy).get(0);

                if (mask == ANY_CHAR){
                    continue;
                }

                if (mask == real.ch()) {
                    continue;
                }

                if (pattern.synonyms().match(mask, real.ch())) {
                    continue;
                }

                return false;
            }
        }
        return true;
    }

    private RuleBoard clone(String pattern1) {
        return (RuleBoard) new RuleBoard(elements, hero).forString(pattern1);
    }

    public boolean isGameOver() {
        return get(hero).isEmpty();
    }
}
