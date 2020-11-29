package diagnostics;

import javafx.beans.property.SimpleStringProperty;

public class TimeClockStringProperties
{
    private SimpleStringProperty date;
    private SimpleStringProperty caseNum;
    private SimpleStringProperty custName;
    private SimpleStringProperty serialNum;
    private SimpleStringProperty clockType;
    private TimeClock clock;



    public TimeClockStringProperties(String date, String caseNum, String custName, String serialNum, String clockType, TimeClock clock) {
        this.date = new SimpleStringProperty(date);
        this.caseNum = new SimpleStringProperty(caseNum);
        this.custName = new SimpleStringProperty(custName);
        this.serialNum = new SimpleStringProperty(serialNum);
        this.clockType = new SimpleStringProperty(clockType);
        this.clock = clock;
    }
    public TimeClock getClock()
    {
        return clock;
    }
    public String getDate()
    {
        return date.get();
    }

    public String getCaseNum()
    {
        return caseNum.get();
    }

    public String getCustName()
    {
        return custName.get();
    }

    public String getSerialNum()
    {
        return serialNum.get();
    }

    public String getClockType()
    {
        return clockType.get();
    }

    public void setDate(String date)
    {
        this.date.set(date);
    }

    public void setCaseNum(String caseNum)
    {
        this.caseNum.set(caseNum);
    }

    public void setCustName(String custName)
    {
        this.custName.set(custName);
    }

    public void setSerialNum(String serialNum)
    {
        this.serialNum.set(serialNum);
    }

    public void setClockType(String clockType)
    {
        this.clockType.set(clockType);
    }
}
