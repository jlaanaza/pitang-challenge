package com.pitang.controller;

import com.pitang.config.JwtUtils;
import com.pitang.dto.LoginDTO;
import com.pitang.dto.UserInfoDTO;
import com.pitang.model.UserDetailsImpl;
import com.pitang.repository.RoleRepository;
import com.pitang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);


        List<String> roles = userDetails.getAuthorities().stream()
                .map( GrantedAuthority::getAuthority )
                .collect( Collectors.toList());

        return ResponseEntity.ok().header( HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoDTO(
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        jwtCookie.getValue()));
    }

//    @PostMapping(value ="/signin")
//    public ResponseEntity<?>  signIn(@RequestBody LoginDTO loginDTO) {
//        UserDTO user = loginService.validateLogin(loginDTO);
//
//        Map<String, Object> claims = buildClams( user );
//
//        String token = jwtTokenService.generateJwtToken(loginDTO.getLogin(), claims);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//
//        return ResponseEntity.ok(response);
//    }

//    @PostMapping(value = "/me")
//    public ResponseEntity<?> getInfoUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
//        String userName = jwtTokenService.getUsernameFromToken( authorization );
//        UserDTO userDTO = loginService.findByUsername( userName );
//
//        return new ResponseEntity<>(userDTO, new HttpHeaders(), HttpStatus.OK);
//    }
//
//    private Map<String, Object> buildClams (UserDTO user){
//        return Map.of(
//                "firstName", user.getFirstName(),
//                "lastName", user.getLastName(),
//                "email", user.getEmail(),
//                "birthday", Date.from( user.getBirthday().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
//                "phone", user.getPhone(),
//                "createdAt", Date.from( user.getCreatedAt().atZone( ZoneId.systemDefault()).toInstant())  ,
//                "lastLogin", Date.from( user.getLastLogin().atZone( ZoneId.systemDefault() ).toInstant())
//        );
//    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }



}
