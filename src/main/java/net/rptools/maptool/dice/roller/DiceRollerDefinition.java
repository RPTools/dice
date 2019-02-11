package net.rptools.maptool.dice.roller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * This is used to annotate classes that implement {@DiceRoller} so that they will be registered with the parser.
 */
public @interface DiceRollerDefinition {

    /**
     * The name of the {@link DiceRoller} that this annotation is for.
     * @return the name of the {@link DiceRoller}.
     */
    String name();

    /**
     * The letters that represent the pattern for the {@DiceRoller}.
     * @return the letters that represent the {@DiceRoller}.
     */
    String[] patterns() default {};

    /**
     * The description of {@DiceRoller}.
     * @return the description of the {@DiceRoller}.
     */
    String description();
}
