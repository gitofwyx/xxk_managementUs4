package com.xxk.management.station.service;

import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.station.entity.Station;

import java.util.List;
import java.util.Map;


public interface StationDevicesService {

    public boolean updateStationDevicesForExchange(Devices devices, OfficesStorage storage) throws Exception;

}
