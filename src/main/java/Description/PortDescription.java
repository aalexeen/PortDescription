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
        // 1 - id_model, 2 - street name (translit), 3 - home number, 4 - home subnumber, 5 - model name, 6 - switch ip
        List switchInfo = InteractDB.getTheList(InteractDB.getFromDB("SwitchInfo", idSwitch.toString()));

        DescriptionGen descr = new DescriptionGen(switchInfo);
        description = descr.getDescription();
    }

}
