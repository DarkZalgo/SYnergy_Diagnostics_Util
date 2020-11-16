package sample;

import java.util.ArrayList;

public class DiagnosticData
{
    private ArrayList<String> turnsOnList;
    private ArrayList<String> functionsList;
    private ArrayList<String> miscList;
    private ArrayList<String> imageIssuesList;
    private ArrayList<String> otherIssuesList;

    public DiagnosticData(ArrayList<String> turnsOnList, ArrayList<String> functionsList, ArrayList<String> miscList, ArrayList<String> imageIssuesList, ArrayList<String> otherIssuesList)
    {
        this.turnsOnList = turnsOnList;
        this.functionsList = functionsList;
        this.miscList = miscList;
        this.imageIssuesList = imageIssuesList;
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

    public ArrayList<String> getOtherIssuesList()
    {
        return otherIssuesList;
    }
}
