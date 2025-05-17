package com.example.controller;

import com.example.model.Employee;
import com.example.model.LeaveRequest;
import com.example.model.User;
import com.example.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AgendaLeaveRequestController {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @GetMapping("/agenda-leave-requests")
    public String getLeaveRequests(HttpSession session, 
                                    @RequestParam(required = false) String name, 
                                    Model model) {
        User user = (User) session.getAttribute("user");
        List<LeaveRequest> allLeaveRequests = new ArrayList<>();
        List<Employee> directStaffs = new ArrayList<>();

        if (user != null && user.getEmployee() != null) {
            directStaffs = user.getEmployee().getDirectStaffs();

            if (name == null) {
                for (Employee staff : directStaffs) {
                    allLeaveRequests.addAll(leaveRequestRepository.findByEmployeeName(staff.getName()));
                }
                allLeaveRequests.addAll(leaveRequestRepository.findByEmployeeName(user.getEmployee().getName()));
            } else {
                allLeaveRequests = leaveRequestRepository.findByEmployeeName(name);
                model.addAttribute("staffName", name);
            }
        }

        model.addAttribute("leaveRequests", allLeaveRequests);
        model.addAttribute("directStaffs", directStaffs);

        return "agenda"; // Thymeleaf template name
    }
}