public class EarnerEvent {
    public String uuid;
    public String state;
    public Long ts;
    public String dateTime;

    public EarnerEvent() {
    }

    public EarnerEvent(String uuid, String state, Long ts, String dateTime) {
        this.uuid = uuid;
        this.state = state;
        this.ts = ts;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "EarnerEvent{" +
                "uuid='" + uuid + '\'' +
                ", state='" + state + '\'' +
                ", ts=" + ts +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
