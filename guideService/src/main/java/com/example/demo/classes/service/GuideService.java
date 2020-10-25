package com.example.demo.classes.service;

import com.example.demo.classes.people.Guide;
import com.example.demo.classes.repos.GuideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class GuideService {

    private final GuideRepo repos;

    @Autowired
    public GuideService(GuideRepo repos) {
        this.repos = repos;
    }


    public Guide addNewGuide(String name, boolean knowEnglish) {
        Guide guide = new Guide(name, knowEnglish);
        repos.save(guide);
        return guide;
    }

    public boolean deleteGuideByName(String name) {
        List<Guide> list = repos.getGuideByName(name);
        if (list.size() == 0) return false;
        repos.delete(list.get(0));
        return true;
    }


    public String getGuideIdByName(String name){
        System.out.println(name);
        return repos.getGuideByName(name).get(0).getId().toString();
    }

    public String getGuideByName(String name) {
        List<Guide> list = repos.getGuideByName(name);
        return list.get(0).toString();
    }

     public Boolean toggleGuideFreeState(UUID  id) {
        Guide guide = repos.getGuideById(id).get(0);
        guide.toggleFreeState();
        repos.save(guide);
        return true;
    }

    public String getFreeGuides() {
        List<Guide> list = (List<Guide>) repos.findAll();
        int listSize = list.size();
        if (listSize == 0) {
            return "Guides list is empty";
        }
        String result = "FREE Guide`s INFO:  ";

        for (int i = 0; i < listSize; i++) {
            Guide guide = list.get(i);
            if(guide.getFreeState()){
                result = result + "\n" + guide;
            }

        }
        return result;
    }





    public String guideInfo() {
        List<Guide> list = (List<Guide>) repos.findAll();
        int listSize = list.size();
        if (listSize == 0) {
            return "Guides list is empty";
        }
        String result = "Guide`s INFO:  ";

        for (int i = 0; i < listSize; i++) {
            result = result + "\n" + list.get(i);
        }
        return result;
    }
}
