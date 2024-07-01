package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;

/**
 *  Handle pet death.
 */
public interface UcKillPet {

    /**
     * Kills the pet if it is in a lethal state.
     */
    void killIfShouldDie(PetEntity pet);

    boolean isDead(PetEntity pet);
}
