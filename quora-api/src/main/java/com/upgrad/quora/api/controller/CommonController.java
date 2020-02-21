package com.upgrad.quora.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")

public class CommonController {

//	@Autowired
//    CommonBusinessService commonBusinessService;
	
//	@RequestMapping(path = "/userprofile/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<UserDetailsResponse> getUserProfileDetails(@PathVariable("userId") String userUuid, @RequestHeader("authorization") String authToken) throws AuthorizationFailedException, UserNotFoundException {
//        UsersEntity usersEntity = commonBusinessService.getUserDetails(userUuid, authToken);
//        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(usersEntity.getFirstName())
//                .lastName(usersEntity.getLastName()).userName(usersEntity.getUserName()).emailAddress(usersEntity.getEmail())
//                .country(usersEntity.getCountry()).aboutMe(usersEntity.getAboutMe()).dob(usersEntity.getDob());
//
//        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
//
//    }

}
