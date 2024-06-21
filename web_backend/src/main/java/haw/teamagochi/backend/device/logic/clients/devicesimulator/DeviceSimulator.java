package haw.teamagochi.backend.device.logic.clients.devicesimulator;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.logic.UcPetConditions;
import haw.teamagochi.backend.pet.logic.UcPetStatus;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@ApplicationScoped
public class DeviceSimulator implements ActionListener {


    @Inject
    UcFindPet ucFindPet;

    @Inject
    UcPetStatus status;

    @Inject
    UcPetConditions conditions;

    // Dynamic UI elements

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    // Status bars
    JProgressBar hungerBar = new JProgressBar();
    JProgressBar xpBar = new JProgressBar();
    JProgressBar happinessBar = new JProgressBar();
    JProgressBar wellbeingBar = new JProgressBar();

    // Buttons
    JButton feedButton;

    // Labels
    JLabel infoLabel = new JLabel();

    public DeviceSimulator() {
        frame.add(panel);
        frame.setSize(200,200);

        // Hunger
        hungerBar.setMaximum(100);
        hungerBar.setMinimum(0);
        hungerBar.setString("hunger");
        panel.add(hungerBar);

        // Wellbeing
        wellbeingBar.setMaximum(100);
        wellbeingBar.setMinimum(0);
        wellbeingBar.setString("wellbeing");
        panel.add(wellbeingBar);

        // Happiness
        happinessBar.setMaximum(100);
        happinessBar.setMinimum(0);
        happinessBar.setString("happiness");
        panel.add(happinessBar);

        // XP
        xpBar.setMaximum(100);
        xpBar.setMinimum(0);
        xpBar.setString("xp");
        panel.add(xpBar);

        // Feed button
        this.feedButton = new JButton("FEED");
        panel.add(feedButton);
        feedButton.addActionListener(this);

        panel.add(infoLabel);

        frame.setVisible(true);

    }





    @Scheduled(every = "{GameCycle.interval}")
    public void run() {
        List<PetEntity> pets = ucFindPet.findAll();
        if (!pets.isEmpty()) {
            PetEntity pet = pets.getFirst();
            hungerBar.setValue(pet.getHunger());
            happinessBar.setValue(pet.getHappiness());
            wellbeingBar.setValue(pet.getWellbeing());
            xpBar.setValue(pet.getXp());
            frame.repaint();
        }


    }

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
