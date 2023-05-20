package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.player.faithtrack.PopeSpaceTile;
import it.polimi.ingsw.view.LocalModel;

public class VaticanReportMessage implements Message{

    private final int popePoint;
    private final PopeSpaceTile[] popeSpaceTiles;

    public VaticanReportMessage(int popePoint, PopeSpaceTile[] popeSpaceTile) {
        this.popePoint = popePoint;
        this.popeSpaceTiles = popeSpaceTile;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setPopePoint(popePoint);
        localModel.setPopeTiles(popeSpaceTiles);
    }
}
