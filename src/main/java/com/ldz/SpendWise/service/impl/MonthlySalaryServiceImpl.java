package com.ldz.SpendWise.service.impl;

import com.ldz.SpendWise.dto.MonthlySalaryDTO;
import com.ldz.SpendWise.exception.MonthlySalaryNotFound;
import com.ldz.SpendWise.mapper.MonthlySalaryMapper;
import com.ldz.SpendWise.mapper.UserMapper;
import com.ldz.SpendWise.model.MonthlySalary;
import com.ldz.SpendWise.repository.MonthlySalaryRepository;
import com.ldz.SpendWise.service.MonthlySalaryService;
import com.ldz.SpendWise.service.UserService;
import com.ldz.SpendWise.service.data.MonthlySalaryData;
import com.ldz.SpendWise.util.AuthenticatedUserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonthlySalaryServiceImpl implements MonthlySalaryService {
    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryMapper monthlySalaryMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthenticatedUserHelper authenticatedUserHelper;


    @Override
    @Transactional(readOnly = true)
    public MonthlySalaryDTO findById(Long id) {
        MonthlySalary monthlySalary = monthlySalaryRepository.findById(id).orElseThrow(() -> new MonthlySalaryNotFound(id));
        return monthlySalaryMapper.toDto(monthlySalary);
    }

    @Override
    @Transactional
    public MonthlySalaryDTO create(MonthlySalaryData monthlySalaryData) {
        MonthlySalary monthlySalary = new MonthlySalary();
        monthlySalary.setUser(userMapper.toEntity(userService.getAuthenticatedUser()));
        monthlySalary.setAmount(monthlySalaryData.getAmount());
        monthlySalary.setDayOfMonth(monthlySalaryData.getDayOfMonth());
        return monthlySalaryMapper.toDto(monthlySalaryRepository.save(monthlySalary));
    }

    @Override
    @Transactional
    public MonthlySalaryDTO update(Long id, MonthlySalaryData monthlySalaryData) {
        MonthlySalary monthlySalary = monthlySalaryRepository.findById(id).orElseThrow(() -> new MonthlySalaryNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(monthlySalary.getUser().getId());
        monthlySalary.setAmount(monthlySalaryData.getAmount());
        monthlySalary.setDayOfMonth(monthlySalaryData.getDayOfMonth());
        return monthlySalaryMapper.toDto(monthlySalaryRepository.save(monthlySalary));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MonthlySalary monthlySalary = monthlySalaryRepository.findById(id).orElseThrow(() -> new MonthlySalaryNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(monthlySalary.getUser().getId());
        monthlySalaryRepository.deleteById(monthlySalary.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public MonthlySalaryDTO findMonthlySalaryForUser(Long userId) {
        //todo: Fix this part
        //authenticatedUserHelper.checkAuthenticatedUser(userId);
        MonthlySalary monthlySalary = monthlySalaryRepository.findByUser_Id(userId).orElseThrow(()
                -> new MonthlySalaryNotFound(String.format("Monthly salary not found for user id = [%s]", userId)));
        return monthlySalaryMapper.toDto(monthlySalary);
    }
}
