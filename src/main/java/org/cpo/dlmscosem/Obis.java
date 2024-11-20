package org.cpo.dlmscosem;

public class Obis {
    public static final Obis ASSOCIATION_OBJECT = new Obis(MEDIUM.ABSTRACT, 0, 40, 0, 0, 255);
    public static final Obis SAP_ASSIGNMENT = new Obis(MEDIUM.ABSTRACT, 0, 41, 0, 0, 255);

    public enum MEDIUM {
        ABSTRACT(0),
        ELECTRICITY(1),
        HEAT(2),
        GAS(3),
        WATER(4),
        PRESSURE(5),
        TEMPERATURE(6);

        byte code;

        private MEDIUM(int code) {
            this.code = (byte) code;
        }

    }

    public enum PHYSICAL_VALUE {
        ACTIVE_ENERGY(1),
        REACTIVE_ENERGY(2),
        APPARENT_ENERGY(3),
        VOLTAGE(4),
        CURRENT(5),
        POWER(6),
        POWER_FACTOR(7),
        FREQUENCY(8);

        byte code;

        private PHYSICAL_VALUE(int code) {
            this.code = (byte) code;
        }

    }

    private byte medium;
    private byte channel;
    private byte physicalValue;
    private byte measurementType;
    private byte classification;
    private byte billingPeriods;

    public Obis(MEDIUM medium, int channel, int physicalValue, int measurementType, int classification,
            int billingPeriods) {
        this.medium = medium.code;
        this.channel = (byte) channel;
        this.physicalValue = (byte) physicalValue;
        this.measurementType = (byte) measurementType;
        this.classification = (byte) classification;
        this.billingPeriods = (byte) billingPeriods;
    }

    public Obis(MEDIUM medium, int channel, PHYSICAL_VALUE physicalValue, int measurementType, int classification,
            int billingPeriods) {
        this(medium, channel, physicalValue.code, measurementType, classification, billingPeriods);
    }

    public byte[] getBytes() {
        return new byte[] { medium, channel, physicalValue, measurementType, classification, billingPeriods };
    }
}
