package Port.Service;

import Service.StatConPostDB;

/**
 * @author alex_jd
 */
public abstract class PortA implements Port, StatConPostDB {

    @Override
    public String getPortDescription() {
        return null;
    }
}
