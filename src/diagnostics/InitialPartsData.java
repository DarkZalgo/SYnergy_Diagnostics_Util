package diagnostics;

import java.io.Serializable;

public class InitialPartsData implements Serializable
{
    private static final long serialVersionUID = -333047085423597354L;

    private String model;
    private String reader;
    private String fpuType;
    private String fpuSize;
    private String communication;
    private String coreboard;
    private String motherboard;
    private String sdCard;
    private String battery;
    private String interfaceBoard;
    private String mac;
    private String image;
    private String version;

    public InitialPartsData(String model, String reader, String fpuType, String fpuSize, String communication,
                     String coreboard, String motherboard, String sdCard, String battery, String interfaceBoard,
                     String mac, String image, String version)
    {
        this.model = model;
        this.reader = reader;
        this.fpuType = fpuType;
        this.fpuSize = fpuSize;
        this.communication = communication;
        this.coreboard = coreboard;
        this.motherboard = motherboard;
        this.sdCard = sdCard;
        this.battery = battery;
        this.interfaceBoard = interfaceBoard;
        this.mac = mac;
        this.image = image;
        this.version = version;
    }

    public String getModel()
    {
        return model;
    }

    public String getReader()
    {
        return reader;
    }

    public String getFpuType()
    {
        return fpuType;
    }

    public String getFpuSize()
    {
        return fpuSize;
    }

    public String getCommunication()
    {
        return communication;
    }

    public String getCoreboard()
    {
        return coreboard;
    }

    public String getMotherboard()
    {
        return motherboard;
    }

    public String getSdCard()
    {
        return sdCard;
    }

    public String getBattery()
    {
        return battery;
    }

    public String getInterfaceBoard()
    {
        return interfaceBoard;
    }

    public String getMac()
    {
        return mac;
    }

    public String getImage()
    {
        return image;
    }

    public String getVersion()
    {
        return version;
    }
}
