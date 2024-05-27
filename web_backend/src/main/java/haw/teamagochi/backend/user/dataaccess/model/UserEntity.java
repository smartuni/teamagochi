package haw.teamagochi.backend.user.dataaccess.model;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class UserEntity  {

  @Id
  @GeneratedValue
  private long id;

  public UserEntity() {}

  @NonNull
  @Column(unique = true)
  private UUID externalID;

  @OneToMany
  private List<PetEntity> pets = new ArrayList<>();

  @OneToMany
  private List<DeviceEntity> devices = new ArrayList<>();

  /**
   * Adds a pet to the user entity.
   * This method does not guarantee that the user pets are up-to-date in the database.
   * @param pet must not be null and must not violate any database constraints
   * @throws IllegalArgumentException
   */
  public void addPet(@Valid PetEntity pet) {
    if (pet == null) throw new IllegalArgumentException("Pet must not be null.");
    this.pets.add(pet);
  }



  /**
   * Adds a device to the user entity.
   * This method does not guarantee that the user devices are up-to-date in the database.
   * @param device must not be null and must not violate any database constraints
   * @throws IllegalArgumentException
   */
  public void addDevice(@Valid DeviceEntity device) {
    if (device == null) throw new IllegalArgumentException("Device must not be null.");
    this.devices.add(device);
  }


}
