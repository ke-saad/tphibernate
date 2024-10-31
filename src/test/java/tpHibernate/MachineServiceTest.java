package tpHibernate;

import entities.Machine;
import entities.Salle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.MachineService;
import services.SalleService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class MachineServiceTest {
    private MachineService machineService;
    private Machine machine;
    private Salle salle;
    private SalleService salleService;

    @Before
    public void setUp() {
        machineService = new MachineService();
        salleService = new SalleService();
        salle = new Salle("A101");

        salleService.create(salle);
        machine = new Machine();
        machine.setRef("MACH-001");
        machine.setDateAchat(new Date());
        machine.setSalle(salle);
        
        machineService.create(machine);
    }

    @After
    public void tearDown() {
        
        Machine foundMachine = machineService.findById(machine.getId());
        if (foundMachine != null) {
            machineService.delete(foundMachine);
        }
        
        Salle foundSalle = salleService.findById(salle.getId());
        if (foundSalle != null) {
            salleService.delete(foundSalle);
        }
    }

    @Test
    public void testCreate() {
        assertNotNull("Machine should have been created with an ID", machine.getId());
    }

    @Test
    public void testFindById() {
        Machine foundMachine = machineService.findById(machine.getId());
        assertNotNull("Machine should be found", foundMachine);
        assertEquals("Found machine should match", machine.getRef(), foundMachine.getRef());
    }

    @Test
    public void testUpdate() {
        machine.setRef("MACH-002"); 
        boolean result = machineService.update(machine);
        assertTrue("Machine should be updated successfully", result);
        Machine updatedMachine = machineService.findById(machine.getId());
        assertEquals("Updated machine ref should match", "MACH-002", updatedMachine.getRef());
    }

    @Test
    public void testDelete() {
        boolean result = machineService.delete(machine);
        assertTrue("Machine should be deleted successfully", result);
        Machine foundMachine = machineService.findById(machine.getId());
        assertNull("Machine should not be found after deletion", foundMachine);
    }

    @Test
    public void testFindBetweenDate() {
        List<Machine> machines = machineService.findBetweenDate(
            new Date(System.currentTimeMillis() - 86400000), 
            new Date() 
        );
        assertNotNull("Machines list should not be null", machines);
        assertTrue("Machines list should contain at least one machine", machines.size() > 0);
    }
}
