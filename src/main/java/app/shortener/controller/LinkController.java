package app.shortener.controller;

import app.shortener.service.LinkService;
import org.springframework.web.bind.annotation.*;

@RestController
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PutMapping("/{owner}/{link}")
    public String shortener(@PathVariable String owner,
                            @PathVariable String link) {
        return linkService.shortener(owner, link);
    }

    @GetMapping("/{owner}")
    public String getByOwner(@PathVariable String owner) {
        return linkService.linksByOwner(owner);
    }

    @DeleteMapping("/{owner}/{link}")
    public String deleteLink(@PathVariable String owner,
                             @PathVariable String link) {
        return linkService.deleteLink(owner, link);
    }
}
