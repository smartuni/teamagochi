package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.CleanlinessVO;
import haw.teamagochi.backend.pet.logic.Events.HealthVO;
import haw.teamagochi.backend.pet.logic.Events.HungerVO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class UcKillPetImpl implements UcKillPet {

    @Inject
    HungerVO hungerVO;

    @Inject
    HealthVO healthVO;

    @Inject
    CleanlinessVO cleanlinessVO;

    @Inject
    UcFindDevice ucFindDevice;

    public static final String deadname = "DEAD"; // If you want to change this name, inform frontend team!

    public boolean shouldDie(PetEntity pet) {
        return pet.getHunger() == hungerVO.getMax() ||
                pet.getHealth() == healthVO.getMin() ||
                pet.getCleanliness() == cleanlinessVO.getMin();
    }

    public boolean isDead(PetEntity pet) {
        return pet.getName().equals(deadname);

    }

    //TODO: Transaktionalität testen
    public void kill(PetEntity pet) {
        pet.setName(deadname);
        DeviceEntity device = ucFindDevice.findByPet(pet.getId());
        //TODO: Send "Death" event to the device.
    }

    public void killIfShouldDie(PetEntity pet) {
        if (!isDead(pet) && shouldDie(pet)) {
            kill(pet);
        }
    }


}
