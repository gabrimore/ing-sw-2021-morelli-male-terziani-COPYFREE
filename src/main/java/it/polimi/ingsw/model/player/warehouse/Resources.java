package it.polimi.ingsw.model.player.warehouse;



import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.*;


public class Resources implements Serializable  {

    public enum ResType {
        @Expose
        COIN("CNS", "/images/resourcesgame/coin.png"),
        @Expose
        STONE("STN", "/images/resourcesgame/stone.png"),
        @Expose
        SERVANT("SRV", "/images/resourcesgame/servant.png"),
        @Expose
        SHIELD("SHD", "/images/resourcesgame/shield.png");

        @Expose
        private final String stringType;
        @Expose
        private final String imageUrl;


        ResType(String type, String imageUrl){
            this.stringType=type;
            this.imageUrl=imageUrl;
        }

        public String getType(String res){
            switch(res){

                case "CNS": return "COIN";
                case "STN": return "STONE";
                case "SRV": return "SERVANT";
            }
            return "SHIELD";
        }

        public String getImageUrl(){
            return this.imageUrl;
        }

        public String toString(){
            return stringType;
        }



    }

    @Expose
    private final ResType type;
    @Expose
    private int counter;

    //This list is used in the possibleResources() method of the warehouse
    @Expose
    public static final ArrayList<ResType> RES_TYPES = new ArrayList<>(Arrays.asList(ResType.COIN, ResType.SERVANT, ResType.SHIELD, ResType.STONE));


    public Resources(ResType type, int counter) {
        this.type = type;
        this.counter = counter;
    }

    /**
     * Allow to retrieve the type of the resource
     * @return type of the resource
     */
    public ResType getResourceType() {
        return type;
    }

    /**
     * Allow to retrieve the type of resource as a String
     * @return type of the resource as String
     */
    public String getType(){
        return type.toString();
    }

    /**
     * Allow to retrieve the quantity of the resource
     * @return quantity of the resource
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Allow to set the quantity of the resource
     * @param counter new quantity of the resource
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Compare two objects. In case these two objects are resources the comparison is also made on the type and the quantity
     * @param obj object with which make the comparison
     * @return true or false depending whether the two objects are the same objects
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Resources res = (Resources) obj;
        if (type != res.getResourceType()) return false;
        if (counter != res.getCounter()) return false;

        return true;
    }

    @Override
    public String toString() {
        return type.toString()+counter;
    }
}
