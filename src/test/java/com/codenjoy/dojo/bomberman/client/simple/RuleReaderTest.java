package com.codenjoy.dojo.bomberman.client.simple;

import com.codenjoy.dojo.bomberman.client.Board;
import com.codenjoy.dojo.services.Direction;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RuleReaderTest extends AbstractRuleReaderTest {

    @Test
    public void shouldNoRules_whenEmptyFile() {
        // given
        loadLns("");

        // when
        reader.load(rules, file);
        
        // then
        assertEquals("[]", reader.errors().toString());
        assertEquals("[]", rules.toString());

    }

    @Test
    public void shouldSeveralRules_whenSingleFile() {
        // given
        loadLns("???",
                "♥☺?",
                "???",
                "RIGHT",
                "",
                "???",
                "?☺♥",
                "???",
                "LEFT",
                "",
                "?♥?",
                "?☺?",
                "???",
                "DOWN",
                "",
                "?☼?",
                "☼☺?",
                "?♥?",
                "RIGHT",
                "",
                "???",
                "?☺?",
                "?♥?",
                "UP");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[]", reader.errors().toString());
        assertEquals(
                "[[???♥☺???? > [RIGHT]], " +
                "[????☺♥??? > [LEFT]], " +
                "[?♥??☺???? > [DOWN]], " +
                "[?☼?☼☺??♥? > [RIGHT]], " +
                "[????☺??♥? > [UP]]]", rules.toString());
    }

    @Test
    public void shouldLoadRules_whenRuleDirective() {
        // given
        loadLns("?☼?",
                "?☺?",
                "???",
                "DOWN",
                "",
                "????????",
                "????????",
                "????????",
                "☺",
                "????????",
                "????????",
                "????????",
                "RULE right",
                "",
                "???",
                "☼☺☼",
                "?#?",
                "LEFT");
        
        loadLns("?☼?",
                "?☺ ",
                "?☼?",
                "RIGHT",
                "",
                "?☼?",
                "?☺ ",
                "?#?",
                "DOWN",
                "",
                "?#?",
                "?☺ ",
                "?☼?",
                "UP",
                "",
                "?#?",
                "?☺ ",
                "?#?",
                "LEFT");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[]", reader.errors().toString());
        assertEquals(
                "[[?☼??☺???? > [DOWN]], " +
                "[????????????????????????☺???????????????????????? > [" +
                    "[?☼??☺ ?☼? > [RIGHT]], " +
                    "[?☼??☺ ?#? > [DOWN]], " +
                    "[?#??☺ ?☼? > [UP]], " +
                    "[?#??☺ ?#? > [LEFT]]]" +
                "], " +
                "[???☼☺☼?#? > [LEFT]]]", rules.toString());
    }

    @Test
    public void shouldCheckBoardWithSubRules_whenRuleDirective() {
        // given
        shouldLoadRules_whenRuleDirective();
        
        Board board = mock(Board.class);
        when(board.isNearMe(eq("?#??☺ ?☼?"))).thenReturn(true);

        // when
        List<Direction> directions = rules.process(board);

        // then 
        assertEquals("[UP]", directions.toString());
    }
    
    @Test
    public void shouldSeveralDirections_whenOneRule() {
        // given
        loadLns("   ",
                "   ",
                "   ",
                "RIGHT,LEFT,DOWN",
                "",
                "   ",
                "   ",
                "   ",
                "  LEFT  , RIGHT,   UP",
                "",
                "   ",
                "   ",
                "   ",
                "DOWN, UP  ",
                "",
                "   ",
                "   ",
                "   ",
                "RIGHT",
                "",
                "   ",
                "   ",
                "   ",
                "    UP ,  DOWN, LEFT, RIGHT,RIGHT");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[]", reader.errors().toString());
        assertEquals(
                "[[          > [RIGHT, LEFT, DOWN]], " +
                "[          > [LEFT, RIGHT, UP]], " +
                "[          > [DOWN, UP]], " +
                "[          > [RIGHT]], " +
                "[          > [UP, DOWN, LEFT, RIGHT, RIGHT]]]", 
                rules.toString());
    }

    @Test
    public void shouldErrorInDirectionsList_whenTwoCommasInside() {
        // given
        loadLns("???",
                "???",
                "???",
                "RIGHT,,LEFT,DOWN");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[[ERROR] Direction 'RIGHT,,LEFT,DOWN' is not valid for pattern: '?????????' at directory\\main.rule:4]",
                reader.errors().toString());

        assertEquals("[]", rules.toString());
    }

    @Test
    public void shouldErrorInDirectionsList_whenOnlyOneComma() {
        // given
        loadLns("???",
                "???",
                "???",
                ",");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[[ERROR] Direction ',' is not valid for pattern: '?????????' at directory\\main.rule:4]",
                reader.errors().toString());

        assertEquals("[]", rules.toString());
    }

    @Test
    public void shouldErrorInDirectionsList_whenCommaAtLast() {
        // given
        loadLns("???",
                "???",
                "???",
                "UP, ");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[[ERROR] Direction 'UP, ' is not valid for pattern: '?????????' at directory\\main.rule:4]",
                reader.errors().toString());

        assertEquals("[]", rules.toString());
    }

    @Test
    public void shouldErrorInDirectionsList_whenCommaAtFirst() {
        // given
        loadLns(
                "???",
                "???",
                "???",
                " , UP");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[[ERROR] Direction ' , UP' is not valid for pattern: '?????????' at directory\\main.rule:4]",
                reader.errors().toString());

        assertEquals("[]", rules.toString());
    }

    @Test
    public void shouldErrorInDirectionsList_whenDirectionIsEmptyForLastPattern() {
        // given
        loadLns(
                "???",
                "???",
                "???",
                "UP",
                "???",
                "???",
                "???",
                "");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[[ERROR] Directions is empty for pattern: '?????????' at directory\\main.rule:9]",
                reader.errors().toString());

        assertEquals("[[????????? > [UP]]]", rules.toString());
    }

    @Test
    public void shouldError_whenPatternIsNotValid() {
        // given
        loadLns(
                "??",
                "???",
                "???",
                "UP");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[[ERROR] Pattern is not valid: '????????' at directory\\main.rule:4]",
                reader.errors().toString());

        assertEquals("[]", rules.toString());
    }

    @Test
    public void shouldErrorInDirectionsList_whenCommasAtFirstAndLast() {
        // given
        loadLns(
                "???",
                "???",
                "???",
                ",DOWN,");

        // when
        reader.load(rules, file);

        // then
        assertEquals("[[ERROR] Direction ',DOWN,' is not valid for pattern: '?????????' at directory\\main.rule:4]",
                reader.errors().toString());

        assertEquals("[]", rules.toString());
    }
    
}