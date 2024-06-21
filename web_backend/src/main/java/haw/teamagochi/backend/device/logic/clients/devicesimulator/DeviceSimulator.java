package haw.teamagochi.backend.device.logic.clients.devicesimulator;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.logic.UcPetConditions;
import haw.teamagochi.backend.pet.logic.UcPetStatus;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ApplicationScoped
public class DeviceSimulator implements ActionListener {


    @Inject
    UcFindPet ucFindPet;

    @Inject
    UcPetStatus status;

    @Inject
    UcPetConditions conditions;


    public DeviceSimulator() {
        frame.add(panel);
        frame.setSize(200,200);

        this.hungerBar = new JProgressBar();
        hungerBar.setMaximum(100);
        hungerBar.setMinimum(0);
        hungerBar.setName("hunger");
        panel.add(hungerBar);

        // Feed button
        this.feedButton = new JButton("FEED");
        panel.add(feedButton);
        feedButton.addActionListener(this);

        panel.add(infoLabel);

        frame.setVisible(true);

    }


    // Interactive UI elements
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JProgressBar hungerBar;
    JButton feedButton;
    JLabel infoLabel = new JLabel();


    @Scheduled(every = "{GameCycleImpl.interval}")
    public void run() {
        PetEntity pet = ucFindPet.findAll().getFirst();
        if (pet != null) {
            System.out.println(pet.getHunger());
            hungerBar.setValue(pet.getHunger());
            frame.repaint();
        }

                /*
                Class<? extends PetEntity> petEntityClass = pet.getClass();
                for (JProgressBar pBar : pBars) {
                    int newValue = (int) petEntityClass.getDeclaredField(pBar.getName()).get(pet);
                    pBar.setValue(newValue);
                }

                 */

    }

    /*
    private JProgressBar createProgressBar(String name) {
        // Class<? extends PetEntity> petEntityClass = PetEntity.class;
        // Arrays.stream(petEntityClass.getDeclaredFields()).toList().m;
        JProgressBar pBar = new JProgressBar();
        pBar.setName(name);
        pBar.setMinimum(0);
        pBar.setMaximum(100);
        this.panel.add(pBar);

        return pBar;
    }
    */

    @Override
    @Transactional
    public void actionPerformed(ActionEvent e) {
        PetEntity pet = ucFindPet.findAll().getFirst();

        if (e.getSource() == this.feedButton) {
            this.infoLabel.setText("Feed button pressed!");
            conditions.decreaseHunger(pet);
        }
    }

}
