package com.example.demo.classes.service;

import com.example.demo.classes.repos.ExcursionRepo;
import com.example.demo.classes.excursion.Excursion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExcursionService {

    private final ExcursionRepo excursionRepo;
    public ExcursionService(ExcursionRepo repos) {
        this.excursionRepo = repos;
    }


    public String addNewExcursion(int day, int month, int duration, int visitors, UUID guideId, String guideName) {
        if (!checkCorrectData(day, month, duration)) {
            return "INCORRECT DATA";
        }

        Excursion excursion = new Excursion(day, month, duration, visitors, guideId, guideName);
        excursionRepo.save(excursion);
        return "EXCURSION WAS CREATED";
    }

    public boolean deleteExcursionById(UUID excursionId) {
        excursionRepo.deleteById(excursionId);
        return true;
    }

    public String getExcursionById(UUID excursionId) {
        Excursion excursion;

        try {
            excursion = excursionRepo.getExcursionById(excursionId).get(0);
        } catch (Exception e) {
            return "EXCURSION NOT FOUND";
        }

        return excursion.toString();
    }

    public String excursionsInfo() {

        List<Excursion> list = (List<Excursion>) excursionRepo.findAll();
        int listSize = list.size();
        if (listSize == 0) {
            return "Excursion list is empty";
        }
        String result = "Excursion`s INFO:  ";

        for (int i = 0; i < listSize; i++) {
            result = result + "\n" + list.get(i);
        }
        return result;
    }

    public boolean checkCorrectData(int day, int month, int duration) {

        if (month == 2 && (day > 29 || day < 1))return false;

        if (day < 1 || day > 31 ||  month < 1 ||
            month > 12 ||  duration > 180 || duration < 0)  return false;

        return true;
    }
}

