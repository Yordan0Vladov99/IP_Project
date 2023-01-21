package com.mjt.tu.alumni.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mjt.tu.alumni.dtos.CartItemResponse;
import com.mjt.tu.alumni.dtos.GroupLinkDto;
import com.mjt.tu.alumni.dtos.ItemRequestResponse;
import com.mjt.tu.alumni.dtos.PhotoSessionResponse;
import com.mjt.tu.alumni.dtos.UserDto;
import com.mjt.tu.alumni.models.User;
import com.mjt.tu.alumni.models.UserType;
import com.mjt.tu.alumni.repos.UserRepository;
import com.mjt.tu.alumni.security.auth.AuthenticationRequest;
import com.mjt.tu.alumni.security.auth.AuthenticationResponse;
import com.mjt.tu.alumni.security.auth.RegisterRequest;
import com.mjt.tu.alumni.security.auth.RegistrationResponse;
import com.mjt.tu.alumni.security.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
        @Autowired
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getId(), request.getPassword()));
                var user = userRepository.findById(request.getId()).orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .userType(user.getType())
                                .build();

        }

        public RegistrationResponse register(RegisterRequest request) {
                var user = User.builder()
                                .id(request.getId())
                                .name(request.getName())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .type(UserType.USER).build();
                userRepository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return RegistrationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        public AuthenticationResponse update(RegisterRequest request) {
                var user = userRepository.findById(request.getId()).orElseThrow();
                user.setName(request.getName());
                userRepository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();

        }

        public boolean userExists(String id) {
                return userRepository.existsById(id);
        }

        public UserDto getUser(String id) {
                User user = userRepository.findById(id).get();
                Set<String> photos = Set.copyOf(user.getPhotos().stream().map(photo -> photo.getFileName()).toList());
                Set<Long> groups = Set.copyOf(user.getGroups().stream().map(group -> group.getId()).toList());

                return new UserDto(user.getName(), photos, groups);
        }

        public Set<GroupLinkDto> getUserGroups(String id) {
                User user = userRepository.findById(id).orElseThrow();
                return Set.copyOf(
                                user.getGroups().stream().map(group -> new GroupLinkDto(group.getId(), group.getName()))
                                                .toList());
        }

        public Set<CartItemResponse> getUserCartItems(String id) {
                User user = userRepository.findById(id).orElseThrow();
                return Set.copyOf(user.getCartItems().stream()
                                .map(item -> new CartItemResponse(item.getId(),
                                                item.getImage().getFileName() + "."
                                                                + item.getImage().getFileExtension(),
                                                item.getType(),
                                                item.getPrice()))
                                .toList());
        }

        public Set<ItemRequestResponse> getSentItemRequests(String id) {
                User user = userRepository.findById(id).orElseThrow();
                return Set.copyOf(user.getSentItemRequests().stream()
                                .map(request -> new ItemRequestResponse(request.getId(), request.getStatus(),
                                                request.getPhoto().getFileName() + "." +
                                                                request.getPhoto().getFileExtension(),
                                                request.getDescription(), request.getCreated()))
                                .toList());
        }

        public Set<ItemRequestResponse> getReceivedItemRequests(String id) {
                User user = userRepository.findById(id).orElseThrow();
                return Set.copyOf(user.getReceivedItemRequests().stream()
                                .map(request -> new ItemRequestResponse(request.getId(), request.getStatus(),
                                                request.getPhoto().getFileName() + "." +
                                                                request.getPhoto().getFileExtension(),
                                                request.getDescription(), request.getCreated()))
                                .toList());
        }

        public Set<PhotoSessionResponse> getSentSessions(String id) {
                User user = userRepository.findById(id).orElseThrow();
                return Set.copyOf(user.getReceivedSessionRequests().stream()
                                .map(session -> new PhotoSessionResponse(session.getId(), session.getPeople(),
                                                session.getSessionDate(), session.getLocation(),
                                                session.getDescription(), session.getStatus()))
                                .toList());
        }

        public Set<PhotoSessionResponse> getReceivedSessions(String id) {
                User user = userRepository.findById(id).orElseThrow();
                return Set.copyOf(user.getReceivedSessionRequests().stream()
                                .map(session -> new PhotoSessionResponse(session.getId(), session.getPeople(),
                                                session.getSessionDate(), session.getLocation(),
                                                session.getDescription(), session.getStatus()))
                                .toList());
        }

        public String deleteUser(String id) {

                userRepository.delete(userRepository.findById(id).orElseThrow());
                return "Deleted";
        }

}
