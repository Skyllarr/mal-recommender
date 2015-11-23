package cz.muni.fi.pv254.utils;

import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.enums.Gender;
//import cz.muni.fi.pv254.repository.AnimeEntryRepository;
import cz.muni.fi.pv254.repository.AnimeRepository;
import cz.muni.fi.pv254.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suomiy on 11/22/15.
 */
@ApplicationScoped
public class StatisticsUtils {

    @Inject
    UserRepository userRepository;

    @Inject
    AnimeRepository animeRepository;

    //@Inject
    //AnimeEntryRepository animeEntryRepository;

    public double getAverageAge(List<User> users) {
        int total = 0;
        int count = 0;

        for(User u : users){
            if(u.getBirthday() != null){
                count++;
                total += u.getBirthday().until(IsoChronology.INSTANCE.dateNow()).getYears();
            }
        }
        return (count > 0 ? ((double) total)/count : 0);
    }

    public void showAverageAgeOfUsers(){
        try {
            List<User> users = userRepository.findAll();
            System.out.println("Average age: " + getAverageAge(users));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAverageAgePerGender() {
        try {
            List<User> users = userRepository.findAll();
            List<User> females = new ArrayList<User>();
            List<User> males = new ArrayList<User>();
            for(User u : users){
                if(u.getGender() != null){
                    if (u.getGender() == Gender.FEMALE)
                        females.add(u);
                    else if (u.getGender() == Gender.MALE)
                         males.add(u);
                }
            }
            System.out.println("Average age of men: " + getAverageAge(males));
            System.out.println("Average age of women: " + getAverageAge(females));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getCategories(List<User> users) {
        int[] ageCategories = new int[20];
        for(User u : users){
            if(u.getBirthday() != null){
                int age = u.getBirthday().until(IsoChronology.INSTANCE.dateNow()).getYears();
                int mod = age % 5;
                int category = ((mod == 0) ?  age : age + (5 - mod))  / 5  - 1;
                ageCategories[category]++;
            }
        }
        return ageCategories;
    }

    public void showAgeCategories() {
        try {
            List<User> users = userRepository.findAll();
            List<User> females = new ArrayList<User>();
            List<User> males = new ArrayList<User>();
            System.out.println("Age categories of users: " + getCategories(users));
            System.out.println("Age categories of men: " + getCategories(females));
            System.out.println("Average age of women: " + getCategories(males));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
