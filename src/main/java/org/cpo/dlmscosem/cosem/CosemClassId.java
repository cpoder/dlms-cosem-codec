package org.cpo.dlmscosem.cosem;

public enum CosemClassId {
        DATA(1),
        REGISTER(3),
        EXTENDED_REGISTER(4),
        DEMAND_REGISTER(5),
        REGISTER_ACTIVATION(6),
        PROFILE_GENERIC(7),
        CLOCK(8),
        SCRIPT_TABLE(9),
        SPECIAL_DAYS_TABLE(11),
        ASSOCIATION_LOGICAL_NAME(15),
        SAP_ASSIGNMENT(17),
        IMAGE_TRANSFER(18),
        IEC_LOCAL_PORT_SETUP(19),
        ACTIVITY_CALENDAR(20),
        REGISTER_MONITOR(21),
        ACTION_SCHEDULE(22),
        IEC_HDLC_SETUP(23),
        IEC_TWISTED_PAIR_SETUP(24),
        UTILITY_TABLES(26),
        MODEM_CONFIGURATION(27),
        AUTO_ANSWER(28),
        AUTO_CONNECT(29),
        PUSH_SETUP(40),
        TCP_UDP_SETUP(41),
        IP4_SETUP(42),
        MAC_ADDRESS_SETUP(43),
        GPRS_SETUP(45),
        GSM_DIAGNOSTIC(47),
        IP6_SETUP(48),
        COMPACT_DATA(62),
        SECURITY_SETUP(64),
        ARBITRATOR(68),
        DISCONNECT_CONTROL(70),
        LIMITER(71);

        int value;

        private CosemClassId(int value) {
                this.value = value;
        }
}
