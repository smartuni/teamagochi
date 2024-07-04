package haw.teamagochi.backend.pet.logic.Events;

import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class XpVO extends PetStatusVO {

    @Inject
    HungerVO hungerVO;

    @Inject
    HealthVO healthVO;

    @Inject
    FunVO funVO;

    @Inject
    CleanlinessVO cleanlinessVO;

    public XpVO() {
        super(0, 5000);
    }

    public int dispatch(PetEvents event, PetStateDTO pet) {
        int xp = pet.getXp();
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
        return  boundaryCheck(xp + increase);
    }

}
