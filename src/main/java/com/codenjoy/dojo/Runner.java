package com.codenjoy.dojo;

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

import com.codenjoy.dojo.bomberman.client.simple.Messages;
import com.codenjoy.dojo.bomberman.client.simple.RuleBoard;
import com.codenjoy.dojo.bomberman.client.simple.YourSolverLite;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.games.bomberman.Element;
import com.codenjoy.dojo.services.RandomDice;

import java.util.Arrays;

public class Runner {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("[ERROR] " + Messages.BAD_FORMAT_PLEASE_RUN_PROGRAM_WITH_2_ARGUMENTS + ": \n" +
                    "\t\t\t1) board url 'http://codenjoy.com:80/codenjoy-contest/board/player/playerId?code=1234567890123456789'\n" +
                    "\t\t\t2) rules directory 'games/bomberman/rules'.\n" +
                    "\t\tArguments are: " + Arrays.toString(args));
            return;
        }

        WebSocketRunner.runClient(args[0],
                new YourSolverLite(args[1], new RandomDice()),
                new RuleBoard(ch -> Element.valueOf(ch), Element.BOMB_BOMBERMAN)
        );
    }

}
