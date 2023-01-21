package com.mjt.tu.alumni.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mjt.tu.alumni.models.Group;
import com.mjt.tu.alumni.models.Photo;
import com.mjt.tu.alumni.models.User;
import com.mjt.tu.alumni.repos.GroupRepository;
import com.mjt.tu.alumni.repos.PhotoRepository;
import com.mjt.tu.alumni.repos.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilesStorageService storageService;

    public void uploadPhoto(String userId, long groupId, MultipartFile file) {

        User user = userRepository.findById(userId).orElseThrow();
        Group group = groupRepository.findById(groupId).orElseThrow();
        Photo photo = new Photo();
        photo.setCreated(LocalDateTime.now());
        photo.setUser(user);
        photo.setGroup(group);
        Optional<String> extension = Optional.ofNullable(file.getOriginalFilename())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        photo.setFileExtension(extension.get());
        userRepository.save(user);
        groupRepository.save(group);
        photo = photoRepository.save(photo);

        storageService.save(file, photo.getFileName(), extension.get());

    }

    public Photo getPhoto(String id) {
        return photoRepository.findById(id).get();
    }

    public String deletePhoto(String id) {
        if (photoRepository.existsById(id)) {
            return "No such photo.";
        }

        photoRepository.delete(photoRepository.findById(id).get());
        return "Deleted";
    }
}
