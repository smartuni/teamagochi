package haw.teamagochi.backend.pet.logic.Events;

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


    public int dispatch(int happiness, PetEvents event, PetStateDTO pet) {
        int increase = 0;
        if (
            (event == PetEvents.FEED && pet.getHunger() == hungerVO.getMin()) ||
            (event == PetEvents.PLAY && pet.getFun() == funVO.getMax()) ||
            (event == PetEvents.CLEAN && pet.getCleanliness() == cleanlinessVO.getMax()) ||
            (event == PetEvents.MEDICATE && pet.getHealth() == healthVO.getMax())
        ) {
            increase = 20;
        } else if (event == PetEvents.REACHED_MAX_STATUS) {
            increase = 40;
        }

        return boundaryCheck(happiness + increase);
    }

    public int deteriorate(int happiness, PetStateDTO pet) {
        int decrease = checkHappinessLimits(pet.getFun());
        decrease += checkHappinessLimits(hungerVO.getMax() - pet.getHunger()); // bring hunger to same scale as fun
        return boundaryCheck(happiness + decrease);

    }




}
