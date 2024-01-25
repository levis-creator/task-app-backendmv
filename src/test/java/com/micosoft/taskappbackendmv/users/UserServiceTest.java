package com.micosoft.taskappbackendmv.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;
    private User user;
    @BeforeEach
    void setUp(){
        user= User.builder().userId("1").email("levis.nyingi@gmail.com")
                .password("password")
                .username("levis.nyingi")
                .build();
    }


    @Test
    void checkIfFetchingUsersFromTheDb() {
        userService.getUsers();
        verify(userRepository).findAll();
    }
    @Test
    void checkingCreatUserWhenAllItemsAreNotFoundInDb(){
        given(userRepository.findByEmailIgnoreCase(user.getEmail())).willReturn(Optional.empty());
        userService.createUsers(user);
        verify(userRepository).save(user);
    }
    @Test
    void checkingWhenEmailIsFoundWhenCreatingDb(){
        given(userRepository.findByEmailIgnoreCase(user.getEmail())).willReturn(Optional.of(user));
        assertThrows(IllegalStateException.class,()->userService.createUsers(user));
        verify(userRepository, never()).save(any());
    }
    @Test
    void checkingWhenUsernameIsFoundInDb(){
        given(userRepository.findByUsernameIgnoreCase(user.getUsername())).willReturn((Optional.of(user)));
        assertThrows(IllegalStateException.class, ()->userService.createUsers(user));
        verify(userRepository, never()).save(any());
    }
    @Test
    void checkingWhenUserIdIsFound(){
        given(userRepository.existsById(user.getUserId())).willReturn(true);
        assertThrows(IllegalStateException.class,()->userService.createUsers(user));
        verify(userRepository, never()).save(any());
    }
    @Test
    void  gettingUserWhenFound(){
        given(userRepository.existsById(user.getUserId())).willReturn(true);
        assertThat(userService.getSingleUser(user.getUserId())).isNotNull();
    }
    @Test
    void  gettingUsersWhenUserIdNotFound(){
        given(userRepository.existsById(user.getUserId())).willReturn(false);
        assertThrows(IllegalStateException.class,()->userService.getSingleUser(user.getUserId()));
    }
    @Test
    void whenDeletingAnExistingUser(){
        given(userRepository.existsById(user.getUserId())).willReturn(true);
        assertThat(userService.deleteUser(user.getUserId())).isNotBlank();
        verify(userRepository).deleteById(user.getUserId());
    }
    @Test
    void  whenDeletingNonExistingUser(){
        given(userRepository.existsById(user.getUserId())).willReturn(false);
        assertThrows(IllegalStateException.class, ()->userService.deleteUser(user.getUserId()));
        verify(userRepository, never()).deleteById(user.getUserId());
    }
    @Test
    void userUpdateWhenUserDoesNotExist(){
        given(userRepository.findById(user.getUserId())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class,()->userService.updateUser(user.getUserId(),user));
        verify(userRepository, never()).save(user);
    }
    @Test
    void userUpdatingUserName(){
      String userId="1";
        User updatedUser = User.builder().userId("1").email("levis.nyingi1@gmail.com")
                .password("newpassword")
                .username("levis.nyingi1")
                .build();
      when(userRepository.findById(userId)).thenReturn(Optional.of(user));
      when(userRepository.findByUsernameIgnoreCase(updatedUser.getUsername())).thenReturn(Optional.empty());
      when(userRepository.findByUsernameIgnoreCase(updatedUser.getUsername())).thenReturn(Optional.empty());

      when(userRepository.save(user)).thenReturn(user);
      User result= userService.updateUser(userId,updatedUser);
      assertThat(updatedUser.getPassword()).isEqualTo(result.getPassword());
        assertThat(updatedUser.getUsername()).isEqualTo(result.getUsername());
      verify(userRepository).save(user);

    }

}