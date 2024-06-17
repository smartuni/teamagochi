package haw.teamagochi.backend.device.service.rest.v1.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A data transfer object (DTO) for device.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@RegisterForReflection
public class DeviceDTO {
  Long id;
  String name;
  String type;
  String ownerId;
  Long petId;
}
