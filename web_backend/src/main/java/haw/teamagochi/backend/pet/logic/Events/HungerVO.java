package haw.teamagochi.backend.pet.logic.Events;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HungerVO extends PetAttributeVO implements Deterioratable {

    public HungerVO() {
        super(0, 100);
    }

    public int deteriorate(int hunger) {
        return boundaryCheck(hunger + 5); // more hunger == worse !
    }


    public int dispatch(int hunger, PetEvents event) {
        if (event == PetEvents.FEED) hunger -= 10;
        return boundaryCheck(hunger);
    }

}
