package haw.teamagochi.backend.pet.logic.game.events;

import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WellbeingVO extends PetStatusVO {

    @Inject
    HungerVO hungerVO;

    @Inject
    HealthVO healthVO;

    @Inject
    FunVO funVO;

    @Inject
    CleanlinessVO cleanlinessVO;

    public WellbeingVO() {
        super(0, 100);
    }


    public int dispatch(PetEvents event, PetStateDTO pet) {
        /*
            If e.g. pet is fed, but pet was not hungry, then status (happiness etc.) should not be improved.
        */
        int wellbeing = pet.getWellbeing();
        int increase = 0;
        if (
                (event == PetEvents.FEED && pet.getHunger() > hungerVO.getMin()) ||
                (event == PetEvents.PLAY && pet.getFun() < funVO.getMax())
        ) {
            increase = 5;
        } else if (
                (event == PetEvents.CLEAN && pet.getCleanliness() < cleanlinessVO.getMax()) ||
                (event == PetEvents.MEDICATE && pet.getHealth() < healthVO.getMax())
        ) {
            increase = 15;
        }
        return boundaryCheck(wellbeing + increase);
    }


    public int deteriorate(PetStateDTO pet) {
        int wellbeing = pet.getWellbeing();
        int decrease = checkWellbeingLimits(pet.getHealth());
        decrease += checkWellbeingLimits(pet.getCleanliness());
        return boundaryCheck(wellbeing + decrease);

    }

    private int checkWellbeingLimits(int healthOrCleanlinessValue){
        if(healthOrCleanlinessValue > 59){
            return 0;
        }else if(healthOrCleanlinessValue > 39){
            return -5;
        }else if(healthOrCleanlinessValue > 19){
            return -10;
        }else if(healthOrCleanlinessValue >= 1){
            return -15;
        }else{// healthOrCleanlinessValue == 0
            return -20;
        }
    }




}
