package zust.logistics.utils;

import org.springframework.beans.BeanUtils;
import zust.logistics.entity.dto.RepairDTO;
import zust.logistics.entity.pojo.Repair_;

import java.util.ArrayList;
import java.util.List;

public class BeanUtil {
    public static <S> Object e2d(S s) {
        if (s == null) return null;
        try {
            Class t = Class.forName(s.getClass().getName().replace("pojo", "dto").replace("_", "DTO"));
            Object dto = t.newInstance();
            BeanUtils.copyProperties(s, dto);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <S> List<Object> e2d(List<S> sList) {
        if (sList == null || sList.size() == 0) return null;
        List<Object> dtos = new ArrayList<>();
        for (S s : sList)
            dtos.add(e2d(s));
        return dtos;
    }

    public static <S> Object d2e(S s) {
        if (s == null) return null;
        try {
            Class t = Class.forName(s.getClass().getName().replace("dto", "pojo").replace("DTO", "_"));
            Object e = t.newInstance();
            BeanUtils.copyProperties(s, e);
            return e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
