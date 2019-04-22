package com.clei.controller;

import com.clei.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("tempOrder")
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    private static final String TABLE_NAME = "temp_orders";
    private static final String INITIAL_DATE = "2016-12-01 00:00:00";
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value="getByPage")
    @ResponseBody
    public Collection<Map<String,Object>> getByPage(String parkLotId,String carLicense,String orderType,String startDate, String endDate, int page, int size){
        Date sDate;
        Date eDate;
        try{
            sDate = SDF.parse(startDate);
            eDate = SDF.parse(endDate);
        } catch (ParseException e) {
            System.out.println("时间参数有错！！！");
            return null;
        }
        //查询参数
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("parkLotId", parkLotId);
        queryMap.put("carLicense", carLicense);
        queryMap.put("orderType", orderType);
        //获得需要检索的各个表以及每个表符合条件的记录数
        List<TableObject> tables = calcTable(sDate,eDate,queryMap);
        //总数据条数
        int totalSize = tables.stream().collect(Collectors.summingInt(TableObject::getCount));
        List<Map<String,Object>> orders = selectRecord(tables,queryMap,page,size);

        System.out.println("totalSize: " + totalSize);
        orders.forEach( v -> v.forEach( (k,val) -> {
            System.out.println( "KEY: " + k + "\t VALUE: " + val );
        } ));

        return orders;
    }

    private List<TableObject> calcTable(Date sDate, Date eDate, Map<String,Object> map){
        Date startDate = new Date(sDate.getTime());
        Date endDate = new Date(eDate.getTime());
        Date firstDate;
        try {
            firstDate = SDF.parse(INITIAL_DATE);
        } catch (ParseException e) {
            System.out.println("时间参数有错！！！");
            return null;
        }
        if(startDate.getTime() < firstDate.getTime()){
            startDate = firstDate;
        }
        Date curDate = new Date();
        if(endDate.getTime() > curDate.getTime()){
            endDate = curDate;
        }
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        //调整完开始时间和结束时间之后。。
        //添加temp_orders表,近6个月的。。所以 减5
        endCalendar.add(Calendar.MONTH,-5);
        setMonthStart(endCalendar);
        //存储表情况的list
        List<TableObject> tables = new ArrayList<>();
        if(startDate.getTime() > endCalendar.getTimeInMillis()){
            addTable(tables,map,TABLE_NAME,startDate,endDate);
        }else{
            addTable(tables,map,TABLE_NAME,endCalendar.getTime(),endDate);
            endCalendar.add(Calendar.SECOND,-1);
        }
        //接下来添加之前的表
        while( startDate.getTime() <= endCalendar.getTimeInMillis() ){
            String tableName = TABLE_NAME + endCalendar.get(Calendar.YEAR) + getPart(endCalendar.get(Calendar.MONTH));
            endDate = endCalendar.getTime();
            //根据月份来判断该季度开始时间
            endCalendar.add(Calendar.MONTH,-(endCalendar.get(Calendar.MONTH)%3));
            setMonthStart(endCalendar);
            Date tempStartDate = endCalendar.getTime();
            if(startDate.getTime() > tempStartDate.getTime()){
                addTable(tables,map,tableName,startDate,endDate);
                break;
            }else{
                addTable(tables,map,tableName,tempStartDate,endDate);
                endCalendar.add(Calendar.SECOND,-1);
            }
        }
        return tables;
    }

    private void addTable(List<TableObject> tables,Map<String,Object> map,String tableName,Date startDate,Date endDate){
        //查询表count需要的参数
        Map<String,Object> copyMap = new HashMap<>();
        copyMap.putAll(map);
        copyMap.put("tableName",tableName);
        copyMap.put("startDate",SDF.format(startDate));
        copyMap.put("endDate",SDF.format(endDate));
        int count = orderService.selectCount(copyMap);
        //添加到list里
        tables.add(new TableObject(tableName,startDate,endDate,count));
    }

    private void setMonthStart(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
    }

    private String getPart(int month){
        switch (month){
            case 0:
            case 1:
            case 2:
                return "part1";
            case 3:
            case 4:
            case 5:
                return "part2";
            case 6:
            case 7:
            case 8:
                return "part3";
            case 9:
            case 10:
            case 11:
                return "part4";
        }
        return "";
    }

    private List<Map<String,Object>> selectRecord(List<TableObject> tables,Map<String,Object> map,int page,int size){
        //倒序一下。。让老的表在前面
        Collections.reverse(tables);
        for(TableObject to : tables){
            System.out.println(to);
        }
        int from = (page-1) * size ;
        int sum = 0;
        //sum 表示目前加起来的条目数
        //over 表示 是不是第一次 sum 大于 from
        boolean over = false;
        for(int i = 0; i < tables.size(); i ++){
            TableObject to = tables.get(i);
            sum += to.getCount();
            if( sum > from ){
                if(over){
                    int thisFrom = 0;
                    if(sum - from > size -1){
                        //这一条表的记录就大于要查的了
                        size = page * size - (sum - to.getCount());
                        to.setLimit(size);
                        to.setOffset(thisFrom);
                        break;
                    }else{
                        to.setLimit(size);
                        to.setOffset(thisFrom);
                    }
                }else{
                    over = true;
                    int thisFrom = to.getCount() - ( sum - from );
                    to.setLimit(size);
                    to.setOffset(thisFrom);
                    if(sum - from > size -1){
                        //这一条表的记录就大于要查的了
                        break;
                    }
                }
            }
        }
        //上面都是设置每个表要查的limit和offset
        //这里正式开查
        map.put("tables",tables);
        return orderService.selectPage(map);
    }

    class TableObject{
        private String name;
        private String start;
        private String end;
        private int count;
        private int limit;
        private int offset;

        public TableObject(String name, Date start, Date end, int count) {
            this.name = name;
            this.start = SDF.format(start);
            this.end = SDF.format(end);
            this.count = count;
        }

        @Override
        public String toString() {
            return "TableObject{" +
                    "name='" + name + '\'' +
                    ", start=" + start +
                    ", end=" + end +
                    ", count=" + count +
                    ", limit=" + limit +
                    ", offset=" + offset +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }
    }
}
