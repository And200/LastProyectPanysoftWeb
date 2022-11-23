package co.edu.sena.service.impl;

import co.edu.sena.domain.DetailOrder;
import co.edu.sena.repository.DetailOrderRepository;
import co.edu.sena.service.DetailOrderService;
import co.edu.sena.service.dto.DetailOrderDTO;
import co.edu.sena.service.mapper.DetailOrderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetailOrder}.
 */
@Service
@Transactional
public class DetailOrderServiceImpl implements DetailOrderService {

    private final Logger log = LoggerFactory.getLogger(DetailOrderServiceImpl.class);

    private final DetailOrderRepository detailOrderRepository;

    private final DetailOrderMapper detailOrderMapper;

    public DetailOrderServiceImpl(DetailOrderRepository detailOrderRepository, DetailOrderMapper detailOrderMapper) {
        this.detailOrderRepository = detailOrderRepository;
        this.detailOrderMapper = detailOrderMapper;
    }

    @Override
    public DetailOrderDTO save(DetailOrderDTO detailOrderDTO) {
        log.debug("Request to save DetailOrder : {}", detailOrderDTO);
        DetailOrder detailOrder = detailOrderMapper.toEntity(detailOrderDTO);
        detailOrder = detailOrderRepository.save(detailOrder);
        return detailOrderMapper.toDto(detailOrder);
    }

    @Override
    public DetailOrderDTO update(DetailOrderDTO detailOrderDTO) {
        log.debug("Request to save DetailOrder : {}", detailOrderDTO);
        DetailOrder detailOrder = detailOrderMapper.toEntity(detailOrderDTO);
        detailOrder = detailOrderRepository.save(detailOrder);
        return detailOrderMapper.toDto(detailOrder);
    }

    @Override
    public Optional<DetailOrderDTO> partialUpdate(DetailOrderDTO detailOrderDTO) {
        log.debug("Request to partially update DetailOrder : {}", detailOrderDTO);

        return detailOrderRepository
            .findById(detailOrderDTO.getId())
            .map(existingDetailOrder -> {
                detailOrderMapper.partialUpdate(existingDetailOrder, detailOrderDTO);

                return existingDetailOrder;
            })
            .map(detailOrderRepository::save)
            .map(detailOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetailOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailOrders");
        return detailOrderRepository.findAll(pageable).map(detailOrderMapper::toDto);
    }

    public Page<DetailOrderDTO> findAllWithEagerRelationships(Pageable pageable) {
        return detailOrderRepository.findAllWithEagerRelationships(pageable).map(detailOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetailOrderDTO> findOne(Long id) {
        log.debug("Request to get DetailOrder : {}", id);
        return detailOrderRepository.findOneWithEagerRelationships(id).map(detailOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailOrder : {}", id);
        detailOrderRepository.deleteById(id);
    }
}
