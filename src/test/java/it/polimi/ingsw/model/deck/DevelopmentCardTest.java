package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.player.warehouse.Resources;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.model.player.warehouse.Resources.ResType.*;
import static org.junit.Assert.*;


public class DevelopmentCardTest {

    ColorLevel CL1;
    ColorLevel CL2;
    ColorLevel CL3;
    ColorLevel CL4;

    DevelopmentCard dc1;
    DevelopmentCard dc2;
    DevelopmentCard dc3;
    DevelopmentCard dc4;

    Deck devDeck;

    Resources resIN1;
    Resources resIN2;
    Resources resIN3;
    Resources resIN4;
    Resources resCost1;
    Resources resCost2;
    Resources resCost3;
    Resources resCost4;
    Resources resCoin;
    Resources resShield;
    Resources resServant;
    Resources resStone;

    ArrayList<Resources> c1;
    ArrayList<Resources> c2;
    ArrayList<Resources> c3;
    ArrayList<Resources> c4;
    ArrayList<Resources> in1;
    ArrayList<Resources> in2;
    ArrayList<Resources> in3;
    ArrayList<Resources> in4;
    ArrayList<Resources> out1;
    ArrayList<Resources> out2;
    ArrayList<Resources> out3;
    ArrayList<Resources> out4;



    //test made on the third row of the development cards
    @Before
    public void setUp() throws Exception {
/*
        CL1= new ColorLevel("green", 1);
        CL2= new ColorLevel("violet", 1);
        CL3= new ColorLevel("blue", 1);
        CL4= new ColorLevel("yellow", 1);

 */

        resCoin= new Resources(COIN, 1);
        resShield= new Resources(SHIELD, 1);
        resServant= new Resources(SERVANT, 1);
        resStone= new Resources(STONE, 1);

        resIN1= new Resources(SERVANT, 2);
        resIN2= new Resources(COIN, 2);
        resIN3= new Resources(STONE, 2);
        resIN4= new Resources(SHIELD, 2);

        resCost1= new Resources(SERVANT, 3);
        resCost2= new Resources(COIN, 3);
        resCost3= new Resources(STONE, 3);
        resCost4= new Resources(SHIELD, 3);

        ArrayList<Resources> c1=new ArrayList<>(1);
        c1.add(resCost1);
        ArrayList<Resources> c2=new ArrayList<>(1);
        c2.add(resCost2);
        ArrayList<Resources> c3=new ArrayList<>(1);
        c2.add(resCost3);
        ArrayList<Resources> c4=new ArrayList<>(1);
        c2.add(resCost4);

        ArrayList<Resources> in1=new ArrayList<>(1);
        in1.add(resIN1);
        ArrayList<Resources> in2=new ArrayList<>(1);
        in1.add(resIN2);
        ArrayList<Resources> in3=new ArrayList<>(1);
        in1.add(resIN3);
        ArrayList<Resources> in4=new ArrayList<>(1);
        in1.add(resIN4);

        ArrayList<Resources> out1=new ArrayList<>(3);
        out1.add(resCoin);
        out1.add(resShield);
        out1.add(resStone);
        ArrayList<Resources> out2=new ArrayList<>(3);
        out1.add(resServant);
        out1.add(resShield);
        out1.add(resStone);
        ArrayList<Resources> out3=new ArrayList<>(3);
        out1.add(resCoin);
        out1.add(resServant);
        out1.add(resShield);
        ArrayList<Resources> out4=new ArrayList<>(3);
        out1.add(resCoin);
        out1.add(resServant);
        out1.add(resStone);



    }


    @Test
    public void DevelopmentCardTest(){
        dc1 = new DevelopmentCard(c1, 3, in1, out1, 0, CL1);
        dc2 = new DevelopmentCard(c2, 3, in2, out2, 0, CL2);
        dc3 = new DevelopmentCard(c3, 3, in3, out3, 0, CL3);
        dc4 = new DevelopmentCard(c4, 3, in4, out4, 0, CL4);

        assertNotNull(dc1);
        assertNotNull(dc2);
        assertNotNull(dc3);
        assertNotNull(dc4);

    }

}