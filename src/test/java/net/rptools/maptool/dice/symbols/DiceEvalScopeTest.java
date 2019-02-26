package net.rptools.maptool.dice.symbols;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceEvalScopeTest {

    @Test
    void getScopeName() {
        assertEquals(DiceEvalScope.LOCAL.getScopeName(), "local");
        assertEquals(DiceEvalScope.GLOBAL.getScopeName(), "global");
        assertEquals(DiceEvalScope.PROPERTY.getScopeName(), "property");
    }

    @Test
    void getScopePrefix() {
        assertEquals(DiceEvalScope.LOCAL.getScopePrefix(), "$");
        assertEquals(DiceEvalScope.GLOBAL.getScopePrefix(), "#");
        assertEquals(DiceEvalScope.PROPERTY.getScopePrefix(), "@");
    }

    @Test
    void getScopeForPrefix() {
        assertEquals(DiceEvalScope.getScopeForPrefix("$"), DiceEvalScope.LOCAL);
        assertEquals(DiceEvalScope.getScopeForPrefix("#"), DiceEvalScope.GLOBAL);
        assertEquals(DiceEvalScope.getScopeForPrefix("@"), DiceEvalScope.PROPERTY);

        assertThrows(IllegalArgumentException.class, () -> { DiceEvalScope.getScopeForPrefix(":::::!"); });
    }
}