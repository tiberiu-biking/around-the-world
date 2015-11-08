package master.pam.crud.helper.test.markers;

import master.pam.crud.impl.CrudImpl;
import master.pam.crud.impl.entity.business.UserEntity;
import master.pam.crud.impl.filter.FilterBuilder;
import master.pam.crud.impl.filter.FilterBuilderConstants;

import java.util.List;
import java.util.Map;

public class UserCrudTest {

  public static void main(String[] args) {
    Map<String, Object> filter = new FilterBuilder().buildFilter(FilterBuilderConstants.ID, Long.valueOf(999998)).getFilter();

    String query = "SELECT u FROM UserEntity u WHERE u.id = :Id";

    CrudImpl.getInstance().startup();
    List<UserEntity> resultList = CrudImpl.getInstance().selectByQuery(UserEntity.class, query, filter);

    UserEntity entity = resultList.get(0);

    System.out.println("Found user :" + entity);

    CrudImpl.getInstance().shutdown();

    // getCRUD().find( UserEntity.class, aDto.getId() );
  }
}
