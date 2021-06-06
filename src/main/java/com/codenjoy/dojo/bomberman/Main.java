package com.codenjoy.dojo.bomberman;

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

import com.codenjoy.dojo.bomberman.model.GameSettings;
import com.codenjoy.dojo.bomberman.services.GameRunner;
import com.codenjoy.dojo.bomberman.services.OptionGameSettings;
import com.codenjoy.dojo.client.local.ws.LocalWSGameRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.services.round.RoundSettingsWrapper;
import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.SettingsImpl;
import com.codenjoy.dojo.services.settings.SimpleParameter;

public class Main {

    public static void main(String[] args) {
        Dice dice = new RandomDice();

        GameRunner gameType = new GameRunner() {
            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            protected GameSettings getGameSettings() {
                return new OptionGameSettings(new SettingsImpl(), dice){
                    @Override
                    public Parameter<Boolean> isMultiple() {
                        return new SimpleParameter<>(true);
                    }

                    @Override
                    public RoundSettingsWrapper getRoundSettings() {
                        return new RoundSettingsWrapper(){
                            @Override
                            public Parameter<Boolean> roundsEnabled() {
                                return new SimpleParameter<>(false);
                            }
                        };
                    }

                    // pres Ctrl-O here and override any setting property
                };
            }
        };

        LocalWSGameRunner.run(gameType, "127.0.0.1", 8080);
    }
}
