package com.ldz.SpendWise.service.impl;

import com.ldz.SpendWise.dto.BalanceDTO;
import com.ldz.SpendWise.dto.ExtraIncomeDTO;
import com.ldz.SpendWise.exception.ExtraIncomeNotFound;
import com.ldz.SpendWise.mapper.ExtraIncomeMapper;
import com.ldz.SpendWise.mapper.UserMapper;
import com.ldz.SpendWise.model.ExtraIncome;
import com.ldz.SpendWise.repository.ExtraIncomeRepository;
import com.ldz.SpendWise.service.BalanceService;
import com.ldz.SpendWise.service.ExtraIncomeService;
import com.ldz.SpendWise.service.UserService;
import com.ldz.SpendWise.service.data.ExtraIncomeData;
import com.ldz.SpendWise.util.AuthenticatedUserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExtraIncomeServiceImpl implements ExtraIncomeService {
    private final ExtraIncomeRepository extraIncomeRepository;
    private final ExtraIncomeMapper extraIncomeMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthenticatedUserHelper authenticatedUserHelper;

    private final BalanceService balanceService;

    @Override
    @Transactional(readOnly = true)
    public ExtraIncomeDTO findById(Long id) {
        ExtraIncome extraIncome = extraIncomeRepository.findById(id).orElseThrow(() -> new ExtraIncomeNotFound(id));
        return extraIncomeMapper.toDto(extraIncome);
    }

    @Override
    @Transactional
    public ExtraIncomeDTO create(ExtraIncomeData extraIncomeData) {
        ExtraIncome extraIncome = new ExtraIncome();
        extraIncome.setUser(userMapper.toEntity(userService.getAuthenticatedUser()));
        updateBalanceForUser(extraIncomeData, extraIncome);
        extraIncome.setAmount(extraIncomeData.getAmount());
        extraIncome.setDate(extraIncomeData.getDate());
        extraIncome.setTimestamp(LocalDateTime.now());
        return extraIncomeMapper.toDto(extraIncomeRepository.save(extraIncome));
    }

    @Override
    @Transactional
    public ExtraIncomeDTO update(Long id, ExtraIncomeData extraIncomeData) {
        ExtraIncome extraIncome = extraIncomeRepository.findById(id).orElseThrow(() -> new ExtraIncomeNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(extraIncome.getUser().getId());
        updateBalanceForUser(extraIncomeData, extraIncome);
        extraIncome.setAmount(extraIncomeData.getAmount());
        extraIncome.setDate(extraIncomeData.getDate());
        extraIncome.setTimestamp(LocalDateTime.now());
        return extraIncomeMapper.toDto(extraIncomeRepository.save(extraIncome));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ExtraIncome extraIncome = extraIncomeRepository.findById(id).orElseThrow(() -> new ExtraIncomeNotFound(id));
        authenticatedUserHelper.checkAuthenticatedUser(extraIncome.getUser().getId());
        extraIncomeRepository.deleteById(extraIncome.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Double findTotalExtraIncomeForMonth(Long userId, Integer month, Integer year) {
        return extraIncomeRepository.findTotalExtraIncome(userId, LocalDateTime.of(year, month, 1, 0, 0, 0), LocalDateTime.of(year, month, 1, 0, 0, 0).plusMonths(1));
    }

    @Override
    @Transactional(readOnly = true)
    public Double findTotalExtraIncomeForInterval(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return extraIncomeRepository.findTotalExtraIncome(userId, startDate, endDate);
    }

    private void updateBalanceForUser(ExtraIncomeData extraIncomeData, ExtraIncome extraIncome) {
        BalanceDTO balance = balanceService.findBalanceForUser(extraIncome.getUser().getId());
        if (extraIncome.getAmount() != null) {
            balanceService.subtractBalanceAmount(balance.getId(), extraIncome.getAmount());
        }
        balanceService.addBalanceAmount(balance.getId(), extraIncomeData.getAmount());
    }
}
