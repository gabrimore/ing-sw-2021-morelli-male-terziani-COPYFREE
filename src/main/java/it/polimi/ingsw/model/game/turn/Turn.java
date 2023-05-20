package it.polimi.ingsw.model.game.turn;

/**
 * This interface is used as a mediator between the controller and the actual model.
 * Each of the classes that implement this interface deal with a part of the model.
 */
public interface Turn {

    void begin();

}
