package haw.teamagochi.backend.pet.logic.game.events;

import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class HappinessVO extends PetStatusVO {

    @Inject
    HungerVO hungerVO;

    @Inject
    HealthVO healthVO;

    @Inject
    FunVO funVO;

    @Inject
    CleanlinessVO cleanlinessVO;

    public HappinessVO() {
        super(0, 100);
    }


    public int dispatch(PetEvents event, PetStateDTO pet) {
        int happiness = pet.getHappiness();
        int increase = 0;
        /*
            If e.g. pet is fed, but pet was not hungry, then status (happiness etc.) should not be improved.
        */
        if (
            (event == PetEvents.FEED && pet.getHunger() > hungerVO.getMin()) ||
            (event == PetEvents.PLAY && pet.getFun() <  funVO.getMax()) ||
            (event == PetEvents.CLEAN && pet.getCleanliness() <  cleanlinessVO.getMax()) ||
            (event == PetEvents.MEDICATE && pet.getHealth() < healthVO.getMax())
        ) {
            increase = 20;
        } else if (event == PetEvents.REACHED_MAX_STATUS) {
            increase = 40;
        }

        return boundaryCheck(happiness + increase);
    }

    public int deteriorate(PetStateDTO pet) {
        int happiness = pet.getHappiness();
        int decrease = checkHappinessLimits(pet.getFun());
        decrease += checkHappinessLimits(hungerVO.getMax() - pet.getHunger()); // bring hunger to same scale as fun
        return boundaryCheck(happiness + decrease);

    }

    private int checkHappinessLimits(int hungerOrFunValue) {
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




}
