package diagnostics;

public class ToggleData
{
    private String groupName;
    private String toggleData;

    private boolean isToggled;
    private boolean isRequired;

    public ToggleData(String groupName, String toggleData, boolean isToggled, boolean isRequired)
    {
        this.groupName = groupName;
        this.toggleData = toggleData;

        this.isToggled = isToggled;
        this.isRequired = isRequired;
    }

    public ToggleData(String toggleData, boolean isToggled)
    {
        this.toggleData = toggleData;
        this.isToggled = isToggled;
    }

    public void clear()
    {
        this.setToggled(false);
        this.setToggleData("");
    }

    public String getGroupName()
    {
        return groupName;
    }

    public String getToggleData() {
        return toggleData;
    }

    public boolean isToggled()
    {
        return isToggled;
    }

    public boolean isRequired()
    {
        return isRequired;
    }

    public void setRequired(boolean required)
    {
        isRequired = required;
    }

    public void setToggleData(String toggleData)
    {
        this.toggleData = toggleData;
    }

    public void setToggled(boolean toggled)
    {
        isToggled = toggled;
    }
}
