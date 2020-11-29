package diagnostics;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class CaseData implements Serializable
{
    private static final long serialVersionUID = -8753677477464695660L;
    private Date startDate;
    private LocalDate receiveDate;
    private String customerName;
    private String caseNum;
    private String serialNum;
    private String initials;
    private String qtyOne;
    private String qtyTwo;

    public CaseData(Date startDate, LocalDate receiveDate, String customerName, String caseNum, String serialNum, String initials, String qtyOne, String qtyTwo)
    {
        this.startDate = startDate;
        this.receiveDate = receiveDate;
        this.customerName = customerName;
        this.caseNum = caseNum;
        this.serialNum = serialNum;
        this.initials = initials;
        this.qtyOne = qtyOne;
        this.qtyTwo = qtyTwo;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public LocalDate getReceiveDate()
    {
        return receiveDate;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getCaseNum()
    {
        return caseNum;
    }

    public String getSerialNum()
    {
        return serialNum;
    }

    public String getInitials()
    {
        return initials;
    }

    public String getQtyOne()
    {
        return qtyOne;
    }

    public String getQtyTwo()
    {
        return qtyTwo;
    }
}
