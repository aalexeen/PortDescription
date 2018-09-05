package Port;

import Description.PortDescription;
import Port.Service.PortA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;

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
        String sqlRequestSwByIdConn = "select * from ports where id = " + idLink + ";";
        // 1 - id_conn, 2 - id_switch(main)
        HashSet<List> idSwitches = getDB.selectExecute(sqlRequestSwByIdConn, 1, 2);
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
