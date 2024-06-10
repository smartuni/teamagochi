package haw.teamagochi.backend.app;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.logic.UcManageDevice;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.logic.UcManagePetType;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcManageUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DummyDataLoader {

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcManageDevice ucManageDevice;

  @Inject
  UcManagePet ucManagePet;

  @Inject
  UcManagePetType ucManagePetType;

  @ConfigProperty(name = "dummydata.load")
  private boolean load;

  public void load() {
    if (load) {
      System.out.println("Loading dummy data NEW ...");
      UserEntity user1 = ucManageUser.create(new UUID(0,1));
      UserEntity user2 = ucManageUser.create(new UUID(0,2));

      DeviceEntity device1 = ucManageDevice.create("DEVICE_NR1", DeviceType.FROG);
      device1.setOwner(user1);

      DeviceEntity device2 = ucManageDevice.create("DEVICE_NR2", DeviceType.FROG);
      device2.setOwner(user2);

      PetTypeEntity petType = ucManagePetType.createPetType("Frog");

      PetEntity pet1 = ucManagePet.create(user1.getId(), "Fifi", petType.getId());
      ucManagePet.create(user1.getId(), "Kiki", petType.getId());

      PetEntity pet3 = ucManagePet.create(user2.getId(), "Baba", petType.getId());
      ucManagePet.create(user2.getId(), "Giggle", petType.getId());

      // Put pets on devices
      device1.setPet(pet1);
      device2.setPet(pet3);

    }
  }
}
