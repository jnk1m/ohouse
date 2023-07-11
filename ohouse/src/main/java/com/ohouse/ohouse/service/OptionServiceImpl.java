package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Options;
import com.ohouse.ohouse.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
  private final OptionRepository optionRepository;

  @Override
  public Options getById(int optionId) {
    return optionRepository.findById(optionId).orElseThrow();
  }
}
