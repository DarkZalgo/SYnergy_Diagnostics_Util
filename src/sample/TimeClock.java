package sample;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class TimeClock
{
    private InitialPartsData initialParts = null;

    private DiagnosticData diagnoses = null;

    private SolutionData solutions = null;

    private ReplacedPartData replacedParts;

    private Stage stage;


    public TimeClock(InitialPartsData initialParts)
    {
        this.initialParts = initialParts;
    }
    public TimeClock(InitialPartsData initialParts, Stage stage)
    {
        this.initialParts = initialParts;
        this.stage = stage;
    }

    public TimeClock(DiagnosticData diagnoses)
    {
        this.diagnoses = diagnoses;
    }
    public TimeClock(DiagnosticData diagnoses, Stage stage)
    {
        this.diagnoses = diagnoses;
        this.stage = stage;
    }

    public TimeClock(SolutionData solutions)
    {
        this.solutions = solutions;
    }
    public TimeClock(SolutionData solutions, Stage stage)
    {
        this.solutions = solutions;
        this.stage = stage;
    }
    public TimeClock(ReplacedPartData replacedParts)
    {
        this.replacedParts = replacedParts;
    }
    public TimeClock(ReplacedPartData replacedParts,Stage stage)
    {
        this.replacedParts = replacedParts;
        this.stage = stage;
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

    public Stage getStage()
    {
        return stage;
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
