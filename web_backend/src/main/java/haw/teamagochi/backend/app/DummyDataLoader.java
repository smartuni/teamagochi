package haw.teamagochi.backend.app;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.logic.DeviceUseCase;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.PetTypeUseCase;
import haw.teamagochi.backend.pet.logic.PetUseCase;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UserUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DummyDataLoader {

  @Inject
  UserUseCase userUseCase;

  @Inject
  DeviceUseCase deviceUseCase;

  @Inject
  PetUseCase petUseCase;

  @Inject
  PetTypeUseCase petTypeUseCase;

  @ConfigProperty(name = "dummydata.load")
  private boolean load;

  public void load() {
    if (load) {
      System.out.println("Loading dummy data NEW ...");
      UserEntity user1 = userUseCase.createUser(new UUID(0,1));
      UserEntity user2 = userUseCase.createUser(new UUID(0,2));

      DeviceEntity device1 = deviceUseCase.createDevice("DEVICE_NR1", DeviceType.FROG);
      device1.setOwner(user1);

      DeviceEntity device2 = deviceUseCase.createDevice("DEVICE_NR2", DeviceType.FROG);
      device2.setOwner(user2);

      PetTypeEntity petType = petTypeUseCase.createPetType("Frog");

      PetEntity pet1 = petUseCase.createPet(user1.getId(), "Fifi", petType.getId());
      petUseCase.createPet(user1.getId(), "Kiki", petType.getId());

      PetEntity pet3 = petUseCase.createPet(user2.getId(), "Baba", petType.getId());
       petUseCase.createPet(user2.getId(), "Giggle", petType.getId());

      // Put pets on devices
      device1.setPet(pet1);
      device2.setPet(pet3);

    }
  }
}
