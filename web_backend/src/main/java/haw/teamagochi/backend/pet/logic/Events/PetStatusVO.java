package haw.teamagochi.backend.pet.logic.Events;

import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;

public abstract class PetStatusVO extends PetAttributeVO {
    public PetStatusVO(int min, int max) {
        super(min, max);
    }

    int checkHappinessLimits(int hungerOrFunValue) {
        if (hungerOrFunValue > 59) {
            return 0;
        } else if (hungerOrFunValue > 39) {
            return -5;
        } else if (hungerOrFunValue > 19) {
            return -10;
        } else if (hungerOrFunValue >= 1) {
            return -15;
        } else {// hungerOrFunValue == 0
            return -20;
        }
    }

    abstract int dispatch(PetEvents event, PetStateDTO pet);
}
