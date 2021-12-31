package app.shortener.service;

import app.shortener.model.Link;
import app.shortener.repository.LinkRepository;
import app.shortener.util.LinkUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LinkService {
    private final LinkRepository repository;
    private final LinkUtil util;

    private final String ownerNumeric = "Owner parameter should be numeric";
    private final String error = "Some error occurred";
    private final String notFound = "Link not found";
    private final String notOwner = "You are not owner of the link";
    private final String status = "status";
    private final String result = "result";
    private final int ok = 200;
    private final int created = 201;
    private final int illegalArgument = 400;
    private final int forbidden = 403;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

    public LinkService(LinkRepository repository, LinkUtil util) {
        this.repository = repository;
        this.util = util;
    }

    @SneakyThrows
    public String shortener(String owner, String originalLink) {
        Map<String, Object> map;

        try {
            var id = Long.parseLong(owner);
            var shortLink = util.getShort();
            originalLink = util.toLink(originalLink);
            repository.save(new Link(shortLink, originalLink, id));

            map = Map.of(status, created, result, shortLink);
        } catch (NumberFormatException ignored) {
            map = Map.of(status, illegalArgument, result, ownerNumeric);
        }

        return writer.writeValueAsString(map);
    }

    @SneakyThrows
    public String linksByOwner(String owner) {
        Map<String, Object> map;

        try {
            var id = Long.parseLong(owner);
            var list = repository.getAllByOwnerId(id);
            map = Map.of(status, ok, result, list);
        } catch (NumberFormatException ignored) {
            map = Map.of(status, illegalArgument, result, ownerNumeric);
        }

        return writer.writeValueAsString(map);
    }

    @SneakyThrows
    public String deleteLink(String owner, String shortLink) {
        Map<String, Object> map;

        label: try {
            var id = Long.parseLong(owner);
            var link = repository.getByShortLink(shortLink);

            if (link == null) {
                map = Map.of(status, illegalArgument, result, notFound);
                break label;
            }

            if (link.getOwnerId() != id) {
                map = Map.of(status, forbidden, result, notOwner);
                break label;
            }

            repository.delete(link);
            map = Map.of(status, ok);
        } catch (NumberFormatException ignored) {
            map = Map.of(status, illegalArgument, result, ownerNumeric);
        }

        return writer.writeValueAsString(map);
    }
}
