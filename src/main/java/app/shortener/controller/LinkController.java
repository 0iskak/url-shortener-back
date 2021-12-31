package app.shortener.controller;

import app.shortener.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{link}")
    public Object opener(@PathVariable String link) {
        return linkService.opener(link);
    }

    @PutMapping("/api/{owner}/{*link}")
    public String shortener(HttpServletRequest request,
                            @PathVariable String owner,
                            @PathVariable String link) {
        return linkService.shortener(owner, link.substring(1), request.getQueryString());
    }

    @GetMapping("/api/{owner}")
    public String getByOwner(@PathVariable String owner) {
        return linkService.linksByOwner(owner);
    }

    @DeleteMapping("/api/{owner}/{link}")
    public String deleteLink(@PathVariable String owner,
                             @PathVariable String link) {
        return linkService.deleteLink(owner, link);
    }
}
