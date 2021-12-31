package app.shortener.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Link {
    @Id
    @NonNull
    private String shortLink;
    @NonNull
    private String originalLink;
    @NonNull
    private long ownerId;
    private long viewsCount = 0L;
}
