package master.pam.crud.impl.dao;

import master.pam.crosscutting.dto.api.IUserDto;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.crud.api.dao.IPasswordDao;
import master.pam.crud.api.dao.IUserDao;
import master.pam.crud.impl.dao.base.BaseDao;
import master.pam.crud.impl.entity.business.UserEntity;
import master.pam.crud.impl.filter.FilterBuilder;
import master.pam.crud.impl.filter.FilterBuilderConstants;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class UserDao extends BaseDao implements IUserDao {

    private final static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private IPasswordDao passwordDao = SpringContext.getBean(IPasswordDao.class);

    @Override
    public IUserDto getUser(String aEmail, String aPassword) {
        logger.trace("Calling getUser: " + aEmail + ", password " + aPassword);

        Map<String, Object> filter = new FilterBuilder().buildFilter(FilterBuilderConstants.EMAIL, aEmail)
                .getFilter();

        String query = "SELECT u FROM UserEntity u WHERE UPPER(u.email) = UPPER(:Email)";
        List<UserEntity> resultList = getCRUD().selectByQuery(UserEntity.class, query, filter);

        IUserDto result = null;
        if (resultList.size() < 1)
            logger.debug("User not found!");
        else {
            logger.debug("User found!");
            UserEntity resultUser = resultList.get(0);
            if (passwordDao.isCorrect(aPassword, resultUser.getId()))
                result = resultUser;
            else
                logger.trace("Password incorrect!");
        }
        return result;
    }

    @Override
    public IUserDto insertUser(IUserDto aDto, String aPassword) {
        UserEntity entity = new UserEntity();
        try {
            BeanUtils.copyProperties(entity, aDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO
            e.printStackTrace();
        }
        getCRUD().insert(entity);
        passwordDao.insert(aPassword, entity.getId());
        return entity;
    }

    @Override
    public IUserDto update(IUserDto aDto) throws IllegalAccessException, InvocationTargetException {
        logger.trace("updateUser: " + aDto);

        UserEntity entityUser = getCRUD().find(UserEntity.class, aDto.getId());
        logger.debug("Found user with id(" + aDto.getId() + "):  " + entityUser);

        BeanUtils.copyProperties(entityUser, aDto);
        getCRUD().update(entityUser);
        return entityUser;
    }

    @Override
    public IUserDto getUser(long aUserId) {
        logger.trace("getUser(" + aUserId + ")");

        Map<String, Object> filter = new FilterBuilder().buildFilter(FilterBuilderConstants.ID, aUserId).getFilter();

        String query = "SELECT u FROM UserEntity u WHERE u.id = :Id";
        return getCRUD().selectSingleByQuery(UserEntity.class, query, filter);
    }

    @Override
    public IUserDto getUser(String aEmail) {

        Map<String, Object> filter = new FilterBuilder().buildFilter(FilterBuilderConstants.EMAIL, aEmail).getFilter();

        String query = "SELECT u FROM UserEntity u WHERE UPPER(u.email) = UPPER(:Email)";
        List<UserEntity> resultList = getCRUD().selectByQuery(UserEntity.class, query, filter);

        if (resultList.size() < 1) {
            logger.trace("User not found!");
            return null;
        } else {
            logger.trace("User found!");
            return resultList.get(0);
        }
    }

    @Override
    public IUserDto getUserByFoursquareId(String aUserFoursquareId) {
        Map<String, Object> filter = new FilterBuilder().buildFilter(FilterBuilderConstants.FOURSQUARE_ID, aUserFoursquareId).getFilter();

        List<UserEntity> resultList = getCRUD().select(UserEntity.class, filter);

        if (resultList.size() < 1) {
            logger.trace("User not found!");
            return null;
        } else {
            logger.trace("User found!");
            return resultList.get(0);
        }
    }

}
