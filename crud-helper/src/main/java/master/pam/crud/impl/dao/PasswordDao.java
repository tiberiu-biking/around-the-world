package master.pam.crud.impl.dao;

import com.master.pam.encrypt.api.util.hash.HashUtil;
import master.pam.crud.api.dao.IPasswordDao;
import master.pam.crud.impl.dao.base.BaseDao;
import master.pam.crud.impl.entity.business.PasswordEntity;
import master.pam.crud.impl.filter.FilterBuilder;
import master.pam.crud.impl.filter.FilterBuilderConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PasswordDao extends BaseDao implements IPasswordDao {

  private final static Logger logger = LoggerFactory.getLogger(PasswordDao.class);

  @Override
  public void insert(String aPassword, Long aUserId) {
    PasswordEntity newPasswordEntity = new PasswordEntity(aUserId, HashUtil.getHash(aPassword));
    getCRUD().insert(newPasswordEntity);
  }

  @Override
  public void update(String aPassword, Long aUserId) {
    List<PasswordEntity> passwordEntityList = getBy(aUserId);
    PasswordEntity passwordEntity = passwordEntityList.get(0);
    passwordEntity.setPassword(HashUtil.getHash(aPassword));
    getCRUD().update(passwordEntity);
  }

  @Override
  public boolean isCorrect(String aPassword, Long aUserId) {
    return true;
  }

  private List<PasswordEntity> getBy(Long aUserId) {
    Map<String, Object> filterPass = new FilterBuilder().buildFilter(FilterBuilderConstants.USER_ID, aUserId).getFilter();

    return getCRUD().selectByQuery(PasswordEntity.class, "SELECT p FROM PasswordEntity p WHERE p.userId = :UserId", filterPass);
  }

}
