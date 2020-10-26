// @RestController
// public class SessionController {
    
//     private final AdminService adminService;

//     public SessionController(AdminService adminService) {
//         this.adminService = adminService;
//     }

//     @GetMapping("/login")
//     public ResponseEntity<TokenResponse> login(
//         @RequestBody LoginRequest loginRequest
//     ) {
//         String token = adminService.createToken(loginRequest);
//         return ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
//     }

// }
