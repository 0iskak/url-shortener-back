package app.shortener.repository;

import app.shortener.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, String> {
    List<Link> getAllByOwnerId(Long ownerId);
    Link getByShortLink(String shortLink);
//    Link getByOwnerIdAndShortLink(Long ownerId, String shortLink);
    void deleteByShortLink(String shortLink);
}
