package com.example.demo.classes.service;

import com.example.demo.classes.excursion.Сluster;
import com.example.demo.classes.repos.GroupRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private final GroupRepo groupRepo;


    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }


    public String addNewGroup(int day, int month, UUID managerId, UUID excursionId, String clientsList) {
        if (!checkCorrectData(day, month)) {
            return "INCORRECT DATA";
        }
        Сluster сluster = new Сluster(managerId, excursionId, clientsList);
        groupRepo.save(сluster);
        return "GROUP WAS CREATED";
    }

    public boolean deleteGroupById(UUID id) {
        groupRepo.deleteById(id);
        return true;
    }

    public String getGroupById(UUID id) {
        Сluster сluster = groupRepo.getById(id).get(0);
        return groupInfo(сluster);
    }

    public String groupsInfo() {

        List<Сluster> list = (List<Сluster>) groupRepo.findAll();
        int listSize = list.size();
        if (listSize == 0) {
            return "Groups list is empty";
        }
        String result = "Groups`s INFO:  ";

        for (int i = 0; i < listSize; i++) {
            result = result + "\n" + groupInfo(list.get(i));
        }
        return result;
    }

    public String groupInfo(Сluster сluster) {
        return ("\n ***** GROUP *****" +
                "\n Менеджер: " + сluster.getManager() +
                "\n Экскурсия: " + сluster.getExcursion() +
                "\n Имена клиентов: " + сluster.getClientsName());
    }


    public boolean checkCorrectData(int day, int month) {

        if (month == 2 && (day > 29 || day < 1)) return false;

        if (day < 1 || day > 31 || month < 1 || month > 12) return false;

        return true;
    }

}
