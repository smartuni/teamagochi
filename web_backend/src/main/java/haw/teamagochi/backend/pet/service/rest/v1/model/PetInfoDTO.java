package haw.teamagochi.backend.pet.service.rest.v1.model;

import lombok.Getter;

@Getter
public class PetInfoDTO {

  long petID;
  String petName;
  String petType;

  public PetInfoDTO(long petID, String petName, String petType){
    this.petID = petID;
    this.petType = petType;
    this.petName = petName;
  }

  //TODO
}
