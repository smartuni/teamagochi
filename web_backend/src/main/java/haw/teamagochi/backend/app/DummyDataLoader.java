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
  boolean load;

  void load() {
    if (load) {
      System.out.println("Loading dummy data V4 ...");

      /*
       * Create users
       */
      UserEntity user1 =
          ucManageUser.create(UUID.fromString("96daba6c-00ae-4ddb-bea5-e9c0cdb5a459"));
      UserEntity user2 =
          ucManageUser.create(UUID.fromString("6b7ec589-17ff-43d0-9ed6-9f44f3bc9c73"));

      /*
       * Create pets
       */
      PetTypeEntity petType = ucManagePetType.createPetType("Frog");

      PetEntity pet1 = ucManagePet.create(user1.getId(), "Fifi", petType.getId());
      ucManagePet.create(user1.getId(), "Kiki", petType.getId());

      PetEntity pet2 = ucManagePet.create(user2.getId(), "Baba", petType.getId());
      ucManagePet.create(user2.getId(), "Giggle", petType.getId());

      /*
       * Create devices
       */
      DeviceEntity device1 = new DeviceEntity("DEVICE_NR1", DeviceType.FROG);
      device1.setOwner(user1);
      device1.setPet(pet1);
      ucManageDevice.create(device1);

      DeviceEntity device2 = new DeviceEntity("DEVICE_NR2", DeviceType.FROG);
      device2.setOwner(user2);
      device2.setPet(pet2);
      ucManageDevice.create(device2);

      DeviceEntity device3 = new DeviceEntity("DEVICE_NR3", DeviceType.FROG);
      device3.setOwner(user1);
      ucManageDevice.create(device3);

      /*
       * Start simulation
       */

    }
  }
}
