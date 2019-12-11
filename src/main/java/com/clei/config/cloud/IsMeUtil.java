package com.clei.config.cloud;

import io.micrometer.core.instrument.util.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 我是我？
 * @author KIyA
 * @since 2019-12-11
 */
public class IsMeUtil {

    /**
     * 到我的回合了吗？
     * @param discoveryClient
     * @param serviceId
     * @return
     */
    /*public static boolean isMyTurn(DiscoveryClient discoveryClient,String serviceId){
        List<String> hosts = getHosts();
        if(null == hosts || hosts.isEmpty()){
            return false;
        }
        // 像我一样的实例们
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        int size = instances.size();
        if(0 == size){
            return false;
        }

        // 就我一个 不是我还是谁？
        if(1 == size){
            return true;
        }

        // 我的位置
        int index = -1;

        outer:
        for (int i = 0; i < size; i++) {
            ServiceInstance instance = instances.get(i);
            String host = instance.getHost();
            for(String h : hosts){
                if(host.equals(h)){
                    index = i;
                    break outer;
                }
            }
        }

        if(-1 == index){
            return false;
        }

        // 到我的回合了吗
        LocalDate now = LocalDate.now();
        int day = now.getDayOfYear();
        // 每年的第几天除以client的数目的余数
        int turn = day % size;
        if(index == turn){
            return true;
        }
        return false;
    }*/

    /**
     * 获取可用host
     * @return
     */
    private static List<String> getHosts(){
        List<String> result = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()){
                NetworkInterface ni = interfaces.nextElement();
                if(ni.isUp()){
                    Enumeration<InetAddress> addresses = ni.getInetAddresses();
                    while (addresses.hasMoreElements()){
                        InetAddress address = addresses.nextElement();
                        if(address instanceof Inet4Address && !address.isLoopbackAddress()){
                            //
                            String host = address.getHostAddress();
                            if(StringUtils.isNotEmpty(host)){
                                result.add(host);
                            }
                        }
                    }
                }
            }
            if(result.isEmpty()){
                result.add(InetAddress.getLocalHost().getHostAddress());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
