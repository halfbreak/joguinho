package pt.cenas.services;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pt.cenas.repositories.PlayerRepository;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final PlayerRepository playerRepository;

    public UserDetailsServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(playerRepository.findByName(username)).map(p -> new User(p.getName(), p.getPassword(), Collections.emptyList()));
    }
}
