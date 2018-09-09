package EquipmentDetail;

import EquipmentDetail.Service.EqpmtA;
import Pattern.InteractDB;
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
    private Logger logger = LoggerFactory.getLogger(EqpmtCurrent.class);

    //private Boolean writePortDesrc = false;

    private HashMap<Integer, Port> portsList = new HashMap<Integer, Port>();
    // String - ifname, Port - the object of Port
    private HashMap<String, Port> portsDescr = new HashMap<String, Port>();


    public EqpmtCurrent() {
        setPortsList();
    }

    public EqpmtCurrent(Integer idEqpmt) {
        this.idEqpmt = idEqpmt;
        logger.debug("current id Equipment {}", idEqpmt);
        setPortsList();
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
        // Get Connections list of current switch
        // 1 - id_link, 5 - id_conn(main), 8 - ifname (GigabitEthernet3/0/21)
        Set<List> linksFromDB = InteractDB.getFromDB("ListOfPorts", idEqpmt.toString());
        logger.debug("linksFromDB {}", linksFromDB);
        if (!linksFromDB.isEmpty()) {
            logger.debug("Port links from DB (id_link, id_conn, ifname) {}", linksFromDB );

            Iterator<List> idLinks = linksFromDB.iterator();
            //Get the id_switch according to id_conn by the id_links
            while (idLinks.hasNext()) {
                List<Object> idLinkCurrent = idLinks.next();
                logger.debug("idLinkCurrent {}", idLinkCurrent);
                String idLink = idLinkCurrent.get(1).toString();
                String idConn = idLinkCurrent.get(1).toString();
                String  ifname = idLinkCurrent.get(2).toString();
                if(idLink != "") {
                    Port portCurrent = new PortCurrent(Integer.valueOf(idLink));
                    //Put the ifname(key) and portCurrent(value) to the hashmap portsDescr
                    logger.debug("id_link={}; id_conn={}; ifname={}; portCurrent={}; ", idLink, idConn, ifname, portCurrent.getPortDescription());
                    //Put into the hashmap IFNAME and object PortCurrent
                    portsDescr.put(ifname, portCurrent);
                } else {
                    Port portCurrent = new PortCurrent();
                    logger.debug("id_link={}; id_conn={}; ifname={}; portCurrent={}; ", idLink, idConn, ifname, portCurrent.getPortDescription());
                    portsDescr.put(ifname, portCurrent);
                }

            }


        }

    }


}
