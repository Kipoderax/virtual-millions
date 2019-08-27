package kipoderax.virtuallotto.game.service;

import kipoderax.virtuallotto.auth.repositories.UserRepository;
import kipoderax.virtuallotto.commons.dtos.mapper.UserMapper;
import kipoderax.virtuallotto.commons.dtos.models.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

        userRepository.findAllOrderByLevel()
                .stream()
                .map(u -> userDtos.add(userMapper.map(u)))
                .collect(Collectors.toList());

        return userDtos;
    }

    public List<UserDto> get5BestPlayers() {
        List<UserDto> userDtoList = new ArrayList<>();

        getAllDtoUsers(userDtoList);

        if (userDtoList.isEmpty()) {
            return Collections.emptyList();
        }
        if (userDtoList.size() < 5) {
            return userDtoList;
        } else {
            return userDtoList.subList(0, 5);
        }
    }

    public List<UserDto> getAllDtoUsersDefault() {
        List<UserDto> userDtos = new ArrayList<>();

        getAllDtoUsers(userDtos);

        userDtos.sort(Comparator.comparing(UserDto::getExperience).
                thenComparing(UserDto::getNumberGame).
                thenComparing(UserDto::getUsername).reversed());

        if (userDtos.isEmpty()) {
            return null;
        }
        return userDtos.subList(0, get100RecordsOfUsers());
    }

    public List<UserDto> getAllDtoUsersBy(String by) {
        List<UserDto> userDtos = new ArrayList<>();
        getAllDtoUsers(userDtos);

        switch (by) {
            case "ym poziomem":

                userDtos.sort(Comparator.comparing(UserDto::getExperience).
                        thenComparing(UserDto::getNumberGame).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "a iloscia gier":
                userDtos.sort(Comparator.comparing(UserDto::getNumberGame).
                        thenComparing(UserDto::getExperience).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "a iloscia trojek":
                userDtos.sort(Comparator.comparing(UserDto::getAmountOfThree).
                        thenComparing(UserDto::getExperience).
                        thenComparing(UserDto::getNumberGame).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "a iloscia czworek":
                userDtos.sort(Comparator.comparing(UserDto::getAmountOfFour).
                        thenComparing(UserDto::getExperience).
                        thenComparing(UserDto::getExperience).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "a iloscia piatek":
                userDtos.sort(Comparator.comparing(UserDto::getAmountOfFive).
                        thenComparing(UserDto::getExperience).
                        thenComparing(UserDto::getNumberGame).
                        thenComparing(UserDto::getUsername).reversed());
                break;

            case "a iloscia szostek":
                userDtos.sort(Comparator.comparing(UserDto::getAmountOfSix).
                        thenComparing(UserDto::getExperience).
                        thenComparing(UserDto::getNumberGame).
                        thenComparing(UserDto::getUsername).reversed());
                break;
        }

        return userDtos.subList(0, get100RecordsOfUsers());
    }

    public int get100RecordsOfUsers() {

        return userRepository.getAllRegisterUsers() > 100 ? 100 : userRepository.getAllRegisterUsers();
    }
}
