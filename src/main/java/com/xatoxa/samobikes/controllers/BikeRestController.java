package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BikeRestController {
    private BikeService bikeService;

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @PostMapping("/check_bike_info")
    public String checkInfo(@Param("id")Integer id,
                            @Param("number")Integer number,
                            @Param("qrNumber")Integer qrNumber,
                            @Param("VIN")String VIN){
        String requestMsg = "";
        if (bikeService.isNumberUnique(id, number)) requestMsg += "1";
        else requestMsg += "0";
        if (bikeService.isQRNumberUnique(id, qrNumber)) requestMsg += "1";
        else requestMsg += "0";
        if (bikeService.isVINUnique(id, VIN)) requestMsg += "1";
        else requestMsg += "0";

        return requestMsg;
    }
}
