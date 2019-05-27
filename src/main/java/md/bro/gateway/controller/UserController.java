package md.bro.gateway.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import md.bro.gateway.client.UserManagementClient;
import md.bro.gateway.dto.UserDataDTO;
import md.bro.gateway.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<String> login(
                        @ApiParam("Username") @RequestParam String username,
                        @ApiParam("Password") @RequestParam String password
    ) {
        return new ResponseEntity<>(userService.signin(username, password), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<String> refresh(HttpServletRequest req) {
        return new ResponseEntity<>(userService.refresh(req.getRemoteUser()), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
        return client.exchange("/signup", user, HttpMethod.POST);
    }
}
