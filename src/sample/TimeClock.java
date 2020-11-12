package sample;

import javafx.scene.control.Alert;

public class TimeClock
{
    private InitialPartsData initialParts = null;

    private DiagnosticData diagnoses = null;

    private SolutionData solutions = null;

    private ReplacedPartData replacedParts;


    public TimeClock(InitialPartsData initialParts)
    {
        this.initialParts = initialParts;
    }

    public TimeClock(DiagnosticData diagnoses)
    {
        this.diagnoses = diagnoses;
    }

    public TimeClock(SolutionData solutions)
    {
        this.solutions = solutions;
    }

    public TimeClock(ReplacedPartData replacedParts)
    {
        this.replacedParts = replacedParts;
    }

    public InitialPartsData getInitialParts()
    {
        return initialParts;
    }

    public DiagnosticData getDiagnoses()
    {
        return diagnoses;
    }

    public SolutionData getSolutions()
    {
        return solutions;
    }

    public ReplacedPartData getReplacedParts()
    {
        return replacedParts;
    }

    public void clear()
    {
        initialParts = null;

        diagnoses = null;

        solutions = null;

        replacedParts = null;
    }

    public boolean isComplete()
    {
        String errorMsg = "The following sections are not completed\n";

        int count = 0;

        boolean isClockComplete = true;

        if (initialParts == null)
        {
            count++;
            errorMsg += "Initial Parts Page\n";
        }
        if (diagnoses == null)
        {
            count++;
            errorMsg += "Diagnoses Page\n";
        }
        if (solutions == null)
        {
            count++;
            errorMsg += "Solutions Page";
        }
        if (count > 0)
        {
            isClockComplete = false;
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText(errorMsg);
            errorAlert.show();
        }

        return isClockComplete;
    }
}
