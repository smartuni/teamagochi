package haw.teamagochi.backend.pet.logic.game.events;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CleanlinessVO extends PetAttributeVO implements Deterioratable {

    public CleanlinessVO() {
        super(0, 100);
    }

    public int deteriorate(int cleanliness) {
        return boundaryCheck(cleanliness - 5);
    }

    public int dispatch(int cleanliness, PetEvents event) {
        if (event == PetEvents.CLEAN) cleanliness += 10;
        return boundaryCheck(cleanliness);
    }

}
