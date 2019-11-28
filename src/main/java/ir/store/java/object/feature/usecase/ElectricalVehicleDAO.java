package ir.store.java.object.feature.usecase;
import ir.store.java.object.model.ElectricalVehicle;

import java.util.List;


public interface ElectricalVehicleDAO{

    public List<ElectricalVehicle> getAllElectricalVehicle();
    public  void addElectricalVehicle(ElectricalVehicle electricalVehicle);
    public ElectricalVehicle getElectricalVehicle(int electricalVehicleId);
    public void updateElectricalVehicle(ElectricalVehicle electricalVehicle);
    public void deleteElectricalVehicle(int electricalVehicleId);


}
