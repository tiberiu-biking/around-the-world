package master.pam.crud.api.dao;

import master.pam.crosscutting.dto.api.IUserDto;

import java.lang.reflect.InvocationTargetException;

public interface IUserDao {

  IUserDto getUser(String aEmail, String aPassword);

  IUserDto insertUser(IUserDto aDto, String aPassword);

  IUserDto update(IUserDto aDto) throws IllegalAccessException, InvocationTargetException;

  IUserDto getUser(long aUserId);

  IUserDto getUser(String aEmail);

  IUserDto getUserByFoursquareId(String aUserFoursquareId);
}
