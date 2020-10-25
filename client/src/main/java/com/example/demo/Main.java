package com.example.demo;

import com.example.demo.methods.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        ManagerMethods manager = new ManagerMethods();
        GuideMethods guide = new GuideMethods();
        ClientMethods client = new ClientMethods();
        ExcursionMethods excursion = new ExcursionMethods();
        GroupMethods group = new GroupMethods();


        manager.createManagers();
        manager.getManagersInfo("", MappingState.all);
        manager.getManagersInfo("Igor", MappingState.byName);
        manager.deleteManagerByName("Misha");

        guide.createGuides();
        guide.getGuidesInfo("", MappingState.all);
        guide.getGuidesInfo("", MappingState.free);
        guide.getGuidesInfo("Medwed", MappingState.byName);
        guide.deleteGuideByName("Karas");
        guide.updateFreeStateByName("Medwed");

        excursion.createExcursion();
        excursion.getExcursionInfo("", MappingState.all);
        excursion.getExcursionInfo("Orange", MappingState.byName);
        excursion.deleteExcursionByName("Apple");

        client.createClients();
        client.getClientsInfo("", MappingState.all);
        client.getClientsInfo("Slava", MappingState.byName);
        client.deleteClientByName("Artem");

        group.createGroup();
        group.getGroupInfo(0, 0, MappingState.all);
        group.getGroupInfo(11, 12, MappingState.byDate);
        group.deleteGroupByDate(11, 12);

    }
}