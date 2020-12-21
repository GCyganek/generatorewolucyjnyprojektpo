package Enums;

import javafx.scene.image.Image;

public enum Icon {
    GRASS("grass.jpg"),
    GROUND("ground.jpg"),
    ANIMAL_LOW_ENERGY("animal-low-energy.jpg"),
    ANIMAL_MEDIUM_ENERGY("animal-medium-energy.jpg"),
    ANIMAL_HIGH_ENERGY("animal-max-energy.jpg"),
    ANIMAL_DOMINANT_GENOME("animal-dominant-genome.jpg");

    public Image icon;

    Icon(String path) {
        try {
            icon = new Image(path);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
