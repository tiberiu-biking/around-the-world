package master.pam.crud.impl;

import master.pam.crosscutting.dto.base.IIdDto;
import master.pam.crud.impl.entity.base.IdEntity;
import master.pam.crud.impl.entity.manager.JpaEntityManager;
import master.pam.crud.impl.iface.ICrud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class CrudImpl implements ICrud {

  private static CrudImpl instance;
  private EntityManager em;
  private final Logger log = LoggerFactory.getLogger(CrudImpl.class);

  public static final ICrud getInstance() {
    if (instance == null)
      instance = new CrudImpl();
    return instance;
  }

  private CrudImpl() {
  }

  @Override
  public void startup() {
    em = JpaEntityManager.getEntityManager();
  }

  @Override
  public void shutdown() {
    em.close();
  }

  @Override
  public <T extends IIdDto> List<T> select(Class<T> aClass) {
    TypedQuery<T> query = JpaEntityManager.createQuery("SELECT c FROM " + aClass.getName() + " c", aClass);

    return query.getResultList();
  }

  @Override
  public <T extends IIdDto> T find(Class<T> aClass, long aId) {
    return JpaEntityManager.find(aClass, aId);
  }

  @Override
  public void insert(IdEntity aEntity) {
    em.getTransaction().begin();
    JpaEntityManager.persist(aEntity);
    JpaEntityManager.flush();
    em.getTransaction().commit();
  }

  @Override
  public void update(IdEntity aEntity) {
    em.getTransaction().begin();
    em.merge(aEntity);
    JpaEntityManager.flush();
    em.getTransaction().commit();
  }

  @Override
  public <T extends IIdDto> List<T> selectByNamedQuery(Class<T> aClass, String aNamedQuery, Map<String, Object> aFilter) {
    TypedQuery<T> query = em.createNamedQuery(aNamedQuery, aClass);
    setQueryParameters(query, aFilter);
    return query.getResultList();
  }

  @Override
  public <T extends IIdDto> List<T> selectByQuery(Class<T> aClass, String aQuery, Map<String, Object> aFilter) {
    return buildQuery(aClass, aQuery, aFilter).getResultList();
  }

  @Override
  public <T extends IIdDto> T selectSingleByQuery(Class<T> aClass, String aQuery, Map<String, Object> aFilter) {
    return buildQuery(aClass, aQuery, aFilter).getSingleResult();
  }

  @Override
  public <T extends IIdDto> List<T> select(Class<T> aClass, Map<String, Object> aFilter) {
    log.trace("Calling select for entity " + aClass.getCanonicalName());

    StringBuilder sqlBuilder = new StringBuilder("SELECT c FROM " + aClass.getName() + " c WHERE 1 = 1 ");
    for (String key : aFilter.keySet())
      sqlBuilder.append(" AND ").append(key).append(" = :").append(key);

    TypedQuery<T> query = em.createQuery(sqlBuilder.toString(), aClass);
    setQueryParameters(query, aFilter);

    log.trace("Statement = " + sqlBuilder.toString());

    return query.getResultList();
  }

  private void setQueryParameters(Query aQuery, Map<String, Object> aParameters) {
    for (String key : aParameters.keySet()) {
      aQuery.setParameter(key, aParameters.get(key));
      log.trace("Set parameter " + key + " = " + aParameters.get(key));
    }
  }

  private <T extends IIdDto> TypedQuery<T> buildQuery(Class<T> aClass, String aQuery, Map<String, Object> aFilter) {
    TypedQuery<T> query = em.createQuery(aQuery, aClass);
    setQueryParameters(query, aFilter);
    return query;
  }

  @Override
  public void delete(IIdDto aEntity) {
    em.getTransaction().begin();
    em.remove(aEntity);
    em.flush();
    em.getTransaction().commit();
  }

  @Override
  public void delete(Class<? extends IIdDto> aEntityClass, long aId) {
    em.getTransaction().begin();
    IIdDto entity = em.find(aEntityClass, aId);
    em.remove(entity);
    em.flush();
    em.getTransaction().commit();
  }

}
