package diagnostics;

import java.io.Serializable;
import java.util.ArrayList;

public class SolutionData implements Serializable
{
    private static final long serialVersionUID = -9165362289006859216L;

    ArrayList<String> imageSolutionsList = new ArrayList<>();
    ArrayList<String> replacedHardwareList = new ArrayList<>();
    ArrayList<String> replacedCosmeticsList = new ArrayList<>();
    ArrayList<String> otherIssuesList = new ArrayList<>();

    public SolutionData(ArrayList<String> imageSolutionsList, ArrayList<String> replacedHardwareList, ArrayList<String> replacedCosmeticsList, ArrayList<String> otherIssuesList)
    {
        this.imageSolutionsList = imageSolutionsList;
        this.replacedHardwareList = replacedHardwareList;
        this.replacedCosmeticsList = replacedCosmeticsList;
        this.otherIssuesList = otherIssuesList;
    }

    public ArrayList<String> getImageSolutionsList()
    {
        return imageSolutionsList;
    }

    public ArrayList<String> getReplacedHardwareList()
    {
        return replacedHardwareList;
    }

    public ArrayList<String> getReplacedCosmeticsList()
    {
        return replacedCosmeticsList;
    }

    public ArrayList<String> getOtherIssuesList()
    {
        return otherIssuesList;
    }
}
