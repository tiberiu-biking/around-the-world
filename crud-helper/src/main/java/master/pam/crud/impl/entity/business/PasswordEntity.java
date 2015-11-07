package master.pam.crud.impl.entity.business;

import master.pam.crud.impl.entity.base.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PASSWORDS")
public class PasswordEntity extends IdEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String password;

    public PasswordEntity() {
    }

    public PasswordEntity(Long aUserId, String aPassword) {
        setUserId(aUserId);
        setPassword(aPassword);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long aUserId) {
        this.userId = aUserId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String aPassword) {
        this.password = aPassword;
    }
}
