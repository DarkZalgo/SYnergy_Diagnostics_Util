package diagnostics;

public class Context {

    private final static Context instance = new Context();

    public static Context getInstance()
    {
        return instance;
    }

    private TimeClock clock = new TimeClock();

    public  TimeClock currentClock()
    {
        return clock;
    }

    public void setClock(TimeClock clock)
    {
        this.clock = clock;
    }
}
