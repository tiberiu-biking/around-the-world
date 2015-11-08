package master.pam.world.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class RelationsExclusionStrategy implements ExclusionStrategy {

  @Override
  public boolean shouldSkipField(FieldAttributes aF) {
    return (aF.getAnnotation(javax.persistence.OneToMany.class) != null)
            || (aF.getAnnotation(javax.persistence.ManyToOne.class) != null);
  }

  @Override
  public boolean shouldSkipClass(Class<?> aClass) {
    return false;
  }

}
