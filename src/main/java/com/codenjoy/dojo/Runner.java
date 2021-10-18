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

import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.pseudo.GameElementReader;
import com.codenjoy.dojo.pseudo.Messages;
import com.codenjoy.dojo.pseudo.YourSolverLite;
import com.codenjoy.dojo.services.RandomDice;

import java.util.Arrays;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        System.out.printf(
                "+-----------------+\n" +
                "| Starting runner |\n" +
                "+-----------------+\n");

        if (args.length != 4) {
            System.out.println("[ERROR] " + Messages.NOT_ENOUGH_ARGUMENTS + ": \n" +
                    "\t\t\t1) game name (for example 'mollymage')\n" +
                    "\t\t\t2) hero sprite names (for example 'HERO,POTION_HERO,DEAD_HERO')\n" +
                    "\t\t\t3) board url (for example 'http://codenjoy.com:80/codenjoy-contest/board/player/playerId?code=1234567890123456789')\n" +
                    "\t\t\t4) rules directory (for example './rules/').\n" +
                    "\t\tArguments are: " + Arrays.toString(args));
            return;
        }

        String game = args[0];
        List<String> heroes = Arrays.asList(args[1].split(","));
        String url = args[2];
        String rules = args[3];

        System.out.printf(
                "Got from Environment:\n" +
                "\t 'GAME':   '%s'\n" +
                "\t 'HEROES': '%s'\n" +
                "\t 'URL':    '%s'\n" +
                "\t 'RULES':  '%s'\n",
                game, heroes, url, rules);

        YourSolverLite solver = new YourSolverLite(rules, new GameElementReader(game, heroes), new RandomDice());
        WebSocketRunner.runClient(url, solver, solver.getBoard());
    }
}