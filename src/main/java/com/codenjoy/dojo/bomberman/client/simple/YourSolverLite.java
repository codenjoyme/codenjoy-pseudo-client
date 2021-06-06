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

import com.codenjoy.dojo.bomberman.client.Board;
import com.codenjoy.dojo.bomberman.model.Elements;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.RandomDice;
import com.google.common.primitives.Chars;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class YourSolverLite implements Solver<Board> {

    private Processor processor;
    private Dice dice;
    private Board board;

    public YourSolverLite(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        if (board.isMyBombermanDead()) return "";

        this.processor = new Processor();

        setup();

        return processor.process(board).toString();
    }

    private void setup() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("games/bomberman/rules/rule.txt")))) {
            String line;
            String pattern = "";
            do {
                line = reader.readLine();

                if (line == null) {
                    break;
                }
                if (StringUtils.isEmpty(StringUtils.trim(line))) {
                    continue;
                }
                if (Direction.isValid(line)) {
                    if (isValidPattern(pattern)) {
                        processor.addIf(Direction.valueOf(line), pattern);
                    } else {
                        System.out.println("[ERROR] Pattern is not valid: " + pattern);
                    }
                    pattern = "";
                } else {
                    pattern += line;
                }
            } while (line != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidPattern(String pattern) {
        return isValidPatternLength(pattern) && isValidPatternSymbols(pattern);
    }

    private boolean isValidPatternSymbols(String pattern) {
        List<Character> allow = Arrays.stream(Elements.values())
                .map(e -> e.ch())
                .collect(Collectors.toList());
        allow.add('.');

        return new LinkedList<>(Chars.asList(pattern.toCharArray())).stream()
                .filter(ch -> !allow.contains(ch))
                .count() == 0;
    }

    private boolean isValidPatternLength(String pattern) {
        double sqrt = Math.sqrt(pattern.length());
        return sqrt == Math.floor(sqrt);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Bad format, please run program with 1 argument " +
                    "like 'http://codenjoy.com:80/codenjoy-contest/board/player/playerId?code=1234567890123456789'");
        }
        WebSocketRunner.runClient(
                args[0],
                new YourSolverLite(new RandomDice()),
                new Board());
    }

}
