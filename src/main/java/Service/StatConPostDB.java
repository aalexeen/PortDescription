package Service;

import simpleDB.GetFromDB;
import snmp.SNMPManager1;

/**
 * @author alex_jd
 */
public interface StatConPostDB {

    public static GetFromDB getDB = new GetFromDB();
    public static SNMPManager1 snmp = new SNMPManager1();
}
