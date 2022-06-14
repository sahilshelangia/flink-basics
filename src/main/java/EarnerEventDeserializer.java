import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;

public class EarnerEventDeserializer implements MapFunction<String, EarnerEvent> {
    @Override
    public EarnerEvent map(String s) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        EarnerEvent event = objectMapper.readValue(s, EarnerEvent.class);
        System.out.println(event.toString());
        return event;
    }
}
