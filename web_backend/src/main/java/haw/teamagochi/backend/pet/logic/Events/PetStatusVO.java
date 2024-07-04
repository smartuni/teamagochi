package haw.teamagochi.backend.pet.logic.Events;

import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;

public abstract class PetStatusVO extends PetAttributeVO {
    public PetStatusVO(int min, int max) {
        super(min, max);
    }



    abstract int dispatch(PetEvents event, PetStateDTO pet);
}
