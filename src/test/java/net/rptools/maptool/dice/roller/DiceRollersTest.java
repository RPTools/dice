package net.rptools.maptool.dice.roller;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DiceRollersTest {

    @Test
    void registeredDiceRollers() {
        DiceRollers diceRollers = DiceRollers.getInstance();

        Reflections reflections = new Reflections("net.rptools.maptool.dice");

        Set<Class<?>> classes =
                reflections.getTypesAnnotatedWith(DiceRollerDefinition.class);
        classes.forEach(cl -> {
            try {
                Object obj = cl.getDeclaredConstructor(new Class[0]).newInstance();
                if (obj instanceof DiceRoller) {
                    DiceRollerDefinition def = cl.getAnnotation(DiceRollerDefinition.class);
                    for (var p: def.patterns()) {
                        assertEquals(diceRollers.getDiceRoller(p).getClass(), obj.getClass());
                    }
                } else {
                    System.err.println("Unable to register DiceRoller " + cl.getName() + " as it is wrong type.");
                }
            } catch (Exception e) {
                System.err.println("Unable to register DiceRoller " + cl.getName());
            }
        });
    }


    @Test
    void getDiceRoller() {
    }
}