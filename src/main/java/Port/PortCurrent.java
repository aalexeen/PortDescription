package Port;

import Description.PortDescription;
import Pattern.InteractDB;
import Port.Service.PortA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * @author alex_jd
 */
public class PortCurrent extends PortA {

    private Integer idLink = null;
    private String description;

    private Logger logger = LoggerFactory.getLogger(PortCurrent.class);

    public PortCurrent() {
        makeDescription("empty");
    }

    public PortCurrent(Integer idLink) {
        this.idLink = idLink;
        makeDescription();
    }

    @Override
    public String getPortDescription() {
        return description;
    }

    public void setDescription() {
        makeDescription();
    }

    private void makeDescription() {
        // 1 - id_conn, 2 - id_switch(main)
        Set<List> idSwitches = InteractDB.getFromDB("SwitchByIdConn", idLink.toString());
        logger.debug("idSwitches {}", idSwitches);
        // Get id_switch from List
        PortDescription descr = new PortDescription(Integer.valueOf(idSwitches.iterator().next().get(1).toString()));
        description = descr.getDescription();
    }

    private void makeDescription(String empty) {
        if(empty.equals("empty")) {
            description = "";
        }
    }
}
