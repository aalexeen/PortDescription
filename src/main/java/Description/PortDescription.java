package Description;

import Description.Service.DescriptionA;
import Pattern.InteractDB;

import java.util.List;

/**
 * @author alex_jd
 */
public class PortDescription extends DescriptionA {

    private Integer idSwitch = null;
    private String description = null;


    public PortDescription() {
    }

    public PortDescription(Integer idSwitch) {
        this.idSwitch = idSwitch;
        makeDescription();

    }

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        makeDescription();
    }

    private void makeDescription() {
        // Get the id_model, street name (translit), home number, home subnumber, model name, switch ip
        /*String sqlRequestSwitchInfo = "select  sw.id_model, str.translit, hm.num, " +
            "hm.subnum_t, mod.name, sw.ip " +
            "from switch sw left join  home hm on sw.id_home=hm.id_home " +
            "left join street str on hm.id_street = str.id_street " +
            "left join model mod on sw.id_model = mod.id_model " +
            "where sw.id_switch = " + idSwitch + ";";*/
        // 1 - id_model, 2 - street name (translit), 3 - home number, 4 - home subnumber, 5 - model name, 6 - switch ip
        //List switchInfo = getDB.getTheList(getDB.selectExecute(sqlRequestSwitchInfo, 1, 2, 3, 4, 6));
        List switchInfo = InteractDB.getTheList(InteractDB.getFromDB("SwitchInfo", idSwitch.toString()));

        DescriptionGen descr = new DescriptionGen(switchInfo);
        description = descr.getDescription();
    }

}
