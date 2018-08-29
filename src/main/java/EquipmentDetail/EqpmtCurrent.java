package EquipmentDetail;

import EquipmentDetail.Service.EqpmtA;
import Port.Service.Port;
import Port.PortCurrent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author alex_jd
 */
public class EqpmtCurrent extends EqpmtA {

    private Integer idEqpmt = null;
    //private String Description = null;
    //private String Address = null;
    //private String IP = null;
    private Logger logger;

    //private Boolean writePortDesrc = false;

    private HashMap<Integer, Port> portsList = new HashMap<Integer, Port>();
    // String - ifname, Port - the object of Port
    private HashMap<String, Port> portsDescr = new HashMap<String, Port>();


    public EqpmtCurrent() {
        //getDB = new GetFromDB();
        //snmp = new SNMPManager1();
        setPortsList();
        logger = LoggerFactory.getLogger(EqpmtCurrent.class);
    }

    public EqpmtCurrent(Integer idEqpmt) {
        //getDB = new GetFromDB();
        //snmp = new SNMPManager1();
        this.idEqpmt = idEqpmt;
        System.out.println("idEqpmp = " + idEqpmt);
        setPortsList();
        logger = LoggerFactory.getLogger(EqpmtCurrent.class);
    }

    public HashMap<Integer, Port> getPortsList() {
        return portsList;
    }

    public void setPortsList() {
        makePortList();

    }

    public HashMap<String, Port> getPortsDescr() {
        return portsDescr;
    }

    private void makePortList() {
        Set<List> linksFromDB = new HashSet<List>();
        // Get Connections list of current switch
        String sqlRequestIdLinksByIdEqpmt = "select * from ports where id_sw = " + idEqpmt + " and id_conn != 0;";
        // 1 - id_link, 5 - id_conn(main), 8 - ifname (GigabitEthernet3/0/21)
        linksFromDB = getDB.selectExecute(sqlRequestIdLinksByIdEqpmt, 1, 5, 8);
        System.out.println("linksFromDB " + linksFromDB);
        if (!linksFromDB.isEmpty()) {
            System.out.println("linksFromDB " + linksFromDB);
            //logger.debug("show the port list {}", linksFromDB.toString());
            /*for (List <List> e :linksFromDB) {
                System.out.println("id_link=" + e.get(0) + "; id_connn=" + e.get(1) + "; ifname=" + e.get(2));
            }*/
            Iterator<List> idLinks = linksFromDB.iterator();
            //Get the id_switch according to id_conn by the id_links
            while (idLinks.hasNext()) {
                List<Object> idLinkCurrent = idLinks.next();

                Port portCurrent = new PortCurrent(Integer.valueOf(idLinkCurrent.get(1).toString()));
                //Put the ifname(key) and portCurrent(value) to the hashmap portsDescr
                //System.out.println("id_link=" + idLinkCurrent.get(0) + "; id_connn=" + idLinkCurrent.get(1) + "; ifname=" + idLinkCurrent.get(2) +  "; portCurrent " + portCurrent.getPortDescription());
                logger.debug("id_link={}; id_conn={}; ifname={}; portCurrent={}; ", idLinkCurrent.get(0), idLinkCurrent.get(1), idLinkCurrent.get(2), portCurrent.getPortDescription());
                //portsList.put(Integer.valueOf(temp.get(0).toString()), portCurrent);
                //Put into the hashmap IFNAME and object PortCurrent
                portsDescr.put(idLinkCurrent.get(2).toString(), portCurrent);


            }


        }

    }


}
