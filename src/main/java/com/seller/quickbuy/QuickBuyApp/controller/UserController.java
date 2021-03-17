package com.seller.quickbuy.QuickBuyApp.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.Role;
import com.seller.quickbuy.QuickBuyApp.entity.RoleName;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.entity.UserCart;
import com.seller.quickbuy.QuickBuyApp.message.request.LoginForm;
import com.seller.quickbuy.QuickBuyApp.message.request.SignUpForm;
import com.seller.quickbuy.QuickBuyApp.message.response.JwtResponse;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.repository.CartRepository;
import com.seller.quickbuy.QuickBuyApp.repository.RoleRepository;
import com.seller.quickbuy.QuickBuyApp.repository.UserRepository;
import com.seller.quickbuy.QuickBuyApp.security.jwt.JwtProvider;
import com.seller.quickbuy.QuickBuyApp.service.UserService;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyConstants;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository  cartRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
		logger.info("In Login authenticateUser()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START );
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + userDetails.getUsername()));;	
//			
//		Role adminRole = roleRepository.findByName(role);
//		System.out.println(user.getRoles());
        String sellerId = user.getAttrribute1();
//		System.out.println(user.getUsername()+ ": "+ user.getEmail());
		logger.info("Signin authenticateUser()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END + userDetails.getUsername());
		return ResponseEntity.ok(new JwtResponse(jwt,  userDetails.getUsername(),  user.getEmail(), sellerId, userDetails.getAuthorities()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		logger.info( "In registerUser()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START );
		if (userRepository.existsByUsername(signUpRequest.getUserName())) {
			logger.info( "In existsByUsername()" + QuickBuyConstants.LOG_SEPRATOR_WITH_ALREDY_EXIST  + signUpRequest.getUserName());
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			logger.info("In existsByUsername()"  +  QuickBuyConstants.LOG_SEPRATOR_WITH_ALREDY_EXIST + signUpRequest.getEmail());
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setUsername(signUpRequest.getUserName());
		user.setEmail(signUpRequest.getEmail());
		user.setFirstName(signUpRequest.getFirstName());
		user.setMiddleName(signUpRequest.getMiddleName());
		user.setLastName(signUpRequest.getLastName());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setCity(signUpRequest.getCity());
		user.setState(signUpRequest.getState());
		user.setAddress(signUpRequest.getAddress());
		user.setPhone(signUpRequest.getPhone());
		user.setZipCode(signUpRequest.getZipCode());
		user.setCountry(signUpRequest.getCountry());
		
//		User user = new User(signUpRequest.getUserName(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getCity(),
//				signUpRequest.getState(),signUpRequest.getAddress() , signUpRequest.getPhone(), signUpRequest.getZipCode(),
//				signUpRequest.getCountry());
	
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "seller":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_SELLER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
//		try {
//		    User savedUser = userRepository.save(user);
//		    System.out.println(savedUser);
//		    // initial Cart
//            UserCart savedCart = cartRepository.save(new UserCart(savedUser));
//            savedUser.setUserCart(savedCart);
//            userRepository.save(savedUser);
////            return ResponseEntity.ok( userRepository.save(savedUser));
//		} catch (Exception e) {
//			 throw new MyException(ResultEnum.VALID_ERROR);
//		}
		User savedUser = null;
		try {
			user = userRepository.save(user);
//		UserCart savedCart = new UserCart();
//		savedCart.setUser(savedUser);
			UserCart savedCart = cartRepository.save(new UserCart(user));
			logger.info("User Cart successfully " + QuickBuyConstants.LOG_SEPRATOR_WITH_SAVED + savedCart);
			savedCart.setCreatedBy(user.getEmail());
			user.setUserCart(savedCart);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("User Cart Exception" + e);
			e.printStackTrace();
		}
		logger.info("User successfully " + QuickBuyConstants.LOG_SEPRATOR_WITH_SAVED + user);
			return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
	  @GetMapping("/profile/{username}")
	    public ResponseEntity<Optional<User>> getProfile(@PathVariable("username") String username, Principal principal) {
			logger.info("In getProfile() " +QuickBuyConstants.LOG_SEPRATOR_WITH_START );
	        if (principal.getName().equals(username)) {
	        	logger.info("In getProfile() " +QuickBuyConstants.LOG_SEPRATOR_WITH_END );
	            return ResponseEntity.ok(userRepository.findByUsername(username));
	            
	        } else {
	        	logger.info("In getProfile() " +QuickBuyConstants.LOG_SEPRATOR_WITH_END + "with exception");
	            return ResponseEntity.badRequest().build();
	        }

	    }
}