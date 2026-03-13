package com.business.controllers;

import com.business.entities.Equipment;
import com.business.entities.RentalRequest;
import com.business.entities.User;
import com.business.repositories.EquipmentRepository;
import com.business.repositories.RentalRequestRepository;
import com.business.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class WheelShareController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    // ── USER: Register ────────────────────────────────────────────────────────
    @PostMapping("/users/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        // Check email already exists
        List<User> existing = new ArrayList<>();
        userRepository.findAll().forEach(existing::add);
        for (User u : existing) {
            if (email.equalsIgnoreCase(u.getUemail())) {
                return ResponseEntity.badRequest().body("Email already registered");
            }
        }
        User u = new User();
        u.setUname(body.get("name"));
        u.setUemail(email);
        u.setUpassword(body.get("password"));
        try {
            u.setUnumber(Long.parseLong(body.getOrDefault("phone", "0")));
        } catch (Exception e) {
            u.setUnumber(0L);
        }
        User saved = userRepository.save(u);
        return ResponseEntity.ok(Map.of(
                "id", saved.getU_id(),
                "name", saved.getUname(),
                "email", saved.getUemail()));
    }

    // ── USER: Login ──────────────────────────────────────────────────────────
    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        for (User u : users) {
            if (email.equalsIgnoreCase(u.getUemail()) && password.equals(u.getUpassword())) {
                return ResponseEntity.ok(Map.of(
                        "id", u.getU_id(),
                        "name", u.getUname(),
                        "email", u.getUemail()));
            }
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    // ── EQUIPMENT: Add new ───────────────────────────────────────────────────
    @PostMapping("/equipments")
    public ResponseEntity<?> addEquipment(@RequestBody Map<String, Object> body) {
        Equipment e = new Equipment();
        e.setName(body.get("name").toString());
        e.setType(body.get("type").toString());
        e.setPricePerDay(Double.parseDouble(body.get("pricePerDay").toString()));
        String imgUrl = body.getOrDefault("imageUrl", "").toString();
        e.setImageUrl(imgUrl.isEmpty() ? "/Images/tractor.jpeg" : imgUrl);
        Equipment saved = equipmentRepository.save(e);
        return ResponseEntity.ok(saved);
    }

    // ── RENTAL: Create request ───────────────────────────────────────────────
    @PostMapping("/rentals")
    public ResponseEntity<?> createRental(@RequestBody Map<String, Object> body) {
        try {
            int userId = Integer.parseInt(body.get("userId").toString());
            Long equipmentId = Long.parseLong(body.get("equipmentId").toString());

            Optional<Equipment> eqOpt = equipmentRepository.findById(equipmentId);
            if (eqOpt.isEmpty())
                return ResponseEntity.badRequest().body("Equipment not found");

            Optional<User> uOpt = userRepository.findById(userId);
            if (uOpt.isEmpty())
                return ResponseEntity.badRequest().body("User not found");

            Equipment eq = eqOpt.get();
            User user = uOpt.get();

            RentalRequest rr = new RentalRequest();
            rr.setEquipmentId(equipmentId);
            rr.setEquipmentName(eq.getName());
            rr.setUserId(userId);
            rr.setUserName(user.getUname());
            rr.setUserEmail(user.getUemail());
            rr.setStatus("PENDING");

            RentalRequest saved = rentalRequestRepository.save(rr);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }

    // ── RENTAL: Get all ──────────────────────────────────────────────────────
    @GetMapping("/rentals")
    public List<RentalRequest> getAllRentals() {
        return rentalRequestRepository.findAll();
    }

    // ── RENTAL: Approve / Reject ─────────────────────────────────────────────
    @PostMapping("/rentals/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<RentalRequest> opt = rentalRequestRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        RentalRequest rr = opt.get();
        rr.setStatus(body.get("status").toUpperCase());
        rentalRequestRepository.save(rr);
        return ResponseEntity.ok(rr);
    }

    // ── RENTAL: Get by User ──────────────────────────────────────────────────
    @GetMapping("/rentals/user/{userId}")
    public List<RentalRequest> getRentalsByUser(@PathVariable int userId) {
        return rentalRequestRepository.findByUserId(userId);
    }
}
