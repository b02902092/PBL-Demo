package com.example.demo;

import com.example.api.SlackApiClient;
import com.example.api.YoutubeApiClient;
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

    @Value("${slackApiToken}")
    private String slackApiToken;

    @Value("${youtubeApiKey}")
    private String youtubeApiKey;

    @GetMapping(value = "/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping(value = "/index")
    public String sayHelloForm(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        model.addAttribute("countries", CountryCodes.values());
        return "index";
    }

    @PostMapping(value = "/index")
    public String sayHello(@ModelAttribute UserProfile userProfile, Model model) {
        model.addAttribute("userProfile", userProfile);
        if (userRepository.findByName(userProfile.getName()) != null) {
            Integer id = userRepository.findByName(userProfile.getName()).getId();
            userProfile.setId(id);
        }
        userRepository.save(userProfile);
        sendHotYoutubeVideos(userProfile);
        return "message";
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
}
