package kipoderax.virtuallotto.game.service;

import kipoderax.virtuallotto.auth.repositories.UserRepository;
import kipoderax.virtuallotto.dtos.mapper.UserMapper;
import kipoderax.virtuallotto.dtos.models.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public StatisticsService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllDtoUsers(List<UserDto> userDtos) {

        userRepository.findAll()
                .stream()
                .map(u -> userDtos.add(userMapper.map(u)))
                .collect(Collectors.toList());

        return userDtos;
    }

    public List<UserDto> getAllDtoUsersDefault() {
        List<UserDto> userDtos = new ArrayList<>();
        getAllDtoUsers(userDtos);

        userDtos.sort(Comparator.comparing(UserDto::getLevel).thenComparing(UserDto::getUsername).reversed());

        return userDtos;
    }

    //todo cdn
    public void comparing(List<UserDto> userDtos, int temp) {

        userDtos.sort(Comparator.comparing(UserDto::getNumberGame).
                thenComparing(UserDto::getLevel).
                thenComparing(UserDto::getUsername).reversed());
    }

    public List<UserDto> getAllDtoUsersBy(String by) {
        List<UserDto> userDtos = new ArrayList<>();
        getAllDtoUsers(userDtos);

        switch (by) {
            case "level":
                userDtos.sort(Comparator.comparing(UserDto::getLevel).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "numberGame":
                userDtos.sort(Comparator.comparing(UserDto::getNumberGame).
                        thenComparing(UserDto::getLevel).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "three":
                userDtos.sort(Comparator.comparing(UserDto::getAmountOfThree).
                        thenComparing(UserDto::getLevel).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "four":
                userDtos.sort(Comparator.comparing(UserDto::getAmountOfFour).
                        thenComparing(UserDto::getLevel).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "five":
                userDtos.sort(Comparator.comparing(UserDto::getAmountOfFive).
                        thenComparing(UserDto::getLevel).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "six":
                userDtos.sort(Comparator.comparing(UserDto::getAmountOfSix).
                        thenComparing(UserDto::getLevel).
                        thenComparing(UserDto::getUsername).reversed());
                break;
        }

        return userDtos;
    }
}
