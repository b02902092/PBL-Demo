package com.example.demo;

import com.example.api.SlackApiClient;
import com.example.api.YoutubeApiClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PblController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    @Value("${slackApiToken}")
    private String slackApiToken;

    @Value("${youtubeApiKey}")
    private String youtubeApiKey;

    @GetMapping(value = "/")
    public String root() {
        return "redirect:/signIn";
    }

    @GetMapping(value = "/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        model.addAttribute("countries", CountryCodes.values());
        return "signUp";
    }

    @PostMapping(value = "/signUp")
    public String signUp(@ModelAttribute UserProfile userProfile, Model model) {
        userProfile.setPassword(DigestUtils.sha256Hex(userProfile.getPassword()));
        if (userRepository.findByName(userProfile.getName()) != null) {
            Integer id = userRepository.findByName(userProfile.getName()).getId();
            userProfile.setId(id);
        }
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("authData", new AuthData());

        AuthData authData = authRepository.findByName(userProfile.getName());
        if (authData != null) {
            authRepository.delete(authData);
        }
        String token = RandomStringUtils.randomNumeric(5);
        authData = new AuthData();
        authData.setName(userProfile.getName());
        authData.setAuthToken(token);
        authRepository.save(authData);
        sendToken(userProfile, token);
        return "auth";
    }

    @PostMapping(value = "/auth")
    public String auth(@ModelAttribute UserProfile userProfile, @ModelAttribute AuthData authData, Model model) {
        String answerToken = authRepository.findByName(authData.getName()).getAuthToken();
        if (answerToken.equals(authData.getAuthToken())) {
            userRepository.save(userProfile);
            sendHotYoutubeVideos(userProfile);
            model.addAttribute("userProfile", userProfile);
            authRepository.delete(authRepository.findByName(authData.getName()));
            return "message";
        } else {
            return "auth";
        }
    }

    @GetMapping(value = "/signIn")
    public String signInForm(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        return "signIn";
    }

    @PostMapping(value = "/signIn")
    public String signIn(@ModelAttribute UserProfile userProfile, Model model) {
        UserProfile foundUser = userRepository.findByName(userProfile.getName());
        if (foundUser != null && foundUser.getPassword().equals(DigestUtils.sha256Hex(userProfile.getPassword()))) {
            model.addAttribute("userProfile", foundUser);
            return "message";
        } else {
            return "loginFail";
        }
    }

    @PostMapping(value = "/delete")
    public String delete(@ModelAttribute UserProfile userProfile, Model model) {
        if (userRepository.findByName(userProfile.getName()) != null) {
            userRepository.deleteById(userProfile.getId());
        }
        model.addAttribute("userProfile", userProfile);
        return "delete";
    }

    private Iterable<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void sendHotYoutubeVideosToAll() {
        for (UserProfile u : this.getAllUsers()) {
            sendHotYoutubeVideos(u);
        }
    }

    private void sendHotYoutubeVideos(UserProfile userProfile) {
        String slackApiUrl = "https://slack.com/api/chat.postMessage";
        String text = YoutubeApiClient.getVideos(youtubeApiKey, userProfile.getMaxResult(), userProfile.getCountry())
                .stream().reduce(
                        "今日のHOT動画は\n", (String joined, String element) -> {
                            return joined + element + "\n";
                        });
        SlackApiClient slackApiClient = new SlackApiClient();
        slackApiClient.postMessage(text, slackApiUrl, userProfile.getName(), slackApiToken);
    }

    private void sendToken(UserProfile userProfile, String token) {
        String slackApiUrl = "https://slack.com/api/chat.postMessage";
        String text = "Your authenticate token is " + token;
        SlackApiClient slackApiClient = new SlackApiClient();
        slackApiClient.postMessage(text, slackApiUrl, userProfile.getName(), slackApiToken);
    }
}
