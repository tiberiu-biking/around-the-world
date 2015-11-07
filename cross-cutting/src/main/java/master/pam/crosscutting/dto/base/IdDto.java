package master.pam.crosscutting.dto.base;


public class IdDto implements IIdDto {

    private long id;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long aId) {
        id = aId;
    }

}
