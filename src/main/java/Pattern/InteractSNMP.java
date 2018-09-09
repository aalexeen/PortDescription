package Pattern;

import snmp.SNMPManager1;

/**
 * @author alex_jd
 */
public class InteractSNMP {
    public static SNMPManager1 snmp;

    static {
         snmp = new SNMPManager1();
    }

    public static String getSNMP (String ip, String nameOfOID) {
        String oid;
        switch (nameOfOID) {
            case "SysInfo" : oid = "1.3.6.1.2.1.1.5.0"; break;
            default: throw new IllegalArgumentException("Invalid name of OID: " + nameOfOID);
        }
        return getFromSNMP(ip, oid);
    }

    private static String getFromSNMP(String ip, String oid) {
        String snmpReply = "";
        try {
            snmp.start();
            snmpReply = snmp.get(ip, oid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                snmp.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return snmpReply;
    }
}
