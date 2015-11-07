package master.pam.crud.impl.iface;

import master.pam.crosscutting.dto.base.IIdDto;
import master.pam.crud.impl.entity.base.IdEntity;

import java.util.List;
import java.util.Map;

public interface ICrud {

    void startup();

    void shutdown();

    <T extends IIdDto> List<T> select(Class<T> aClass);

    <T extends IIdDto> List<T> select(Class<T> aClass, Map<String, Object> aFilter);

    <T extends IIdDto> T find(Class<T> aClass, long aId);

    void insert(IdEntity aEntity);

    void delete(IIdDto aEntity);

    void delete(Class<? extends IIdDto> aEntityClass, long aId);

    <T extends IIdDto> List<T> selectByNamedQuery(Class<T> aClass, String aNamedQuery, Map<String, Object> aFilter);

    <T extends IIdDto> List<T> selectByQuery(Class<T> aClass, String aQuery, Map<String, Object> aFilter);

    <T extends IIdDto> T selectSingleByQuery(Class<T> aClass, String aQuery, Map<String, Object> aFilter);

    void update(IdEntity aDto);

}
