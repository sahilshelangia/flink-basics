import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

public class MyProcessWindowFunction extends ProcessWindowFunction<EarnerEvent, List<String>, String, TimeWindow> {
    @Override
    public void process(String s, Context context, Iterable<EarnerEvent> iterable, Collector<List<String>> collector) throws Exception {
        List<String>window = new ArrayList<>();
        for(EarnerEvent event: iterable){
            window.add(event.toString());
        }
        collector.collect(window);
    }
}
