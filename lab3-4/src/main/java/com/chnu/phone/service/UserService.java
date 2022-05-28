package com.chnu.phone.service;

import com.chnu.phone.entity.User;
import com.chnu.phone.entity.Utility;
import com.chnu.phone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User saveOrUpdate(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        if (user.getId() != null) {
            User fromDB = userRepository.findById(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User with id" + user.getId() + "doesn't exist."));
            Set<Utility> utilities = fromDB.getUtilities();
            user.setUtilities(utilities);
        }
        return userRepository.save(user);
    }

    public List<User> all(){
        return userRepository.findAll();
    }

    public User byId(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id" + id + "doesn't exist."));

    }
}
