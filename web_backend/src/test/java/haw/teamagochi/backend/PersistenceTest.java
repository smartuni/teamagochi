package haw.teamagochi.backend;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcManageDevice;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class PersistenceTest {

    @Inject
    UcFindDevice ucFindDevice;

    @Inject
    UcManageDevice ucManageDevice;

    @AfterEach
    void afterEach() {
        ucManageDevice.deleteAll();
    }

    @Test
    @Transactional
    // Hibernate automatically updates changes to persisted entities in DB.
    // Only works when method marked @Transactional!
    void testHibernateEntityManagement() {
        DeviceEntity oldDevice = ucManageDevice.create("Old name", DeviceType.FROG);
        oldDevice.setName("New name");
        DeviceEntity newDevice = ucFindDevice.find(oldDevice.getId());
        Assertions.assertEquals(oldDevice.getName(), newDevice.getName());

    }

}
