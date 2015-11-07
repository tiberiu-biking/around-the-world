package master.pam.crosscutting.gson;

import master.pam.crosscutting.dto.impl.MarkerDto;
import master.pam.crosscutting.dto.timeline.TimelineItemDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GsonHelperTest {

    @Test
    public void test() {
        List<TimelineItemDto> timelineItems = new ArrayList<TimelineItemDto>();

        MarkerDto m1 = new MarkerDto(1, 2);

        MarkerDto m2 = new MarkerDto(3, 4);

        TimelineItemDto t1 = new TimelineItemDto(20013);
        t1.getMarkers().add(m1);
        t1.getMarkers().add(m2);


        MarkerDto m3 = new MarkerDto(5, 6);

        TimelineItemDto t2 = new TimelineItemDto(20014);
        t1.getMarkers().add(m3);

        timelineItems.add(t1);
        timelineItems.add(t2);

        System.out.println(GsonHelper.toGson(timelineItems));

    }

}
