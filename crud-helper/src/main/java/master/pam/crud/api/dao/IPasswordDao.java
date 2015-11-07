package master.pam.crud.api.dao;

public interface IPasswordDao {

    void insert(String aPassword, Long aUserId);

    void update(String aPassword, Long aUserId);

    boolean isCorrect(String aPassword, Long aUserId);

}
