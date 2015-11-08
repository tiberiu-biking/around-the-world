package master.pam.crud.impl.dao.base;

import master.pam.crud.impl.CrudImpl;
import master.pam.crud.impl.iface.ICrud;

public class BaseDao {

  protected static ICrud getCRUD() {
    return CrudImpl.getInstance();
  }

}
