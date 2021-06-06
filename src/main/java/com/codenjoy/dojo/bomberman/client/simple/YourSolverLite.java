package com.codenjoy.dojo.bomberman.client.simple;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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

import com.codenjoy.dojo.client.Encoding;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.printer.CharElements;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class YourSolverLite implements Solver<RuleBoard> {

    private Processor processor;

    public YourSolverLite(String rulesPlace, List<CharElements> elements, Dice dice) {
        this.processor = new Processor(rulesPlace, elements, dice, this::println);
    }

    private void println(Message message) {
        try {
            new PrintStream(System.out, true, Encoding.UTF8).println(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String get(RuleBoard board) {
        return processor.next(board).toString();
    }
}
