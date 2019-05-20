package md.bro.gateway.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import md.bro.gateway.client.UserManagementClient;
import md.bro.gateway.dto.UserDataDTO;
import md.bro.gateway.dto.UserResponseDTO;
import md.bro.gateway.service.UserService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gateway/users")
@Api(tags = "gateway")
public class UserController {

    private final UserService userService;
    private final UserManagementClient client;

    @GetMapping
    public ResponseEntity retrieve(Pageable pageable) {
         return client.exchange("", pageable, HttpMethod.GET);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        return  client.exchange("/" + id, null, HttpMethod.GET);
    }

    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid username/password supplied"),
    })
    public String login(
                        @ApiParam("Username") @RequestParam String username,
                        @ApiParam("Password") @RequestParam String password) {

        return userService.signin(username, password);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }



    @PostMapping("/signup")
    public ResponseEntity signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
        return client.exchange("/signup", user, HttpMethod.POST);
    }
//
//    @DeleteMapping(value = "/{username}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @ApiOperation(value = "${UserController.delete}")
//    @ApiResponses(value = {
//            @ApiResponse(code = 400, message = "Something went wrong"),
//            @ApiResponse(code = 403, message = "Access denied"),
//            @ApiResponse(code = 404, message = "The user doesn't exist"),
//            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
//    public String delete(@ApiParam("Username") @PathVariable String username) {
//        return username;
//    }
//
//    @GetMapping(value = "/{username}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 400, message = "Something went wrong"),
//            @ApiResponse(code = 403, message = "Access denied"),
//            @ApiResponse(code = 404, message = "The user doesn't exist"),
//            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
//    public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
//        return userService.search(username);
//    }


}
