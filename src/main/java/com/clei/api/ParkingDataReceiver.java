package com.clei.api;

import com.clei.api.dto.ParkingDataDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public/rest")
public class ParkingDataReceiver {
    @RequestMapping("parkingData")
    public Map<String,Object> getParkingData(@RequestBody ParkingDataDTO parkingDataDTO){
        Map<String,Object> map = new HashMap<>();
        if(null != parkingDataDTO){
            map.put("status","success");
            map.put("data",parkingDataDTO);
        }else{
            map.put("status","failure");
        }
        System.out.println(map);
        return map;
    }
}
