package co.edu.sena.service.impl;

import co.edu.sena.domain.OrderPlaced;
import co.edu.sena.repository.OrderPlacedRepository;
import co.edu.sena.service.OrderPlacedService;
import co.edu.sena.service.dto.OrderPlacedDTO;
import co.edu.sena.service.mapper.OrderPlacedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderPlaced}.
 */
@Service
@Transactional
public class OrderPlacedServiceImpl implements OrderPlacedService {

    private final Logger log = LoggerFactory.getLogger(OrderPlacedServiceImpl.class);

    private final OrderPlacedRepository orderPlacedRepository;

    private final OrderPlacedMapper orderPlacedMapper;

    public OrderPlacedServiceImpl(OrderPlacedRepository orderPlacedRepository, OrderPlacedMapper orderPlacedMapper) {
        this.orderPlacedRepository = orderPlacedRepository;
        this.orderPlacedMapper = orderPlacedMapper;
    }

    @Override
    public OrderPlacedDTO save(OrderPlacedDTO orderPlacedDTO) {
        log.debug("Request to save OrderPlaced : {}", orderPlacedDTO);
        OrderPlaced orderPlaced = orderPlacedMapper.toEntity(orderPlacedDTO);
        orderPlaced = orderPlacedRepository.save(orderPlaced);
        return orderPlacedMapper.toDto(orderPlaced);
    }

    @Override
    public OrderPlacedDTO update(OrderPlacedDTO orderPlacedDTO) {
        log.debug("Request to save OrderPlaced : {}", orderPlacedDTO);
        OrderPlaced orderPlaced = orderPlacedMapper.toEntity(orderPlacedDTO);
        orderPlaced = orderPlacedRepository.save(orderPlaced);
        return orderPlacedMapper.toDto(orderPlaced);
    }

    @Override
    public Optional<OrderPlacedDTO> partialUpdate(OrderPlacedDTO orderPlacedDTO) {
        log.debug("Request to partially update OrderPlaced : {}", orderPlacedDTO);

        return orderPlacedRepository
            .findById(orderPlacedDTO.getId())
            .map(existingOrderPlaced -> {
                orderPlacedMapper.partialUpdate(existingOrderPlaced, orderPlacedDTO);

                return existingOrderPlaced;
            })
            .map(orderPlacedRepository::save)
            .map(orderPlacedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderPlacedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderPlaceds");
        return orderPlacedRepository.findAll(pageable).map(orderPlacedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderPlacedDTO> findOne(Long id) {
        log.debug("Request to get OrderPlaced : {}", id);
        return orderPlacedRepository.findById(id).map(orderPlacedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderPlaced : {}", id);
        orderPlacedRepository.deleteById(id);
    }
}
