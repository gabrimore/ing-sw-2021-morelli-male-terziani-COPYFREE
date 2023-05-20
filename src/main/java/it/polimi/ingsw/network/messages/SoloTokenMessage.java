package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

public class SoloTokenMessage implements Message{

    private final String url;
    private final String effect;

    public SoloTokenMessage(String url, String effect) {
        this.url = url;
        this.effect = effect;
    }

    public String getUrl() {
        return url;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public void action(LocalModel localModel) {
        if (localModel.getCliOrGui() == 1) {
            System.out.println("SoloToken: " + effect + "\n");
        }
    }
}
