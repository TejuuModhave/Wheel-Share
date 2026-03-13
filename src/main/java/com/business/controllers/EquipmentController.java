package com.business.controllers;

import com.business.entities.Equipment;
import com.business.repositories.EquipmentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class EquipmentController {

        @Autowired
        private EquipmentRepository equipmentRepository;

        // ── Using user-provided images from static/Images/ ──────────────────────
        private static final String[][] EQUIPMENT_DATA = {
                        { "John Deere 5310 Tractor", "Tractor", "2500", "/Images/tractor.jpeg" },
                        { "Claas Dominator Harvester", "Harvester", "5000", "/Images/harvester.jpeg" },
                        { "JCB 3CX Backhoe Loader", "Excavator", "3500", "/Images/JCB.jpeg" },
                        { "BOMAG Road Roller", "Road Roller", "2000", "/Images/Road Roller.jpeg" },
                        { "Reversible Mouldboard Plow", "Plow", "800", "/Images/Plough.jpeg" },
                        { "Disc Harrow DH-9", "Harrow", "600", "/Images/harvester.jpeg" },
        };

        @PostConstruct
        public void seedData() {
                // Always clear and re-seed so image URLs are always fresh
                equipmentRepository.deleteAll();
                for (String[] row : EQUIPMENT_DATA) {
                        equipmentRepository.save(
                                        new Equipment(row[0], row[1], Double.parseDouble(row[2]), row[3]));
                }
        }

        // GET /api/equipments
        @GetMapping("/api/equipments")
        public List<Equipment> getAllEquipment() {
                return equipmentRepository.findAll();
        }

        // GET /equipments → serve static HTML
        @GetMapping("/equipments")
        public String equipmentsPage() {
                return "forward:/equipments.html";
        }
}
