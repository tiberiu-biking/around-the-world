package master.pam.world.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class PasswordExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes aF) {
        return (aF.getName().equalsIgnoreCase("PASSWORD"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }

}
