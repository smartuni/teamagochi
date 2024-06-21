package haw.teamagochi.backend.device.logic.clients.devicesimulator;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.logic.UcPetConditions;
import haw.teamagochi.backend.pet.logic.UcPetInteractions;
import haw.teamagochi.backend.pet.logic.UcPetStatus;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class DeviceSimulator implements ActionListener {


    @Inject
    UcFindPet ucFindPet;

    @Inject
    UcPetInteractions ucPetInteractions;


    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    // Status bars
    JProgressBar healthBar = new JProgressBar();
    JProgressBar cleanlinessBar = new JProgressBar();
    JProgressBar funBar = new JProgressBar();
    JProgressBar hungerBar = new JProgressBar();
    JProgressBar xpBar = new JProgressBar();
    JProgressBar happinessBar = new JProgressBar();
    JProgressBar wellbeingBar = new JProgressBar();

    // Buttons
    JButton feedButton = new JButton("FEED");
    JButton cleanButton = new JButton("CLEAN");
    JButton medicateButton = new JButton("MEDICATE");
    JButton playButton = new JButton("PLAY");

    // Labels
    JLabel infoLabel = new JLabel();

    public DeviceSimulator() {
        /*
         * Initialize UI widgets
         */

        frame.add(panel);
        frame.setSize(200,320);

        /*
         * Initialize progress bars
         */

        List<JProgressBar> pBars = new LinkedList<>(Arrays.asList(
                xpBar,
                happinessBar,
                wellbeingBar,
                hungerBar,
                healthBar,
                cleanlinessBar,
                funBar
        ));

        for (JProgressBar pBar : pBars) {
            pBar.setMinimum(0);
            pBar.setMaximum(pBar == xpBar ? 5000 : 100);
            pBar.setStringPainted(true);
            panel.add(pBar);

        }

        // XP
        xpBar.setString("xp");

        // Happiness
        happinessBar.setString("happiness");

        // Wellbeing
        wellbeingBar.setString("wellbeing");

        // Hunger
        hungerBar.setString("hunger");
        hungerBar.setForeground(Color.RED);

        // Health
        healthBar.setString("health");

        // Cleanliness
        cleanlinessBar.setString("cleanliness");

        // Fun
        funBar.setString("fun");


        /*
         * Initialize buttons
         */

        List<JButton> buttons = new LinkedList<>(Arrays.asList(
                feedButton,
                medicateButton,
                playButton,
                cleanButton
        ));

        for (JButton button : buttons) {
            panel.add(button);
            button.addActionListener(this);
        }


        /*
         * Initialize Labels
         */
        panel.add(infoLabel);


        frame.setVisible(true);

    }





    @Scheduled(every = "1s")
    public void run() {
        List<PetEntity> pets = ucFindPet.findAll();
        if (!pets.isEmpty()) {
            PetEntity pet = pets.getFirst();
            hungerBar.setValue(pet.getHunger());
            happinessBar.setValue(pet.getHappiness());
            wellbeingBar.setValue(pet.getWellbeing());
            xpBar.setValue(pet.getXp());
            cleanlinessBar.setValue(pet.getCleanliness());
            funBar.setValue(pet.getFun());
            healthBar.setValue(pet.getHealth());

            frame.repaint();
        }


    }

    @Override
    @Transactional
    public void actionPerformed(ActionEvent e) {
        PetEntity pet = ucFindPet.findAll().getFirst();
        Object source = e.getSource();
        assert(source instanceof JButton);

        // Switch statements don't work on objects ...
        if (source == this.feedButton) {
            ucPetInteractions.feedPet(pet);
        } else if (source == this.cleanButton) {
            ucPetInteractions.cleanPet(pet);
        } else if (source == this.medicateButton) {
            ucPetInteractions.medicatePet(pet);
        } else if (source == this.playButton) {
            ucPetInteractions.playWithPet(pet);
        }

        JButton pressedButton = (JButton) source;
        this.infoLabel.setText(String.format("%s button pressed!", pressedButton.getText()));
    }

}
