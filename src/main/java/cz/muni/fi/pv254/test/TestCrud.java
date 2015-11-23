package cz.muni.fi.pv254.test;

import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.repository.UserRepository;

/**
 * Created by skylar on 24.11.15.
 */
public class TestCrud {

    public static void run(UserRepository userRepository){
        System.out.println("find 1000");
        System.out.println(userRepository.find(1000).size());

        try {
            User user;
            System.out.println("creating user");
            user = userRepository.create(new User());
            System.out.println(userRepository.findOne(user.getId()));

            user.setName("name");
            System.out.println("updating user");
            System.out.println(userRepository.update(user));
            System.out.println("findinf updated user");
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
}
