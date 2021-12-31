package app.shortener.util;

import app.shortener.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class LinkUtil {
    private final LinkRepository repository;
    private final List<Character> chars = new ArrayList<>();
    private final Random random = new Random();

    public LinkUtil(LinkRepository repository) {
        this.repository = repository;

        for (var c = 'a'; c <= 'z'; c++) {
            chars.add(c);
            chars.add(Character.toUpperCase(c));
        }
        for (var c = '0'; c <= '9'; c++)
            chars.add(c);
    }

    public String getShort() {
        var s = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            var r = random.nextInt(chars.size());
            s.append(chars.get(r));
        }

        if (repository.findById(s.toString()).isPresent())
            return getShort();

        return s.toString();
    }

    public String toLink(String link) {
        try {
            new URL(link);
            return link;
        } catch (MalformedURLException ignored) {
            return "https://" + link;
        }
    }
}
