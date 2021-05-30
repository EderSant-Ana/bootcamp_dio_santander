package com.bootcamp.santander.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.santander.exceptions.BusinessException;
import com.bootcamp.santander.exceptions.NotFoundException;
import com.bootcamp.santander.mapper.StockMapper;
import com.bootcamp.santander.model.Stock;
import com.bootcamp.santander.model.dto.StockDTO;
import com.bootcamp.santander.repository.StockRepository;
import com.bootcamp.santander.util.MessageUtils;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private StockMapper mapper;

	@Transactional
	public StockDTO save(StockDTO dto) {
		Optional<Stock> optionalStock = stockRepository.findByNameAndDate(dto.getName(), dto.getDate());
		
		if(optionalStock.isPresent()) {
			throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
		}
		
		Stock stock = mapper.toEntity(dto);
		stockRepository.save(stock);
		return mapper.toDto(stock);
	}

	@Transactional
	public StockDTO update(@Valid StockDTO dto) {
		Optional<Stock> optionalStock = stockRepository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());
		if(optionalStock.isPresent()) {
			throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
		}
		Stock stock = mapper.toEntity(dto);
		//System.out.println(stock);
		stockRepository.save(stock);
		return mapper.toDto(stock);

	}

	@Transactional
	public List<StockDTO> findAll() {
		return mapper.toDto(stockRepository.findAll());
	}

	@Transactional
	public StockDTO findById(Long id) {		
		return stockRepository.findById(id).map(x -> mapper.toDto(x)).orElseThrow(NotFoundException::new);
		//or return stockRepository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
	}

	@Transactional
	public StockDTO delete(Long id) {
		StockDTO dto = this.findById(id);
		stockRepository.deleteById(dto.getId());
		return dto;
		}

	@Transactional
	public List<StockDTO> findByToday() {
		return stockRepository.findByToday(LocalDate.now()).map(x -> mapper.toDto(x)).orElseThrow(NotFoundException::new);
	}
	
	
}
	
	
	

