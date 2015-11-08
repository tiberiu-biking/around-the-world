package master.pam.crud.impl.entity.base;

import master.pam.crosscutting.dto.base.IIdDto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class IdEntity implements IIdDto {

  @javax.persistence.Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Override
  public long getId() {
    return id;
  }

  public void setId(long aId) {
    this.id = aId;
  }
}
