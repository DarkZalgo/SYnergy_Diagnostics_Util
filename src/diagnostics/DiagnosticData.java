package diagnostics;

import java.io.Serializable;
import java.util.ArrayList;

public class DiagnosticData implements Serializable
{
    private static final long serialVersionUID = -2222121479548445444L;
    private ArrayList<String> turnsOnList;
    private ArrayList<String> functionsList;
    private ArrayList<String> miscList;
    private ArrayList<String> imageIssuesList;
    private ArrayList<String> badPartsList;
    private ArrayList<String> otherIssuesList;

    public DiagnosticData(ArrayList<String> turnsOnList, ArrayList<String> functionsList, ArrayList<String> miscList, ArrayList<String> imageIssuesList, ArrayList<String> badPartsList, ArrayList<String> otherIssuesList)
    {
        this.turnsOnList = turnsOnList;
        this.functionsList = functionsList;
        this.miscList = miscList;
        this.imageIssuesList = imageIssuesList;
        this.badPartsList = badPartsList;
        this.otherIssuesList = otherIssuesList;
    }

    public ArrayList<String> getTurnsOnList()
    {
        return turnsOnList;
    }

    public ArrayList<String> getFunctionsList()
    {
        return functionsList;
    }

    public ArrayList<String> getMiscList()
    {
        return miscList;
    }

    public ArrayList<String> getImageIssuesList()
    {
        return imageIssuesList;
    }

    public ArrayList<String> getBadPartsList() {
        return badPartsList;
    }

    public ArrayList<String> getOtherIssuesList()
    {
        return otherIssuesList;
    }
}
