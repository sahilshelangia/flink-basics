import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class WordCountJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        String topic = "test";
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "127.0.0.1:9092");
        DataStream<String> data = env.addSource(new FlinkKafkaConsumer<String>(topic, new SimpleStringSchema(), props));
        data.map(new EarnerEventDeserializer())
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<EarnerEvent>(Time.seconds(20)) {
                    @Override
                    public long extractTimestamp(EarnerEvent earnerEvent) {
                        return earnerEvent.ts;
                    }
                })
                .keyBy(earnerEvent -> earnerEvent.uuid)
                .window(TumblingEventTimeWindows.of(Time.minutes(1)))
                .process(new MyProcessWindowFunction())
                .print();
        env.execute("Windowing in flink");
    }
}
