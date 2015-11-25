package cz.muni.fi.pv254.test;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.repository.AnimeRepository;
import cz.muni.fi.pv254.repository.UserRepository;

import java.util.List;

/**
 * Created by skylar on 24.11.15.
 */
public class TestCrud {

    public static void run(UserRepository userRepository){
        System.out.println("find configuredSize");
        List<User> users = userRepository.findAll();
        System.out.println(users.size());

        if(users.size() > 0){
            System.out.println("Showing first: " + users.get(0));
        }

        try {
            User user;

            System.out.println("creating user");
            user = userRepository.create( new User());
            System.out.println(userRepository.findOne(user.getId()));
            user.setName("Test name");
            System.out.println("updating user");
            System.out.println(userRepository.update(user));
            System.out.println("finding updated user");
            System.out.println(userRepository.findOne(user.getId()));

            try {
                System.out.println("deleting user");
                user = userRepository.delete(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run(AnimeRepository animeRepository){
        System.out.println("find configuredSize");
        List<Anime> animes = animeRepository.findAll();
        System.out.println(animes.size());

        if(animes.size() > 0){
            System.out.println("Showing first: " + animes.get(0));
        }

        try {
            Anime anime;

            System.out.println("creating anime");
            anime = animeRepository.create( new Anime());
            System.out.println(animeRepository.findOne(anime.getId()));

            anime.setTitle("Test title");
            System.out.println("updating anime");
            System.out.println(animeRepository.update(anime));
            System.out.println("finding updated anime");
            System.out.println(animeRepository.findOne(anime.getId()));

            try {
                System.out.println("deleting anime");
                anime = animeRepository.delete(anime.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(anime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
