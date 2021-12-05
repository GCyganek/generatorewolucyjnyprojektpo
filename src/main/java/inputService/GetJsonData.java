package inputService;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetJsonData {
    private static Configurations config = null;

    private static void readConfigFromJson() {
        Gson gson = new Gson();
        String path = "parameters.json";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            config = gson.fromJson(br, Configurations.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configurations getConfig() {
        readConfigFromJson();

        if (config.getWidth() <= 0 || config.getHeight() <= 0) {
            throw new IllegalArgumentException("Bad starting arguments: map width and height values should be more than 0");
        }
        if (config.getHeight() * config.getHeight() < config.getAnimalsOnStart()) {
            throw new IllegalArgumentException("Bad starting arguments: too small map to place all animals on different fields");
        }
        if (config.getJungleRatio() <= 0 || config.getJungleRatio() >= 1) {
            throw new IllegalArgumentException("Bad starting arguments: jungleRatio should be a decimal number in range (0, 1)");
        }
        if (config.getSimulations() < 1) {
            throw new IllegalArgumentException("Bad starting arguments: there should be at least one simulation to display");
        }

        return config;
    }

}
