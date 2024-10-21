package haw.teamagochi.backend.pet.logic.game.events;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FunVO extends PetAttributeVO implements Deterioratable {

    public FunVO() {
        super(0, 100);
    }

    public int deteriorate(int fun) {
        return boundaryCheck(fun - 5);
    }


    public int dispatch(int fun, PetEvents event) {
        if (event == PetEvents.PLAY) fun += 10;
        return boundaryCheck(fun);
    }

}
