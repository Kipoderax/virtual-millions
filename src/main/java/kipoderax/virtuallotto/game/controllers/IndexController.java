package kipoderax.virtuallotto.game.controllers;

import kipoderax.virtuallotto.auth.repositories.HistoryGameRepository;
import kipoderax.virtuallotto.auth.repositories.UserRepository;
import kipoderax.virtuallotto.auth.service.SessionCounter;
import kipoderax.virtuallotto.game.model.GameModel;
import kipoderax.virtuallotto.game.service.StatisticsService;
import kipoderax.virtuallotto.game.service.SumUpInformation;
import kipoderax.virtuallotto.game.service.dto.HistoryGameDtoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class IndexController {

    private UserRepository userRepository;
    private StatisticsService statisticsService;
    private HistoryGameDtoService historyGameDtoService;
    private HistoryGameRepository historyGameRepository;
    private SumUpInformation sumUpInformation;

    public IndexController(UserRepository userRepository,
                           StatisticsService statisticsService,
                           HistoryGameDtoService historyGameDtoService,
                           HistoryGameRepository historyGameRepository,
                           SumUpInformation sumUpInformation) {

        this.userRepository = userRepository;
        this.statisticsService = statisticsService;
        this.historyGameDtoService = historyGameDtoService;
        this.historyGameRepository = historyGameRepository;
        this.sumUpInformation = sumUpInformation;
    }

    @GetMapping({"/"})
    public String showWinners(Model model, GameModel gameModel) {

        String date = gameModel.getDateGame().subList(0, 1).toString()
                .substring(1, 11).replace('-', '.');
        model.addAttribute("date", date);

        Collections.sort(gameModel.getLastNumbers().subList(0, 6));
        model.addAttribute("result", gameModel.getLastNumbers().subList(0, 6));
        model.addAttribute("fourth", gameModel.getRewardsMoney()[2]);
        model.addAttribute("fifth", gameModel.getRewardsMoney()[3]);
        model.addAttribute("sixth", gameModel.getRewardsMoney()[4]);

        model.addAttribute("top5level", statisticsService.get5BestPlayers());
        model.addAttribute("toplastxp", historyGameDtoService.getLast5BestExperience());

        model.addAttribute("amountRegisterPlayers", userRepository.getAllRegisterUsers());
        model.addAttribute("sessionCounter", SessionCounter.getActiveSessions());

        return "index";
    }

    @GetMapping("/informacje")
    public String information(Model model) {

        model.addAttribute("top5level", statisticsService.get5BestPlayers());
        model.addAttribute("toplastxp", historyGameDtoService.getLast5BestExperience());

        model.addAttribute("amountRegisterPlayers", userRepository.getAllRegisterUsers());
        model.addAttribute("sessionCounter", SessionCounter.getActiveSessions());

        return "game/information";
    }
}
