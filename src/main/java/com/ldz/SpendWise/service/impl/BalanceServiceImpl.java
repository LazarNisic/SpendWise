package com.ldz.SpendWise.service.impl;

import com.ldz.SpendWise.dto.BalanceDTO;
import com.ldz.SpendWise.exception.BalanceNotFound;
import com.ldz.SpendWise.mapper.BalanceMapper;
import com.ldz.SpendWise.mapper.UserMapper;
import com.ldz.SpendWise.model.Balance;
import com.ldz.SpendWise.repository.BalanceRepository;
import com.ldz.SpendWise.service.BalanceService;
import com.ldz.SpendWise.service.UserService;
import com.ldz.SpendWise.service.data.BalanceData;
import com.ldz.SpendWise.util.AuthenticatedUserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticatedUserHelper authenticatedUserHelper;

    @Override
    @Transactional(readOnly = true)
    public BalanceDTO findById(Long id) {
        Balance balance = balanceRepository.findById(id).orElseThrow(() -> new BalanceNotFound(id));
        return balanceMapper.toDto(balance);
    }

    @Override
    @Transactional
    public BalanceDTO create(BalanceData balanceData) {
        Balance balance = new Balance();
        balance.setUser(userMapper.toEntity(userService.getAuthenticatedUser()));
        balance.setAmount(balanceData.getAmount());
        balance.setTimestamp(LocalDateTime.now());
        return balanceMapper.toDto(balanceRepository.save(balance));
    }

    @Override
    @Transactional
    public BalanceDTO update(Long id, BalanceData balanceData) {
        Balance balance = balanceRepository.findById(id).orElseThrow(() -> new BalanceNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(balance.getUser().getId());
        balance.setAmount(balanceData.getAmount());
        balance.setTimestamp(LocalDateTime.now());
        return balanceMapper.toDto(balanceRepository.save(balance));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Balance balance = balanceRepository.findById(id).orElseThrow(() -> new BalanceNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(balance.getUser().getId());
        balanceRepository.deleteById(id);
    }
}
