package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.faithtrack.VaticanReportException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FaithTrackTest {

    private FaithTrack faithTrack;

    @Before
    public void start(){
        faithTrack = new FaithTrack();
        assertNotNull(faithTrack);
    }

    @After
    public void restart(){
        faithTrack = null;
    }

    @Test
    public void positionAndPointTest(){
        int j = 0, k = 0;
        for(int i=0; i<=9; i++){
            try {
                faithTrack.setPosition();
            } catch (VaticanReportException e){
                j = -1;
            }
        }
        k = faithTrack.getPoint();

        assertEquals(j , -1);
        assertEquals(k, 6);
        assertEquals(faithTrack.getPosition(), 10);
    }

    @Test
    public void vaticanReportTest(){
        int j, k = 0, h = 0;
        int tile = 0;

        for(int i=0; i<=6; i++) {
            try {
                faithTrack.setPosition();
            } catch (VaticanReportException e) {
                tile = e.getPope();
                k++;
            }
        }
        faithTrack.vaticanReport(0);
        j = faithTrack.getPoint();
        h = faithTrack.getPopePoint();

        assertEquals(j, 4);
        assertEquals(h, 2);
        assertTrue(faithTrack.getPopeTiles()[tile].isDiscarded());

        for(int i=0; i<=9; i++) {
            try {
                faithTrack.setPosition();
            } catch (VaticanReportException e) {
                k++;
            }
        }
        j = faithTrack.getPoint();

        assertEquals(faithTrack.getPosition(), 17);
        assertEquals(k, 1);
        assertEquals(j, 14);
    }
}